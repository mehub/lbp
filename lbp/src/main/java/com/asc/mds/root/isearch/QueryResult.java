package com.asc.mds.root.isearch;

import java.util.ArrayList;
import java.util.List;

import com.asc.mds.root.isearch.document.LuceneDoc;

/**
 * @author wangshaohu
 * @date 2011-4-21 下午01:58:37
 * @version 1.0.0
 */
public class QueryResult<T extends LuceneDoc> {

	private boolean isSuccess;
	private int queryTime;
	private int foundNum;
	private double maxScore;
	private List<T> results = new ArrayList<T>();

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(int queryTime) {
		this.queryTime = queryTime;
	}

	public int getFoundNum() {
		return foundNum;
	}

	public void setFoundNum(int foundNum) {
		this.foundNum = foundNum;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public List<T> getResults() {
		return results;
	}

	public void addResult(T t) {
		if (results != null) {
			results.add(t);
		}
	}

}
