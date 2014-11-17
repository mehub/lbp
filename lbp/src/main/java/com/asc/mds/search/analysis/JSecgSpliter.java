/**
 * 
 */
package com.asc.mds.search.analysis;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-21 下午6:42:35
 */
public class JSecgSpliter {
	
	public static String[] split(String keyword) throws IOException {
		Reader reader = new StringReader(keyword);
		List<String> result = new ArrayList<String>();
		TokenStream tokenStream = AnalyzerFactory.getQueryAnalyzer() .tokenStream("", reader);
		while(tokenStream.incrementToken()){
			CharTermAttribute termAtt = tokenStream.addAttribute(CharTermAttribute.class);
			result.add(termAtt.toString());
		}
		
		return result.toArray(new String[result.size()]);
	}

}
