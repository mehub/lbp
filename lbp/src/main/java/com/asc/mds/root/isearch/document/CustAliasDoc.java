package com.asc.mds.root.isearch.document;

import java.lang.reflect.Field;

/**
 * 
 * 类描述 . 客户别称表lucene document
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-1 上午11:53:13
 */
public class CustAliasDoc extends LuceneDoc {

	private String id;
	private String address;		// 地址
	private String code;		// 编码
	private String name;		// 企业名
	private String province;	// 省
	private String city;
	private String county;
	private String ownerAscCode;
	private String ownerAscId;
	private String region;		//大区
	private String ctName;		//ct_name
	private String percent;		// 匹配率

	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getOwnerAscCode() {
		return ownerAscCode;
	}

	public void setOwnerAscCode(String ownerAscCode) {
		this.ownerAscCode = ownerAscCode;
	}

	public String getOwnerAscId() {
		return ownerAscId;
	}

	public void setOwnerAscId(String ownerAscId) {
		this.ownerAscId = ownerAscId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getCtName() {
		return ctName;
	}

	public void setCtName(String ctName) {
		this.ctName = ctName;
	}
	
	private String dateVersion;

	public String getDateVersion() {
		return dateVersion;
	}

	public void setDateVersion(String dateVersion) {
		this.dateVersion = dateVersion;
	}
	
	/* 兼容workbench索引定义不一致 */
	public static Field get(String fieldName) throws SecurityException, NoSuchFieldException{
		
		if ("orgName".equals(fieldName)) {
			return CustAliasDoc.class.getDeclaredField("name");
			
		}
		return null;
	}
	
}
