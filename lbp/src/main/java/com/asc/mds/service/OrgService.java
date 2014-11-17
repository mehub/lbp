package com.asc.mds.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.SysConstant;
import com.asc.common.persist.IPersistDao;
import com.asc.common.persist.jdbc.AbstractJdbcDao;
import com.asc.mds.root.EntitySolrSetting;
import com.asc.mds.root.bean.Org;
import com.asc.mds.root.bean.OrgTemp;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.mds.root.iservice.OrgIService;
import com.asc.mds.root.model.OrgModel;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;
import com.asc.mds.root.state.OperateState;
import com.asc.mds.root.xservice.IOrgXService;
import com.asc.search.hibernate4.HibernateSearcher;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:35
 */
@Service
@WebService(endpointInterface = "com.asc.mds.root.xservice.IOrgXService")
public class OrgService extends OrgIService implements IOrgService, IOrgXService, IAuditService {

	public OrgService(){}
	public OrgService(IPersistDao dao){
		this.dao = dao;
	}
	@Autowired
	private IPersistDao dao;
	@Autowired
	private AbstractJdbcDao oracleJdbcDao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	public void setOracleJdbcDao(AbstractJdbcDao oracleJdbcDao) {
		this.oracleJdbcDao = oracleJdbcDao;
	}
	
	private OrgTemp getOrgTempByRelId(String relid){
		StringBuffer hql = new StringBuffer("from OrgTemp o where o.relid=?");
		List<OrgTemp> list = dao.find(hql.toString(), new Object[]{relid});
		if(list != null && list.size() > 0) return list.get(0);
		return null;
	}
	
	@Override
	//当flag=true时，是在修改正式表的时候修改临时表的数据,model为正式表的数据
	//当flag=false时，在新增或修改临时表时的操作，model为临时表的数据
	public void update(OrgModel model,boolean flag) {
		OrgTemp orgTemp=new OrgTemp();
		BeanUtils.copyProperties(model, orgTemp,new String[] { "isDisable","audiState","audiPass"});
		if(flag){//修改正式表的时候修改临时表的数据
			//修改正式表，不直接通过审核时
			if(!(!StringUtils.isBlank(model.getAudiPass()) && Integer.parseInt(model.getAudiPass())==AudiState.AUDIT_AUTO.getCode())){
				Org org=dao.get(Org.class, model.getId());
				org.setState(OperateState.UPDATING);
				dao.update(org);
			}
			orgTemp=this.getOrgTempByRelId(model.getId());
			if(orgTemp == null) orgTemp = new OrgTemp();
			BeanUtils.copyProperties(model, orgTemp,new String[]{
				"isDisable","audiState","audiPass","id","createTime","creator","relid"});
		}else{//新增或修改临时表时的操作
			if(StringUtils.isBlank(model.getId())){
				orgTemp.setId(null);
				orgTemp.setCreateTime(new Date());
				orgTemp.setCreator(SecurityManager.getCurrentUser().getUsername());
			}
		}
		orgTemp.setIsDisable(DataState.getByCode(model.getIsDisable()));
		orgTemp.setLastmodifyTime(new Date());
		orgTemp.setAudiState(AudiState.WAIT_AUDI);
		dao.saveOrUpdate(orgTemp);
		//审核状态时为“直接通过：2”
		if(!StringUtils.isBlank(model.getAudiPass()) && Integer.parseInt(model.getAudiPass())==AudiState.AUDIT_AUTO.getCode()){
			audit(orgTemp,AudiState.AUDIT_AUTO.getCode());
		}
	}
	
	public void audit(OrgTemp orgTemp, Integer state) {
		if(isExist(orgTemp)){
			orgTemp.setAudiMemo(SysConstant.EXIST);
			orgTemp.setAudiState(AudiState.AUDIT_EXCEPT);
			orgTemp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
			orgTemp.setLastmodifyTime(new Date());
			dao.update(orgTemp);
			return;
		}
		//OrgTemp orgTemp=dao.get(OrgTemp.class, id);
		//临时表审核状态为“审核未通过：4”，不新增主表
		if(state!=AudiState.AUDIT_MANU_UNPASS.getCode()){
			Org org=new Org();
			BeanUtils.copyProperties(orgTemp, org,new String[] { "id" });
			if(StringUtils.isBlank(orgTemp.getRelid())){
				//临时表中的主表id为空，主表id设置为null，执行保存操作
				org.setId(null);
			}else{
				//临时表中的主表id部位空，主表id设置为relid，执行修改操作
				org.setId(orgTemp.getRelid());
				org.setLastmodifyTime(new Date());
			}
			org.setAudiUser(SecurityManager.getCurrentUser().getUsername());
			org.setState(OperateState.NORMAL);
			dao.saveOrUpdate(org);
			
			SolrUpdater.searcherUpdate(EntitySolrSetting.Org);//solr searcher update
			
			orgTemp.setRelid(org.getId());
		}
		orgTemp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
		orgTemp.setAudiState(AudiState.getByCode(state));
		orgTemp.setLastmodifyTime(new Date());
		dao.update(orgTemp);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public String audit(String fileId, List<?> temps, AudiState audiState) {
		List<OrgTemp> temp = null;
		if(temps != null){
			temp = (List<OrgTemp>)temps;
		}else{
			StringBuffer hql = new StringBuffer("from OrgTemp o where o.fileId=?");
			temp = dao.find(hql.toString(), new Object[]{fileId});
		}
		if(temp != null && temp.size() > 0){
			List<Org> dest = new ArrayList<Org>();
			StringBuffer msg = new StringBuffer();
			for(OrgTemp s : temp){
				Org p = new Org();
				p.setState(OperateState.NORMAL);
				s.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				if(isExist(s)){
					msg.append(s.getId() + " ");
					s.setAudiMemo(SysConstant.EXIST);
					s.setAudiState(AudiState.AUDIT_EXCEPT);
				} else {
					BeanUtils.copyProperties(s, p, new String[]{"id"});
					p.setState(OperateState.NORMAL);
					dao.save(p);
					dest.add(p);
					s.setAudiMemo("审核通过");
					s.setAudiState(audiState);
					s.setRelid(p.getId());
				}
				dao.update(s);
			}
			SolrUpdater.searcherUpdate(EntitySolrSetting.Org);//solr searcher update
			
			return msg.length() > 0 ? msg.toString() : SysConstant.SUCCESS;
		}
		return SysConstant.NODATA;
	}

	@Override
	public void disabled(String orgId,String state) {
		Org org=dao.get(Org.class, orgId);
		DataState d=DataState.getByCode(Integer.parseInt(state));
		org.setIsDisable(d);
		dao.update(org);
	}

	@Override
	public void remove(String[] ids) {
		for(String id:ids){
			Org org=dao.get(Org.class, id);
			OrgTemp orgTemp=this.getOrgTempByRelId(org.getId());
			if(orgTemp != null)dao.delete(orgTemp);
			if(org != null)dao.delete(org);
		}
		SolrUpdater.searcherUpdate(EntitySolrSetting.Org);//solr searcher update
	}

	//isExist
	private boolean isExist(OrgTemp dest) {
		StringBuffer hql = new StringBuffer("from Org where (code=? or orgName =?)");
		
		Org obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getCode(), dest.getOrgName(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getCode(), dest.getOrgName()});
		}
		
		if(obj != null){
			return true;
		}
		return false;
	}
	
	private Org isExist(Org dest) {
		StringBuffer hql = new StringBuffer("from Org where (orgName =?)");
		
		Org obj = null;
		
		if(!StringUtils.isEmpty(dest.getId())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getCode(), dest.getOrgName(), dest.getId()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getCode(), dest.getOrgName()});
		}
		
		if(obj != null){
			return obj;
		}
		return null;
	}
	
	/*-----------------------------------Search--------------------------------------------------*/
	@Autowired
	private HibernateSearcher hibernateSearcher;
	public void setHibernateSearcher(HibernateSearcher hibernateSearcher) {
		this.hibernateSearcher = hibernateSearcher;
	}
	public List<Org> search(int start, int limit, OrgModel model){
		List<Query> combine = new ArrayList<Query>();
		if(!StringUtils.isBlank(model.getOrgName())){
			combine.add(hibernateSearcher.createQueryWithFuzzy(Org.class, new String[]{"orgName"}, model.getOrgName()));
		}
		if(!StringUtils.isBlank(model.getTypeId())){
			combine.add(hibernateSearcher.createQueryWithFuzzy(Org.class, new String[]{"typeId"}, model.getTypeId()));
		}
		Query[] querys= new Query[combine.size()];
		for(int i = 0; i < combine.size(); i++){
			querys[i] = combine.get(i);
		}
		List<Org> res = hibernateSearcher.queryCombineMust(Org.class, start, limit, querys);
		if(res != null && res.size() > 0){
			for(Org o : res) o.setAcName(o.getOrgName() + "/" + o.getCode());
		}
		return res;
	}
	
	/*-----------------------------------XService Implement--------------------------------------*/
	//batch audit
	public String batchAudit(String... id){
		String ids = Arrays.toString(id);
		String hql = "select t from OrgTemp t where id in (?)";
		List<OrgTemp> list = dao.find(hql, ids.substring(1, ids.length()-1));
		if(list == null || list.size() < 1) return SysConstant.NODATA;
		
		List<Org> dest = new ArrayList<Org>();
		for(OrgTemp temp : list){
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				continue;
			}
			
			temp.setAudiState(AudiState.AUDIT_MANU_PASS);
			
			Org obj = new Org();
			BeanUtils.copyProperties(temp, obj, new String[]{"id"});
			dest.add(obj);
		}
		
		dao.saveOrUpdateAll(dest);
		dao.saveOrUpdateAll(list);
		
		SolrUpdater.searcherUpdate(EntitySolrSetting.Org);//solr searcher update
		
		return SysConstant.SUCCESS;
	}

	@Override
	public String autoAuditForBench(OrgModel model) {
		Org org = new Org();
		BeanUtils.copyProperties(model, org,new String[] { "id" });
		org.setState(OperateState.NORMAL);
		Org o = isExist(org);
		if(o == null){
			String id = (String) dao.save(org);
			SolrUpdater.searcherUpdate(EntitySolrSetting.Org);//solr searcher update
			return id;
		}
		return o.getId();
	}

	public int xupdate(String[] fields, Object[] fieldValues, String[] conditions, Object[] values){
		if(fields != null && fieldValues != null
				&& fields.length == fieldValues.length
				&& conditions != null && values != null
				&& conditions.length == values.length){
			//sql
			StringBuilder sql = new StringBuilder("UPDATE MD_ORG SET ");
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
			
			return dao.executeSQL(sql.toString(), params);
		}
		return 0;
	}


}