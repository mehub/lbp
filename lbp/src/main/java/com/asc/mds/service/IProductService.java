package com.asc.mds.service;

import com.asc.mds.root.bean.ProductTemp;
import com.asc.mds.root.iservice.IProductIService;
import com.asc.mds.root.model.ProductModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:15:31
 */
public interface IProductService extends IProductIService {

	String add(ProductModel model);
	String update(ProductModel model, String table);
	
	String audit(ProductModel model);
	String remove(String id);
	
	boolean isExist(ProductTemp dest);
	
}