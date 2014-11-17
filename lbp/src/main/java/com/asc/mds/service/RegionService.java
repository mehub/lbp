package com.asc.mds.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.SysConstant;
import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.EntitySolrSetting;
import com.asc.mds.root.bean.Region;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.mds.root.iservice.RegionIService;
import com.asc.mds.root.model.RegionModel;
import com.asc.mds.root.xservice.IRegionXService;
import com.asc.mds.search.area.AreaExtracterUtil;
import com.asc.mds.search.area.bean.Area;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:51
 */
@Service
public class RegionService extends RegionIService implements IRegionService, IRegionXService {
	
	public RegionService(){}
	public RegionService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}

	//saveOrUpdate
	public String saveOrUpdate(RegionModel model){
		Region dest = new Region();
		BeanUtils.copyProperties(model, dest);
		if(StringUtils.isEmpty(model.getId())){
			dest.setId(null);
		}
		dest.setLastmodifyTime(new Date());
		dest.setModifyer(SecurityManager.getCurrentUser().getUsername());
		dao.saveOrUpdate(dest);
		
		SolrUpdater.searcherUpdate(EntitySolrSetting.Region);//solr searcher update
		
		return SysConstant.SUCCESS;
	}
	
	//remove
	public String remove(String id){
		dao.executeSQL("delete from MD_REGION where PARENT_ID=?", new Object[]{id});
		dao.executeSQL("delete from MD_REGION where id=?", new Object[]{id});
		
		SolrUpdater.searcherUpdate(EntitySolrSetting.Region);//solr searcher update
		
		return SysConstant.SUCCESS;
	}
	
	/*-----------------------------------XService Implement--------------------------------------*/
	@Override
	public Region getByName(String name) {
		String sql="from Region where regionName=?";
		return dao.getUnique(sql, new Object[]{name});
	}
	
	public String extraterstr(String name){
		return AreaExtracterUtil.extraterstr(name);
	}
	
	public Area extrater(String name){
		return AreaExtracterUtil.extrater(name);
	}
	
}