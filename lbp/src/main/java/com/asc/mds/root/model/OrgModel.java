package com.asc.mds.root.model;

import java.util.Date;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:30:24
 */
public class OrgModel {
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
	private String terminalProp;			  	  //终端属性
	private Integer qualityCheck;                 //质检标签0：历史数据，1：新增数据，2：免检数据
	private String typeId;                        //类别
	private String typeName;                      //类别名称
	private String regionId;                      //区域
	private String regionName;                    //区域
	private Integer isDisable;                    //0：启用； 1：停用
	private String creator;                       //创建人
	private Date createTime;                      //创建时间
	private Date lastmodifyTime;                  //最后修改日期
	private String audiUser;                      //审核人
	private String fingerMark;                    //数据指纹
	private Integer state;						  //数据状态 1：正常，2：变更中
	private String audiPass;					  //直接通过审核
	private String relid;						  //主表ID	
	private Integer audiState;                    //审核状态
	
	private boolean useLike;					  //使用like查询
	
	private String [] nameKeys;
	
	public String[] getNameKeys() {
		return this.nameKeys;
	}
	public void setNameKeys(String[] nameKeys) {
		this.nameKeys = nameKeys;
	}
	public OrgModel(){}

	public OrgModel(String orgName){
		this.orgName = orgName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getXlevel() {
		return xlevel;
	}
	public void setXlevel(String xlevel) {
		this.xlevel = xlevel;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public Integer getQualityCheck() {
		return qualityCheck;
	}
	public void setQualityCheck(Integer qualityCheck) {
		this.qualityCheck = qualityCheck;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	public String getCreator(){
		return this.creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastmodifyTime() {
		return lastmodifyTime;
	}
	public void setLastmodifyTime(Date lastmodifyTime) {
		this.lastmodifyTime = lastmodifyTime;
	}
	public String getAudiUser() {
		return audiUser;
	}
	public void setAudiUser(String audiUser) {
		this.audiUser = audiUser;
	}
	public String getFingerMark() {
		return fingerMark;
	}
	public void setFingerMark(String fingerMark) {
		this.fingerMark = fingerMark;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAudiPass() {
		return audiPass;
	}
	public void setAudiPass(String audiPass) {
		this.audiPass = audiPass;
	}
	public String getRelid() {
		return relid;
	}
	public void setRelid(String relid) {
		this.relid = relid;
	}
	public Integer getAudiState() {
		return audiState;
	}
	public void setAudiState(Integer audiState) {
		this.audiState = audiState;
	}
	public String getTerminalProp() {
		return this.terminalProp;
	}
	public void setTerminalProp(String terminalProp) {
		this.terminalProp = terminalProp;
	}
	public boolean getUseLike() {
		return useLike;
	}
	public void setUseLike(boolean useLike) {
		this.useLike = useLike;
	}
	
}