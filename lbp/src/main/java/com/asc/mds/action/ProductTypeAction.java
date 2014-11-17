package com.asc.mds.action;

import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.mds.root.model.ProductTypeModel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:21:33
 */
@Controller
public class ProductTypeAction extends BaseAction implements ModelDriven<ProductTypeModel> {
	
	private ProductTypeModel model = new ProductTypeModel();
	
	public String list(){
		return null;
	}

	public ProductTypeModel getModel() {
		return model;
	}
}