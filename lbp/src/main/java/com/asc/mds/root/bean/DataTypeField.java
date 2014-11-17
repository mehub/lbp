package com.asc.mds.root.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "MD_DATA_FIELD")
public class DataTypeField {
	
	private String id;                            //ID
	private String name;                          //名称
	private String ownerId;                       //'ASC'：安捷力模板定义
	private Integer dataType;					  //数据类型
	private String fieldName;                     //字段名
	private String propName;                      //属性名
	private String propType;					  //属性类型
	private String propFormat;					  //
	private Integer orderIndex;                   //顺序
	private String selectValue;                   //下拉值
	private Integer isOpen;						  //是否公开0：是,1否
	

	@Id
	@GeneratedValue(generator = "CustomerFieldsGenerator")
	@GenericGenerator(name = "CustomerFieldsGenerator", strategy = "com.asc.common.StringSequenceGenerator")
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}

	@Column(name = "OWNER_ID")
	public String getOwnerId(){
		return this.ownerId;
	}
	public void setOwnerId(String ownerId){
		this.ownerId = ownerId;
	}

	@Column(name = "DATA_TYPE")
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	
	@Column(name = "FIELD_NAME")
	public String getFieldName(){
		return this.fieldName;
	}
	public void setFieldName(String fieldName){
		this.fieldName = fieldName;
	}

	@Column(name = "PROP_NAME")
	public String getPropName(){
		return this.propName;
	}
	public void setPropName(String propName){
		this.propName = propName;
	}

	@Column(name = "ORDER_INDEX")
	public Integer getOrderIndex(){
		return this.orderIndex;
	}
	public void setOrderIndex(Integer orderIndex){
		this.orderIndex = orderIndex;
	}

	@Column(name = "SELECT_VALUE")
	public String getSelectValue(){
		return this.selectValue;
	}
	public void setSelectValue(String selectValue){
		this.selectValue = selectValue;
	}
	
	@Column(name = "PROP_TYPE")
	public String getPropType() {
		return this.propType;
	}
	public void setPropType(String propType) {
		this.propType = propType;
	}
	@Column(name = "PROP_FORMAT")
	public String getPropFormat() {
		return this.propFormat;
	}
	public void setPropFormat(String propFormat) {
		this.propFormat = propFormat;
	}
	
	@Column(name = "IS_OPEN")
	public Integer getIsOpen() {
		return this.isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	
	
}