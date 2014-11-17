package com.asc.mds.root.xservice;

import javax.jws.WebService;

import com.asc.mds.root.bean.Region;
import com.asc.mds.root.iservice.IRegionIService;


/**
 * 
 * 类描述 .对外接口服务
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-01-24 下午3:08:30
 */
@WebService
public interface IRegionXService extends IRegionIService {
	
	Region getByName(String name);
	
	String extraterstr(String name);
	
}