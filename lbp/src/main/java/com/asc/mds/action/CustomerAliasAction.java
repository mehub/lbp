package com.asc.mds.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.AutoCompleteUtils;
import com.asc.common.BaseAction;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.CustomerAlias;
import com.asc.mds.service.ICustomerAliasService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 . 客户企业别称表
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-27 上午9:55:47
 */
@Controller
public class CustomerAliasAction extends BaseAction implements ModelDriven<CustomerAlias> {
	
	private CustomerAlias model = new CustomerAlias();
	private List<CustomerAlias> list;
	
	@Autowired
	private ICustomerAliasService customerAliasService;
	public void setCustomerAliasService(ICustomerAliasService customerAliasService) {
		this.customerAliasService = customerAliasService;
	}

	public String list(){
		list = customerAliasService.getSplitPage(start, limit, model);
		long total = customerAliasService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "customerAlias");
		return "list";
	}
	
	public CustomerAlias getModel() {
		return model;
	}
	public List<CustomerAlias> getList() {
		return list;
	}
	
	public void autoComplete() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setCtName(query);
			List<CustomerAlias> alias = customerAliasService.search(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(alias, "ctName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	//templateDownload
	private String inputPath;
	private String templateFileName;
	public String templateDownload(){
		this.templateFileName = "TEMPLATE_CUSTOMER_ALIAS.xlsx";
		this.inputPath = "templates/TEMPLATE_CUSTOMER_ALIAS.xlsx";
		return "templateDownload";
	}
	public InputStream getTemplates() throws IOException{
		return this.getClass().getClassLoader().getResourceAsStream(inputPath);
	}
	public String getInputPath() {
		return inputPath;
	}
	public String getTemplateFileName() {
		return templateFileName;
	}
}