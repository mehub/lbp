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
public enum DataState implements StringValueEnum {
	
	ENABLE(0, "启用"),
	DISABLE(1, "停用");
    
    private int code;
    private String value;
    
    private DataState() {}
    
	private DataState(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public synchronized static DataState getByCode(int code){
		DataState[] enums = DataState.values();
		for(DataState o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static DataState getByValue(String value){
		DataState[] enums = DataState.values();
		for(DataState o : enums){
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
