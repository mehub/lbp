/**
 * AdviceTskfOrg.java  2011-6-23
 * Copyright 2007-2010 AgileSC, All Rights Reserved
 */
package com.asc.mds.root.model;

import java.io.Serializable;

/**
 * @author wangshaohu
 * @date 2011-6-23 下午03:50:39
 * @version 1.0.0
 */
public class AdviceTskfOrg implements Serializable {
	
	private static final long serialVersionUID = -7939557514424921448L;
	
	private String province;
	private String city;
	private String ctyCode;
	private String county;
	private String couCode;
	private String code;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCtyCode() {
		return ctyCode;
	}
	public void setCtyCode(String ctyCode) {
		this.ctyCode = ctyCode;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCouCode() {
		return couCode;
	}
	public void setCouCode(String couCode) {
		this.couCode = couCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
