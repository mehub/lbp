/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root;


/**
 * 类描述 .索引实体统计信息
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-26 下午4:30:51
 */
public class IndexedEntity {
	
	private String code;				
	private String name;			//索引名称
	private String clazz;			//索引实体
	private int indexedCount;   	//索引文档数量
	private String solrUrl;			//solr地址
	private String ids;				//1,2,3
	
	private long searchQueryCount;
	private long searchExecutionTotalTime;
	private long searchExecutionMaxTime;
	private String queryExecutionMaxTimeQueryString;

	//for view model
	private String currentVersion;	//日期版本

	private int isRebuilding;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public int getIndexedCount() {
		return indexedCount;
	}
	public void setIndexedCount(int indexedCount) {
		this.indexedCount = indexedCount;
	}
	public String getSolrUrl() {
		return solrUrl;
	}
	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	public int getIsRebuilding() {
		return isRebuilding;
	}
	public void setIsRebuilding(int isRebuilding) {
		this.isRebuilding = isRebuilding;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public long getSearchQueryCount() {
		return searchQueryCount;
	}
	public void setSearchQueryCount(long searchQueryCount) {
		this.searchQueryCount = searchQueryCount;
	}
	public long getSearchExecutionTotalTime() {
		return searchExecutionTotalTime;
	}
	public void setSearchExecutionTotalTime(long searchExecutionTotalTime) {
		this.searchExecutionTotalTime = searchExecutionTotalTime;
	}
	public long getSearchExecutionMaxTime() {
		return searchExecutionMaxTime;
	}
	public void setSearchExecutionMaxTime(long searchExecutionMaxTime) {
		this.searchExecutionMaxTime = searchExecutionMaxTime;
	}
	public String getQueryExecutionMaxTimeQueryString() {
		return queryExecutionMaxTimeQueryString;
	}
	public void setQueryExecutionMaxTimeQueryString(
			String queryExecutionMaxTimeQueryString) {
		this.queryExecutionMaxTimeQueryString = queryExecutionMaxTimeQueryString;
	}
	
}
