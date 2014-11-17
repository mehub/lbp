package com.asc.mds.root.xservice;

import java.util.List;

import javax.jws.WebService;

import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.iservice.IOrgProductIService;
import com.asc.mds.root.model.OrgProductModel;
import com.asc.mds.root.state.AudiState;

/**
 * 
 * 类描述 .对外接口服务
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-01-05 下午3:08:30
 */
@WebService
public interface IOrgProductXService extends IOrgProductIService {
	
	/** batch audit */
	String batchAudit(String... id);
	
	String autoAuditForBench(OrgProductModel model);
	
	int xupdate(String[] fields, Object[] fieldValues, String[] conditions, Object[] values);
	
	/**
	 * 免检标记
	 */
	boolean porQualityCheck(String relationId);
	
	/**
	 * 质检不通过关系作废
	 */
	boolean delRelation(String relationId);
	
	boolean xbatchAudit( List<OrgProductModel> temps, AudiState audiState);
	
	String xadd(OrgProductModel model);
	
	String xupdateTemp(OrgProductTemp temp);
	
}