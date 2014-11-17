package com.asc.mds.service;

import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.iservice.IOrgProductIService;
import com.asc.mds.root.model.OrgProductModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:15:05
 */
public interface IOrgProductService extends IOrgProductIService {
	
	String add(OrgProductModel model);
	String update(OrgProductModel model, String table);
	String updateTemp(OrgProductTemp temp);
	
	String audit(OrgProductModel model);
	String remove(String id);
	
	String qualityCheck(String id,Integer qualityCheck);
	
}