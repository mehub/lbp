/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.state;

import com.asc.common.util.StringValueEnum;

/**
 * 类描述 .
 * 1：待处理，2：处理中，3：导入完成，4：处理异常
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-8-9 上午11:07:35
 */
public enum ProcState implements StringValueEnum {
	
	WAIT_PROCESS(1, "待处理"),
	PROCESSING(2, "处理中"),
	IMPORT_SUCCESS(3, "导入成功"),
	PROCESS_EXCEPT(4, "处理异常");
    
    private int code;
    private String value;
    
    private ProcState() {}
    
	private ProcState(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public synchronized static ProcState getByCode(int code){
		ProcState[] enums = ProcState.values();
		for(ProcState o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static ProcState getByValue(String value){
		ProcState[] enums = ProcState.values();
		for(ProcState o : enums){
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
