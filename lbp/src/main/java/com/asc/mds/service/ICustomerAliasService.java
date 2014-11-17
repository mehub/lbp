package com.asc.mds.service;

import java.util.List;

import com.asc.mds.root.bean.CustomerAlias;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-27 上午10:13:02
 */
public interface ICustomerAliasService {
	
	long getTotal(CustomerAlias model);
	List<CustomerAlias> getSplitPage(int start, int limit, CustomerAlias model);
	List<CustomerAlias> search(int start, int limit, CustomerAlias model);
}