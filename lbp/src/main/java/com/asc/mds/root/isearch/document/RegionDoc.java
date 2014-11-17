package com.asc.mds.root.isearch.document;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-10 上午10:54:41
 */
public class RegionDoc extends LuceneDoc{
	
	private String code;
	private String province;
	private String city;
	private String county;
	private String xlevel;
	private String cityId;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getXlevel() {
		return xlevel;
	}
	public void setXlevel(String xlevel) {
		this.xlevel = xlevel;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	public String toString(){
		StringBuffer sb  = new StringBuffer();
		if(this.province != null){
			sb.append(this.province);
		}
		if(this.city != null){
			sb.append(this.city);
		}
		if(this.county != null){
			sb.append(this.county);
		}
		return sb.toString();
	}
	
}
