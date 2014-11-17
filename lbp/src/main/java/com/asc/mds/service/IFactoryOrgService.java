package com.asc.mds.service;

import com.asc.mds.root.model.FactoryOrgModel;
import com.asc.mds.root.xservice.IFactoryOrgXService;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:14:44
 */
public interface IFactoryOrgService extends IFactoryOrgXService {
	
	String add(FactoryOrgModel model);
	String update(FactoryOrgModel model);
	String audit(FactoryOrgModel model);
	String remove(String id);
	
	String qualityCheck(String id, Integer qualityCheck);
	
}