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
import com.asc.mds.root.bean.Product;
import com.asc.mds.root.bean.ProductTemp;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.mds.root.iservice.ProductIService;
import com.asc.mds.root.model.ProductModel;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataState;
import com.asc.mds.root.state.OperateState;
import com.asc.mds.root.xservice.IProductXService;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:43
 */
@Service
@WebService(endpointInterface = "com.asc.mds.root.xservice.IProductXService")
public class ProductService extends ProductIService implements IProductService, IProductXService, IAuditService {
	
	public ProductService(){}
	public ProductService(IPersistDao dao){
		this.dao = dao;
	}
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	//add
	public String add(ProductModel model){
		ProductTemp dest = new ProductTemp();
		BeanUtils.copyProperties(model, dest, new String[] { "isDisable" });
		
		dest.setCreateTime(new Date());
		dest.setCreator(SecurityManager.getCurrentUser().getUsername());
		dest.setLastmodifyTime(new Date());
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dest.setAudiState(AudiState.WAIT_AUDI);
		
		if(isExist(dest)){
			return SysConstant.EXIST;
		}
		
		dao.save(dest);
		
		//direct audit
		if(model.getAudiPass() != null && model.getAudiPass() == AudiState.AUDIT_AUTO.getCode()){
			model.setAudiState(AudiState.AUDIT_AUTO.getCode());
			model.setId(dest.getId());
			audit(model);
		}
		return SysConstant.SUCCESS;
	}
	
	//update
	public String update(ProductModel model, String table){
		
		ProductTemp dest = null;
		if("stand".equals(table)){
			dest = dao.getUnique("select t from ProductTemp t where t.relid=?", new Object[]{model.getId()});
		} else {
			dest = dao.get(ProductTemp.class, model.getId());
		}
		if(dest == null) dest = new ProductTemp();
		BeanUtils.copyProperties(model, dest, new String[] {"id", "isDisable" });
		dest.setAudiState(AudiState.WAIT_AUDI);
		dest.setIsDisable(DataState.getByCode(model.getIsDisable()));
		dest.setLastmodifyTime(new Date());
		
		if(isExist(dest)){
			return SysConstant.EXIST;
		}
		
		dao.saveOrUpdate(dest);
		
		if(model.getAudiPass() != null && model.getAudiPass() == AudiState.AUDIT_AUTO.getCode()){
			model.setAudiState(AudiState.AUDIT_AUTO.getCode());
			model.setId(dest.getId());
			audit(model);
		} else if("stand".equals(table)){
			Product p = dao.get(Product.class, model.getId());
			p.setState(OperateState.UPDATING);
			dao.update(p);
			SolrUpdater.searcherUpdate(EntitySolrSetting.Product);//solr searcher update
		}
		return SysConstant.SUCCESS;
	}
	
	//audit record
	public String audit(ProductModel model){
		if(model.getAudiState() != null && model.getAudiState().intValue() == AudiState.AUDIT_MANU_UNPASS.getCode()){
			dao.executeSQL("update md_product_temp set audi_state=?, audi_memo=? where id=?", 
					new Object[]{model.getAudiState(), model.getAudiMemo(), model.getId()});
		} else {
			ProductTemp temp = dao.get(ProductTemp.class, model.getId());
			
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				temp.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				dao.saveOrUpdate(temp);
				return SysConstant.EXIST;
			}
			
			Product dest = null;
			if(!StringUtils.isEmpty(model.getRelid())){
				dest = dao.get(Product.class, temp.getRelid());
			}else{
				dest = new Product();
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
		SolrUpdater.searcherUpdate(EntitySolrSetting.Product);//solr searcher update
		return SysConstant.SUCCESS;
	}
	
	//remove
	public String remove(String id){
		dao.executeSQL("delete from MD_PRODUCT where ID=?", new Object[]{id});
		dao.executeSQL("delete from MD_PRODUCT_TEMP where RELID=?", new Object[]{id});
		return SysConstant.SUCCESS;
	}
	
	//audit file
	@SuppressWarnings("unchecked")
	public String audit(String fileId, List<?> temps, AudiState audiState) {
		List<ProductTemp> temp = null;
		if(temps != null){
			temp = (List<ProductTemp>)temps;			
		}else{
			StringBuffer hql = new StringBuffer("from ProductTemp where fileId=?");
			temp = dao.find(hql.toString(), new Object[]{fileId});
		}
		
		if(temp != null && temp.size() > 0){
			StringBuffer msg = new StringBuffer();
			Product p = null;
			for(ProductTemp s : temp){
				p = new Product();
				p.setState(OperateState.NORMAL);
				s.setAudiUser(SecurityManager.getCurrentUser().getUsername());
				if(isExist(s)){
					msg.append(s.getId() + " ");
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
			SolrUpdater.searcherUpdate(EntitySolrSetting.Product);//solr searcher update
			
			return msg.length() > 0 ? msg.toString() : SysConstant.SUCCESS;
		}
		return SysConstant.NODATA;
	}
		
	//isExist
	public boolean isExist(ProductTemp dest) {
		StringBuffer hql = new StringBuffer("from Product where (commonname=? and unit=? and spec=?)");
		
		Product obj = null;
		
		if(!StringUtils.isEmpty(dest.getRelid())){
			hql.append(" and id != ?");
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getCommonname(), dest.getUnit(), dest.getSpec(), dest.getRelid()});
			
		} else {
			obj = dao.getUnique(hql.toString(), new Object[]{dest.getCommonname(), dest.getUnit(), dest.getSpec()});
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
		String hql = "select t from ProductTemp t where id in (?)";
		List<ProductTemp> list = dao.find(hql, ids.substring(1, ids.length()-1));
		if(list == null || list.size() < 1) return SysConstant.NODATA;
		
		List<Product> dest = new ArrayList<Product>();
		for(ProductTemp temp : list){
			if(isExist(temp)){
				temp.setAudiMemo(SysConstant.EXIST);
				temp.setAudiState(AudiState.AUDIT_EXCEPT);
				continue;
			}
			
			temp.setAudiState(AudiState.AUDIT_MANU_PASS);
			
			Product obj = new Product();
			BeanUtils.copyProperties(temp, obj, new String[]{"id"});
			dest.add(obj);
		}
		
		dao.saveOrUpdateAll(dest);
		dao.saveOrUpdateAll(list);
		
		return SysConstant.SUCCESS;
	}

	@Override
	public String autoAuditForBench(ProductModel model) {
		return audit(model);
	}
	
}