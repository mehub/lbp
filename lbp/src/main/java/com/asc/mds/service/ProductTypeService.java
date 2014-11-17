package com.asc.mds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.mds.root.bean.ProductType;
import com.asc.mds.root.model.ProductTypeModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:47
 */
@Service
public class ProductTypeService implements IProductTypeService {
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}

	public List<ProductType> getSplitPage(int start, int limit, ProductTypeModel model) {
		return dao.getSplitPage("select t from ProductType t " + getCondition(model), start, limit, new Object[0]);
	}

	public long getTotal(ProductTypeModel model) {
		return dao.getTotal("select count(t.id) from ProductType t " + getCondition(model), new Object[0]);
	}
	  
	private String getCondition(ProductTypeModel model) {
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1=1 ");
		
		if (model != null) {
			
		}
		
		return sb.toString();
	}
	
}