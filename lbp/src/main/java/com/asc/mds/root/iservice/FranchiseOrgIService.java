package com.asc.mds.root.iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.FranchiseOrg;
import com.asc.mds.root.bean.FranchiseOrgTemp;
import com.asc.mds.root.model.FranchiseOrgModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-12 下午4:12:01
 */
@Service
public class FranchiseOrgIService implements IFranchiseOrgIService {
	
	public FranchiseOrgIService(){}
	public FranchiseOrgIService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}

	@Override
	public FranchiseOrg getRelationUseName(String ownerOrgId, String name) {
		return dao.getUnique("from FranchiseOrg co where co.owner.id=? and co.orgName = ?", ownerOrgId, name);
	}
	
	public List<FranchiseOrg> getSplitPage(int start, int limit, FranchiseOrgModel model) {
		return dao.getSplitPage("select m from FranchiseOrg m " + getCondition(model) , start, limit, new Object[0]);
	}

	public long getTotal(FranchiseOrgModel model) {
		return dao.getTotal("select count(m.id) from FranchiseOrg m " + getCondition(model), new Object[0]);
	}
	  
	private String getCondition(FranchiseOrgModel model) {
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		
		if (model != null) {
			if(!StringUtils.isEmpty(model.getOwnerAscName())){
				if(model.getUseLike())
					hql.append(" and m.owner.orgName like '%"+model.getOwnerAscName()+"%' ");
				else
					hql.append(" and m.owner.orgName = '"+model.getOwnerAscName()+"' ");
			}
			if(!StringUtils.isEmpty(model.getOrgName())){
				if(model.getUseLike())
					hql.append(" and m.orgName like '%"+model.getOrgName()+"%' ");
				else
					hql.append(" and m.orgName = '"+model.getOrgName()+"' ");
			}
			if(!StringUtils.isEmpty(model.getCode())){
				hql.append(" and m.code = '" + model.getCode() + "'");
			}
			if(model.getState() != null && model.getState() > 0){
				hql.append(" and m.state = " + model.getState().intValue());
			}
			if(model.getAudiState() != null && model.getAudiState() > 0){
				hql.append(" and m.audiState = " + model.getAudiState().intValue());
			}
			if(!StringUtils.isEmpty(model.getStartTime())){
				hql.append(" and m.lastmodifyTime >= to_date('" + model.getStartTime() + "','yyyy-MM-dd hh24:mi:ss')");
			}
			if(!StringUtils.isEmpty(model.getEndTime())){
				hql.append(" and m.lastmodifyTime <= to_date('" + model.getEndTime() + "', 'yyyy-MM-dd hh24:mi:ss')");
			}
		}
		
		return hql.toString();
	}

	@Override
	public List<FranchiseOrgTemp> getTempSplitPage(int start, int limit,
			FranchiseOrgModel model) {
		return dao.getSplitPage("select m from FranchiseOrgTemp m " + getCondition(model) , start, limit, new Object[0]);
	}

	@Override
	public long getTempTotal(FranchiseOrgModel model) {
		return dao.getTotal("select count(m.id) from FranchiseOrgTemp m " + getCondition(model), new Object[0]);
	}

	@Override
	public FranchiseOrg get(String id) {
		return dao.get(FranchiseOrg.class, id);
	}

	@Override
	public FranchiseOrgTemp getTemp(String id) {
		return dao.get(FranchiseOrgTemp.class, id);
	}

	
}