/**
 * 
 */
package com.asc.mds.search.area.searcher;

import java.util.List;

import com.asc.mds.search.area.LuceneQueryInfo;
import com.asc.mds.search.area.bean.Area;

/**
 * @author WuBo
 * @CreateDate 2011-4-29
 * @version 1.0.1
 */
public interface IAreaSearcher {
	
	void init() throws Exception;
	
	LuceneQueryInfo search(String field, String text) throws Exception;
	
	Area joinSearch(List<LuceneQueryInfo> queryDatas) throws Exception;
	
	void close();
}
