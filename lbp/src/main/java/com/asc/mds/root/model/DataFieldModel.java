package com.asc.mds.root.model;


/**
 * 类描述 . 数据类型字段域
 * 1：企业
 * 2：产品
 * 3：区域
 * 4：厂商企业
 * 5：经销商企业
 * 6：企业产品
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:55:05
 */
public class DataFieldModel {
	
	private String id;                            //ID
	private String name;                          //名称
	private String ownerId;                       //'ASC'：安捷力模板定义
	private int dataType;						  //报表类别
	private String fieldName;                     //字段名
	private String propName;                      //属性名
	private String propType;					  //属性类型
	private String propFormat;					  //
	private int orderIndex;                       //顺序
	private String selectValue;                   //下拉值
	private int isOpen;							  //是否公开0：是,1否
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropType() {
		return propType;
	}
	public void setPropType(String propType) {
		this.propType = propType;
	}
	public String getPropFormat() {
		return propFormat;
	}
	public void setPropFormat(String propFormat) {
		this.propFormat = propFormat;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getSelectValue() {
		return selectValue;
	}
	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	
}