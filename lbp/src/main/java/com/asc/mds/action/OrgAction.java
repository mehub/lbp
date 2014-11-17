package com.asc.mds.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.AutoCompleteUtils;
import com.asc.common.BaseAction;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.Franchise;
import com.asc.mds.root.bean.Org;
import com.asc.mds.root.bean.OrgTemp;
import com.asc.mds.root.bean.OrgType;
import com.asc.mds.root.model.OrgModel;
import com.asc.mds.service.IOrgService;
import com.asc.mds.service.IOrgTypeService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:19:41
 */
@Controller
public class OrgAction extends BaseAction implements ModelDriven<OrgModel>,Preparable{

	private List<Org> orgList;
	private List<OrgTemp> orgTempList;
	private List<Franchise> franchiseList;
	private OrgModel model = new OrgModel();
	private String id;
	private List<OrgType> types=new ArrayList<OrgType>();
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IOrgTypeService orgTypeService;
	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}

	public String list(){
		orgList=orgService.getSplitPage(start, limit, model);
		Long total=orgService.getTotal(model);
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, total.intValue(), "file");
		return "success";
	}
	
	public String listTemp(){
		orgTempList=orgService.getTempSplitPage(start, limit, model);
		Long total=orgService.getTempTotal(model);
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, total.intValue(), "file");
		return "temp";
	}
	
	public String listFran(){
		franchiseList=orgService.getFranchiseSplitPage(start, limit, model);
		Long total=orgService.getFranchiseTotal(model);
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, total.intValue(), "file");
		return "franchise";
	}

	public String save() throws Exception{
		try {
			orgService.update(model,true);
			msg="true";
		} catch (Exception e) {
			msg="false";
			e.printStackTrace();
		}
		model=new OrgModel();
		return list();
	}
	
	public String saveTemp() throws Exception{
		try {
			orgService.update(model,false);
			msg="true";
		} catch (Exception e) {
			msg="false";
			e.printStackTrace();
		}
		model=new OrgModel();
		return listTemp();
	}
	
	public String input(){
		String detail=this.getRequest().getParameter("detail");
		if(StringUtils.isBlank(detail)){
			return "input";
		}else{
			return "detail";
		}
	}
	public String inputTemp(){
		String detail=this.getRequest().getParameter("detail");
		if(StringUtils.isBlank(detail)){
			return "temp-input";
		}else{
			return "detail";
		}
	}
	public String audit(){
		String state=this.getRequest().getParameter("state");
		if(!StringUtils.isBlank(state)){
			OrgTemp orgTemp=orgService.getTemp(id);
			orgService.audit(orgTemp, Integer.parseInt(state));
		}
		return listTemp();
	}
	
	public String delete(){
		try {
			String idStr=this.getRequest().getParameter("ids");
			String[] ids=idStr.split(",");
			orgService.remove(ids);
			msg="true";
		} catch (Exception e) {
			msg="false";
			e.printStackTrace();
		}
		return list();
	}
	
	public void prepareList() throws Exception {
		types=orgTypeService.getAll();
	}
	public void prepareListTemp() throws Exception {
		prepareList();
	}
	public void prepareInput() throws Exception {
		types=orgTypeService.getAll();
		if (!StringUtils.isBlank(id)) {
			Org org=orgService.getOrg(id);
			BeanUtils.copyProperties(org,model,new String[] { "state","isDisable"});
			model.setIsDisable(org.getIsDisable().getCode());
		} else {
			model = new OrgModel();
		}
	}
	
	public void prepareInputTemp() throws Exception {
		types=orgTypeService.getAll();
		if (!StringUtils.isBlank(id)) {
			OrgTemp org=orgService.getTemp(id);
			BeanUtils.copyProperties(org,model,new String[] { "audiState","isDisable" });
			model.setIsDisable(org.getIsDisable().getCode());
		} else {
			model = new OrgModel();
		}
	}
	
	public void autoCompleteMore() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setOrgName(query);
			orgList=orgService.search(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(orgList, "acName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	
	public void autoComplete() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setOrgName(query);
			orgList=orgService.search(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(orgList, "orgName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	
	public void autoCompleteTemp() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setOrgName(query);
			orgTempList=orgService.getTempSplitPage(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(orgTempList, "orgName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	
	public void autoCompleteFran() throws IOException{
		if(StringUtils.isEmpty(query)) return;
		try {
			model.setOrgName(query);
			franchiseList=orgService.getFranchiseSplitPage(0, 10, model);
			String json = AutoCompleteUtils.autoCompleteObjectWarp(franchiseList, "orgName", "id");
			writeToPage(json);
		} catch (Exception e) {
			writeToPage("");
			e.printStackTrace();
		}
		response.getWriter().flush();
		response.getWriter().close();
		return;
	}
	
	public void checkName() throws IOException{
		if(StringUtils.isBlank(model.getOrgName())) return;
		try {
			boolean rdt=true;
			if(StringUtils.isBlank(model.getId())){
				rdt=orgService.nameCheck(model.getOrgName());
			}else{
				String oldName=this.request.getParameter("oldName");
				if(oldName.equals(model.getOrgName())){
					rdt=true;
				}else{
					rdt=orgService.nameCheck(model.getOrgName());
				}
			}
			
//			JSONObject json=new JSONObject();
//			json.put("data", rdt);
			writeToPage("");//TODO
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	public void checkCode() throws IOException{
		if(StringUtils.isBlank(model.getCode())) return;
		try {
			boolean rdt=true;
			if(StringUtils.isBlank(model.getId())){
				rdt=orgService.codeCheck(model.getCode());
			}else{
				String oldCode=this.request.getParameter("oldCode");
				if(oldCode.equals(model.getCode())){
					rdt=true;
				}else{
					rdt=orgService.codeCheck(model.getCode());
				}
			}
			
//			JSONObject json=new JSONObject();
//			json.put("data", rdt);
			writeToPage("");//TODO
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	
	public OrgModel getModel() {
		return model;
	}

	public List<Org> getOrgList() {
		return orgList;
	}
	public List<OrgTemp> getOrgTempList() {
		return orgTempList;
	}

	public List<Franchise> getFranchiseList() {
		return franchiseList;
	}

	@Override
	public void prepare() throws Exception {}

	public void setId(String id) {
		this.id = id;
	}

	public List<OrgType> getTypes() {
		return types;
	}

	public void setTypes(List<OrgType> types) {
		this.types = types;
	}
	
	
}