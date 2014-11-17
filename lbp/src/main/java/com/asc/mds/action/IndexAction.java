package com.asc.mds.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.common.util.MathUtil;
import com.asc.mds.root.IndexedEntity;
import com.asc.mds.service.IIndexService;
import com.asc.mds.service.IndexService;
import com.asc.search.hibernate4.IndexingProgressMonitor;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .索引管理
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:21:52
 */
@Controller
public class IndexAction extends BaseAction implements ModelDriven<IndexedEntity> {
	
	private IndexedEntity model = new IndexedEntity();
	private List<IndexedEntity> list;
	
	@Autowired
	private IIndexService indexService;
	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}

	//list
	public String list(){
		list = indexService.list();	
		return "success";
	}
	
	public void rebuild(){
		try{
			writeToPage("1");	
			indexService.rebuild(model);
		} catch (Exception e){
			writeToPage("0");
			e.printStackTrace();
		}
	}
	public void update(){
		try{
			writeToPage("1");	
			indexService.update(model);
		} catch (Exception e){
			writeToPage("0");
			e.printStackTrace();
		}
	}
	public void optimize(){
		try{
			indexService.optimize(model);
			writeToPage("1");	
		} catch (Exception e){
			writeToPage("0");
			e.printStackTrace();
		}
	}
	
	public String purges(){
		try{
			this.flag = "true";
			indexService.purges(model);
		} catch (Exception e){
			e.printStackTrace();
		}
		return list();
	}
	
	
	public void progressing(){
		try{
			Map<String, IndexingProgressMonitor> monitors = IndexService.getMonitors();
			Iterator<Entry<String, IndexingProgressMonitor>> iterator = monitors.entrySet().iterator();
			Entry<String, IndexingProgressMonitor> next = null;
			IndexingProgressMonitor monitor = null;
			StringBuffer json = new StringBuffer("[");
			int i = 0;
			while(iterator.hasNext()){
				if(i > 0) json.append(",");
				next = iterator.next();
				monitor = next.getValue();
				if(monitor != null){
					json.append("{\"code\":\""+ next.getKey() + "\"")
						.append(", \"percent\":\""+MathUtil.divMethod(monitor.getDocumentsDoneCounter(), monitor.getTotalCounter(), 2)*100+"\"}");
				}
			}
			json.append("]");
			writeToPage(json.toString());	
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public IndexedEntity getModel() {
		return model;
	}
	public List<IndexedEntity> getList() {
		return list;
	}
	
}