/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.search.filter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;

/**
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-1 下午4:54:42
 */
public class CurrentVersionFactory {

	private String currentVersion;
	
	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	@Factory
	public Filter getFilter() {
		Query query = new TermQuery(new Term("dateVersion", currentVersion));
		return new CachingWrapperFilter(new QueryWrapperFilter(query));
	}
	
}
