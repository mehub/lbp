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
import com.asc.mds.root.bean.FranchiseOrg;
import com.asc.mds.root.bean.FranchiseOrgTemp;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.mds.root.iservice.FranchiseOrgIService;
import com.asc.mds.root.model.FranchiseOrgModel;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;
import com.asc.mds.root.state.OperateState;
import com.asc.mds.root.xservice.IFranchiseOrgXService;
import com.asc.search.hibernate4.HibernateSearcher;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:27
 */
@Service
@WebService(endpointInterface = "com.asc.mds.root.xservice.IFranchiseOrgXService")
public class FranchiseOrgService extends FranchiseOrgIService implements IFranchiseOrgService, IFranchiseOrgXService, IAuditService {
	
	public FranchiseOrgService(){}
	public FranchiseOrgService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String audit(String fileId, List<?> temp, AudiState audiState) {
		List<FranchiseOrgTemp> temps = null;
		if(temp != null){
			temps = (List<FranchiseOrgTemp>)temp;
		}else{
			StringBuffer hql = new StringBuffer("from FranchiseOrgTemp where fileId=?");
			temps = dao.find(hql.toString(), new Object[]{fileId});
		}
		if(temps != null && temps.size() > 0){
			FranchiseOrg p = null;
			for(FranchiseOrgTemp s : temps){
				p = new FranchiseOrg();
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
			return SysConstant.SUCCESS;
		}
		return null;
	}

	@Override
	public String add(FranchiseOrgModel model) {
		FranchiseOrgTemp dest = new FranchiseOrgTemp();
		BeanUtils.copyProperties(model, dest, new String[] { "isDisable", "state", "audiState","fileId","qualityCheck" });
		
		if(isExistTemp(dest))
			return SysConstant.EXIST;
		
		if (model.getQualityCheck()==null) {
			dest.setQualityCheck(1);
		}
		
		dest.setCreateTime(new Date());
		if(SecurityManager.getCurrentUser()!=null)
			dest.setCreator(SecurityManager.getCurrentUser().getUsername());
		dest.setLastmodifyTime(new Date());
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dest.setAudiState(AudiState.WAIT_AUDI);
		
		dao.save(dest);
		
		return SysConstant.SUCCESS;
	}

	@Override
	public String update(FranchiseOrgModel model) {
		FranchiseOrgTemp dest = getTemp(model.getId());
		if(dest == null) dest = new FranchiseOrgTemp();
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
	public String audit(FranchiseOrgModel model) {
		
		if(model.getAudiState().intValue() == AudiState.AUDIT_MANU_UNPASS.getCode()){
			dao.executeSQL("update MD_FRANCHISE_ORG_TEMP set audi_state=?, audi_memo=? where id=?", 
					new Object[]{model.getAudiState(), model.getAudiMemo(), model.getId()});
		} else {
			FranchiseOrgTemp temp = dao.get(FranchiseOrgTemp.class, model.getId());
			
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				temp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				dao.saveOrUpdate(temp);
				return SysConstant.EXIST;
			}
			
			FranchiseOrg dest = new FranchiseOrg();
			
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
		}
		
		return SysConstant.SUCCESS;
	}
	
	@Override
	public String qualityCheck(String id, Integer qualityCheck) {
		FranchiseOrg franchiseOrg = get(id);
		if (franchiseOrg != null) {
			franchiseOrg.setQualityCheck(qualityCheck);
			dao.update(franchiseOrg);
			return SysConstant.SUCCESS;
		}
		return SysConstant.ERROR;
	}
	
	//remove
	public String remove(String id){
		dao.executeSQL("delete from MD_FRANCHISE_ORG where ID=?", new Object[]{id});
		dao.executeSQL("delete from MD_FRANCHISE_ORG_TEMP where RELID=?", new Object[]{id});
		return SysConstant.SUCCESS;
	}
	
	
	//isExist
	private boolean isExist(FranchiseOrgTemp dest) {
		StringBuffer hql = new StringBuffer("from FranchiseOrg where (ownerAscId=? and partnerAscId =?)");
		
		FranchiseOrg obj = null;
		
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
	
	private boolean isExist(FranchiseOrg dest) {
		StringBuffer hql = new StringBuffer("from FranchiseOrg where (ownerAscId=? and orgName =?)");
		
		FranchiseOrg obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getOrgName(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getOrgName()});
		}
		
		if(obj != null){
			return true;
		}
		return false;
	}
	

	//isExist
	private boolean isExistTemp(FranchiseOrgTemp dest) {
		StringBuffer hql = new StringBuffer("from FranchiseOrgTemp where (ownerAscId=? and orgName =?)");
		
		FranchiseOrgTemp obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getOrgName(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getOrgName()});
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
	public List<FranchiseOrg> search(int start, int limit, FranchiseOrgModel model){
		return hibernateSearcher.queryByKeyWord(FranchiseOrg.class, new String[]{"orgName"}, model.getOrgName(), start, limit);
	}
	
	/*-----------------------------------XService Implement--------------------------------------*/
	//batch audit
	public String batchAudit(String... id){
		String ids = Arrays.toString(id);
		String hql = "select t from FranchiseOrgTemp t where id in (?)";
		List<FranchiseOrgTemp> list = dao.find(hql, ids.substring(1, ids.length()-1));
		if(list == null || list.size() < 1) return SysConstant.NODATA;
		
		List<FranchiseOrg> dest = new ArrayList<FranchiseOrg>();
		for(FranchiseOrgTemp temp : list){
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				continue;
			}
			
			temp.setAudiState(AudiState.AUDIT_MANU_PASS);
			
			FranchiseOrg obj = new FranchiseOrg();
			BeanUtils.copyProperties(temp, obj, new String[]{"id"});
			dest.add(obj);
		}
		
		dao.saveOrUpdateAll(dest);
		dao.saveOrUpdateAll(list);
		
		return SysConstant.SUCCESS;
	}

	@Override
	public String autoAuditForBench(FranchiseOrgModel model) {
		FranchiseOrg dest = new FranchiseOrg();
		BeanUtils.copyProperties(model, dest, new String[]{"id", "isDisable", "state", "audiState","fileId","qualityCheck"});
		if(isExist(dest)){
			dest = dao.getUnique("from FranchiseOrg where (ownerAscId=? and orgName =?)", new Object[]{dest.getOwnerAscId(), dest.getOrgName()});
			if(dest != null)
				return dest.getId();
		}
		dest.setId(null);
		String res = (String) dao.save(dest);
		SolrUpdater.searcherUpdate(EntitySolrSetting.FactoryOrg);//solr searcher update
		
		return res;
	}

	@Override
	public boolean porQualityCheck(String franchiseOrgId) {
		String sql="update MD_FRANCHISE_ORG set QUALITY_CHECK=? where ID=?";
		int flag=dao.executeSQL(sql, 1,franchiseOrgId);
		if(flag==1){
			return true;				
		}
		return false;
	}

	@Override
	public boolean delFranchiseRelation(String franchiseOrgId) {
		String sql="delete MD_FRANCHISE_ORG  where ID=?";
		int flag=dao.executeSQL(sql, franchiseOrgId);
		if(flag==1){
			return true;				
		}
		return false;
	}

	@Override
	public String xadd(FranchiseOrgModel model) {
		return add(model);
	}
	
}