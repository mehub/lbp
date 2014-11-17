package com.asc.mds.root.iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.FactoryOrgTemp;
import com.asc.mds.root.model.FactoryOrgModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-12 下午4:08:43
 */
@Service
public class FactoryOrgIService implements IFactoryOrgIService {
	
	public FactoryOrgIService(){}
	public FactoryOrgIService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	public List<FactoryOrg> getSplitPage(int start, int limit, FactoryOrgModel model) {
		return dao.getSplitPage("select m from FactoryOrg m " + getCondition(model) , start, limit, new Object[0]);
	}

	public long getTotal(FactoryOrgModel model) {
		return dao.getTotal("select count(m.id) from FactoryOrg m " + getCondition(model), new Object[0]);
	}
	
	public List<FactoryOrg> getSplitPageByType(int start, int limit, FactoryOrgModel model,String type) {
		String hql = "select m from FactoryOrg m " + getCondition(model);
 		if(type != null && !type.equals("")) {
			hql+=" and m.ascOrg.orgType.id='"+type+"'";
		}
		return dao.getSplitPage( hql, start, limit, new Object[0]);
	}
	public long getTotalByType(FactoryOrgModel model,String type) {
		String hql = "select count(m.id) from FactoryOrg m " + getCondition(model);
		if (type != null && !type.equals("")) {
			hql+=" and ascOrg.orgType.id='"+type+"'";
		}
		return dao.getTotal(hql, new Object[0]);
	}
	  
	@Override
	public List<FactoryOrgTemp> getTempSplitPage(int start, int limit,
			FactoryOrgModel model) {
		return dao.getSplitPage("select m from FactoryOrgTemp m " + getCondition(model) , start, limit, new Object[0]);
	}
	
	@Override
	public long getTempTotal(FactoryOrgModel model) {
		return dao.getTotal("select count(m.id) from FactoryOrgTemp m " + getCondition(model), new Object[0]);
	}
	
	private String getCondition(FactoryOrgModel model) {
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
					hql.append("and m.orgName like '%"+model.getOrgName()+"%' ");
				else
					hql.append("and m.orgName = '"+model.getOrgName()+"' ");
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
			if(!StringUtils.isEmpty(model.getOwnerAscId())){
				hql.append(" and m.ownerAscId ='" + model.getOwnerAscId() + "'");
			}
			if(!StringUtils.isEmpty(model.getPartnerAscName())){
				if(model.getUseLike())
					hql.append(" and m.ascOrg.orgName like '%" + model.getPartnerAscName() + "%'");
				else
					hql.append(" and m.ascOrg.orgName = '" + model.getPartnerAscName() + "'");
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
	public FactoryOrg get(String id) {
		return dao.get(FactoryOrg.class, id);
	}

	@Override
	public FactoryOrgTemp getTemp(String id) {
		return dao.get(FactoryOrgTemp.class, id);

	}

	@Override
	public FactoryOrg getRelation(String ownerId, String ascOrgId) {
		String sql = "from FactoryOrg co where co.owner.id = ? and co.ascOrg.id = ? order by co.id desc ";
		return dao.getUnique(sql, ownerId, ascOrgId);
	}

	@Override
	public FactoryOrg getRelationByCodeAndame(String ownerId, String relationCode,
			String relationName) {
		FactoryOrg factoryOrg=null;
		StringBuffer sql=new StringBuffer();
		sql.append("from FactoryOrg co where co.code=? and  co.owner.id = ? ");
		if(null!=relationName && !relationName.trim().equals("") ){
			sql.append(" and co.orgName = ? ");
			factoryOrg = dao.getUnique(sql.toString(),relationCode, ownerId, relationName);
		}else{
			factoryOrg = dao.getUnique(sql.toString(),relationCode, ownerId);
		}
		return factoryOrg;
	}

}