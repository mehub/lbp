package com.asc.mds.service;

import java.util.List;

import com.asc.mds.root.bean.OrgType;
import com.asc.mds.root.model.OrgTypeModel;


/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:15:20
 */
public interface IOrgTypeService {
	
	Long getTotal(OrgTypeModel model);
	
	List<OrgType> getSplitPage(int start, int limit, OrgTypeModel model);
	
	OrgType get(String id);
	
	List<OrgType> getAll();
	
	void update(OrgType orgType);
	
}