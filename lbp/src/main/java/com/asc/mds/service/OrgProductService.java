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
import com.asc.mds.root.bean.OrgProduct;
import com.asc.mds.root.bean.OrgProductTemp;
import com.asc.mds.root.iservice.OrgProductIService;
import com.asc.mds.root.model.OrgProductModel;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;
import com.asc.mds.root.state.OperateState;
import com.asc.mds.root.xservice.IOrgProductXService;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:31
 */
@Service
@WebService(endpointInterface = "com.asc.mds.root.xservice.IOrgProductXService")
public class OrgProductService extends OrgProductIService implements IOrgProductService, IOrgProductXService, IAuditService {
	
	public OrgProductService(){}
	public OrgProductService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	@Override
	public String add(OrgProductModel model) {
		OrgProductTemp dest = new OrgProductTemp();
		BeanUtils.copyProperties(model, dest, new String[] { "isDisable", "state", "audiState","fileId","qualityCheck" });
		
		if (model.getQualityCheck()==null) {
			dest.setQualityCheck(1);
		}
		
		dest.setCreateTime(new Date());
		if(StringUtils.isEmpty(dest.getCreator()))
			dest.setCreator(SecurityManager.getCurrentUser().getUsername());
		dest.setLastmodifyTime(new Date());
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dest.setAudiState(AudiState.WAIT_AUDI);
		
		dao.save(dest);
		
		return dest.getId();
	}

	@Override
	public String update(OrgProductModel model, String table) {
		OrgProductTemp dest = null;
		if("stand".equals(table)){
			dest = dao.getUnique("select t from OrgProductTemp t where t.relid=?", new Object[]{model.getId()});
		}else{
			dest = getTemp(model.getId());
		}
		if(dest == null) dest = new OrgProductTemp();
		
		BeanUtils.copyProperties(model, dest, new String[] { "id", "isDisable", "state", "audiState","fileId","qualityCheck"});
		dest.setLastmodifyTime(new Date());
		dest.setAudiState(AudiState.WAIT_AUDI);
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dao.saveOrUpdate(dest);
		
		if("stand".equals(table)){
			OrgProduct p = get(model.getId());
			p.setState(OperateState.UPDATING);
			dao.update(p);
		}
		
		return SysConstant.SUCCESS;
	}


	@Override
	public String audit(OrgProductModel model) {
		if(model.getAudiState().intValue() == AudiState.AUDIT_MANU_UNPASS.getCode()){
			dao.executeSQL("update md_org_product_temp set audi_state=?, audi_memo=? where id=?", 
					new Object[]{model.getAudiState(), model.getAudiMemo(), model.getId()});
		} else {
			OrgProductTemp temp = dao.get(OrgProductTemp.class, model.getId());
			
			if(temp!=null&&isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				temp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				dao.saveOrUpdate(temp);
				return SysConstant.EXIST;
			}
			
			OrgProduct dest = null;
			if(temp!=null&&!StringUtils.isEmpty(temp.getRelid())){
				dest = get(temp.getRelid());
			} else {
				dest = new OrgProduct();
			}
			BeanUtils.copyProperties(temp, dest, new String[]{"id"});
			dest.setState(OperateState.NORMAL);
			dest.setAudiUser(SecurityManager.getCurrentUser().getUsername());
			dao.saveOrUpdate(dest);
			
			temp.setAudiMemo(model.getAudiMemo());
			temp.setAudiState(AudiState.getByCode(model.getAudiState()));
			temp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
			temp.setRelid(dest.getId());
			dao.saveOrUpdate(temp);
		}
		return SysConstant.SUCCESS;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String audit(String fileId, List<?> temps, AudiState audiState) {
		List<OrgProductTemp> temp = null; 
		if(temps != null){
			temp = (List<OrgProductTemp>)temps;
		}else{
			StringBuffer hql = new StringBuffer("from OrgProductTemp where fileId=?");
			temp = dao.find(hql.toString(), new Object[]{fileId});
		}
		if(temp != null && temp.size() > 0){
			OrgProduct p = null;
			for(OrgProductTemp s : temp){
				p = new OrgProduct();
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
	public String qualityCheck(String id, Integer qualityCheck) {
		OrgProduct orgProduct = get(id);
		if (orgProduct != null) {
			orgProduct.setQualityCheck(qualityCheck);
			dao.update(orgProduct);
			return SysConstant.SUCCESS;
		}
		return SysConstant.ERROR;
	}
	
	//remove
	public String remove(String id){
		dao.executeSQL("delete from MD_ORG_PRODUCT where ID=?", new Object[]{id});
		dao.executeSQL("delete from MD_ORG_PRODUCT_TEMP where RELID=?", new Object[]{id});
		return SysConstant.SUCCESS;
	}
	
	//isExist
	private boolean isExist(OrgProductTemp dest) {
		StringBuffer hql = new StringBuffer("from OrgProduct where (ownerAscId=? and productAscId =?)");
		
		OrgProduct obj = null;
		
		if(!StringUtils.isEmpty(dest.getRelid())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getProductAscId(), dest.getRelid()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getOwnerAscId(), dest.getProductAscId()});
		}
		
		if(obj != null){
			return true;
		}
		return false;
	}
	
	/*-----------------------------------XService Implement--------------------------------------*/
	//batch audit
	public String batchAudit(String... id){
		String ids = Arrays.toString(id);
		String hql = "select t from OrgProductTemp t where id in (?)";
		List<OrgProductTemp> list = dao.find(hql, ids.substring(1, ids.length()-1));
		if(list == null || list.size() < 1) return SysConstant.NODATA;
		
		List<OrgProduct> dest = new ArrayList<OrgProduct>();
		for(OrgProductTemp temp : list){
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				continue;
			}
			
			temp.setAudiState(AudiState.AUDIT_MANU_PASS);
			
			OrgProduct obj = new OrgProduct();
			BeanUtils.copyProperties(temp, obj, new String[]{"id"});
			dest.add(obj);
		}
		
		dao.saveOrUpdateAll(dest);
		dao.saveOrUpdateAll(list);
		
		return SysConstant.SUCCESS;
	}

	@Override
	public String autoAuditForBench(OrgProductModel model) {
		OrgProduct dest = new OrgProduct();
		BeanUtils.copyProperties(model, dest, new String[]{"id", "isDisable", "state", "audiState","fileId","qualityCheck"});
		return (String) dao.save(dest);
	}
	
	public int xupdate(String[] fields, Object[] fieldValues, String[] conditions, Object[] values){
		if(fields != null && fieldValues != null
				&& fields.length == fieldValues.length
				&& conditions != null && values != null
				&& conditions.length == values.length){
			//sql
			StringBuilder sql = new StringBuilder("UPDATE MD_ORG_PRODUCT SET ");
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

	@Override
	public boolean porQualityCheck(String relationId) {
		String sql="update MD_ORG_PRODUCT set QUALITY_CHECK=? where ID=?";
		int flag=dao.executeSQL(sql, 1,relationId);
		if(flag==1){
			return true;				
		}
		return false;
	}

	@Override
	public boolean delRelation(String relationId) {
		String sql="delete MD_ORG_PRODUCT  where ID=?";
		int flag=dao.executeSQL(sql, relationId);
		if(flag==1){
			return true;				
		}
		return false;
	}

	@Override
	public boolean xbatchAudit(List<OrgProductModel> temps, AudiState audiState) {
		for(OrgProductModel model :temps)
		{
			audit(model);
		}
		return false;
	}

	@Override
	public String updateTemp(OrgProductTemp temp) {
		dao.update(temp);
		return null;
	}

	@Override
	public String xadd(OrgProductModel model) {
		return add(model);
	}

	@Override
	public String xupdateTemp(OrgProductTemp temp) {
		return updateTemp(temp);
	}
}