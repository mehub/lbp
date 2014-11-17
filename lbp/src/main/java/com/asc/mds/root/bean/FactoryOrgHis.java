package com.asc.mds.root.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.asc.mds.root.state.DataState;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:24:33
 */
@Entity
@Table(name = "MD_FACTORY_ORG_HIS")
public class FactoryOrgHis {
	private String id;                            		//ID
	private String ownerAscId;                    		//所属企业ASC_ID
	private String partnerAscId;                  		//对应企业ASC_ID
	private String code;                          		//编码
	private String name;                          		//名称
	private String address;                       		//地址
	private String telephone;                     		//电话
	private String email;                         		//邮箱
	private String memo;                          		//备注
	private String xlevel;                        		//经销商级别：一级经销商，二级经销商，其他经销商
	private String xtype;                         		//终端类别：药店、药房、公司、卫生所、服务站...
	private Integer qualityCheck;                 		//质检标签0：历史数据，1：新增数据，2：免检数据
	private Integer target;                       		//目标标识：1：目标，2：非目标
	private String province;                      		//省
	private String city;                          		//市
	private DataState isDisable;  						//是否停用 0：启用； 1：停用
	private String ownerAscCode;                  		//所属企业ASC_CODE
	private String partnerAscCode;                		//对应企业ASC_CODE
	private String dateVersion;                   		//日期版本：201212
	private String creator;                       		//创建人
	private Date createTime;                      		//创建时间
	private Date lastmodifyTime;                  		//最后修改日期
	private String audiUser;                      		//审核人
	private String fingerMark;                    		//数据指纹
	private String fileId;						  		//文件ID

	@Id
	@GeneratedValue(generator = "MdFactoryOrgHisGenerator")
	@GenericGenerator(name = "MdFactoryOrgHisGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@Column(name = "OWNER_ASC_ID")
	public String getOwnerAscId(){
		return this.ownerAscId;
	}
	public void setOwnerAscId(String ownerAscId){
		this.ownerAscId = ownerAscId;
	}

	@Column(name = "PARTNER_ASC_ID")
	public String getPartnerAscId(){
		return this.partnerAscId;
	}
	public void setPartnerAscId(String partnerAscId){
		this.partnerAscId = partnerAscId;
	}

	@Column(name = "CODE")
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}

	@Column(name = "NAME")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}

	@Column(name = "ADDRESS")
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone(){
		return this.telephone;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	@Column(name = "EMAIL")
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}

	@Column(name = "MEMO")
	public String getMemo(){
		return this.memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}

	@Column(name = "XLEVEL")
	public String getXlevel(){
		return this.xlevel;
	}
	public void setXlevel(String xlevel){
		this.xlevel = xlevel;
	}

	@Column(name = "XTYPE")
	public String getXtype(){
		return this.xtype;
	}
	public void setXtype(String xtype){
		this.xtype = xtype;
	}

	@Column(name = "QUALITY_CHECK")
	public Integer getQualityCheck(){
		return this.qualityCheck;
	}
	public void setQualityCheck(Integer qualityCheck){
		this.qualityCheck = qualityCheck;
	}

	@Column(name = "TARGET")
	public Integer getTarget(){
		return this.target;
	}
	public void setTarget(Integer target){
		this.target = target;
	}

	@Column(name = "PROVINCE")
	public String getProvince(){
		return this.province;
	}
	public void setProvince(String province){
		this.province = province;
	}

	@Column(name = "CITY")
	public String getCity(){
		return this.city;
	}
	public void setCity(String city){
		this.city = city;
	}

	@Type(type="com.asc.common.StringValueEnumType",
			  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.DataState")})
	@Column(name = "IS_DISABLE")
	public DataState getIsDisable(){
		return this.isDisable;
	}
	public void setIsDisable(DataState isDisable){
		this.isDisable = isDisable;
	}

	@Column(name = "OWNER_ASC_CODE")
	public String getOwnerAscCode(){
		return this.ownerAscCode;
	}
	public void setOwnerAscCode(String ownerAscCode){
		this.ownerAscCode = ownerAscCode;
	}

	@Column(name = "PARTNER_ASC_CODE")
	public String getPartnerAscCode(){
		return this.partnerAscCode;
	}
	public void setPartnerAscCode(String partnerAscCode){
		this.partnerAscCode = partnerAscCode;
	}

	@Column(name = "DATE_VERSION")
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

	@Column(name = "AUDI_USER")
	public String getAudiUser(){
		return this.audiUser;
	}
	public void setAudiUser(String audiUser){
		this.audiUser = audiUser;
	}

	@Column(name = "FINGER_MARK")
	public String getFingerMark(){
		return this.fingerMark;
	}
	public void setFingerMark(String fingerMark){
		this.fingerMark = fingerMark;
	}
	
	@Column(name = "FILEID")
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}