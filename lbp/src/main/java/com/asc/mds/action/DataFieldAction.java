package com.asc.mds.action;

import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.mds.root.model.DataFieldModel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 类描述 .
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:55:05
 */
@Controller
public class DataFieldAction extends BaseAction implements ModelDriven<DataFieldModel> {
	
	private DataFieldModel model = new DataFieldModel();
	
	public String list(){
		return null;
	}

	public DataFieldModel getModel() {
		return model;
	}
}