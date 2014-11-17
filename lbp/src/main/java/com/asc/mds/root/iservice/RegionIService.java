package com.asc.mds.root.iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.Region;
import com.asc.mds.root.model.RegionModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-12 下午5:04:20
 */
@Service
public class RegionIService implements IRegionIService {
	
	public RegionIService(){}
	public RegionIService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}

	public List<Region> getSplitPage(int start, int limit, RegionModel model) {
		return dao.getSplitPage("select t from Region t " + getCondition(model) + " order by t.id ", start, limit, new Object[0]);
	}

	public long getTotal(RegionModel model) {
		return dao.getTotal("select count(t.id) from Region t " + getCondition(model), new Object[0]);
	}
	
	//get
	public Region get(String id){
		if(!StringUtils.isEmpty(id)){
			return dao.get(Region.class, id);
		}
		return null;
	}
	
	private String getCondition(RegionModel model) {
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		if (model != null) {
			if(!StringUtils.isEmpty(model.getParentId())){
				hql.append(" and t.parentId like '%" + model.getParentId() + "%'");
			}
			if(!StringUtils.isEmpty(model.getRegionName())){
				hql.append(" and t.regionName like '%" + model.getRegionName() + "%'");
			}
			if(!StringUtils.isEmpty(model.getCode())){
				hql.append(" and t.code like '%" + model.getCode() + "%'");
			}
		}
		return hql.toString();
	}
	
	//list by parent id
	public List<Region> listByParentId(String parentId){
		List<Region> list = null;
		if(!StringUtils.isEmpty(parentId)){
			list = dao.findByPropertiesAndOrder(Region.class,
					new String[] { "parentId" }, new Object[] { parentId },
					new String[] { "id" }, new boolean[] { true });
		}
		return list;
	}
	
}