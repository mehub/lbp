/**
 * 
 */
package com.asc.mds.search.area.bean;

/**
 * 扩展区域（安捷力信息系统有限公司<-->上海 需手动添加添加索引）
 * @author chenzhenling
 * @CreateDate 2013-6-22
 * @version 1.0.1
 */
public class ExtArea {
	private String province;
	private String city;
	private String keyword;
	private String name;
	private float similarity;
	public ExtArea(){}
	public ExtArea(String province, String city, String keyword,
			String name, float similarity) {
		super();
		this.province = province;
		this.city = city;
		this.keyword = keyword;
		this.name = name;
		this.similarity = similarity;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}
}
