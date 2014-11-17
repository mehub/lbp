package com.asc.mds.service;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.mds.root.bean.OrgType;
import com.asc.mds.root.model.OrgTypeModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:39
 */
@Service
public class OrgTypeService implements IOrgTypeService{
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	public List<OrgType> getSplitPage(int start, int limit, OrgTypeModel model) {
		return dao.getSplitPage("select t from OrgType t " + getCondition(model), start, limit, new Object[0]);
	}

	public Long getTotal(OrgTypeModel model) {
		return dao.getTotal("select count(t.id) from OrgType t " + getCondition(model), new Object[0]);
	}
	  
	private String getCondition(OrgTypeModel model) {
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1=1 ");
		
		if (model != null) {
			
		}
		
		return sb.toString();
	}
	
	public void upload(File file, String postfix, String factoryId) {
		
	}

	@Override
	public void update(OrgType orgType) {
		if(StringUtils.isBlank(orgType.getId())){
			dao.save(orgType);
		}else{
			dao.update(orgType);
		}
		
	}

	@Override
	public OrgType get(String id) {
		return dao.get(OrgType.class, id);
	}

	@Override
	public List<OrgType> getAll() {
		return dao.findAll(OrgType.class);
	}
	
}