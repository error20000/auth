package com.tools.db.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.tools.db.dao.MysqlBaseDao;
import com.tools.jdbc.Column;
import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.Table;
import com.tools.utils.Tools;


public abstract class MysqlBaseDaoImpl<T> implements MysqlBaseDao<T> {
	
	protected DataSource dataSource = null;
	protected JdbcOperate jdbcOperate = null;
	
	/**
	 * 如果需要使用事物，要初始化dataSource
	 */
	public abstract void initJdbcOperate();
	
	@Override
	public int save(T object) {
		String tableName = "";
		if(object.getClass().isAnnotationPresent(Table.class)){
			tableName = object.getClass().getAnnotation(Table.class).value();
		}
		return save(object, tableName);
	}

	@Override
	public int save(T object, String tableName) {
		int res = 0;
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		String sql = "INSERT INTO `"+tableName+"` (SQLC) VALUES (SQLV)";
		String sqlC = "";
		String sqlV = "";
		Field[] fields = Tools.getFields(object.getClass());
		Method[] methods = Tools.getMethods(object.getClass());
		//遍历属性，只有有get/set方法的属性，才加入insert.
		for (Field f : fields) {
			String fname = f.getName();
			boolean get = false;
			boolean set = false;
			for (Method m : methods) {
				if(("get"+fname).equalsIgnoreCase(m.getName())){
					get = true;
				}
				if(("set"+fname).equalsIgnoreCase(m.getName())){
					set = true;
				}
			}
			if(get && set){
				if(f.isAnnotationPresent(PrimaryKey.class)){
					fname = f.getAnnotation(PrimaryKey.class).value();
				}else if(f.isAnnotationPresent(Column.class)){
					fname = f.getAnnotation(Column.class).value();
				}
				sqlC += ","+fname;
				sqlV += ",:"+fname;
			}
		}
		if(!Tools.isNullOrEmpty(sqlC)){
			sql.replace("SQLC", sqlC.substring(1));
		}
		if(!Tools.isNullOrEmpty(sqlV)){
			sql.replace("SQLV", sqlV.substring(1));
		}
		
		try {
			res = jdbcOperate.update(sql, object);
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int batchSave(List<T> objects) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchSave(List<T> objects, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(T object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(T object, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(String wsql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(String wsql, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Map<String, Object> deleteCondition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Map<String, Object> deleteCondition, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String wsql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String wsql, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchDelete(String fieldName, List<String> fieldValues) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchDelete(String fieldName, List<String> fieldValues, String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> find(int start, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> find(Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> find(Map<String, Object> queryCondition, int start, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> find(String wsql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> find(String wsql, int start, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long size(Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long size(String wsql) {
		// TODO Auto-generated method stub
		return 0;
	}


}
