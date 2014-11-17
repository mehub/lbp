package com.asc.mds.root.model;

import java.util.Date;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:30:50
 */
public class FranchiseOrgModel {
	
	private String id;                            //ID
	private String ownerAscId;                    //所属企业ASC_ID
	private String partnerAscId;                  //对应企业ASC_ID
	private String code;                          //编码
	private String orgName;                       //名称
	private String address;                       //地址
	private String telephone;                     //电话
	private String email;                         //邮箱
	private String memo;                          //备注
	private String xlevel;                        //经销商级别：一级经销商，二级经销商，其他经销商
	private String xtype;                         //终端类别：药店、药房、公司、卫生所、服务站...
	private Integer qualityCheck;                 //质检标签0：历史数据，1：新增数据，2：免检数据
	private Integer target;                       //目标标识：1：目标，2：非目标
	private String province;                      //省
	private String city;                          //市
	private Integer isDisable;                    //是否停用 0：启用； 1：停用
	private String ownerAscCode;                  //所属企业ASC_CODE
	private String partnerAscCode;                //对应企业ASC_CODE
	private String creator;                       //创建人
	private Date createTime;                      //创建时间
	private Date lastmodifyTime;                  //最后修改日期
	private String audiUser;                      //审核人
	private String audiMemo;
	private String fingerMark;                    //数据指纹
	private Integer state;						  //数据状态 1：正常，2：变更中
	
	private String relid;                         //正式表ID
	
	private Integer audiState;					  //0：待审，1：已审-自动 ，2：已审-人工
	
	private Integer audiPass;					  //直接通过审核
	
	private String ownerAscName;				  //所属企业ASC_NAME
	
	private String partnerAscName;				  //对应企业ASC_NAME
	
	private boolean useLike;					  //使用like查询
	
	private String startTime;                     //开始时间
	private String endTime;	                      //结束时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerAscId() {
		return ownerAscId;
	}
	public void setOwnerAscId(String ownerAscId) {
		this.ownerAscId = ownerAscId;
	}
	public String getPartnerAscId() {
		return partnerAscId;
	}
	public void setPartnerAscId(String partnerAscId) {
		this.partnerAscId = partnerAscId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getOrgName() {
		return this.orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public Integer getQualityCheck() {
		return qualityCheck;
	}
	public void setQualityCheck(Integer qualityCheck) {
		this.qualityCheck = qualityCheck;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
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
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	public String getOwnerAscCode() {
		return ownerAscCode;
	}
	public void setOwnerAscCode(String ownerAscCode) {
		this.ownerAscCode = ownerAscCode;
	}
	public String getPartnerAscCode() {
		return partnerAscCode;
	}
	public void setPartnerAscCode(String partnerAscCode) {
		this.partnerAscCode = partnerAscCode;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRelid() {
		return this.relid;
	}
	public void setRelid(String relid) {
		this.relid = relid;
	}
	
	public String getOwnerAscName() {
		return this.ownerAscName;
	}
	public void setOwnerAscName(String ownerAscName) {
		this.ownerAscName = ownerAscName;
	}

	public String getPartnerAscName() {
		return this.partnerAscName;
	}
	public void setPartnerAscName(String partnerAscName) {
		this.partnerAscName = partnerAscName;
	}
	
	public Integer getAudiPass() {
		return this.audiPass;
	}
	public void setAudiPass(Integer audiPass) {
		this.audiPass = audiPass;
	}
	public Integer getAudiState() {
		return this.audiState;
	}
	public void setAudiState(Integer audiState) {
		this.audiState = audiState;
	}
	public String getAudiMemo() {
		return this.audiMemo;
	}
	public void setAudiMemo(String audiMemo) {
		this.audiMemo = audiMemo;
	}
	public boolean getUseLike() {
		return useLike;
	}
	public void setUseLike(boolean useLike) {
		this.useLike = useLike;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}