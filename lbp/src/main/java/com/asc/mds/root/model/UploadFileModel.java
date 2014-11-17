package com.asc.mds.root.model;


/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:32:26
 */
public class UploadFileModel {
	
	private String id;                            //ID
	private String filename;                      //名称
	private String filetype;                      //文件类别 
	private Integer filesize;                     //文件大小
	private Integer recordNum;                    //记录数目
	private Integer datatype;                     //数据类型 1：企业，2：产品，3：企业关系，4：产品关系，5：区域
	private String storepath;                     //存储路径
	private Integer state;                        //1：待处理，2：处理中，3：导入完成，4：处理异常
	private Integer repeatState;                  //1：正常，2：文件内重复，3：文件外重复
	private String exceptmsg;                     //异常信息
	private String memo;                          //备注
	private String creator;                       //创建人
	private String startTime;                     //开始时间
	private String endTime;	                      //结束时间
	private Integer audiState;                    //审核状态
	private String audiUser;                      //审核人
	private Integer audiPass;					  //直接通过审核
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public Integer getFilesize() {
		return filesize;
	}
	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}
	public Integer getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(Integer recordNum) {
		this.recordNum = recordNum;
	}
	public Integer getDatatype() {
		return datatype;
	}
	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}
	public String getStorepath() {
		return storepath;
	}
	public void setStorepath(String storepath) {
		this.storepath = storepath;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getRepeatState() {
		return repeatState;
	}
	public void setRepeatState(Integer repeatState) {
		this.repeatState = repeatState;
	}
	public String getExceptmsg() {
		return exceptmsg;
	}
	public void setExceptmsg(String exceptmsg) {
		this.exceptmsg = exceptmsg;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreator(){
		return this.creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
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
	public Integer getAudiState() {
		return audiState;
	}
	public void setAudiState(Integer audiState) {
		this.audiState = audiState;
	}
	public String getAudiUser() {
		return audiUser;
	}
	public void setAudiUser(String audiUser) {
		this.audiUser = audiUser;
	}
	public Integer getAudiPass() {
		return audiPass;
	}
	public void setAudiPass(Integer audiPass) {
		this.audiPass = audiPass;
	}
	
}