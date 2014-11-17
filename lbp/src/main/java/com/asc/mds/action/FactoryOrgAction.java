package com.asc.mds.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.AutoCompleteUtils;
import com.asc.common.BaseAction;
import com.asc.common.SysConstant;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.FactoryOrgTemp;
import com.asc.mds.root.model.FactoryOrgModel;
import com.asc.mds.service.IFactoryOrgService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:19:03
 */
@Controller
public class FactoryOrgAction extends BaseAction implements ModelDriven<FactoryOrgModel> {
	
	private FactoryOrgModel model = new FactoryOrgModel();
	private List<FactoryOrg> list;
	private List<FactoryOrgTemp> listTemp;
	
	@Autowired
	private IFactoryOrgService factoryOrgService;
	public void setFactoryOrgService(IFactoryOrgService factoryOrgService) {
		this.factoryOrgService = factoryOrgService;
	}
	
	public String list(){
		list = factoryOrgService.getSplitPage(start, limit, model);
		long total = factoryOrgService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "factoryOrg");
		return "list";
	}
	
	public String listTemp(){
		listTemp = factoryOrgService.getTempSplitPage(start, limit, model);
		long total = factoryOrgService.getTempTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "factoryOrgTemp");
		return "listTemp";
	}
	//get
	public String get(){
		FactoryOrg source = factoryOrgService.get(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		if (source.getIsDisable()!= null) {
			model.setIsDisable(source.getIsDisable().getCode());
		}
		if (source.getState() != null) {
			model.setState(source.getState().getCode());
		}
		return flag;
	}
	
	//getTemp
	public String getTemp(){
		FactoryOrgTemp source = factoryOrgService.getTemp(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		model.setIsDisable(source.getIsDisable().getCode());
		model.setAudiState(source.getAudiState().getCode());
		return flag;
	}
		
	//update
	public void update(){
		String res ;
		if(flag != null && "update".equals(flag)){
			res = factoryOrgService.update(model);
		}else{
			res = factoryOrgService.add(model);
		}
		if(res == SysConstant.EXIST)
			errorinfo("{\"res\":false,\"info\":\""+res+"\"}");
		else
			success(res);
	}

	//audi
	public void audi(){
		String res = factoryOrgService.audit(model);
		
		if(res == SysConstant.EXIST)
			errorinfo("{\"res\":false,\"info\":\""+res+"\"}");
		else
			success(res);
	}
	//qualityCheck
	public void qualityCheck() {
		String res = factoryOrgService.qualityCheck(model.getId(), model.getQualityCheck());
		success(res);
	}
	
	//remove
	public void remove(){
		String res = factoryOrgService.remove(model.getId());
		writeToPage(res);
	}
	
	public void autoComplete() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setOrgName(query);
			List<FactoryOrg> list=factoryOrgService.search(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(list, "orgName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	
	public FactoryOrgModel getModel() {
		return model;
	}
	public List<FactoryOrg> getList() {
		return list;
	}
	public void setList(List<FactoryOrg> list) {
		this.list = list;
	}
	public List<FactoryOrgTemp> getListTemp() {
		return listTemp;
	}
	public void setListTemp(List<FactoryOrgTemp> listTemp) {
		this.listTemp = listTemp;
	}
	public void setModel(FactoryOrgModel model) {
		this.model = model;
	}
}