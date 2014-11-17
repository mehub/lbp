package com.asc.common;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.annotations.common.util.ReflectHelper;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import com.asc.common.util.EnumReflect;
import com.asc.common.util.StringValueEnum;

/**
 * 自定义Hibernate类型，用于将数据库中的数值型字段返回为ENUM类型
 * @author wangshaohu
 * @date 2011-1-4 下午06:51:54
 * @version
 */
public class StringValueEnumType<E extends Enum<?>&StringValueEnum>
	implements UserType,ParameterizedType{
	
	private Class<E> entityClass;
	
	public StringValueEnumType(){
	}
	
	public int[] sqlTypes() {
		return new int[]{Types.INTEGER};
	}

	public Class<E> returnedClass() {
		return entityClass;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		if(rs == null){
			return null;
		}
		Integer value = rs.getInt(names[0]);
		if(value == null){
			return null;
		}
		return EnumReflect.getEnumByCode(entityClass, value);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int, org.hibernate.engine.spi.SessionImplementor)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if(value == null){
			st.setNull(index, Types.INTEGER);
		}else{
			st.setInt(index,((E)value).getCode());
		}
		
	}
	
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		if(rs == null){
			return null;
		}
		Integer value = rs.getInt(names[0]);
		if(value == null){
			return null;
		}
		return EnumReflect.getEnumByCode(entityClass, value);
	}

	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if(value == null){
			st.setNull(index, Types.INTEGER);
		}else{
			st.setInt(index,((E)value).getCode());
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Serializable disassemble(Object value) throws HibernateException {
		return (Enum)value;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}
	
	
	public void setParameterValues(Properties parameters) {
		String entityName = parameters.getProperty("enumType");
		try {
			@SuppressWarnings("unchecked")
			Class<E> asSubclass = (Class<E>)ReflectHelper.classForName(entityName).asSubclass(StringValueEnum.class).asSubclass(Enum.class);
			entityClass = asSubclass;
		} catch (ClassNotFoundException e) {
			throw new MappingException(entityName+"is not found");
		}
	}

}
