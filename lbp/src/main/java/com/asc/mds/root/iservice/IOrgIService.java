package com.asc.mds.root.iservice;

import java.util.List;

import javax.jws.WebMethod;

import com.asc.mds.root.bean.Franchise;
import com.asc.mds.root.bean.Org;
import com.asc.mds.root.bean.OrgTemp;
import com.asc.mds.root.isearch.document.RegionDoc;
import com.asc.mds.root.model.OrgModel;



/**
 * 
 * 类描述 . 提供本地、对外的查询接口
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-4-27 上午11:19:37
 */
public interface IOrgIService {
	
	@WebMethod
	Long getTotal(OrgModel model);
	@WebMethod
	List<Org> getSplitPage(int start, int limit, OrgModel model);
	
	Long getTempTotal(OrgModel model);
	List<OrgTemp> getTempSplitPage(int start, int limit, OrgModel model);
	
	Long getFranchiseTotal(OrgModel model);
	List<Franchise> getFranchiseSplitPage(int start, int limit, OrgModel model);
	
	@WebMethod
	Org getOrg(String id);
	OrgTemp getTemp(String id);
			
	boolean nameCheck(String orgName);
	boolean codeCheck(String orgCode);
	
	//查询和厂商有贸易关系的经销商
	Long getFranFactotyTotal(String factoryId,OrgModel model);
	List<Org> getFranFactotySplitPage(int start, int limit, String factoryId,OrgModel model);
	
	//查询和厂商有贸易关系的经销商
	List<Org> getSplitPageBySql(int start, int limit, OrgModel model,String ownerId);
	long getTotalBySql(OrgModel model,String ownerId);
	
	//查询和经销商有贸易关系的厂商
	List<Org> getSplitPageBySqlFran(int start, int limit, OrgModel model,String franId);
	long getTotalBySqlFran(OrgModel model, String franId);
	
	RegionDoc getRegionDoc(String orgId);
	
}