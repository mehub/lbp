package com.asc.mds.root.iservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.persist.IPersistDao;
import com.asc.common.persist.jdbc.AbstractJdbcDao;
import com.asc.mds.root.bean.Franchise;
import com.asc.mds.root.bean.Org;
import com.asc.mds.root.bean.OrgTemp;
import com.asc.mds.root.isearch.document.RegionDoc;
import com.asc.mds.root.model.OrgModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-12 下午4:14:08
 */
@Service
public class OrgIService implements IOrgIService {

	public OrgIService(){}
	public OrgIService(IPersistDao dao){
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
	
	public List<Org> getSplitPage(int start, int limit, OrgModel model) {
		return dao.getSplitPage("select t from Org t " + getCondition(model), start, limit, new Object[0]);
	}
	
	@Override
	public List<OrgTemp> getTempSplitPage(int start, int limit, OrgModel model) {
		return dao.getSplitPage("select t from OrgTemp t " + getCondition(model), start, limit, new Object[0]);
	}

	public Long getTotal(OrgModel model) {
		return dao.getTotal("select count(t.id) from Org t " + getCondition(model), new Object[0]);
	}
	
	public Long getTempTotal(OrgModel model) {
		return dao.getTotal("select count(t.id) from OrgTemp t " + getCondition(model), new Object[0]);
	}
	  
	private String getCondition(OrgModel model) {
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1=1 ");
		
		if (model != null) {
			if(!StringUtils.isBlank(model.getOrgName())){
				if(model.getUseLike())
					sb.append("and t.orgName like '%"+model.getOrgName()+"%' ");
				else
					sb.append("and t.orgName = '"+model.getOrgName()+"' ");
			}
			if(!StringUtils.isBlank(model.getTypeId())){
				sb.append("and t.typeId = '"+model.getTypeId()+"' ");
			}
			if(!StringUtils.isBlank(model.getCode())){
				sb.append("and t.code = '"+model.getCode()+"' ");
			}
			if(model.getAudiState() != null && model.getAudiState() > 0){
				sb.append(" and t.audiState = " + model.getAudiState().intValue());
			}
			if(model.getNameKeys()!=null&&model.getNameKeys().length>0){
				sb.append("and t.orgName like '%");
				for(String key :model.getNameKeys())
				{
					if(!"".equals(key))
					{
						sb.append(key+"%");
					}
				}
				sb.append("'");
			}
			
		}
		return sb.toString();
	}
	
	@Override
	public Org getOrg(String id) {
		return dao.get(Org.class,id);
	}
	
	@Override
	public OrgTemp getTemp(String id) {
		return dao.get(OrgTemp.class, id);
	}

	@Override
	public boolean nameCheck(String orgName) {
		String sql="select t from OrgTemp t where t.orgName=?";
		OrgTemp org=dao.getUnique(sql, orgName);
		if(null==org){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean codeCheck(String orgCode) {
		String sql="select t from OrgTemp t where t.code=?";
		OrgTemp org=dao.getUnique(sql, orgCode);
		if(null==org){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<Franchise> getFranchiseSplitPage(int start, int limit,
			OrgModel model) {
		return dao.getSplitPage("select t from Franchise t " + getCondition(model), start, limit, new Object[0]);
	}

	@Override
	public Long getFranchiseTotal(OrgModel model) {
		return dao.getTotal("select count(t.id) from Franchise t " + getCondition(model), new Object[0]);
	}
	
	@Override
	public Long getFranFactotyTotal(String factoryId,OrgModel model) {
		Long total=dao.getTotal("select count(t.id) from Org t "+getCondition(model)+" and t.id in" +
				"(select m.partnerAscId from FactoryOrg m where m.ownerAscId=?)", 
				new Object[]{factoryId});
		return total;
	}

	@Override
	public List<Org> getFranFactotySplitPage(int start, int limit,String factoryId,OrgModel model) {
		List<Org> list=dao.getSplitPage("select t from Org t "+getCondition(model)+" and t.id in" +
				"(select m.partnerAscId from FactoryOrg m where m.ownerAscId=?)", 
				start, limit, new Object[]{factoryId});
		return list;
	}
	
	public RegionDoc getRegionDoc(String orgId){
		StringBuffer sql = new StringBuffer();
    	sql.append(" select T.XLEVEL xlevel,T.NAME county,T.PARENT_NAME city,T.PROVINCE province")
    		.append(" from MD_REGION T join  MD_ORG T2 ")
    		.append(" ON T.ID=T2.REGION_ID AND T2.ID=? ");
    	
    	return oracleJdbcDao.getUnique(RegionDoc.class,sql.toString(), new Object[]{orgId});
	}

	@Override
	public List<Org> getSplitPageBySql(int start, int limit, OrgModel model,String ownerId) {
		
		StringBuilder sql=new StringBuilder("select *  from");
		sql.append("( select row_.*, rownum rownum_  from");
		sql.append(" ( select t.id,t.name from MD_ORG t left join md_factory_org forg on t.id=forg.partner_asc_id where 1=1 ");
		if(StringUtils.isNotBlank(model.getOrgName())){
			sql.append(" and t.name like'%").append(model.getOrgName()).append("%'");
		}
		sql.append(" and t.TYPE_ID='").append(model.getTypeId());
		sql.append("' and forg.owner_asc_id='").append(ownerId).append("') row_ ");
		sql.append(" where rownum <= ").append(limit+start).append(" )  where rownum_ >").append(start);
		List<Object[]> list=dao.listBySQL(sql.toString());
		List<Org> orgList=new ArrayList<Org>();
		for(Object[] obj:list){
			Org org=new Org(obj[0].toString(),obj[1].toString());
			orgList.add(org);
		}
		return orgList;
	}
	
	public List<Org> getSplitPageBySqlFran(int start, int limit, OrgModel model,String franId){
		StringBuilder sql=new StringBuilder("select *  from");
		sql.append("( select row_.*, rownum rownum_  from");
		sql.append(" ( select t.id,t.name from MD_ORG t left join md_factory_org forg on t.id=forg.owner_asc_id where 1=1 ");
		if(StringUtils.isNotBlank(model.getOrgName())){
			sql.append(" and t.name like'%").append(model.getOrgName()).append("%'");
		}
		sql.append(" and t.TYPE_ID='").append(model.getTypeId());
		sql.append("' and forg.partner_asc_id='").append(franId).append("') row_ ");
		sql.append(" where rownum <= ").append(limit+start).append(" )  where rownum_ >").append(start);
		List<Object[]> list=dao.listBySQL(sql.toString());
		List<Org> orgList=new ArrayList<Org>();
		for(Object[] obj:list){
			Org org=new Org(obj[0].toString(),obj[1].toString());
			orgList.add(org);
		}
		return orgList;
	}

	@Override
	public long getTotalBySql(OrgModel model, String ownerId) {
		StringBuilder sql=new StringBuilder("select count(*) from MD_ORG org LEFT join md_factory_org forg on org.id=forg.partner_asc_id ");
		sql.append(" where 1=1 and org.TYPE_ID='").append(model.getTypeId()).append("'");
		if(StringUtils.isNotBlank(model.getOrgName())){
			sql.append(" and org.name like'%").append(model.getOrgName()).append("%'");
		}
		sql.append(" and forg.owner_asc_id='").append(ownerId).append("'");
		List<Object> list=dao.listBySQL(sql.toString());
		long total=Long.valueOf(list.get(0).toString());
		return total;
	}
	
	public long getTotalBySqlFran(OrgModel model, String franId) {
		StringBuilder sql=new StringBuilder("select count(*) from MD_ORG org LEFT join md_factory_org forg on org.id=forg.owner_asc_id ");
		sql.append(" where 1=1 and org.TYPE_ID='").append(model.getTypeId()).append("'");
		if(StringUtils.isNotBlank(model.getOrgName())){
			sql.append(" and org.name like'%").append(model.getOrgName()).append("%'");
		}
		sql.append(" and forg.partner_asc_id='").append(franId).append("'");
		List<Object> list=dao.listBySQL(sql.toString());
		long total=Long.valueOf(list.get(0).toString());
		return total;
	}

}