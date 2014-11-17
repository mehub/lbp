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
public enum OperateState implements StringValueEnum {
	
	NORMAL(1, "正常"),
	UPDATING(2, "变更中");
    
    private int code;
    private String value;
    
    private OperateState() {}
    
	private OperateState(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public synchronized static OperateState getByCode(int code){
		OperateState[] enums = OperateState.values();
		for(OperateState o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static OperateState getByValue(String value){
		OperateState[] enums = OperateState.values();
		for(OperateState o : enums){
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
