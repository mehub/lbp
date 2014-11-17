package com.asc.mds.root.xservice;

import java.util.List;

import javax.jws.WebService;

import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.FactoryOrgHis;
import com.asc.mds.root.iservice.IFactoryOrgIService;
import com.asc.mds.root.model.FactoryOrgModel;

/**
 * 
 * 类描述 .对外接口服务
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-01-05 下午3:08:30
 */
@WebService
public interface IFactoryOrgXService extends IFactoryOrgIService {

	/** batch audit */
	String batchAudit(String... id);
	
	int xupdate(String[] fields, Object[] fieldValues, String[] conditions, Object[] values);
	/**
	 */
	String autoAuditForBench(FactoryOrgModel factoryOrgModel);
	
	/**
	 */
	String autoAuditHisForBench(FactoryOrgHis factoryOrgHis);
	/**
	 */
	boolean porQualityCheck(String relationId);
	
	/**
	 * 质检不通过关系作废
	 */
	boolean delRelation(String relationId);
	
	void xSave(FactoryOrg org);
	
	List<FactoryOrg> search(int start, int limit, FactoryOrgModel model);
	
}