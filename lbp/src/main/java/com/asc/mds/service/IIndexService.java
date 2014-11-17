package com.asc.mds.service;

import java.util.List;

import com.asc.mds.root.IndexedEntity;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-27 上午10:13:02
 */
public interface IIndexService {
	
	List<IndexedEntity> list();
	
	void rebuild(IndexedEntity model);
	
	void update(IndexedEntity model);
	
	void optimize(IndexedEntity model);
	
	void purges(IndexedEntity model);
	
}