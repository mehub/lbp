package com.asc.mds.root.state;

import com.asc.common.util.StringValueEnum;

public enum CheckState  implements StringValueEnum {
	
	HISTORY_DATA(0, "历史数据"),
	ADD_DATA(1, "新增数据"),
	NO_CHECK_DATA(2, "免检数据");
	
	private int code;
    private String value;
    
    private CheckState() {}
    private CheckState(int code, String value) {
		this.code = code;
		this.value = value;
	}
	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public synchronized static CheckState getByCode(int code){
		CheckState[] enums = CheckState.values();
		for(CheckState o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static CheckState getByValue(String value){
		CheckState[] enums = CheckState.values();
		for(CheckState o : enums){
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
