package com.asc.mds.root.xservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.asc.mds.root.bean.Org;
import com.asc.mds.root.iservice.IOrgIService;
import com.asc.mds.root.model.OrgModel;


/**
 * 
 * 类描述 .对外接口服务
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-01-05 下午3:08:30
 */
@WebService
public interface IOrgXService extends IOrgIService {
	
	/** batch audit */
	String batchAudit(String... id);

	int xupdate(String[] fields, Object[] fieldValues, String[] conditions, Object[] values);
	
	String autoAuditForBench(OrgModel model);
	
	@WebMethod
	List<Org> search(int start, int limit, OrgModel model);
	
}