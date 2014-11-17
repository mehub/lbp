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
import com.asc.mds.root.bean.FranchiseOrg;
import com.asc.mds.root.bean.FranchiseOrgTemp;
import com.asc.mds.root.model.FranchiseOrgModel;
import com.asc.mds.service.IFranchiseOrgService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:18:56
 */
@Controller
public class FranchiseOrgAction extends BaseAction implements ModelDriven<FranchiseOrgModel> {

	private FranchiseOrgModel model = new FranchiseOrgModel();
	private List<FranchiseOrg> list;
	private List<FranchiseOrgTemp> listTemp;
	
	@Autowired
	private IFranchiseOrgService franchiseOrgService;
	public void setFranchiseOrgService(IFranchiseOrgService franchiseOrgService) {
		this.franchiseOrgService = franchiseOrgService;
	}
	
	public String list(){
		list = franchiseOrgService.getSplitPage(start, limit, model);
		long total = franchiseOrgService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "franchiseOrg");
		return "list";
	}
	
	public String listTemp(){
		listTemp = franchiseOrgService.getTempSplitPage(start, limit, model);
		long total = franchiseOrgService.getTempTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "franchiseOrgTemp");
		return "listTemp";
	}
	//get
	public String get(){
		FranchiseOrg source = franchiseOrgService.get(model.getId());
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
		FranchiseOrgTemp source = franchiseOrgService.getTemp(model.getId());
		BeanUtils.copyProperties(source, model, new String[]{"isDisable", "state", "audiState"});
		model.setIsDisable(source.getIsDisable().getCode());
		model.setAudiState(source.getAudiState().getCode());
		return flag;
	}
		
	//update
	public void update(){
		String res ; 
		if(flag != null && "update".equals(flag)){
			res = franchiseOrgService.update(model);
		}else{
			res = franchiseOrgService.add(model);
		}
		
		if(res == SysConstant.EXIST)
			errorinfo("{\"res\":false,\"info\":\""+res+"\"}");
		else
			success(res);
	}

	//audi
	public void audi(){
		String res = franchiseOrgService.audit(model);
		
		if(res == SysConstant.EXIST)
			errorinfo("{\"res\":false,\"info\":\""+res+"\"}");
		else
			success(res);
	}
	//qualityCheck
	public void qualityCheck() {
		String res = franchiseOrgService.qualityCheck(model.getId(), model.getQualityCheck());
		success(res);
	}
	
	//remove
	public void remove(){
		String res = franchiseOrgService.remove(model.getId());
		writeToPage(res);
	}
	
	public void autoComplete() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setOrgName(query);
			List<FranchiseOrg> list=franchiseOrgService.search(0, 10, model);
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
	
	public FranchiseOrgModel getModel() {
		return model;
	}
	public List<FranchiseOrg> getList() {
		return list;
	}
	public void setList(List<FranchiseOrg> list) {
		this.list = list;
	}
	public List<FranchiseOrgTemp> getListTemp() {
		return listTemp;
	}
	public void setListTemp(List<FranchiseOrgTemp> listTemp) {
		this.listTemp = listTemp;
	}
	public void setModel(FranchiseOrgModel model) {
		this.model = model;
	}
}