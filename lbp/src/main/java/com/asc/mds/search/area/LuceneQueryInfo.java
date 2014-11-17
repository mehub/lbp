package com.asc.mds.search.area;

import org.apache.lucene.search.Query;

/**
 * 查询信息
 * @author WuBo
 * @CreateDate 2011-4-29
 * @version 1.0.1
 */
public class LuceneQueryInfo {
	
	Query query;
	String field;
	String keyword;
	int hits;
	
	public LuceneQueryInfo(Query query, String field, String keyword, int hits){
		this.query = query;
		this.field = field;
		this.keyword = keyword;
		this.hits = hits;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
	
}
