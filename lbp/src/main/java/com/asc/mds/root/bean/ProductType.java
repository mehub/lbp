package com.asc.mds.root.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午3:25:39
 */
@Entity
@Table(name = "MD_PRODUCT_TYPE")
public class ProductType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7974394179369444868L;
	private String id;                            //ID
	private String code;                          //编码
	private String name;                          //名称
	private String parentId;                      //上级分类ID
	

	@Id
	@GeneratedValue(generator = "MdRegionGenerator")
	@GenericGenerator(name = "MdRegionGenerator", strategy = "com.asc.common.StringSequenceGenerator")
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
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}

	@Column(name = "PARENT_ID")
	public String getParentId(){
		return this.parentId;
	}
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
}