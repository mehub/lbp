package com.asc.mds.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.SysConstant;
import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.EntitySolrSetting;
import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.FactoryOrgHis;
import com.asc.mds.root.bean.FactoryOrgTemp;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.mds.root.iservice.FactoryOrgIService;
import com.asc.mds.root.model.FactoryOrgModel;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;
import com.asc.mds.root.state.OperateState;
import com.asc.mds.root.xservice.IFactoryOrgXService;
import com.asc.search.hibernate4.HibernateSearcher;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:21
 */
@Service
@WebService(endpointInterface = "com.asc.mds.root.xservice.IFactoryOrgXService")
public class FactoryOrgService extends FactoryOrgIService implements IFactoryOrgService, IFactoryOrgXService, IAuditService {
	
	public FactoryOrgService(){}
	public FactoryOrgService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}

	@Override
	public String add(FactoryOrgModel model) {
		FactoryOrgTemp dest = new FactoryOrgTemp();
		BeanUtils.copyProperties(model, dest, new String[] { "isDisable", "state", "audiState","fileId","qualityCheck" });
		
		if(isExistTemp(dest))
			return SysConstant.EXIST;
		
		if (model.getQualityCheck()==null) {
			dest.setQualityCheck(1);
		}
		
		dest.setCreateTime(new Date());
		dest.setCreator(SecurityManager.getCurrentUser().getUsername());
		dest.setLastmodifyTime(new Date());
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dest.setAudiState(AudiState.WAIT_AUDI);
		
		dao.save(dest);
		
		return SysConstant.SUCCESS;
	}

	@Override
	public String update(FactoryOrgModel model) {
		FactoryOrgTemp dest = getTemp(model.getId());
		if(dest == null) dest = new FactoryOrgTemp();
		BeanUtils.copyProperties(model, dest, new String[] { "isDisable", "state", "audiState","fileId","qualityCheck"});
		
		if(isExistTemp(dest))
			return SysConstant.EXIST;
		dest.setLastmodifyTime(new Date());
		dest.setAudiState(AudiState.WAIT_AUDI);
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dao.update(dest);
		return SysConstant.SUCCESS;
	}

	@Override
	public String audit(FactoryOrgModel model) {
		
		if(model.getAudiState().intValue() == AudiState.AUDIT_MANU_UNPASS.getCode()){
			dao.executeSQL("update MD_FACTORY_ORG_TEMP set audi_state=?, audi_memo=? where id=?", 
					new Object[]{model.getAudiState(), model.getAudiMemo(), model.getId()});
		} else {
			
			FactoryOrgTemp temp = dao.get(FactoryOrgTemp.class, model.getId());
			
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				temp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				dao.saveOrUpdate(temp);
				return SysConstant.EXIST;
			}
			
			FactoryOrg dest = new FactoryOrg();
			
			BeanUtils.copyProperties(temp, dest, new String[]{"id"});
			
			if(!StringUtils.isEmpty(model.getRelid())){
				dest.setId(model.getRelid());
			}
			dest.setAudiUser(SecurityManager.getCurrentUser().getUsername());
			dest.setState(OperateState.NORMAL);
			dao.saveOrUpdate(dest);
			
			
			temp.setAudiState(AudiState.AUDIT_MANU_PASS);
			temp.setAudiMemo(model.getAudiMemo());
			temp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
			temp.setRelid(dest.getId());
			dao.saveOrUpdate(temp);
			
			SolrUpdater.searcherUpdate(EntitySolrSetting.FactoryOrg);//solr searcher update
			
		}
		return SysConstant.SUCCESS;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String audit(String fileId, List<?> temp, AudiState audiState) {
		List<FactoryOrgTemp> temps = null;
		if(temp != null){
			temps = (List<FactoryOrgTemp>)temp;
		}else{
			StringBuffer hql = new StringBuffer("from FactoryOrgTemp where fileId=?");
			temps = dao.find(hql.toString(), new Object[]{fileId});
		}
		if(temps != null && temps.size() > 0){
			FactoryOrg p = null;
			for(FactoryOrgTemp s : temps){
				p = new FactoryOrg();
				p.setState(OperateState.NORMAL);
				s.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				if(isExist(s)){
					s.setAudiMemo(SysConstant.EXIST);
					s.setAudiState(AudiState.AUDIT_EXCEPT);
				} else {
					BeanUtils.copyProperties(s, p, new String[]{"id"});
					s.setAudiMemo("审核通过");
					s.setAudiState(audiState);
					dao.save(p);
					s.setRelid(p.getId());
				}
				dao.saveOrUpdate(s);
			}
			
			SolrUpdater.searcherUpdate(EntitySolrSetting.FactoryOrg);//solr searcher update
			
			return SysConstant.SUCCESS;
		}
		return null;
	}
	
	@Override
	public String qualityCheck(String id, Integer qualityCheck) {
		FactoryOrg factoryOrg = get(id);
		if (factoryOrg != null) {
			factoryOrg.setQualityCheck(qualityCheck);
			dao.update(factoryOrg);
			return SysConstant.SUCCESS;
		}
		return SysConstant.ERROR;
	}
	
	//remove
	public String remove(String id){
		dao.executeSQL("delete from MD_FACTORY_ORG where ID=?", new Object[]{id});
		dao.executeSQL("delete from MD_FACTORY_ORG_TEMP where RELID=?", new Object[]{id});
		return SysConstant.SUCCESS;
	}
	
	
	//isExist
	private boolean isExist(FactoryOrgTemp dest) {
		StringBuffer hql = new StringBuffer("from FactoryOrg where (ownerAscId=? and partnerAscId =?)");
		
		FactoryOrg obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getPartnerAscId(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getPartnerAscId()});
		}
		
		if(obj != null){
			return true;
		}
		return false;
	}
	
	//isExist
	private boolean isExist(FactoryOrg dest) {
		StringBuffer hql = new StringBuffer("from FactoryOrg where (ownerAscId=? and partnerAscId =?)");
		
		FactoryOrg obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getPartnerAscId(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getPartnerAscId()});
		}
		
		if(obj != null){
			return true;
		}
		return false;
	}
	
	//isExist
	private boolean isExistTemp(FactoryOrgTemp dest) {
		StringBuffer hql = new StringBuffer("from FactoryOrgTemp where (ownerAscId=? and partnerAscId =?)");
		
		FactoryOrgTemp obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getPartnerAscId(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getPartnerAscId()});
		}
		
		if(obj != null){
			return true;
		}
		return false;
	}
	
	/*-----------------------------------Search--------------------------------------------------*/
	@Autowired
	private HibernateSearcher hibernateSearcher;
	public void setHibernateSearcher(HibernateSearcher hibernateSearcher) {
		this.hibernateSearcher = hibernateSearcher;
	}
	public List<FactoryOrg> search(int start, int limit, FactoryOrgModel model){
		return hibernateSearcher.queryByKeyWord(FactoryOrg.class, new String[]{"orgName"}, model.getOrgName(), start, limit);
	}
	
	/*-----------------------------------XService Implement--------------------------------------*/
	//batch audit
	public String batchAudit(String... id){
		String ids = Arrays.toString(id);
		String hql = "select t from FactoryOrgTemp t where id in (?)";
		List<FactoryOrgTemp> list = dao.find(hql, ids.substring(1, ids.length()-1));
		if(list == null || list.size() < 1) return SysConstant.NODATA;
		
		List<FactoryOrg> dest = new ArrayList<FactoryOrg>();
		for(FactoryOrgTemp temp : list){
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				continue;
			}
			
			temp.setAudiState(AudiState.AUDIT_MANU_PASS);
			
			FactoryOrg obj = new FactoryOrg();
			BeanUtils.copyProperties(temp, obj, new String[]{"id"});
			dest.add(obj);
		}
		
		dao.saveOrUpdateAll(dest);
		dao.saveOrUpdateAll(list);
		
		SolrUpdater.searcherUpdate(EntitySolrSetting.FactoryOrg);//solr searcher update
		
		return SysConstant.SUCCESS;
	}

	public int xupdate(String[] fields, Object[] fieldValues, String[] conditions, Object[] values){
		if(fields != null && fieldValues != null
				&& fields.length == fieldValues.length
				&& conditions != null && values != null
				&& conditions.length == values.length){
			//sql
			StringBuilder sql = new StringBuilder("UPDATE MD_FACTORY_ORG SET ");
			for (int i = 0; i < fields.length; i++) {
				if(i>0)sql.append(", ");
				sql.append(fields[i]).append("=?");
			}
			sql.append(" where ");
			for (int i = 0; i < conditions.length; i++) {
				if(i>0)sql.append(", ");
				sql.append(conditions[i]).append("=?");
			}
			//params
			Object[] params = Arrays.copyOf(fieldValues, fieldValues.length+values.length);
			System.arraycopy(values, 0, params, fieldValues.length, conditions.length);
			
			int res = dao.executeSQL(sql.toString(), params);
			return res;
		} else {
			return 0;
		}
	}
	@Override
	public String autoAuditForBench(FactoryOrgModel factoryOrgModel) {
		
		FactoryOrg dest = new FactoryOrg();
		BeanUtils.copyProperties(factoryOrgModel, dest, new String[]{"id", "isDisable", "state", "audiState","fileId","qualityCheck"});
		if(isExist(dest)){
			dest = dao.getUnique("from FactoryOrg where (ownerAscId=? and orgName =?)", new Object[]{dest.getOwnerAscId(), dest.getOrgName()});
			if(dest != null)
				return dest.getId();
		}
		dest.setId(null);
		String res = (String) dao.save(dest);
		SolrUpdater.searcherUpdate(EntitySolrSetting.FactoryOrg);//solr searcher update
		
		return res;
	}

	@Override
	public String autoAuditHisForBench(FactoryOrgHis factoryOrgHis) {
		String res = (String) dao.save(factoryOrgHis);
		return res;
	}

	@Override
	public boolean porQualityCheck(String relationId) {
		String sql="update MD_FACTORY_ORG set QUALITY_CHECK=? where ID=?";
		int flag=dao.executeSQL(sql, 1,relationId);
		if(flag==1){
			return true;				
		}
		return false;
	}

	@Override
	public boolean delRelation(String relationId) {
		String sql="delete MD_FACTORY_ORG  where ID=?";
		int flag=dao.executeSQL(sql, relationId);
		if(flag==1){
			return true;				
		}
		return false;
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

	@Override
	public void xSave(FactoryOrg org) {
		dao.save(org);
		SolrUpdater.searcherUpdate(EntitySolrSetting.FactoryOrg);//solr searcher update
	}

}