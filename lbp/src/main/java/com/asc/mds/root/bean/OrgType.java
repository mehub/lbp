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
 * @version 版本信息 创建时间 2012-12-13 下午3:25:23
 */
@Entity
@Table(name = "MD_ORG_TYPE")
public class OrgType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3726305822177570778L;
	private String id;                            //ID
	private String code;                          //编码
	private String typeName;                      //名称
	private Integer state;                        //状态
	

	@Id
	@GeneratedValue(generator = "MdOrgTypeGenerator")
	@GenericGenerator(name = "MdOrgTypeGenerator", strategy = "com.asc.common.StringSequenceGenerator")
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
	public String getTypeName(){
		return this.typeName;
	}
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	@Column(name = "STATE")
	public Integer getState(){
		return this.state;
	}
	public void setState(Integer state){
		this.state = state;
	}
}