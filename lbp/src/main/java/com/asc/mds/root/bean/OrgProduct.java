package com.asc.mds.root.bean;

import java.io.Serializable;
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

import com.asc.mds.root.state.DataState;
import com.asc.mds.root.state.OperateState;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:25:06
 */
@Entity
@Table(name = "MD_ORG_PRODUCT")
public class OrgProduct implements Serializable {
	
	private static final long serialVersionUID = -8835093623441777233L;
	
	private String id;                            		//ID
	private String ownerAscId;                    		//所属企业ASC_ID
	private String productAscId;                  		//对应产品ASC_ID
	private String code;                          		//编码
	private String commonname;                    		//通用名
	private String scientificname;                		//学名
	private String unit;                          		//单位
	private String pack;                          		//包装
	private String spec;                          		//规格
	private String batchnumber;                   		//批号
	private String origin;                        		//生产厂商
	private String discount;                      		//换算比
	private String productType;                   		//产品类别
	private Integer qualityCheck;                 		//质检标签0：历史数据，1：新增数据，2：免检数据
	private String ownerAscCode;                  		//所属企业ASC_CODE
	private String productAscCode;                		//对应产品ASC_CODE
	private String memo;                          		//备注
	private DataState isDisable;  				  		//是否停用 0：启用； 1：停用
	private String creator;                       		//创建人
	private Date createTime;                      		//创建时间
	private Date lastmodifyTime;                  		//最后修改日期
	private String audiUser;                      		//审核人
	private String fingerMark;                    		//数据指纹
	private OperateState state= OperateState.NORMAL;	//数据状态 1：正常，2：变更中
	private String fileId;						  		//文件ID
	
	private String ownerAscName;
	private String productAscName;
	
	@Id
	@GeneratedValue(generator = "MdOrgProductGenerator")
	@GenericGenerator(name = "MdOrgProductGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	@Formula("(select t1.name from MD_ORG t1 where t1.ID = OWNER_ASC_ID) ")
	public String getOwnerAscName() {
		return ownerAscName;
	}
	public void setOwnerAscName(String ownerAscName) {
		this.ownerAscName = ownerAscName;
	}
	@Formula("(select t1.commonname from MD_PRODUCT t1 where t1.ID = PRODUCT_ASC_ID) ")
	public String getProductAscName() {
		return productAscName;
	}
	public void setProductAscName(String productAscName) {
		this.productAscName = productAscName;
	}
	
	@Column(name = "OWNER_ASC_ID")
	public String getOwnerAscId(){
		return this.ownerAscId;
	}
	public void setOwnerAscId(String ownerAscId){
		this.ownerAscId = ownerAscId;
	}

	private Product ascProduct;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PRODUCT_ASC_ID",insertable=false,updatable=false)
	public Product getAscProduct() {
		return ascProduct;
	}
	public void setAscProduct(Product ascProduct) {
		this.ascProduct = ascProduct;
	}
	
	@Column(name = "PRODUCT_ASC_ID")
	public String getProductAscId(){
		return this.productAscId;
	}
	public void setProductAscId(String productAscId){
		this.productAscId = productAscId;
	}

	@Column(name = "CODE")
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}

	@Column(name = "COMMONNAME")
	public String getCommonname(){
		return this.commonname;
	}
	public void setCommonname(String commonname){
		this.commonname = commonname;
	}

	@Column(name = "SCIENTIFICNAME")
	public String getScientificname(){
		return this.scientificname;
	}
	public void setScientificname(String scientificname){
		this.scientificname = scientificname;
	}

	@Column(name = "UNIT")
	public String getUnit(){
		return this.unit;
	}
	public void setUnit(String unit){
		this.unit = unit;
	}

	@Column(name = "PACK")
	public String getPack(){
		return this.pack;
	}
	public void setPack(String pack){
		this.pack = pack;
	}

	@Column(name = "SPEC")
	public String getSpec(){
		return this.spec;
	}
	public void setSpec(String spec){
		this.spec = spec;
	}

	@Column(name = "BATCHNUMBER")
	public String getBatchnumber(){
		return this.batchnumber;
	}
	public void setBatchnumber(String batchnumber){
		this.batchnumber = batchnumber;
	}

	@Column(name = "ORIGIN")
	public String getOrigin(){
		return this.origin;
	}
	public void setOrigin(String origin){
		this.origin = origin;
	}

	@Column(name = "DISCOUNT")
	public String getDiscount(){
		return this.discount;
	}
	public void setDiscount(String discount){
		this.discount = discount;
	}

	@Column(name = "PRODUCT_TYPE")
	public String getProductType(){
		return this.productType;
	}
	public void setProductType(String productType){
		this.productType = productType;
	}

	@Column(name = "QUALITY_CHECK")
	public Integer getQualityCheck(){
		return this.qualityCheck;
	}
	public void setQualityCheck(Integer qualityCheck){
		this.qualityCheck = qualityCheck;
	}

	@Column(name = "OWNER_ASC_CODE")
	public String getOwnerAscCode(){
		return this.ownerAscCode;
	}
	public void setOwnerAscCode(String ownerAscCode){
		this.ownerAscCode = ownerAscCode;
	}

	@Column(name = "PRODUCT_ASC_CODE")
	public String getProductAscCode(){
		return this.productAscCode;
	}
	public void setProductAscCode(String productAscCode){
		this.productAscCode = productAscCode;
	}

	@Column(name = "MEMO")
	public String getMemo(){
		return this.memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
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
	
	@Type(type="com.asc.common.StringValueEnumType",
			  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.OperateState")})
	@Column(name = "STATE")
	public OperateState getState() {
		return state;
	}
	public void setState(OperateState state) {
		this.state = state;
	}
	
	@Column(name = "FILEID")
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}