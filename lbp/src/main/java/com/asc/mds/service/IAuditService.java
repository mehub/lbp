/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.service;

import java.util.List;

import com.asc.mds.root.state.AudiState;

/**
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-14 下午3:38:14
 */
public interface IAuditService {
	
	String audit(String fileId, List<?>temps, AudiState audiState);
	
}