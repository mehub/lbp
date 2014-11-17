package com.asc.mds.service;

import com.asc.mds.root.bean.OrgTemp;
import com.asc.mds.root.model.OrgModel;
import com.asc.mds.root.xservice.IOrgXService;


/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:15:12
 */
public interface IOrgService extends IOrgXService {

	void update(OrgModel model, boolean flag);
	void audit(OrgTemp temp, Integer state);
	
	void remove(String[] ids);
	void disabled(String id, String state);
	
}