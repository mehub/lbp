package com.asc.mds.root.iservice;

import java.util.List;

import com.asc.mds.root.bean.FranchiseOrg;
import com.asc.mds.root.bean.FranchiseOrgTemp;
import com.asc.mds.root.model.FranchiseOrgModel;



/**
 * 
 * 类描述 . 提供本地、对外的查询接口
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-4-27 上午11:19:54
 */
public interface IFranchiseOrgIService {
	
	long getTotal(FranchiseOrgModel model);
	List<FranchiseOrg> getSplitPage(int start, int limit, FranchiseOrgModel model);
	
	long getTempTotal(FranchiseOrgModel model);
	List<FranchiseOrgTemp> getTempSplitPage(int start, int limit, FranchiseOrgModel model);
	
	FranchiseOrg get(String id);
	FranchiseOrgTemp getTemp(String id);
	
	FranchiseOrg getRelationUseName(String ownerOrgId, String name);
	
}