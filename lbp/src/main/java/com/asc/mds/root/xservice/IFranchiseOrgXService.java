package com.asc.mds.root.xservice;

import java.util.List;

import javax.jws.WebService;

import com.asc.mds.root.bean.FranchiseOrg;
import com.asc.mds.root.iservice.IFranchiseOrgIService;
import com.asc.mds.root.model.FranchiseOrgModel;


/**
 * 
 * 类描述 .对外接口服务
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-01-05 下午3:08:30
 */
@WebService
public interface IFranchiseOrgXService extends IFranchiseOrgIService {
	
	/** batch audit */
	String batchAudit(String... id);
	
	/**
	 */
	String autoAuditForBench(FranchiseOrgModel model);
	
	/**
	 * 免检标记
	 */
	boolean porQualityCheck(String franchiseOrgId);
	
	/**
	 * 质检不通过关系作废
	 */
	boolean delFranchiseRelation(String franchiseOrgId);
	
	String xadd(FranchiseOrgModel model);
	
	List<FranchiseOrg> search(int start, int limit, FranchiseOrgModel model);
	
}