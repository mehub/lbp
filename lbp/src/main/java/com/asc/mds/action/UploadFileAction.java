package com.asc.mds.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.asc.common.BaseAction;
import com.asc.common.util.PagingToolbar;
import com.asc.mds.root.bean.UploadFile;
import com.asc.mds.root.model.UploadFileModel;
import com.asc.mds.root.state.DataType;
import com.asc.mds.service.IUploadFileService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2012-12-13 下午1:21:02
 */
@Controller
public class UploadFileAction extends BaseAction implements ModelDriven<UploadFileModel> {

	private Logger log = Logger.getLogger(UploadFileAction.class);
	
	private UploadFileModel model = new UploadFileModel();
	private List<UploadFile> list;

	@Autowired
	private IUploadFileService uploadFileService;
	public void setUploadFileService(IUploadFileService uploadFileService) {
		this.uploadFileService = uploadFileService;
	}

	public String list(){
		list = uploadFileService.getSplitPage(start, limit, model);
		long total = uploadFileService.getTotal(model);
		int t = Long.valueOf(total).intValue();
		pageBar = PagingToolbar.getPageToolBarAdv(start, limit, t, "file");
		return "success";
	}

	public void audit(){
		String res = uploadFileService.audit(model, null);
		success(res);
	}

	//upLoad
	private File uploadFile;
	private String uploadFileFileName;
	public void upload() throws IOException {
		uploadFileService.upload(uploadFile, uploadFileFileName, model);
		//success(res);
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write("ok");
	}
	
	//templateDownload
	private String inputPath;
	private String templateFileName;
	public String templateDownload(){
		this.templateFileName = "TEMPLATE_ORG.xls";
		this.inputPath = "templates/TEMPLATE_ORG.xls";
		
		if (DataType.Product.getCode() == model.getDatatype()) {
			
			this.templateFileName = "TEMPLATE_PRODUCT.xls";
			this.inputPath = "templates/TEMPLATE_PRODUCT.xls";

		} else if (DataType.FactoryOrg.getCode() == model.getDatatype()) {
			
			this.templateFileName = "TEMPLATE_FACTORY_ORG.xls";
			this.inputPath = "templates/TEMPLATE_FACTORY_ORG.xls";
			
		} else if (DataType.FranchiseOrg.getCode() == model.getDatatype()) {
			
			this.templateFileName = "TEMPLATE_FRANCHISE_ORG.xls";
			this.inputPath = "templates/TEMPLATE_FRANCHISE_ORG.xls";
			
		} else if (DataType.OrgProduct.getCode() == model.getDatatype()) {
			
			this.templateFileName = "TEMPLATE_ORG_PRODUCT.xls";
			this.inputPath = "templates/TEMPLATE_ORG_PRODUCT.xls";
			
		}
		return "templateDownload";
	}
	
	//downloadFile
	private String fileName;
	public String download(){
		UploadFile file = uploadFileService.get(model.getId());
		this.inputPath = file.getStorepath();
		this.fileName=file.getFilename();
		return "download";
	}
	public InputStream getInputStream() {
		try {
			return new FileInputStream(inputPath);
		} catch (FileNotFoundException e) {
			log.error(fileName + "该文件已不存在，可能被删除或转移！");
			return null;
		}
	}
	
	public InputStream getTemplates() throws IOException{
		return this.getClass().getClassLoader().getResourceAsStream(inputPath);
	}	
	public String getInputPath() {
		return inputPath;
	}
	public String getTemplateFileName() {
		return templateFileName;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	public UploadFileModel getModel() {
		return model;
	}
	public List<UploadFile> getList() {
		return list;
	}
	
}