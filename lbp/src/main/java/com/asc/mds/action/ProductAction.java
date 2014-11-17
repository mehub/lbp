package com.asc.mds.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.AutoCompleteUtils;
import com.asc.common.BaseAction;
import com.asc.common.SysConstant;
import com.asc.common.util.PagingToolbar;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.Product;
import com.asc.mds.root.bean.ProductTemp;
import com.asc.mds.root.model.ProductModel;
import com.asc.mds.service.IProductService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:21:52
 */
@Controller
public class ProductAction extends BaseAction implements ModelDriven<ProductModel> {
	
	private ProductModel model = new ProductModel();
	private List<Product> list;
	private List<ProductTemp> listTemp;
	private String table;
	
	@Autowired
	private IProductService productService;
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	//list
	public String list(){
		list = productService.getSplitPage(start, limit, model);
		long total = productService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "product");
		return "list";
	}
	
	//listTemp
	public String listTemp(){
		listTemp = productService.getTempSplitPage(start, limit, model);
		long total = productService.getTempTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "productTemp");
		return "listTemp";
	}
	
	//get
	public String get(){
		Product source = productService.get(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		model.setIsDisable(source.getIsDisable().getCode());
		model.setState(source.getState().getCode());
		model.setRelid(source.getId());
		return flag;
	}
	
	//getTemp
	public String getTemp(){
		ProductTemp source = productService.getTemp(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		model.setIsDisable(source.getIsDisable().getCode());
		model.setAudiState(source.getAudiState().getCode());
		model.setAudiPass(source.getAudiState().getCode());
		return flag;
	}
		
	//addOrUpdate
	public void addOrUpdate(){
		String res = "";
		if(flag != null && "update".equals(flag)){
			res = productService.update(model, table);
		}else{
			res = productService.add(model);
		}
		if(res == SysConstant.EXIST)
			errorinfo("{\"res\":false,\"info\":\""+res+"\"}");
		else
			success(res);
	}

	//audit
	public void audit(){
		String res = productService.audit(model);
		success(res);
	}

	//remove
	public void remove(){
		String res = productService.remove(model.getId());
		writeToPage(res);
	}
	
	//autoComplete
	public void autoComplete() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setCommonname(query);
			list = productService.getSplitPage(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(list, "acName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	
	public ProductModel getModel() {
		return model;
	}
	public List<Product> getList() {
		return list;
	}
	public List<ProductTemp> getListTemp() {
		return listTemp;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	
}