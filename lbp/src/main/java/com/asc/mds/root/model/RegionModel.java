package com.asc.mds.root.model;

import java.util.Date;


/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:32:01
 */
public class RegionModel {
	
	private String id;                            //ID
	private String parentId;                      //父区域ID
	private String parentName;                    //父名称
	private String code;                          //区域代码
	private String regionName;                    //区域名称
	private String shortName;                     //区域简称
	private String keyWords;                      //关键词：解决模糊匹配问题，区域名称同名问题预留
	private String post;                          //邮编
	private String xlevel;                        //级别
	private String province;                      //省
	private String modifyer;					  //修改人
	private Date lastmodifyTime;                  //最后修改日期
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getXlevel() {
		return xlevel;
	}
	public void setXlevel(String xlevel) {
		this.xlevel = xlevel;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getModifyer() {
		return modifyer;
	}
	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}
	public Date getLastmodifyTime() {
		return lastmodifyTime;
	}
	public void setLastmodifyTime(Date lastmodifyTime) {
		this.lastmodifyTime = lastmodifyTime;
	}

}