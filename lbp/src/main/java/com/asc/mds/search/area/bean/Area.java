package com.asc.mds.search.area.bean;
/**
 * 区域
 * @author WuBo
 * @CreateDate 2011-4-19
 * @version 1.0.1
 */
public class Area {
	private String country;
	private String province;
	private String city;
	private String county;
	private boolean hasConflict; //区域是否有冲突 e.g. [上海]安捷力信息系统有限公司[北京]分公司
	private ExtArea mar;
	private String xlevel;       //级别
	
	public Area(){}
	public Area(String country, String province, String city, String county, String xlevel) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
		this.county = county;
		this.xlevel = xlevel;
	}
	
	public Area(ExtArea mar) {
		super();
		this.province = mar.getProvince();
		this.city = mar.getCity();
		this.mar = mar;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public boolean isHasConflict() {
		return hasConflict;
	}
	public void setHasConflict(boolean hasConflict) {
		this.hasConflict = hasConflict;
	}
	public ExtArea getMar() {
		return mar;
	}
	public void setMar(ExtArea mar) {
		this.mar = mar;
	}
	public String getXlevel(){
		return this.xlevel;
	}
	public void setXlevel(String xlevel){
		this.xlevel = xlevel;
	}
	public String toString(){
		return country+province+city+county;
	}
	
}
