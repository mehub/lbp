package com.asc.mds.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.search.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.InitConfUtils;
import com.asc.mds.root.EntitySolrSetting;
import com.asc.mds.root.IndexedEntity;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.search.hibernate4.HibernateSearcher;
import com.asc.search.hibernate4.IndexingProgressMonitor;

/**
 * 
 * 类描述 .索引管理
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-27 上午10:18:37
 */
@Service
public class IndexService implements IIndexService {
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	private HibernateSearcher hibernateSearcher;
	public void setHibernateSearcher(HibernateSearcher hibernateSearcher) {
		this.hibernateSearcher = hibernateSearcher;
	}
	
	private static String prefix = "_mi";
	private static Map<String, IndexingProgressMonitor> monitors = new ConcurrentHashMap<String, IndexingProgressMonitor>();
	
	public static synchronized IndexingProgressMonitor getMonitor(String code){
		IndexingProgressMonitor monitor = monitors.get(code);
		if(monitor == null){
			monitor = new IndexingProgressMonitor();
			monitors.put(code, monitor);			
		}
		return monitor;
	}
	
	public static Map<String, IndexingProgressMonitor> getMonitors() {
		return monitors;
	}

	public static synchronized void complete(String code){
		if(monitors.containsKey(code))
			monitors.remove(code);	
	}
	
	public List<IndexedEntity> list(){
		Statistics stat = hibernateSearcher.getStatistics();
		Set<String> cns = stat.getIndexedClassNames();
		Iterator<String> its = cns.iterator();
		List<IndexedEntity> list = new ArrayList<IndexedEntity>();
		IndexedEntity ie = null;
		EntitySolrSetting es=null;
		while(its.hasNext()){
			String clazz = its.next();
			ie = new IndexedEntity();
			es = EntitySolrSetting.getByClass(clazz);
			ie.setClazz(clazz);
			ie.setCode(prefix+es.getCode());
			ie.setName(es.getValue());
			ie.setIndexedCount(stat.getNumberOfIndexedEntities(clazz));
			ie.setSolrUrl(InitConfUtils.getParamValue("search.solr.url") + "#/" + es.getSolrName());
			ie.setIsRebuilding(es.getIsRebuilding());
			ie.setQueryExecutionMaxTimeQueryString(stat.getSearchQueryExecutionMaxTimeQueryString());
			ie.setSearchExecutionMaxTime(stat.getSearchQueryExecutionMaxTime());
			ie.setSearchExecutionTotalTime(stat.getSearchQueryTotalTime());
			ie.setSearchQueryCount(stat.getSearchQueryExecutionCount());
			
			list.add(ie);
		}		
		return list;
	}
	
	public void rebuild(IndexedEntity model){
		String clazz = model.getClazz();
		EntitySolrSetting es = EntitySolrSetting.getByClass(clazz);
		
		es.setIsRebuilding(1);
		
		hibernateSearcher.massIndex(getMonitor(prefix+es.getCode()), es.getClz());
		
		es.setIsRebuilding(0);
		complete(prefix+es.getCode());
		
		SolrUpdater.searcherUpdate(es);//solr searcher update
	}
	
	public void update(IndexedEntity model){
		String clazz = model.getClazz();
		EntitySolrSetting es = EntitySolrSetting.getByClass(clazz);
		
		es.setIsRebuilding(1);
		
		IndexingProgressMonitor monitor = getMonitor(prefix+es.getCode());
		monitor.addToTotalCount(dao.getTotal(es.getClz()));
		hibernateSearcher.flushToIndexes(monitor, es.getClz());
		
		es.setIsRebuilding(0);
		complete(prefix+es.getCode());
		
		optimize(model);
	}
	
	public void optimize(IndexedEntity model){
		String clazz = model.getClazz();
		EntitySolrSetting es = EntitySolrSetting.getByClass(clazz);
		hibernateSearcher.optimize(es.getClz());
		SolrUpdater.searcherUpdate(es);//solr searcher update
	}
	
	public void purges(IndexedEntity model){
		String clazz = model.getClazz();
		EntitySolrSetting es = EntitySolrSetting.getByClass(clazz);
		String str = model.getIds();
		str = str.replaceAll("，", ",");
		hibernateSearcher.purgeList(es.getClz(), str.split(","));
		SolrUpdater.searcherUpdate(es);//solr searcher update
	}
	
}