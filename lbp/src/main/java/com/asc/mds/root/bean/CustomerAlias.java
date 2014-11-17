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
 * 类描述 . 客户企业别称表，存档厂商每月的别称信息
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:24:33
 */
@Entity
@Table(name = "MD_CUSTOMER_ORG_ALIAS")
@Indexable(name="CustomerAlias")
public class CustomerAlias extends Searchable implements Serializable {

	private static final long serialVersionUID = 1661096478219937204L;
	
	public static String[] projections = {"id", "code", "orgName", "ctName", "city"
					, "province", "region", "ownerAscId", "ownerAscCode", "dateVersion"};
	
	private String id;                            		//ID
	private String code;                          		//编码
	private String orgName;                        		//名称
	private String ctName;								//CT_NAME
	private String county;								//县
	private String city;                          		//市
	private String province;                      		//省
	private String region;								//大区
	private String ownerAscId;                    		//所属企业ASC_ID
	private String ownerAscCode;                  		//所属企业ASC_CODE
	private String dateVersion;                   		//日期版本：201212
	private String creator;                       		//创建人
	private Date createTime;                      		//创建时间	
	private Date lastmodifyTime;                  		//最后修改日期
	private String fileId;						  		//文件ID

	private boolean useLike;					  		//使用like查询
	
	@Id
	@Indexable(name="id", id=true)
	@GeneratedValue(generator = "CustomerAliasGenerator")
	@GenericGenerator(name = "CustomerAliasGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@Indexable(name="code")
	@Column(name = "CODE")
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}

	@Indexable(name="orgName")
	@Column(name = "NAME")
	public String getOrgName(){
		return this.orgName;
	}
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

	@Indexable(name="ctName")
	@Column(name = "CT_NAME")
	public String getCtName() {
		return ctName;
	}
	public void setCtName(String ctName) {
		this.ctName = ctName;
	}
	
	@Indexable(name="region")
	@Column(name = "REGION")
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	@Indexable(name="province")
	@Column(name = "PROVINCE")
	public String getProvince(){
		return this.province;
	}
	public void setProvince(String province){
		this.province = province;
	}

	@Indexable(name="city")
	@Column(name = "CITY")
	public String getCity(){
		return this.city;
	}
	public void setCity(String city){
		this.city = city;
	}

	@Indexable(name="county")
	@Column(name = "COUNTY")
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	@Indexable(name="ownerAscId")
	@Column(name = "OWNER_ASC_ID")
	public String getOwnerAscId(){
		return this.ownerAscId;
	}
	public void setOwnerAscId(String ownerAscId){
		this.ownerAscId = ownerAscId;
	}

	@Indexable(name="ownerAscCode")
	@Column(name = "OWNER_ASC_CODE")
	public String getOwnerAscCode(){
		return this.ownerAscCode;
	}
	public void setOwnerAscCode(String ownerAscCode){
		this.ownerAscCode = ownerAscCode;
	}
	
	@Indexable(name="dateVersion")
	@Column(name = "DATEVERSION")
	public String getDateVersion(){
		return this.dateVersion;
	}
	public void setDateVersion(String dateVersion){
		this.dateVersion = dateVersion;
	}

	@Column(name = "CREATOR")
	public String getCreator(){
		return this.creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	@Column(name = "LASTMODIFY_TIME")
	public Date getLastmodifyTime(){
		return this.lastmodifyTime;
	}
	public void setLastmodifyTime(Date lastmodifyTime){
		this.lastmodifyTime = lastmodifyTime;
	}
	
	@Column(name = "FILEID")
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Transient
	public boolean getUseLike() {
		return useLike;
	}
	public void setUseLike(boolean useLike) {
		this.useLike = useLike;
	}
	
}