package com.asc.mds.root.iservice;

import java.util.List;

import javax.jws.WebMethod;

import com.asc.mds.root.bean.OrgProduct;
import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.model.OrgProductModel;


/**
 * 
 * 类描述 . 提供本地、对外的查询接口
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-4-27 上午11:19:17
 */
public interface IOrgProductIService {
	
	long getTotal(OrgProductModel model);
	List<OrgProduct> getSplitPage(int start, int limit, OrgProductModel model);
	
	long getTempTotal(OrgProductModel model);
	List<OrgProductTemp> getTempSplitPage(int start, int limit, OrgProductModel model);

	OrgProduct get(String id);
	
	@WebMethod
	OrgProductTemp getTemp(String id);
	
	OrgProduct getRelationById(String ownerId, String ascProductId);
	OrgProduct getRelationByCode(String ownerId, String code, String dataOwner);
	
}