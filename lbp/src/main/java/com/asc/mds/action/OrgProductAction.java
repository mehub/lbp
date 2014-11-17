package com.asc.mds.action;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.OrgProduct;
import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.model.OrgProductModel;
import com.asc.mds.service.IOrgProductService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:22:08
 */
@Controller
public class OrgProductAction extends BaseAction implements ModelDriven<OrgProductModel> {
	
	private OrgProductModel model = new OrgProductModel();
	private List<OrgProduct> list;
	private List<OrgProductTemp> listTemp;
	private String table;
	
	@Autowired
	private IOrgProductService orgProductService;
	public void setOrgProductService(IOrgProductService orgProductService) {
		this.orgProductService = orgProductService;
	}
	
	public String list(){
		list = orgProductService.getSplitPage(start, limit, model);
		long total = orgProductService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "orgProduct");
		return "list";
	}
	
	public String listTemp(){
		listTemp = orgProductService.getTempSplitPage(start, limit, model);
		long total = orgProductService.getTempTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "orgProductTemp");
		return "listTemp";
	}
	
	//get
	public String get(){
		OrgProduct source = orgProductService.get(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		if (source.getIsDisable()!= null) {
			model.setIsDisable(source.getIsDisable().getCode());
		}
		if (source.getState() != null) {
			model.setState(source.getState().getCode());
		}
		model.setRelid(source.getId());
		return flag;
	}
	
	//getTemp
	public String getTemp(){
		OrgProductTemp source = orgProductService.getTemp(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		model.setIsDisable(source.getIsDisable().getCode());
		model.setAudiState(source.getAudiState().getCode());
		return flag;
	}
		
	//update
	public void update(){
		if(flag != null && "update".equals(flag)){
			String res = orgProductService.update(model, table);
			success(res);
		}else{
			String res = orgProductService.add(model);
			success(res);
		}
	}

	//audi
	public void audi(){
		String res = orgProductService.audit(model);
		success(res);
	}
	//qualityCheck
	public void qualityCheck() {
		String res = orgProductService.qualityCheck(model.getId(), model.getQualityCheck());
		success(res);
	}
	
	//remove
	public void remove(){
		String res = orgProductService.remove(model.getId());
		writeToPage(res);
	}
	
	public OrgProductModel getModel() {
		return model;
	}
	public List<OrgProduct> getList() {
		return list;
	}
	public void setList(List<OrgProduct> list) {
		this.list = list;
	}
	public List<OrgProductTemp> getListTemp() {
		return listTemp;
	}
	public void setListTemp(List<OrgProductTemp> listTemp) {
		this.listTemp = listTemp;
	}
	public void setModel(OrgProductModel model) {
		this.model = model;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	

}