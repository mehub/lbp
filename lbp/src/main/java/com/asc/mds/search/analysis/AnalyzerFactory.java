/**
 * 
 */
package com.asc.mds.search.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer4X;
import org.lionsoul.jcseg.core.JcsegTaskConfig;

/**
 * @author WuBo
 * @CreateDate 2011-4-27
 * @version 1.0.1
 */
public class AnalyzerFactory {
	
	public final static int MODE = JcsegTaskConfig.COMPLEX_MODE;
	private static Analyzer analyzer; 
	
	public static Analyzer getQueryAnalyzer(){
		if(analyzer == null){
			analyzer = AnalyzerHolder.ja;
		}
		return analyzer;
	}
	
	private static class AnalyzerHolder{
		static JcsegAnalyzer4X ja = new JcsegAnalyzer4X(MODE);
//		static JcsegAnalyzer ja = new JcsegAnalyzer(MODE);
	}

}
