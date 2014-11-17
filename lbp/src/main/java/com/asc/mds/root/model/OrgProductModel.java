package com.asc.mds.root.model;

import java.util.Date;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:30:10
 */
public class OrgProductModel {
	private String id;                            //ID
	private String ownerAscId;                    //所属企业ASC_ID
	private String productAscId;                  //对应产品ASC_ID
	private String code;                          //编码
	private String commonname;                    //通用名
	private String scientificname;                //学名
	private String unit;                          //单位
	private String pack;                          //包装
	private String spec;                          //规格
	private String batchnumber;                   //批号
	private String origin;                        //生产厂商
	private String discount;                      //换算比
	private String productType;                   //产品类别
	private Integer qualityCheck;	              //质检标签0：历史数据，1：新增数据，2：免检数据
	private String ownerAscCode;                  //所属企业ASC_CODE
	private String productAscCode;                //对应产品ASC_CODE
	private String memo;                          //备注
	private Integer isDisable;                    //0：启用； 1：停用
	private String creator;                       //创建人
	private Date createTime;                      //创建时间
	private Date lastmodifyTime;                  //最后修改日期
	private String relid;                         //正式表ID
	private Integer audiState;                    //审核状态 0：待审，1：已审-自动 ，2：已审-人工
	private String audiMemo;					  //审核备注
	private String audiUser;                      //审核人
	private String fingerMark;                    //数据指纹
	private Integer state;						  //数据状态 1：正常，2：变更中
	private String fileId;						  //文件ID
	private String ownerAscName;
	private String productAscName;
	
	
	public String getOwnerAscName() {
		return ownerAscName;
	}
	public void setOwnerAscName(String ownerAscName) {
		this.ownerAscName = ownerAscName;
	}
	public String getProductAscName() {
		return productAscName;
	}
	public void setProductAscName(String productAscName) {
		this.productAscName = productAscName;
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
	public String getAudiMemo() {
		return audiMemo;
	}
	public void setAudiMemo(String audiMemo) {
		this.audiMemo = audiMemo;
	}
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
	public String getProductAscId() {
		return productAscId;
	}
	public void setProductAscId(String productAscId) {
		this.productAscId = productAscId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCommonname() {
		return commonname;
	}
	public void setCommonname(String commonname) {
		this.commonname = commonname;
	}
	public String getScientificname() {
		return scientificname;
	}
	public void setScientificname(String scientificname) {
		this.scientificname = scientificname;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getBatchnumber() {
		return batchnumber;
	}
	public void setBatchnumber(String batchnumber) {
		this.batchnumber = batchnumber;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Integer getQualityCheck() {
		return qualityCheck;
	}
	public void setQualityCheck(Integer qualityCheck) {
		this.qualityCheck = qualityCheck;
	}
	public String getOwnerAscCode() {
		return ownerAscCode;
	}
	public void setOwnerAscCode(String ownerAscCode) {
		this.ownerAscCode = ownerAscCode;
	}
	public String getProductAscCode() {
		return productAscCode;
	}
	public void setProductAscCode(String productAscCode) {
		this.productAscCode = productAscCode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}