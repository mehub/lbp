package com.asc.mds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.CustomerAlias;
import com.asc.search.hibernate4.HibernateSearcher;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-27 上午10:18:37
 */
@Service
public class CustomerAliasService implements ICustomerAliasService {
	
	public CustomerAliasService(){}
	public CustomerAliasService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	public List<CustomerAlias> getSplitPage(int start, int limit, CustomerAlias model) {
		return dao.getSplitPage("select m from CustomerAlias m " + getCondition(model) , start, limit, new Object[0]);
	}

	public long getTotal(CustomerAlias model) {
		return dao.getTotal("select count(m.id) from CustomerAlias m " + getCondition(model), new Object[0]);
	}
	
	private String getCondition(CustomerAlias model) {
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		
		if (model != null) {
			if(!StringUtils.isEmpty(model.getCtName())){
				if(model.getUseLike())
					hql.append("and m.ctName like '%"+model.getCtName()+"%' ");
				else
					hql.append("and m.ctName = '"+model.getCtName()+"' ");
			}
			if(!StringUtils.isEmpty(model.getOrgName())){
				if(model.getUseLike())
					hql.append("and m.orgName like '%"+model.getOrgName()+"%' ");
				else
					hql.append("and m.orgName = '"+model.getOrgName()+"' ");
			}
			if(!StringUtils.isEmpty(model.getCode())){
				hql.append(" and m.code = '" + model.getCode() + "'");
			}			
			if(!StringUtils.isEmpty(model.getOwnerAscId())){
				hql.append(" and m.ownerAscId ='" + model.getOwnerAscId() + "'");
			}
		}
		return hql.toString();
	}
	
	/*-----------------------------------Search--------------------------------------------------*/
	@Autowired
	private HibernateSearcher hibernateSearcher;
	public void setHibernateSearcher(HibernateSearcher hibernateSearcher) {
		this.hibernateSearcher = hibernateSearcher;
	}
	public List<CustomerAlias> search(int start, int limit, CustomerAlias model){
		return hibernateSearcher.queryByKeyWord(CustomerAlias.class, new String[]{"ctName"}, model.getCtName(), start, limit);
	}
	
}