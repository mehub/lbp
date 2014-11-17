package com.asc.mds.root.iservice;

import java.util.List;

import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.FactoryOrgTemp;
import com.asc.mds.root.model.FactoryOrgModel;


/**
 * 
 * 类描述 . 提供本地、对外的查询接口
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-4-27 上午11:20:07
 */
public interface IFactoryOrgIService {
	
	long getTotal(FactoryOrgModel model);
	List<FactoryOrg> getSplitPage(int start, int limit, FactoryOrgModel model);
	
	long getTotalByType(FactoryOrgModel model,String type);
	List<FactoryOrg> getSplitPageByType(int start, int limit, FactoryOrgModel model,String type);
	
	long getTempTotal(FactoryOrgModel model);
	List<FactoryOrgTemp> getTempSplitPage(int start, int limit, FactoryOrgModel model);
	
	FactoryOrg get(String id);
	FactoryOrgTemp getTemp(String id);
	
	FactoryOrg getRelation(String ownerId, String ascOrgId);
	
	FactoryOrg getRelationByCodeAndame(String ownerId, String relationCode, String relationName);
	
}