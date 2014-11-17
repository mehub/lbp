package com.asc.mds.root.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:25:17
 */
@Entity
@Table(name = "MD_ORG_TEMP")
public class OrgTemp {
	private String id;                            //ID
	private String code;                          //编码
	private String orgName;                       //名称
	private String shortName;                     //简称
	private String address;                       //地址
	private String telephone;                     //电话
	private String fax;                           //传真
	private String email;                         //邮箱
	private String url;                           //网址
	private String memo;                          //备注
	private String xlevel;                        //经销商级别：一级经销商，二级经销商，其他经销商
	private String classify;                      //分类性质：药店、药房、公司、卫生所、服务站...
	private Integer qualityCheck=1;               //质检标签0：历史数据，1：新增数据，2：免检数据
	private String typeId;                        //类别
	private String typeName;                      //类别名称
	private String regionId;                      //区域ID
	private String regionName;                    //区域名称
	private Region region;                    	  //区域
	private DataState isDisable;  				  //是否停用 0：启用； 1：停用
	private String creator;                       //创建人
	private Date createTime;                      //创建时间
	private Date lastmodifyTime;                  //最后修改日期
	private String relid;                         //正式表ID
	private AudiState audiState;                  //审核状态 0：待审，1：已审-自动 ，2：已审-人工
	private String audiMemo;					  //审核备注
	private String audiUser;                      //审核人
	private String fingerMark;                    //数据指纹
	private String fileId;						  //文件ID

	@Id
	@GeneratedValue(generator = "MdOrgTempGenerator")
	@GenericGenerator(name = "MdOrgTempGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@Column(name = "CODE")
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}

	@Column(name = "NAME")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "SHORT_NAME")
	public String getShortName(){
		return this.shortName;
	}
	public void setShortName(String shortName){
		this.shortName = shortName;
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

	@Column(name = "FAX")
	public String getFax(){
		return this.fax;
	}
	public void setFax(String fax){
		this.fax = fax;
	}

	@Column(name = "EMAIL")
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}

	@Column(name = "URL")
	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		this.url = url;
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

	@Column(name = "CLASSIFY")
	public String getClassify(){
		return this.classify;
	}
	public void setClassify(String classify){
		this.classify = classify;
	}

	@Column(name = "QUALITY_CHECK")
	public Integer getQualityCheck(){
		return this.qualityCheck;
	}
	public void setQualityCheck(Integer qualityCheck){
		this.qualityCheck = qualityCheck;
	}

	@Column(name = "TYPE_ID")
	public String getTypeId(){
		return this.typeId;
	}
	public void setTypeId(String typeId){
		this.typeId = typeId;
	}
	@Formula("(select r.NAME from MD_ORG_TYPE r where r.id=TYPE_ID)")
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Column(name = "REGION_ID",insertable=false,updatable=false)
	public String getRegionId(){
		return this.regionId;
	}
	public void setRegionId(String regionId){
		this.regionId = regionId;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="REGION_ID")
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	
	@Formula("(select r.NAME from MD_REGION r where r.id=REGION_ID)")
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	@Column(name = "RELID")
	public String getRelid(){
		return this.relid;
	}
	public void setRelid(String relid){
		this.relid = relid;
	}

	@Type(type="com.asc.common.StringValueEnumType",
			  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.AudiState")})
	@Column(name = "AUDI_STATE")
	public AudiState getAudiState(){
		return this.audiState;
	}
	public void setAudiState(AudiState audiState){
		this.audiState = audiState;
	}

	@Column(name = "AUDI_MEMO")
	public String getAudiMemo() {
		return audiMemo;
	}
	public void setAudiMemo(String audiMemo) {
		this.audiMemo = audiMemo;
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