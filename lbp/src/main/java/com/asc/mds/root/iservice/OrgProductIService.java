package com.asc.mds.root.iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.OrgProduct;
import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.model.OrgProductModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-12 下午4:12:28
 */
@Service
public class OrgProductIService implements IOrgProductIService {
	
	public OrgProductIService(){}
	public OrgProductIService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}

	public List<OrgProduct> getSplitPage(int start, int limit, OrgProductModel model) {
		return dao.getSplitPage("select t from OrgProduct t " + getCondition(model), start, limit, new Object[0]);
	}

	public long getTotal(OrgProductModel model) {
		return dao.getTotal("select count(t.id) from OrgProduct t " + getCondition(model), new Object[0]);
	}
	  
	private String getCondition(OrgProductModel model) {
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		
		if (model != null) {
			if(!StringUtils.isEmpty(model.getOwnerAscId())){
				hql.append(" and t.ownerAscId ='" + model.getOwnerAscId() + "'");
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
	
	@Override
	public List<OrgProductTemp> getTempSplitPage(int start, int limit,
			OrgProductModel model) {
		return dao.getSplitPage("select t from OrgProductTemp t " + getCondition(model), start, limit, new Object[0]);
	}

	@Override
	public long getTempTotal(OrgProductModel model) {
		return dao.getTotal("select count(t.id) from OrgProductTemp t " + getCondition(model), new Object[0]);
	}

	@Override
	public OrgProduct get(String id) {
		return dao.get(OrgProduct.class, id);
	}

	@Override
	public OrgProductTemp getTemp(String id) {
		return dao.get(OrgProductTemp.class, id);
	}
	
	@Override
	public OrgProduct getRelationById(String ownerId, String ascProductId) {
		String sql = " from OrgProduct where ownerAscId = ? and ascProduct.id = ?";
		return dao.getUnique(sql, ownerId, ascProductId);
	}

	@Override
	public OrgProduct getRelationByCode(String ownerId, String code, String dataOwner) {
		String sql = " from OrgProduct where ownerAscId = ? and code = ? and ascProduct.ownerId = ? ";
		return dao.getUnique(sql, ownerId, code, dataOwner);
	}
	
}