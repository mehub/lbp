/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root;

import com.asc.mds.root.bean.CustomerAlias;
import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.FranchiseOrg;
import com.asc.mds.root.bean.Org;
import com.asc.mds.root.bean.OrgProduct;
import com.asc.mds.root.bean.Product;
import com.asc.mds.root.bean.Region;
import com.asc.mds.root.isearch.document.CustAliasDoc;
import com.asc.mds.root.isearch.document.OrgDoc;
import com.asc.mds.root.isearch.document.ProductDoc;
import com.asc.mds.root.isearch.document.RegionDoc;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-26 下午5:26:04
 */
public enum EntitySolrSetting {
	
	Org(1, "企业", Org.class, OrgDoc.class, "org/query"),
	Product(2, "产品", Product.class, ProductDoc.class, "product/query"),
	Region(3, "区域", Region.class, RegionDoc.class, "region/query"),
	FactoryOrg(4, "厂商企业", FactoryOrg.class, null, "factoryorg/query"),
	FranchiseOrg(5, "经销商企业", FranchiseOrg.class, null, "franchiseorg/query"),
	OrgProduct(6, "企业产品", OrgProduct.class, null, "orgproduct/query"),
	CustomerAlias(7, "客户别称", CustomerAlias.class, CustAliasDoc.class, "custalias/query");
    
    private int code;
    private String value;
    private Class<?> clz;
    private Class<?> docClz;
    private String solrName;
    
    private int isRebuilding = 0;
    
    private EntitySolrSetting() {}
    
	private EntitySolrSetting(int code, String value, Class<?> clz, String solrName) {
		this.code = code;
		this.value = value;
		this.clz = clz;
		this.solrName = solrName;
	}
	
	private EntitySolrSetting(int code, String value, Class<?> clz, Class<?> docClz, String solrName) {
		this.code = code;
		this.value = value;
		this.clz = clz;
		this.docClz = docClz;
		this.solrName = solrName;
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

	public Class<?> getDocClz() {
		return docClz;
	}

	public void setDocClz(Class<?> docClz) {
		this.docClz = docClz;
	}

	public String getSolrName() {
		return solrName;
	}
	
	public int getIsRebuilding() {
		return isRebuilding;
	}

	public void setIsRebuilding(int isRebuilding) {
		this.isRebuilding = isRebuilding;
	}

	public synchronized static EntitySolrSetting getByClass(String clazz){
		EntitySolrSetting[] enums = EntitySolrSetting.values();
		for(EntitySolrSetting o : enums){
			if(o.clz.getName().equals(clazz)){
				return o;
			}
		}
		return null;
	}
	
	public synchronized static EntitySolrSetting getByCode(int code){
		EntitySolrSetting[] enums = EntitySolrSetting.values();
		for(EntitySolrSetting o : enums){
			if(o.code == code){
				return o;
			}
		}
		return null;
	}
	
}
