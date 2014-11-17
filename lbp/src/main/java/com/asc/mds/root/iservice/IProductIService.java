package com.asc.mds.root.iservice;

import java.util.List;

import javax.jws.WebMethod;

import com.asc.mds.root.bean.Product;
import com.asc.mds.root.bean.ProductTemp;
import com.asc.mds.root.model.ProductModel;


/**
 * 
 * 类描述 . 提供本地、对外的查询接口
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-4-27 上午11:18:41
 */
public interface IProductIService {

	long getTotal(ProductModel model);
	List<Product> getSplitPage(int start, int limit, ProductModel model);
	
	long getTempTotal(ProductModel model);
	List<ProductTemp> getTempSplitPage(int start, int limit, ProductModel model);
	
	ProductTemp getTemp(String id);
	
	@WebMethod
	Product get(String id);
}