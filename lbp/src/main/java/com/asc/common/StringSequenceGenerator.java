/**
 * 
 */
package com.asc.common;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

/**
 * @author wangshaohu
 * @date 2010-8-11
 */
public class StringSequenceGenerator extends SequenceGenerator implements PersistentIdentifierGenerator,Configurable{
	
	@Override
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		super.configure(new LongType(), params, dialect);
	}
	/**
	 * 将数据库中查询的Sequence转换成为字符串使用
	 */
	public Serializable generate(SessionImplementor session, Object obj) 
	throws HibernateException {
		Serializable sequnece = super.generate(session, obj);
		Long id = Long.parseLong(sequnece.toString());
		return String.format("%1$012d", id);

	}

}
