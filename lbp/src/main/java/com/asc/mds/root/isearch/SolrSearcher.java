package com.asc.mds.root.isearch;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.asc.common.util.InitConfUtils;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.EntitySolrSetting;
import com.asc.mds.root.isearch.document.CustAliasDoc;
import com.asc.mds.root.isearch.document.LuceneDoc;
import com.asc.mds.root.isearch.document.OrgDoc;
import com.asc.mds.root.isearch.document.ProductDoc;

/**
 * 
 * 类描述 . solr 搜索器
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-1 下午2:18:13
 */
@SuppressWarnings("unchecked")
public class SolrSearcher {

	private static Logger log = Logger.getLogger(SolrSearcher.class);

	public static String SOLR_URL = InitConfUtils.getParamValue("search.solr.url");

	public static String SEARCH_ORG_URL = InitConfUtils.getParamValue("search.org.url");

	public static <T extends LuceneDoc> String search(String queryName, QueryParams info,
			Class<T> clazz, EntitySolrSetting es) {
		if (StringUtils.isEmpty(queryName)) {
			return null;
		}
		try {
			String query = info.getQueryStr();
			String qurl = SOLR_URL + es.getSolrName().replace("query", "select?q=")
					+ URLEncoder.encode(queryName, "UTF-8") + query;
			log.debug("search request:" + qurl);
			URL url = new URL(qurl);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			return doc.asXML();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T extends LuceneDoc> List<T> searchDocs(String name, Class<T> clazz, EntitySolrSetting es) {
		QueryResult<T> res = parseSearchResult(search(name, new QueryParams(), clazz, es), clazz);
		return res == null ? null : res.getResults();
	}
	
	public static <T extends LuceneDoc> QueryResult<T> parseSearchResult(
			String xml, Class<T> clazz) {
		try {
			if (StringUtils.isEmpty(xml)) {
				return null;
			}
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			QueryResult<T> sr = new QueryResult<T>();
			Iterator<Element> elementIterator = root.elementIterator();
			while (elementIterator.hasNext()) {
				Element element = elementIterator.next();
				if (element.getName().equals("lst")) {
					parseResponseHeader(element, sr);
				} else if (element.getName().equals("result")) {
					parseDoc(element, clazz, sr);
				}
			}
			return sr;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T extends LuceneDoc> void parseResponseHeader(
			Element header, QueryResult<T> sr) {
		List<Element> elements = header.elements();
		for (Element element : elements) {
			String attr = element.attribute("name").getText();
			if (attr.equals("status")) {
				sr.setSuccess(element.getText().equals("0"));
			} else if (attr.equals("QTime")) {
				sr.setQueryTime(Integer.parseInt(element.getText()));
			}
		}
	}

	private static <T extends LuceneDoc> void parseDoc(Element result,
			Class<T> clazz, QueryResult<T> sr) {
		int numFound = Integer.parseInt(result.attributeValue("numFound"));
		sr.setFoundNum(numFound);
		double maxScore = Double.parseDouble(result.attributeValue("maxScore"));
		sr.setMaxScore(maxScore);
		if (!result.isTextOnly()) {
			Iterator<Element> docIterator = result.elementIterator("doc");
			while (docIterator.hasNext()) {
				Element doc = docIterator.next();
				T org = getResult(clazz, doc);
				sr.addResult(org);
			}
		}
	}

	private static <T> T getResult(Class<T> clazz, Element doc) {
		if (doc.isTextOnly()) {
			return null;
		}
		T instance = null;
		try {
			instance = clazz.newInstance();
			Iterator<Element> iterator = doc.elementIterator();
			while (iterator.hasNext()) {
				Element node = iterator.next();
				String fieldName = node.attributeValue("name").replace("_", "");
				String type = node.getName();
				Field field = getField(clazz, fieldName);
				if (field == null) {
					continue;
				}
				String fieldValue = node.getText();
				if (type.equals("str")) {
					field.set(instance, fieldValue);
				} else if (type.equals("float")) {
					field.setDouble(instance, Double.parseDouble(fieldValue));
				}

			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}

	private static Field getField(Class<?> clazz, String fieldName) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields == null) {
			return null;
		}
		for (Field field : fields) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				field.setAccessible(true);
				return field;
			}
		}
		try {
			Field f = null;
			if(OrgDoc.class.isAssignableFrom(clazz)){
				
				f = OrgDoc.get(fieldName);
				
			} else if(CustAliasDoc.class.isAssignableFrom(clazz)){
				
				f = CustAliasDoc.get(fieldName);
				
			} else if(ProductDoc.class.isAssignableFrom(clazz)){
				
				f = ProductDoc.get(fieldName);
				
			} 
			if(f != null){
				f.setAccessible(true);
				return f;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

}
