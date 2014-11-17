/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.state;

import com.asc.common.util.StringValueEnum;

/**
 * 类描述 .
 * 1：正常，2：文件内重复，3：文件外重复
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-8-9 上午11:07:35
 */
public enum RepeatState implements StringValueEnum {
	
	NORMAL(1, "正常"),
	IN_DUPLICATE(2, "文件内重复"),
	OUT_DUPLICATE(3, "文件外重复");
    
    private int code;
    private String value;
    
    private RepeatState() {}
    
	private RepeatState(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public synchronized static RepeatState getByCode(int code){
		RepeatState[] enums = RepeatState.values();
		for(RepeatState o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static RepeatState getByValue(String value){
		RepeatState[] enums = RepeatState.values();
		for(RepeatState o : enums){
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
