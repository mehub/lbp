package com.asc.mds.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.Region;
import com.asc.mds.root.model.RegionModel;
import com.asc.mds.service.IRegionService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:21:24
 */
@Controller
public class RegionAction extends BaseAction implements ModelDriven<RegionModel> {
	
	private RegionModel model = new RegionModel();
	private List<Region> list;
	
	@Autowired
	private IRegionService regionService;
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	
	public String list(){
		if("source".equals(model.getParentId())){
			model.setParentId(null);
		}
		list = regionService.getSplitPage(start, limit, model);	
		long total = regionService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "region");
		return "success";
	}

	//get
	public String get() {
		Region source = regionService.get(model.getId());
		if(source != null){
			BeanUtils.copyProperties(source, model);
		}
		return "update";
	}
	
	//list jstree
	public void listJSTree() throws IOException {
		if(model.getParentId() == null || model.getParentId().length() < 1){
			model.setParentId("-1");
		}
		List<Region> list = regionService.listByParentId(model.getParentId());
		if(list == null ){
			writeToPage("[]");
			return;
		}
		StringBuffer json = new StringBuffer("[");
		if(list != null && list.size() > 0){
			Region area = null;
			for(int i = 0; i< list.size(); i++){
				if(i > 0){
					json.append(",");
				}
				area = list.get(i);
				json.append("{");
				json.append("\"data\":{\"title\":\"" + area.getRegionName() + "\"},\"state\":\"closed\",")
					.append("\"attr\":{\"id\":\"" + area.getId() + "\",\"name\":\"" + area.getRegionName() +"\"}");
				json.append("}");
			}
		}
		json.append("]");
		writeToPage(json.toString());
	}
	
	//saveOrUpdate
	public void saveOrUpdate(){
		String res = regionService.saveOrUpdate(model);
		success(res);
	}
	
	//remove
	public void remove(){
		String res = regionService.remove(model.getId());
		writeToPage(res);
	}
	
	//list jqtree
	@Deprecated
	public void listJQTree() {
		StringBuffer json=new StringBuffer("[");
		List<Region> list = null;
		if(model.getParentId() == null || "source".equals(model.getParentId())){
			model.setParentId("-1");
		}
		list = regionService.listByParentId(model.getParentId());
		Region region = null;
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				region = list.get(i);
				List<Region> tmp = regionService.listByParentId(region.getId());
				boolean isleaf = true;
				if(tmp != null && tmp.size() > 0){
					isleaf = false; 
				}
				if(i > 0){
					json.append(",");
				}
				json.append("{\"id\":\"" + region.getId() + "\",")
					.append("\"text\":\"<a href=javascript:operation('" + region.getId() + "')>" + region.getRegionName() + "</a>\",")
					.append("\"classes\":\"" + (isleaf == true ? "file":"folder") + "\",")
					.append("\"hasChildren\":" + (isleaf == true ? "false":"true") + "}");
			}
		}
		json.append("]");
		writeToPage(json.toString());
	}
	
	public RegionModel getModel() {
		return model;
	}
	public List<Region> getList() {
		return list;
	}
	
}