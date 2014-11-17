package com.asc.mds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asc.common.SysConstant;
import com.asc.common.persist.IPersistDao;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.DataTypeField;
import com.asc.mds.root.state.DataType;


/**
 * 类描述 .
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:55:05
 */
@Service
public class DataTypeFieldService implements IDataTypeFieldService {

	@Autowired
	private IPersistDao dao;
	public void setDao(IPersistDao dao) {
		this.dao = dao;
	}
	
	public List<DataTypeField> findFields(String ownerId, DataType dataType) {
		if (StringUtils.isEmpty(ownerId) || dataType == null) return null;
		StringBuffer hql = new StringBuffer("From DataTypeField f where f.ownerId = ? and f.dataType = ? ");
		hql.append(" order by f.orderIndex");
		List<DataTypeField> fields = dao.find(hql.toString(), new Object[] {ownerId, dataType.getCode()});
		if (fields == null || fields.isEmpty()) {
			fields = dao.find(hql.toString(), new Object[] {SysConstant.OWNER_ASC, dataType.getCode()});
		}
		return fields;
	}
	
}