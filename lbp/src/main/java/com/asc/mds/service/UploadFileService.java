package com.asc.mds.service;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.SysConstant;
import com.asc.common.excel.ExcelReadException;
import com.asc.common.persist.IPersistDao;
import com.asc.common.persist.SpringContext;
import com.asc.common.util.StringUtils;
import com.asc.mds.UploadFileUtil;
import com.asc.mds.root.bean.UploadFile;
import com.asc.mds.root.model.UploadFileModel;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataType;
import com.asc.sf.SecurityManager;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:38:57
 */
@Service
public class UploadFileService implements IUploadFileService {
	
	private static final Logger log = Logger.getLogger(UploadFileService.class);
	
	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	public List<UploadFile> getSplitPage(int start, int limit, UploadFileModel model) {
		return dao.getSplitPage("from UploadFile t " + getCondition(model) + " order by t.createTime desc ", start, limit, new Object[0]);
	}

	public long getTotal(UploadFileModel model) {
		return dao.getTotal("select count(t.id) from UploadFile t " + getCondition(model), new Object[0]);
	}
	  
	private String getCondition(UploadFileModel model) {
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		
		if (model != null) {
			if(!StringUtils.isEmpty(model.getFilename())){
				hql.append(" and t.filename like '%" + model.getFilename() + "%'");
			}
			if(!StringUtils.isEmpty(model.getStartTime())){
				hql.append(" and t.createTime >= to_date('" + model.getStartTime() + "','yyyy-MM-dd hh24:mi:ss')");
			}
			if(!StringUtils.isEmpty(model.getEndTime())){
				hql.append(" and t.createTime <= to_date('" + model.getEndTime() + "', 'yyyy-MM-dd hh24:mi:ss')");
			}
			if(model.getDatatype() != null && model.getDatatype() > 0){
				hql.append(" and t.datatype = " + model.getDatatype().intValue());
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
	
	//get
	public UploadFile get(String id){
		if(!StringUtils.isEmpty(id)){
			return dao.get(UploadFile.class, id);
		}
		return null;
	}
	
	//upload
	public String upload(File file, String fileName, UploadFileModel model){
		try {			
			List<?> list = UploadFileUtil.upload(file, fileName, SysConstant.OWNER_ASC,
											DataType.getByCode(model.getDatatype()));
			if(list != null && list.size() > 0){
				dao.saveOrUpdateAll(list);
				//direct audit
//				if(model.getAudiPass() != null && model.getAudiPass() == AudiState.AUDIT_AUTO.getCode()){
//					model.setId(fileName);
//					model.setAudiState(AudiState.AUDIT_AUTO.getCode());
//					audit(model, list);
//				}
			}
			return SysConstant.SUCCESS;
		} catch (ExcelReadException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//save
	public String save(UploadFile file){
		file.setCreator(SecurityManager.getCurrentUser().getUsername());
		return (String)dao.save(file);
	}
	
	//save or update
	public void saveOrUpdate(UploadFile file){
		dao.saveOrUpdate(file);
	}
	
	//audit
	public String audit(UploadFileModel model, List<?> temps) {
		try{
			IAuditService audit = SpringContext.getBean(DataType.getByCode(model.getDatatype()).getServiceName());
			
			String msg = audit.audit(model.getId(), temps, AudiState.getByCode(model.getAudiState().intValue()));
			
			if(model.getAudiState().intValue() == AudiState.AUDIT_MANU_UNPASS.getCode()){
				dao.executeSQL("update md_upload_file set audi_state=?, memo=? where id=?", 
						new Object[]{model.getAudiState(), model.getMemo(), model.getId()});
			} else {
				Integer as = null;
				if(!SysConstant.SUCCESS.equals(msg)){
					as = AudiState.AUDIT_EXCEPT.getCode();
				}else{
					as = model.getAudiState().intValue();
				}
				dao.executeSQL("update MD_UPLOAD_FILE set audi_state = ?, audi_user=?, memo=?, exceptmsg=? where id=?", 
						new Object[]{as.intValue(), SecurityManager.getCurrentUser().getUsername(), 
									model.getMemo(), msg, model.getId()});
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysConstant.ERROR;
		}
		return SysConstant.SUCCESS;
	}

}