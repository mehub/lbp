package com.asc.mds.service;

import java.io.File;
import java.util.List;

import com.asc.mds.root.bean.UploadFile;
import com.asc.mds.root.model.UploadFileModel;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:16:08
 */
public interface IUploadFileService {
	
	UploadFile get(String id);
	
	long getTotal(UploadFileModel model);
	List<UploadFile> getSplitPage(int start, int limit, UploadFileModel model);

	String upload(File file, String fileName, UploadFileModel model);
	
	String audit(UploadFileModel model, List<?> temps);
	
	String save(UploadFile file);
	
	void saveOrUpdate(UploadFile file);
	
}