/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.search;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.FieldCacheType;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.cfg.IndexedMapping;
import org.hibernate.search.cfg.SearchMapping;

import com.asc.mds.root.isearch.Indexable;
import com.asc.mds.search.analysis.JcsegTokenizerFactory3X;

/**
 * 类描述 . hibernate search mapping<p>
 * <b>programming mapping entity，avoid to infect code</b>
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-16 上午10:43:29
 */
public class HSearchMappingFactory {
	
	private String[] packageToScan = {"com.asc.mds.root.bean"}; 
	
	private Logger log = Logger.getLogger(HSearchMappingFactory.class);
	
	@Factory
	public SearchMapping getSearchMapping(){
		
		SearchMapping mapping = new SearchMapping();
		
		String jsecAnalyzerName = "jcsegAnalyzer";
		//analyzerDef
		mapping.analyzerDef(jsecAnalyzerName, JcsegTokenizerFactory3X.class)
					.tokenizerParam("mode", "complex");
		
		String path = HSearchMappingFactory.class.getClassLoader().getResource("").getPath();
		for(String pk : packageToScan){
			String cp = path + pk.replaceAll("\\.", "/");
			File dir = new File(cp);
			if(!dir.exists() || !dir.isDirectory()) {
				log.warn(pk + " is not exsist or not directory!");
				continue;
			}
			
			File[] cfs = dir.listFiles();
			if(cfs.length < 1) continue;
			String fn = null;
			for(File f : cfs){
				fn = f.getName();
				int index = fn.lastIndexOf(".class");
				if (index == -1) continue;
				String cn = pk + "." + fn.substring(0, index);
				try {
					Class<?> clazz = Class.forName(cn);
					boolean isIndexed = clazz.isAnnotationPresent(Indexable.class);
					if(isIndexed){
						Indexable ia = clazz.getAnnotation(Indexable.class);
						if(ia == null) continue;
						String name = ia.name();
						
						IndexedMapping im = mapping.entity(clazz).indexed()
								.indexName(name)
								.cacheFromIndex(FieldCacheType.CLASS)
								;
						
						Method[] methods = clazz.getDeclaredMethods();
						for(Method method : methods){
							boolean an = method.isAnnotationPresent(Indexable.class);
							if(an){
								Indexable fi = method.getAnnotation(Indexable.class);
								if(fi.id())
									im.property(fi.name(), ElementType.METHOD)
											.documentId();
								else if(fi.embedded())
									im.property(fi.name(), ElementType.METHOD)
											.indexEmbedded();
								else 
									im.property(fi.name(), ElementType.METHOD).field()
											.analyzer(jsecAnalyzerName)
											.analyze(fi.analyzer() == 1 ? Analyze.YES : Analyze.NO)
											.store(fi.store() == 1 ? Store.YES : Store.NO);
							}
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return mapping;
	}
	
}
