package com.asc.mds.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.OrgType;
import com.asc.mds.root.model.OrgTypeModel;
import com.asc.mds.service.IOrgTypeService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:21:58
 */
@Controller
public class OrgTypeAction extends BaseAction implements ModelDriven<OrgTypeModel>,Preparable{
	
	private OrgTypeModel model = new OrgTypeModel();
	private List<OrgType> orgTypeList;
	@Autowired
	private IOrgTypeService orgTypeService;
	private String id;
	
	public String list(){
		orgTypeList=orgTypeService.getSplitPage(start, limit, model);
		Long total=orgTypeService.getTotal(model);
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, total.intValue(), "orgType");
		return "success";
	}
	
	public void save() throws Exception{
		try {
			OrgType orgType=new OrgType();
			BeanUtils.copyProperties(model, orgType);
			orgTypeService.update(orgType);
			success("1");
		} catch (Exception e) {
			error("0");
			e.printStackTrace();
		}
	}

	public String input(){
		return "update";
	}
	public OrgTypeModel getModel() {
		return model;
	}

	public List<OrgType> getOrgTypeList() {
		return orgTypeList;
	}

	public void prepareInput() throws Exception {
		if (!StringUtils.isBlank(id)) {
			OrgType orgType=orgTypeService.get(id);
			BeanUtils.copyProperties(orgType,model);
		} else {
			model = new OrgTypeModel();
		}
	}
	@Override
	public void prepare() throws Exception {
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	
	
}