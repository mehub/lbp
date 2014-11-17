package com.asc.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class BaseAction implements ServletRequestAware,ServletResponseAware{
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected int start=0;
	protected int limit=10;
	protected String msg;
	protected String pageBar;
	protected String query;
	protected String flag;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPageBar() {
		return pageBar;
	}
	public void setPageBar(String pageBar) {
		this.pageBar = pageBar;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getQuery() {
		return this.query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public void error(Object msg){
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write("{\"success\": \"false\",\"data\":\""+msg+"\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void success(Object msg){
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write("{\"success\": \"true\",\"data\":\""+msg+"\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void errorinfo(Object msg){
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write("{\"success\": \"false\",\"data\":"+msg+"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeToPage(String str){
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
