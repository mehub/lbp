package com.asc.mds.service;

import java.util.List;

import com.asc.mds.root.bean.DataTypeField;
import com.asc.mds.root.state.DataType;


/**
 * 类描述 .
 * 
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:55:05
 */
public interface IDataTypeFieldService {

	List<DataTypeField> findFields(String ownerId, DataType dataType);
	
}