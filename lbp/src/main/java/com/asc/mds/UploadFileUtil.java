/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asc.common.ReflectUtil;
import com.asc.common.excel.BaseFileReader;
import com.asc.common.excel.ExcelReadException;
import com.asc.common.excel.ExcelVersion;
import com.asc.common.excel.FileReaderFactory;
import com.asc.common.persist.SpringContext;
import com.asc.common.util.DateUtils;
import com.asc.common.util.FileUtils;
import com.asc.common.util.InitConfUtils;
import com.asc.common.util.StringUtils;
import com.asc.mds.root.bean.DataTypeField;
import com.asc.mds.root.bean.UploadFile;
import com.asc.mds.root.state.AudiState;
import com.asc.mds.root.state.DataType;
import com.asc.mds.root.state.ProcState;
import com.asc.mds.service.IDataTypeFieldService;
import com.asc.mds.service.IUploadFileService;
import com.asc.sf.SecurityManager;

/**
 * 类描述  .
 * @author liaorui
 * @version 版本信息 创建时间 2012-8-3 下午04:04:07
 */
public class UploadFileUtil {
	
	private static IDataTypeFieldService dataTypeFieldService;
	private static IUploadFileService uploadFileService;
	
	static
	{
		dataTypeFieldService = SpringContext.getBean("dataTypeFieldService");
		uploadFileService = SpringContext.getBean("uploadFileService");
	}
	
	private UploadFileUtil(){}
	
	/**
	 * 上传文件
	 * @param file	文件
	 * @param fileName 后缀 eg: xlsx、xls
	 * @param ownerId ASC
	 * @param dataType 数据类型
	 * @return 
	 * @throws ExcelReadException
	 */
	public static List<?> upload(File file, String fileName, String ownerId, DataType dataType) throws ExcelReadException {
		List<DataTypeField> fields = dataTypeFieldService.findFields(ownerId, dataType);
		return upload(file, fileName, dataType, ownerId, fields);
	}
	
	/**
	 * 上传文件
	 * @param file 数据文件
	 * @param postfix 后缀 eg: xlsx、xls
	 * @param dataType 文件类型
	 * @param cfields 文件字段
	 * @return
	 * @throws ExcelReadException
	 */
	private static List<?> upload(File file, String fileName, DataType dataType, String ownerId, List<DataTypeField> cfields) throws ExcelReadException {
		if(cfields==null || cfields.isEmpty())
			return null;	
		
		FileInputStream fis  = null;
		FileInputStream fisr  = null;
		FileOutputStream fos = null;
		UploadFile uf = null;
		List<Object> rs = new ArrayList<Object>();
		try {
			String path = InitConfUtils.getParamValue("upload.storepath")
					+ dataType.getCode()
					+ "/"
					+ DateUtils.getStrNowDateHms() + "_" + fileName;
			FileUtils.createDirectory(InitConfUtils.getParamValue("upload.storepath")
					+ dataType.getCode());
			fos = new FileOutputStream(path);
			
			fisr = new FileInputStream(file);
			byte[] buffer = new byte[fisr.available()];
			
			fisr.read(buffer);
			fos.write(buffer);
			fos.flush();
			fos.close();
			fisr.close();
			
			String postfix = fileName.substring(fileName.lastIndexOf(".")+1);
			
			BaseFileReader reader =  FileReaderFactory.getFileReader(postfix);
			
			fis = new FileInputStream(file);
			
			List<Map<String,String>> datas = reader.getFileContent(fis, 0);
			
			if(datas == null ||datas.size()==0)
				 return rs; 
			
			uf = new UploadFile(fileName, postfix, dataType);
			uf.setAudiState(AudiState.WAIT_AUDI);
			uf.setCreateTime(new Date());
			uf.setFilesize(Long.valueOf(file.length()).intValue());
			uf.setStorepath(dataType.getCode() + "/" + DateUtils.getStrNowDateHms() + "_" + fileName);
			uf.setState(ProcState.WAIT_PROCESS);
			String fileId = uploadFileService.save(uf);
			fileName = fileId;//向外传递fileId
			for(Map<String, String> dt :datas)
			{
				Object instance= null;
				try {
					instance = dataType.getClz().newInstance();

					for(DataTypeField cfield : cfields)
					{
						Object value = dt.get(cfield.getName());
						
						try {
							ReflectUtil.setFiledValue(instance, cfield.getPropName(), value);
						} catch (NoSuchFieldException e) {
						}
					}
					ReflectUtil.setFiledValue(instance, "fileId", fileId);
					ReflectUtil.setFiledValue(instance, "creator", SecurityManager.getCurrentUser().getUsername());
					ReflectUtil.setFiledValue(instance, "createTime", new Date());
					ReflectUtil.setFiledValue(instance, "lastmodifyTime", new Date());
					ReflectUtil.setFiledValue(instance, "audiState", AudiState.WAIT_AUDI);					
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					rs.add(instance);
				}
			}
			uf.setState(ProcState.IMPORT_SUCCESS);
		} catch (Exception e) {
			uf.setState(ProcState.PROCESS_EXCEPT);
			String msg = e.getMessage();
			if(msg != null){
				uf.setExceptmsg(msg.length() > 500 ? msg.substring(0, 480) : msg);
			}
			throw new ExcelReadException("文件读取异常",e);
		}finally
		{
			if(fis!=null)
			{
				try {
					fis.close();
				} catch (IOException e) {
					//ignore
				}
			}
			uploadFileService.saveOrUpdate(uf);
		}
		return rs;
	}
	/**
	 * 生成Excel
	 * @param datas 数据
	 * @param dataType 文件类型
	 * @param ownerId 厂商ID
	 * @param version excel版本
	 * @return
	 */
	public static Workbook createExcel(List<?> datas, DataType dataType, String ownerId, ExcelVersion version)
	{
		List<DataTypeField> fields = dataTypeFieldService.findFields(ownerId,dataType);
		return createExcel(datas, dataType, fields, version);
	}
	
	/**
	 * 生成Excel
	 * @param datas 数据
	 * @param dataType 数据类型
	 * @param cfields 字段
	 * @param version excel版本
	 * @return
	 */
	public static Workbook createExcel(List<?> datas, DataType dataType, List<DataTypeField> cfields, ExcelVersion version)
	{
		Workbook wb = (version == ExcelVersion.XLS_2003) ?  new HSSFWorkbook():new XSSFWorkbook();
		
		Sheet sheet =wb.createSheet(dataType.getValue());

		Row title = sheet.createRow(0);
		
		title.setHeight((short)300);
		
		CellStyle titleStyle = createTitleStyle(wb);
		
		for(int i =0;i<cfields.size();i++)
		{
			DataTypeField field = cfields.get(i);
			
			Cell cell = title.createCell(i);
			
			cell.setCellValue(field.getName());
			cell.setCellStyle(titleStyle);
			
			sheet.autoSizeColumn(i);
			
			if(StringUtils.isEmpty(field.getSelectValue()))
				continue;
			
			//数据校验
			String [] selectValues = field.getSelectValue().split(",");
			DataValidation dv = null;
			
			CellRangeAddressList regions = new CellRangeAddressList(1, 65535, i, i);
			
			if(version == ExcelVersion.XLS_2003)
			{
				DVConstraint constraint = DVConstraint.createExplicitListConstraint(selectValues);
				
				dv = new HSSFDataValidation(regions, constraint);
				
			}else
			{
				XSSFDataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet)sheet);
				DataValidationConstraint constraint = helper.createExplicitListConstraint(selectValues);
				
				dv = helper.createValidation(constraint, regions);
			}
			sheet.addValidationData(dv);
		}
		
		for(int i=0;i<datas.size();i++)
		{
			Object instance = datas.get(i);
			Row row = sheet.createRow(i+1);
			
			for(int j =0;j<cfields.size();j++)
			{
				DataTypeField field = cfields.get(j);
					
				Object result = null;
				try {
					result = ReflectUtil.getFieldValue(instance, field.getPropName());
				} catch (NoSuchFieldException e) {

				}
				if(result == null) 
					continue;
				
				if (result instanceof Date) {
					result  = DateUtils.dateToString((Date) result, "yyyy-MM-dd");
				}
				row.createCell(j).setCellValue(result==null?"":result.toString());
			 }
		}
		return wb;
	}

	private static CellStyle createTitleStyle(Workbook wb) {
		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font font =	wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short)10);
		titleStyle.setFont(font);
		return titleStyle;
	}
	
	@SuppressWarnings("unused")
	private static CellStyle createStyle(Workbook wb,HSSFColor bgColor,short fillPattern) {
		CellStyle cstyle = wb.createCellStyle();
		cstyle.setFillForegroundColor(bgColor.getIndex());
		cstyle.setFillPattern(fillPattern);
		cstyle.setFillBackgroundColor(bgColor.getIndex());
		
		cstyle.setBorderBottom((short) 1);  
		cstyle.setBorderTop((short) 1);  
		cstyle.setBorderLeft((short) 1);  
		cstyle.setBorderRight((short) 1);  
		return cstyle;
	}
	
	public static void fos(Workbook hbds,String fileName) {
		try {
			FileOutputStream fos=new FileOutputStream(fileName);
			hbds.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
