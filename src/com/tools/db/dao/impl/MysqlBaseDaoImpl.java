package com.tools.db.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
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

	/**
	 * dataSource 事务处理相关（非必须），默认：null
	 */
	protected DataSource dataSource = null;
	/**
	 * jdbc 主要方法（必须）初始化，默认：null
	 */
	protected JdbcOperate jdbcOperate = null;
	/**
	 * sql 调试打印（非必须），默认：false
	 */
	protected boolean debug = false;
	
	
	public MysqlBaseDaoImpl() {
		initJdbcOperate();
	}

	/**
	 * 如果需要使用事物，要初始化dataSource
	 */
	public abstract void initJdbcOperate();
	
	//TODO save
	@Override
	public int save(T object) {
		if(object == null){
			return 0;
		}
		String tableName = "";
		if(object.getClass().isAnnotationPresent(Table.class)){
			tableName = object.getClass().getAnnotation(Table.class).value();
		}
		return save(object, tableName);
	}

	@Override
	public int save(T object, String tableName) {
		int res = 0;
		if(object == null){
			return res;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		String sql = parseInsert(object, tableName);
		//DEBUG
		if(debug){
			System.out.println("save sql: " + sql);
		}
		try {
			res = jdbcOperate.update(sql, object);
			//DEBUG
			if(debug){
				System.out.println("save result: " + res);
			}
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int batchSave(List<T> objects) {
		if(objects == null || objects.size() == 0){
			return 0;
		}
		T object = objects.get(0);
		String tableName = "";
		if(object.getClass().isAnnotationPresent(Table.class)){
			tableName = object.getClass().getAnnotation(Table.class).value();
		}
		return batchSave(objects, tableName);
	}

	@Override
	public int batchSave(List<T> objects, String tableName) {
		int res = 0;
		if(objects == null || objects.size() == 0){
			return res;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		T object = objects.get(0);
		String sql = parseInsert(object, tableName);
		//DEBUG
		if(debug){
			System.out.println("batch save sql: "+sql);
		}
		try {
			int[] tmp = jdbcOperate.batchObject(sql, objects);
			for (int i : tmp) {
				res += i;
			}
			//DEBUG
			if(debug){
				System.out.println("batch save result: " + res);
			}
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private String parseInsert(T object, String tableName){
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
				String value = "";
				if(f.isAnnotationPresent(PrimaryKey.class)){
					value = f.getAnnotation(PrimaryKey.class).value();
				}else if(f.isAnnotationPresent(Column.class)){
					value = f.getAnnotation(PrimaryKey.class).value();
				}
				fname = Tools.isNullOrEmpty(value) ? fname : value;
				sqlC += ",`"+fname+"`";
				sqlV += ",:"+fname;
			}
		}
		if(!Tools.isNullOrEmpty(sqlC)){
			sql = sql.replace("SQLC", sqlC.substring(1));
		}
		if(!Tools.isNullOrEmpty(sqlV)){
			sql = sql.replace("SQLV", sqlV.substring(1));
		}
		return sql;
	}

	//TODO modify
	@Override
	public int modify(T object) {
		if(object == null){
			return 0;
		}
		String tableName = "";
		if(object.getClass().isAnnotationPresent(Table.class)){
			tableName = object.getClass().getAnnotation(Table.class).value();
		}
		return modify(object, tableName);
	}

	@Override
	public int modify(T object, String tableName) {
		int res = 0;
		if(object == null){
			return res;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		String sql = parseUpdate(object, tableName);
		//DEBUG
		if(debug){
			System.out.println("modify sql: " + sql);
		}
		try {
			res = jdbcOperate.update(sql, object);
			//DEBUG
			if(debug){
				System.out.println("modify result: " + res);
			}
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public int batchModify(List<T> objects) {
		if(objects == null || objects.size() == 0){
			return 0;
		}
		T object = objects.get(0);
		String tableName = "";
		if(object.getClass().isAnnotationPresent(Table.class)){
			tableName = object.getClass().getAnnotation(Table.class).value();
		}
		return batchModify(objects, tableName);
	}

	@Override
	public int batchModify(List<T> objects, String tableName) {
		int res = 0;
		if(objects == null || objects.size() == 0){
			return res;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		T object = objects.get(0);
		String sql = parseUpdate(object, tableName);
		//DEBUG
		if(debug){
			System.out.println("batch modify sql: "+sql);
		}
		try {
			int[] tmp = jdbcOperate.batchObject(sql, objects);
			for (int i : tmp) {
				res += i;
			}
			//DEBUG
			if(debug){
				System.out.println("batch modify result: " + res);
			}
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private String parseUpdate(T object, String tableName){
		String sql = "UPDATE `"+tableName+"` SET SQLS WHERE SQLW";
		String sqlS = "";
		String sqlW = "";
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
				String value = "";
				if(f.isAnnotationPresent(PrimaryKey.class)){
					value = f.getAnnotation(PrimaryKey.class).value();
					if(f.isAnnotationPresent(Column.class)){
						value = f.getAnnotation(Column.class).value();
					}
					fname = Tools.isNullOrEmpty(value) ? fname : value;
					sqlS += ",`"+fname+"`=:"+fname;
					sqlW += " and `"+fname+"`=:"+fname;
				}else{ 
					if(f.isAnnotationPresent(Column.class)){
						value = f.getAnnotation(Column.class).value();
					}
					fname = Tools.isNullOrEmpty(value) ? fname : value;
					sqlS += ",`"+fname+"`=:"+fname;
				}
			}
		}
		if(!Tools.isNullOrEmpty(sqlS)){
			sql = sql.replace("SQLS", sqlS.substring(1));
		}
		if(!Tools.isNullOrEmpty(sqlW)){
			sql = sql.replace("SQLW", sqlW.substring(" and ".length()));
		}else{
			sql = sql.replace("SQLW", " 1 = 1 ");
		}
		return sql;
	}

	@Override
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue) {
		//Type: MysqlBaseDaoImpl<T>, Class: MysqlBaseDaoImpl;
		Type type = getClass().getGenericSuperclass();
//		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
//		Class<?> clss = (Class<?>) params[0];
		String tableName = "";
		try {
			Class<?>[] clsses = Tools.getGenericClass((ParameterizedType) type);
			Class<?> clss = clsses[0];
			if(clss.isAnnotationPresent(Table.class)){
				tableName = clss.getAnnotation(Table.class).value();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return modify(updateCondition, updateValue, tableName);
	}

	@Override
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue, String tableName) {
		int res = 0;
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		if(updateValue == null || updateValue.isEmpty()){
			return res;
		}
		String sql = "UPDATE `"+tableName+"` SET SQLS WHERE SQLW";
		String sqlS = "";
		String sqlW = "";
		Map<String, Object> params = new HashMap<String, Object>();
		
		for (String key : updateValue.keySet()) {
			sqlS += ",`"+key+"`=:"+key;
			params.put(key, updateValue.get(key));
		}
		if(updateCondition != null){
			//key+"#"+key 是为了兼容  UPDATE table SET column=:column WHERE column=:column
			for (String key : updateCondition.keySet()) {
				sqlW += " and `"+key+"`=:"+key+"#"+key;
				params.put(key+"#"+key, updateCondition.get(key));
			}
		}
		if(!Tools.isNullOrEmpty(sqlS)){
			sql = sql.replace("SQLS", sqlS.substring(1));
		}
		if(!Tools.isNullOrEmpty(sqlW)){
			sql = sql.replace("SQLW", sqlW.substring(" and ".length()));
		}else{
			sql = sql.replace("SQLW", " 1 = 1 ");
		}
		//DEBUG
		if(debug){
			System.out.println("condition modify sql: "+sql);
		}
		try {
			res = jdbcOperate.update(sql, params);
			//DEBUG
			if(debug){
				System.out.println("condition modify result: " + res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
