/***********************************************************************
Copyright (c) 2007, AgileSC,Inc.China
All rights reserved.
************************************************************************/
package com.asc.mds.root.isearch;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类描述 .hibernate search索引注解
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-7-16 上午11:26:24
 */
@Retention( RetentionPolicy.RUNTIME )
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
@Documented
public @interface Indexable {
	
	String name();
	
	int analyzer() default 1;
	
	int store() default 1;
	
	boolean embedded() default false;
	
	boolean id() default false;
		
}
