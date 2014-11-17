package com.asc.mds.service;

import com.asc.mds.root.iservice.IRegionIService;
import com.asc.mds.root.model.RegionModel;


/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:15:58
 */
public interface IRegionService extends IRegionIService {
	
	String saveOrUpdate(RegionModel model);
	
	String remove(String id);
	
}