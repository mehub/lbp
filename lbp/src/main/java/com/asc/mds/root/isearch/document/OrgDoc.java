package com.asc.mds.root.isearch.document;

import java.lang.reflect.Field;


/**
 * 
 * 类描述 . 企业lucene document
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-1 上午11:55:51
 */
public class OrgDoc extends LuceneDoc {
    
    private String address;			//地址
    private String areaName;		//区域
    private String code;			//编码
    private String id;				//企业Id
    private String name;			//企业名
    private String parentAreaName;	//上级地区
    private String province;		//省
    private String typename;		//类型
    private String percent;			//匹配率
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAreaName() {
        return areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getParentAreaName() {
        return parentAreaName;
    }
    
    public void setParentAreaName(String parentAreaName) {
        this.parentAreaName = parentAreaName;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getTypename() {
        return typename;
    }
    
    public void setTypename(String typename) {
        this.typename = typename;
    }

    
    public String getPercent() {
        return percent;
    }

    
    public void setPercent(String percent) {
        this.percent = percent;
    }
    
    
    private String typeId;					//类型Id
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	/* 兼容workbench索引定义不一致 */
	public static Field get(String fieldName) throws SecurityException, NoSuchFieldException{
		
		if ("orgName".equals(fieldName)) {
			return OrgDoc.class.getDeclaredField("name");
			
		} else if ("typeName".equals(fieldName)) {
			return OrgDoc.class.getDeclaredField("typename");
			
		} else if ("region.province".equals(fieldName)) {
			return OrgDoc.class.getDeclaredField("province");
			
		} else if ("region.parentName".equals(fieldName)) {
			return OrgDoc.class.getDeclaredField("parentAreaName");
			
		} else if ("region.regionName".equals(fieldName)) {
			return OrgDoc.class.getDeclaredField("areaName");
			
		}
		return null;
	}

}
