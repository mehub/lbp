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

import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:25:34
 */
@Entity
@Table(name = "MD_PRODUCT_TEMP")
public class ProductTemp {
	private String id;                            //ID
	private String code;                          //编码
	private String commonname;                    //通用名
	private String scientificname;                //学名
	private String unit;                          //单位
	private String pack;                          //包装
	private String spec;                          //规格
	private String batchnumber;                   //批号
	private String origin;                        //生产厂商
	private String memo;                          //备注
	private Integer isImport;                     //是否进口 0：非； 1：是
	private DataState isDisable;  				  //是否停用 0：启用； 1：停用
	private String typeId;                        //类别
	private String creator;                       //创建人
	private Date createTime;                      //创建时间
	private Date lastmodifyTime;                  //最后修改日期
	private String relid;                         //正式表ID
	private AudiState audiState;                  //审核状态 0：待审，1：已审-自动 ，2：已审-人工
	private String audiMemo;					  //审核备注
	private String audiUser;                      //审核人
	private String fingerMark;                    //数据指纹
	private String fileId;						  //文件ID

	private String ownerId;								//所属厂商ID
	private String ownerName;							//所属厂商名称
	
	@Id
	@GeneratedValue(generator = "MdProductTempGenerator")
	@GenericGenerator(name = "MdProductTempGenerator", strategy = "com.asc.common.StringSequenceGenerator")
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

	@Column(name = "MEMO")
	public String getMemo(){
		return this.memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}

	@Column(name = "IS_IMPORT")
	public Integer getIsImport(){
		return this.isImport;
	}
	public void setIsImport(Integer isImport){
		this.isImport = isImport;
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

	@Column(name = "TYPE_ID")
	public String getTypeId(){
		return this.typeId;
	}
	public void setTypeId(String typeId){
		this.typeId = typeId;
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
	
	@Column(name = "OWNER_ID")
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	@Column(name = "OWNER_NAME")
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}