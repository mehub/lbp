/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.isearch;

//import org.hibernate.search.annotations.Analyzer;
//import org.hibernate.search.annotations.AnalyzerDef;
//import org.hibernate.search.annotations.TokenizerDef;
//
//import com.asc.mds.search.analysis.JcsegTokenizerFactory3X;

/**
 * 类描述 . hibernate-search api
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-25 下午5:02:14
 */
//@AnalyzerDef(
//	name="jcsegAnalyzer",
//	tokenizer=@TokenizerDef(
//				  factory=JcsegTokenizerFactory3X.class
//				, params={@org.hibernate.search.annotations.Parameter(name="mode", value="complex")}
//			)
//)
//@Analyzer(definition="jcsegAnalyzer")
public class Searchable {
	
	protected float __HSearch_Score;	//匹配分数
	protected String percent;			//百分比
	
	public void set__HSearch_Score(float __HSearch_Score) {
		this.__HSearch_Score = __HSearch_Score;
	}

	public float getHSearch_Score() {
		return __HSearch_Score;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	
}
