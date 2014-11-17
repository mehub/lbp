/**
 * 
 */
package com.asc.mds.root.isearch.document;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-1 上午11:49:38
 */
public abstract class LuceneDoc {
	
	protected float score;

	public double getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
}
