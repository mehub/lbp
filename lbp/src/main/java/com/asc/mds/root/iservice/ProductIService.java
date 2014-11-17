package com.asc.mds.root.iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.Product;
import com.asc.mds.root.bean.ProductTemp;
import com.asc.mds.root.model.ProductModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-12 下午4:18:25
 */
@Service
public class ProductIService implements IProductIService {
	
	public ProductIService(){}
	public ProductIService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	//search
	public List<Product> getSplitPage(int start, int limit, ProductModel model) {
		List<Product> res = dao.getSplitPage("select t from Product t " + getCondition(model), start, limit, new Object[0]);
		if(res != null && res.size() > 0){
			for(Product o : res) o.setAcName(o.getCommonname() + "/" + o.getCode() + "/" + o.getSpec() + "/" + o.getUnit());
		}
		return res;
	}

	public long getTotal(ProductModel model) {
		return dao.getTotal("select count(t.id) from Product t " + getCondition(model), new Object[0]);
	}
	
	//searchTemp
	public List<ProductTemp> getTempSplitPage(int start, int limit, ProductModel model) {
		return dao.getSplitPage("select t from ProductTemp t " + getCondition(model), start, limit, new Object[0]);
	}

	public long getTempTotal(ProductModel model) {
		return dao.getTotal("select count(t.id) from ProductTemp t " + getCondition(model), new Object[0]);
	}
	//condition
	private String getCondition(ProductModel model) {
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		
		if (model != null) {
			
			if(!StringUtils.isEmpty(model.getOwnerName())){
				hql.append(" and t.ownerName like '%" + model.getOwnerName() + "%'");
			}
			
			if(!StringUtils.isEmpty(model.getCommonname())){
				hql.append(" and t.commonname like '%" + model.getCommonname() + "%'");
			}
			if(!StringUtils.isEmpty(model.getCode())){
				hql.append(" and t.code like '%" + model.getCode() + "%'");
			}
			if(model.getState() != null && model.getState() > 0){
				hql.append(" and t.state = " + model.getState().intValue());
			}
			if(model.getAudiState() != null && model.getAudiState() > 0){
				hql.append(" and t.audiState = " + model.getAudiState().intValue());
			}
		}
		
		return hql.toString();
	}
	
	//get
	public Product get(String id){
		return dao.get(Product.class, id);
	}
	
	//getTemp
	public ProductTemp getTemp(String id){
		return dao.get(ProductTemp.class, id);
	}	
	
}