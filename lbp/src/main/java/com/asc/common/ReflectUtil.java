/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import com.asc.common.util.DateUtils;

/**
 * 类描述  .
 * @author liaorui
 * @version 版本信息 创建时间 2012-6-4 上午10:05:52
 */
public class ReflectUtil {
	/**
	 * 获取类的字段值
	 * @param source 类的对象
	 * @param fieldName 字段名称
	 * @return	
	 * @throws NoSuchFieldException 
	 */
	public static Object getFieldValue(Object source,String fieldName) throws NoSuchFieldException
	{
		Object o = null;
		try {
			Field field = source.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			o = field.get(source);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	/**
	 * 设置字段值
	 * @param source
	 * @param fieldName
	 * @param fieldvalue
	 * @throws NoSuchFieldException 
	 */
	public static void setFiledValue(Object source,String fieldName,Object fieldvalue) throws NoSuchFieldException
	{
		try {
			Field field = source.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			setValue(source, field, fieldvalue);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see ObjectConvertorUtils
	 */
	private static void setValue(Object obj, Field field, Object value) throws IllegalAccessException{
		if(value == null){
			return;
		}
		try{
			field.set(obj, value);
		}catch(IllegalArgumentException e){
			field.set(obj, getRealValue(field.getType(),value));
		}
	}
	
	private static Object getRealValue(Class<?> type, Object value){
		if(int.class.isAssignableFrom(type)){
			return new Integer(value.toString());
		}else if (Integer.class.isAssignableFrom(type)) {
			return new Integer(value.toString());
		}else if(long.class.isAssignableFrom(type)){
			return new Long(value.toString());
		}else if(boolean.class.isAssignableFrom(type)){
			return new Boolean(value.toString());
		}else if(double.class.isAssignableFrom(type)){
			return new Double(value.toString());
		}else if(char.class.isAssignableFrom(type)){
			return (Character) value.toString().charAt(0);
		}else if(short.class.isAssignableFrom(type)){
			return new Short(value.toString());
		}else if(float.class.isAssignableFrom(type)){
			return new Float(value.toString());
		}else if(byte.class.isAssignableFrom(type)){
			return new Byte(value.toString());
		}else if(BigDecimal.class.isAssignableFrom(type)){
			return new BigDecimal(value.toString());
		}
		if(Date.class.isAssignableFrom(type)){
			return DateUtils.string2Date(value.toString());
		}
		return null;
	}
}
