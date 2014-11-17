package com.asc.mds.root.iservice;

import java.util.List;

import javax.jws.WebMethod;

import com.asc.mds.root.bean.Region;
import com.asc.mds.root.model.RegionModel;

/**
 * 
 * 类描述 . 提供本地、对外的查询接口
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-4-27 上午11:18:27
 */
public interface IRegionIService {
	
	long getTotal(RegionModel model);

	List<Region> getSplitPage(int start, int limit, RegionModel model);
	
	@WebMethod
	Region get(String id);
	
	@WebMethod
	List<Region> listByParentId(String parentId);
	
}