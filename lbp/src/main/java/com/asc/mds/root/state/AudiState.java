/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.state;

import com.asc.common.util.StringValueEnum;

/**
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-8-9 上午11:07:35
 */
public enum AudiState implements StringValueEnum {
	
	WAIT_AUDI(1, "待审"),
	AUDIT_AUTO(2, "直接通过"),
	AUDIT_MANU_PASS(3, "审核通过"),
	AUDIT_MANU_UNPASS(4, "审核未通过"),
	AUDIT_EXCEPT(5, "审核异常");
    
    private int code;
    private String value;
    
    private AudiState() {}
    
	private AudiState(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public synchronized static AudiState getByCode(int code){
		AudiState[] enums = AudiState.values();
		for(AudiState o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static AudiState getByValue(String value){
		AudiState[] enums = AudiState.values();
		for(AudiState o : enums){
			if(value.equals(o.value)){
				return o;
			}
		}
		return null;
	}

	@Override
	public Class<?> getClz() {
		return null;
	}
	
}
