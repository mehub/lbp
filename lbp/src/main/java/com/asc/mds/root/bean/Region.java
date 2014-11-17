package com.asc.mds.root.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.asc.mds.root.isearch.Indexable;
import com.asc.mds.root.isearch.Searchable;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:25:45
 */
@Entity
@Table(name = "MD_REGION")
@Indexable(name="Region")
public class Region extends Searchable implements Serializable{
	
	private static final long serialVersionUID = 4332857732239659183L;
	
	/*检索投影域*/
	public static String[] projections = {"id", "code", "regionName", "parentName", "province", "xlevel", "country"};
	
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

	private String country="中国";				  //国家 default 中国
	
	@Id
	@Indexable(name="id", id=true)
	@GeneratedValue(generator = "MdRegionGenerator")
	@GenericGenerator(name = "MdRegionGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@Column(name = "PARENT_ID")
	public String getParentId(){
		return this.parentId;
	}
	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	@Indexable(name="parentName")
	@Column(name = "PARENT_NAME")
	public String getParentName(){
		return this.parentName;
	}
	public void setParentName(String parentName){
		this.parentName = parentName;
	}

	@Indexable(name="code")
	@Column(name = "CODE")
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}
	
	@Indexable(name="regionName")
	@Column(name = "NAME")
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName(){
		return this.shortName;
	}
	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	@Column(name = "KEY_WORDS")
	public String getKeyWords(){
		return this.keyWords;
	}
	public void setKeyWords(String keyWords){
		this.keyWords = keyWords;
	}

	@Column(name = "POST")
	public String getPost(){
		return this.post;
	}
	public void setPost(String post){
		this.post = post;
	}

	@Indexable(name="xlevel")
	@Column(name = "XLEVEL")
	public String getXlevel(){
		return this.xlevel;
	}
	public void setXlevel(String xlevel){
		this.xlevel = xlevel;
	}

	@Indexable(name="province")
	@Column(name = "PROVINCE")
	public String getProvince(){
		return this.province;
	}
	public void setProvince(String province){
		this.province = province;
	}
	
	@Column(name = "MODIFYER")
	public String getModifyer() {
		return modifyer;
	}
	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}
	
	@Column(name = "LASTMODIFY_TIME")
	public Date getLastmodifyTime() {
		return lastmodifyTime;
	}
	public void setLastmodifyTime(Date lastmodifyTime) {
		this.lastmodifyTime = lastmodifyTime;
	}
	
	@Indexable(name="country")
	@Transient
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}