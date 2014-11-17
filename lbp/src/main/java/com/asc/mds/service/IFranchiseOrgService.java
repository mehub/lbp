package com.asc.mds.service;

import com.asc.mds.root.model.FranchiseOrgModel;
import com.asc.mds.root.xservice.IFranchiseOrgXService;


/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:14:58
 */
public interface IFranchiseOrgService extends IFranchiseOrgXService {

	String add(FranchiseOrgModel model);
	String update(FranchiseOrgModel model);
	String audit(FranchiseOrgModel model);
	String remove(String id);
	
	String qualityCheck(String id, Integer qualityCheck) ;
	
}