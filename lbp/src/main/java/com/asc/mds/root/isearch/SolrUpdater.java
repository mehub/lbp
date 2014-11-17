/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.isearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.asc.common.util.InitConfUtils;
import com.asc.mds.root.EntitySolrSetting;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-9 下午2:57:39
 */
public class SolrUpdater {
	
	private static final Logger log = Logger.getLogger(SolrUpdater.class);
	private static String indexUrl = null;
	static {
		if(indexUrl == null){
			indexUrl = InitConfUtils.getParamValue("search.solr.url");
		}
	}
	//<delete><id>1234</id></delete>
	public static void delete(EntitySolrSetting es, String... ids){
		if(ids == null || ids.length < 1){
			return;
		}
		StringBuffer bf = new StringBuffer("<delete>");
		for(String id : ids)
			bf.append("<id>").append(id).append("</id>");
		bf.append("</delete>");
		update(es, bf.toString(), "update?commit=true");			
	}
	
	public static void searcherUpdate(EntitySolrSetting es){
		update(es, "", "searchreopen");
	}
	
	public static void update(EntitySolrSetting es, String doc, String handler){
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			String tmp = indexUrl + es.getSolrName().replace("query", handler);
			conn = (HttpURLConnection) new URL(tmp).openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "text/xml");
			conn.setDoOutput(true);
			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			os.write(doc);
			os.close();
			is = conn.getInputStream();
			while (is.available() > 0) {
				byte[] b = new byte[is.available()];
				is.read(b);
				log.debug(new String(b));				
			}
			is.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(conn != null) conn.disconnect();
		}
	}

}
