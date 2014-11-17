/**
 * 
 */
package com.asc.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**提供将查询的对象和字段值包装成simpleAutoComplete控件需要的返回值的一些方法
 * 返回的对象限定大小为20，暂时不支持分页功能
 * @author wangshaohu
 * @date 2010-8-25
 */
public class AutoCompleteUtils {
	private static final Integer MAX_LENGTH =20;
	/**
	 * 将单字段的集合包装成有效的返回值
	 * @param results
	 * @return
	 */
	public static String autoCompleteSingleFieldWarp(List<?> results){
		if(results == null || results.size()==0){
			return "";
		}
		if(results.size() > MAX_LENGTH){
			results =results.subList(0, 20);
		}
		StringBuffer json = new StringBuffer();
		json.append("<ul>");
		for(Object result :results){
			json.append("<li>"+result.toString()+"</li>");
		}
		json.append("</ul>");
		return json.toString();
	}
	
	public static String autoCompleteSingleFieldWarp(String[] results){
		if(results == null || results.length==0){
			return "";
		}
		List<?> resultList = Arrays.asList(results);
		return autoCompleteSingleFieldWarp(resultList);
	}
	
	
	/**
	 * 
	 * @param results 需要包装的对象集合
	 * @param showElement 需要在前台显示的对象中的属性名
	 * @param hiddenPropery 需要发送到前台的隐藏属性名称（前台发送以“，”分割）
	 * @return
	 */
	public static String  autoCompleteObjectWarp(List<?> results,String showElement,String hiddenPropery){
		if(results == null || results.size()==0){
			return "";
		}
		if(results.size() > MAX_LENGTH){
			results =results.subList(0, 20);
		}
		StringBuffer json = new StringBuffer();
		json.append("<ul>");
		if(hiddenPropery == null || hiddenPropery.trim().length()==0){
			createNoHiddenPropertyHtml(results, showElement, json);
		}else{
			createHtml(results, showElement, json,hiddenPropery.split(","));
		}
		json.append("</ul>");
		return json.toString();
	}
	
	
	private static void createHtml(List<?> results, String showElement,
			StringBuffer json,String[] propertys) {
		for(Object result : results){
			Class<?> clazz = result.getClass();
			json.append("<li");
			if(Map.class.isAssignableFrom(clazz)){
				Map<?, ?> map = (Map<?, ?>)result;
				for(String property: propertys){
					if(map.containsKey(property)){
						json.append(" "+property+"=\"");
						json.append(map.get(property).toString()+"\"");
					}
				}
				if(map.containsKey(showElement)){
					json.append(">"+map.get(showElement).toString()+"<li>");
				}else{
					json.append(">NULL<li>");
				}
				continue;
			}
			List<Field> fields = new ArrayList<Field>();
			iterateClass(fields,clazz);
			String showValue = null;
			try {
				for(Field field : fields){
					field.setAccessible(true);
					if(Collection.class.isAssignableFrom(field.getType())){
						continue;
					}
					if(field.getName().equals(showElement)){
						showValue = field.get(result).toString();
						continue;
					}
					for(String property:propertys){
						if(!property.equals(field.getName())){
							continue;
						}
						json.append(" "+property+"=\"");
						json.append(field.get(result).toString()+"\"");
					}
				}
				json.append(">"+showValue+"<li>");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void iterateClass(List<Field> fields, Class<?> clazz){
		Field[] fs = clazz.getDeclaredFields();
		if(fs.length != 0 ){
			fields.addAll(Arrays.asList(fs));
		}
		if(clazz.getSuperclass()!=null){
			iterateClass(fields,clazz.getSuperclass());
		}
	}

	private static void createNoHiddenPropertyHtml(List<?> results, String showElement,
			StringBuffer json) {
		for(Object result : results){
			Class<?> clazz = result.getClass();
			json.append("<li>");
			if(Map.class.isAssignableFrom(clazz)){
				Map<?, ?> map = (Map<?, ?>)result;
				if(map.containsKey(showElement)){
					json.append(map.get(showElement).toString()+"<li>");
				}else{
					json.append("NULL<li>");
				}
				continue;
			}
			Field[] fields = clazz.getDeclaredFields();
			String showValue = null;
			try {
				for(Field field : fields){
					field.setAccessible(true);
					if(Collection.class.isAssignableFrom(field.getType())){
						continue;
					}
					if(field.getName().equals(showElement)){
						showValue = field.get(result).toString();
						break;
					}
				}
				json.append(showValue+"<li>");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
