package com.asc.mds.root.xservice;

import javax.jws.WebService;

import com.asc.mds.root.iservice.IProductIService;
import com.asc.mds.root.model.ProductModel;

/**
 * 
 * 类描述 .对外接口服务
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-01-05 下午3:08:30
 */
@WebService
public interface IProductXService extends IProductIService {
	
	/** batch audit */
	String batchAudit(String... id);
	
	String autoAuditForBench(ProductModel model);
}