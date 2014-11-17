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
import com.asc.mds.root.state.DataType;
import com.asc.mds.root.state.ProcState;
import com.asc.mds.root.state.RepeatState;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:25:51
 */
@Entity
@Table(name = "MD_UPLOAD_FILE")
public class UploadFile {
	private String id;                            //ID
	private String filename;                      //名称
	private String filetype;                      //文件类别 
	private Integer filesize;                     //文件大小
	private Integer recordNum;                    //记录数目
	private DataType datatype;	              	  //数据类型 1：企业，2：产品，3：区域，4：厂商企业，5：经销商企业，6：企业产品
	private String storepath;                     //存储路径
	private ProcState state;                      //1：待处理，2：处理中，3：导入完成，4：处理异常
	private RepeatState repeatState;          	  //1：正常，2：文件内重复，3：文件外重复
	private String exceptmsg;                     //异常信息
	private String memo;                          //备注
	private String creator;                       //创建人
	private Date createTime;                      //创建时间
	private AudiState audiState;                  //审核状态
	private String audiUser;                      //审核人
	

	public UploadFile(){}
	
	public UploadFile(String filename, String filetype, DataType datatype){
		this.filename = filename;
		this.datatype = datatype;
		this.filetype = filetype;
	}
	
	@Id
	@GeneratedValue(generator = "MdUploadFileGenerator")
	@GenericGenerator(name = "MdUploadFileGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@Column(name = "FILENAME")
	public String getFilename(){
		return this.filename;
	}
	public void setFilename(String filename){
		this.filename = filename;
	}

	@Column(name = "FILETYPE")
	public String getFiletype(){
		return this.filetype;
	}
	public void setFiletype(String filetype){
		this.filetype = filetype;
	}

	@Column(name = "FILESIZE")
	public Integer getFilesize(){
		return this.filesize;
	}
	public void setFilesize(Integer filesize){
		this.filesize = filesize;
	}

	@Column(name = "RECORD_NUM")
	public Integer getRecordNum(){
		return this.recordNum;
	}
	public void setRecordNum(Integer recordNum){
		this.recordNum = recordNum;
	}

	@Type(type="com.asc.common.StringValueEnumType",
		  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.DataType")})
	@Column(name = "DATATYPE")
	public DataType getDatatype(){
		return this.datatype;
	}
	public void setDatatype(DataType datatype){
		this.datatype = datatype;
	}

	@Column(name = "STOREPATH")
	public String getStorepath(){
		return this.storepath;
	}
	public void setStorepath(String storepath){
		this.storepath = storepath;
	}

	@Type(type="com.asc.common.StringValueEnumType",
			  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.ProcState")})
	@Column(name = "STATE")
	public ProcState getState(){
		return this.state;
	}
	public void setState(ProcState state){
		this.state = state;
	}

	@Type(type="com.asc.common.StringValueEnumType",
			  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.RepeatState")})
	@Column(name = "REPEAT_STATE")
	public RepeatState getRepeatState(){
		return this.repeatState;
	}
	public void setRepeatState(RepeatState repeatState){
		this.repeatState = repeatState;
	}

	@Column(name = "EXCEPTMSG")
	public String getExceptmsg(){
		return this.exceptmsg;
	}
	public void setExceptmsg(String exceptmsg){
		this.exceptmsg = exceptmsg;
	}

	@Column(name = "MEMO")
	public String getMemo(){
		return this.memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
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

	@Type(type="com.asc.common.StringValueEnumType",
			  parameters={@Parameter(name="enumType",value="com.asc.mds.root.state.AudiState")})
	@Column(name = "AUDI_STATE")
	public AudiState getAudiState(){
		return this.audiState;
	}
	public void setAudiState(AudiState audiState){
		this.audiState = audiState;
	}

	@Column(name = "AUDI_USER")
	public String getAudiUser(){
		return this.audiUser;
	}
	public void setAudiUser(String audiUser){
		this.audiUser = audiUser;
	}
}