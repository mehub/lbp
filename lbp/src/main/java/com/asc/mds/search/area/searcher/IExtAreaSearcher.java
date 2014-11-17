/**
 * 
 */
package com.asc.mds.search.area.searcher;

import com.asc.mds.search.area.bean.Area;


/**
 * @author WuBo
 * @CreateDate 2011-4-29
 * @version 1.0.1
 */
public interface IExtAreaSearcher {
	
	void init() throws Exception;
	
	Area search(String keyword) throws Exception;
	
	void close();
}
