/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.state;

import com.asc.common.util.StringValueEnum;
import com.asc.mds.root.bean.FactoryOrgTemp;
import com.asc.mds.root.bean.FranchiseOrgTemp;
import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.bean.OrgTemp;
import com.asc.mds.root.bean.ProductTemp;
import com.asc.mds.root.bean.Region;

/**
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-8-9 上午11:07:35
 */
public enum DataType implements StringValueEnum {
	
	Org(1, "企业", OrgTemp.class, "orgService"),
	Product(2, "产品", ProductTemp.class, "productService"),
	Region(3, "区域", Region.class, "regionService"),
	FactoryOrg(4, "厂商企业", FactoryOrgTemp.class, "factoryOrgService"),
	FranchiseOrg(5, "经销商企业", FranchiseOrgTemp.class, "franchiseOrgService"),
	OrgProduct(6, "企业产品", OrgProductTemp.class, "orgProductService");
    
    private int code;
    private String value;
    private Class<?> clz;
    private String serviceName;
    
    private DataType() {}
    
	private DataType(int code, String value, Class<?> clz, String serviceName) {
		this.code = code;
		this.value = value;
		this.clz = clz;
		this.serviceName = serviceName;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}
	
	public Class<?> getClz() {
		return clz;
	}

	public String getServiceName() {
		return serviceName;
	}

	public synchronized static DataType getByCode(int code){
		DataType[] enums = DataType.values();
		for(DataType o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static DataType getByValue(String value){
		DataType[] enums = DataType.values();
		for(DataType o : enums){
			if(value.equals(o.value)){
				return o;
			}
		}
		return null;
	}
	
}
