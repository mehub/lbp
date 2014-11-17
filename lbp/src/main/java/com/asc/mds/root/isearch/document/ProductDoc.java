package com.asc.mds.root.isearch.document;

import java.lang.reflect.Field;

import com.asc.mds.root.isearch.helper.TextComparer;

/**
 * 
 * 类描述 . 产品lucene document
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-1 上午11:56:15
 */
public class ProductDoc extends LuceneDoc {

	private String code; 			//编码
	private String id; 				//产品Id
	private String commonName; 		//产品名
	private String chemistryName; 	//化学名
	private String unit; 			//单位
	private String spec; 			//规格
	private String ownerOrgName; 	//所属企业
	private String origin; 			//生产厂家
	private String percent; 		//匹配率

	/**
	 * 比较产品信息，获得匹配率
	 * @author yangzelai
	 * @param productUnionCode
	 */
	public void comparison(String productUnionCode) {
		float f = TextComparer.compare(productUnionCode, this.getUnionCode()) * 100;
		int df = Math.round(f);
		if (df == 100) {
			df = (int) (f);
		}
		this.setPercent(df + "%");
	}

	public String getUnionCode() {
		StringBuilder sb = new StringBuilder();
		if (this.getCode() != null)
			sb.append(this.getCode());
		if (this.getCommonName() != null)
			sb.append(this.getCommonName());
		if (this.getSpec() != null)
			sb.append(this.getSpec());
		if (this.getUnit() != null)
			sb.append(this.getUnit());
		return sb.toString();
	}

	public ProductDoc() {

	}

	public ProductDoc(String str) {
		String[] strs = str.split(",");
		if (strs.length >= 8) {
			this.setCode(strs[0]);
			this.setId(strs[1]);
			this.setCommonName(strs[2]);
			this.setChemistryName(strs[3]);
			this.setUnit(strs[4]);
			this.setSpec(strs[5]);
			this.setOwnerOrgName(strs[6]);
			this.setOrigin(strs[7]);
		}
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

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getChemistryName() {
		return chemistryName;
	}

	public void setChemistryName(String chemistryName) {
		this.chemistryName = chemistryName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getOwnerOrgName() {
		return ownerOrgName;
	}

	public void setOwnerOrgName(String ownerOrgName) {
		this.ownerOrgName = ownerOrgName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	private String ownerId;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	/* 兼容workbench索引定义不一致 */
	public static Field get(String fieldName) throws SecurityException, NoSuchFieldException{
		
		if ("commonname".equals(fieldName)) {
			return ProductDoc.class.getDeclaredField("commonName");
			
		} else if ("scientificname".equals(fieldName)) {
			return ProductDoc.class.getDeclaredField("chemistryName");
			
		} else if ("ownerName".equals(fieldName)) {
			return ProductDoc.class.getDeclaredField("ownerOrgName");
			
		} 
		return null;
	}
}
