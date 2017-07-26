package com.tools.db.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.tools.db.dao.MysqlBaseDao;
import com.tools.jdbc.Column;
import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.Table;
import com.tools.utils.LogsTool;
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
			LogsTool.logSet("sql debug", "【SAVE】save sql: " + sql);
			LogsTool.logSet("sql debug", "【SAVE】save params: " + JSON.toJSONString(object));
		}
		try {
			res = jdbcOperate.update(sql, object);
			//DEBUG
			if(debug){
				System.out.println("save result: " + res);
				LogsTool.logSet("sql debug", "【SAVE】save result: " + res);
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
			System.out.println("batch save sql: " + sql);
			LogsTool.logSet("sql debug", "【SAVE】batch save sql: " + sql);
			LogsTool.logSet("sql debug", "【SAVE】batch save params: " + JSON.toJSONString(objects));
		}
		try {
			int[] tmp = jdbcOperate.batchObject(sql, objects);
			for (int i : tmp) {
				res += i;
			}
			//DEBUG
			if(debug){
				System.out.println("batch save result: " + res);
				LogsTool.logSet("sql debug", "【SAVE】 batch save result: " + res);
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
			LogsTool.logSet("sql debug", "【MODIFY】 modify sql: " + sql);
			LogsTool.logSet("sql debug", "【MODIFY】 modify params: " + JSON.toJSONString(object));
		}
		try {
			res = jdbcOperate.update(sql, object);
			//DEBUG
			if(debug){
				System.out.println("【MODIFY】 modify result: " + res);
				LogsTool.logSet("sql debug", "【MODIFY】 modify result: " + res);
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
			System.out.println("batch modify sql: " + sql);
			LogsTool.logSet("sql debug", "【MODIFY】 batch modify sql: " + sql);
			LogsTool.logSet("sql debug", "【MODIFY】 batch modify params: " + JSON.toJSONString(objects));
		}
		try {
			int[] tmp = jdbcOperate.batchObject(sql, objects);
			for (int i : tmp) {
				res += i;
			}
			//DEBUG
			if(debug){
				System.out.println("batch modify result: " + res);
				LogsTool.logSet("sql debug", "【MODIFY】 batch modify result: " + res);
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
			System.out.println("condition modify sql: " + sql);
			LogsTool.logSet("sql debug", "【MODIFY】 condition modify sql: " + sql);
			LogsTool.logSet("sql debug", "【MODIFY】 condition modify params: " + JSON.toJSONString(params));
		}
		try {
			res = jdbcOperate.update(sql, params);
			//DEBUG
			if(debug){
				System.out.println("condition modify result: " + res);
				LogsTool.logSet("sql debug", "【MODIFY】 condition modify result: " + res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//TODO delete
	@Override
	public int delete(Map<String, Object> deleteCondition) {
		Type type = getClass().getGenericSuperclass();
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
		return delete(deleteCondition, tableName);
	}

	@Override
	public int delete(Map<String, Object> deleteCondition, String tableName) {
		int res = 0;
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		if(deleteCondition == null || deleteCondition.isEmpty()){
			return res;
		}
		String sql = "DELETE FROM `"+tableName+"` WHERE SQLW";
		String sqlW = "";
		
		for (String key : deleteCondition.keySet()) {
			sqlW += " and `"+key+"`=:"+key;
		}
		if(!Tools.isNullOrEmpty(sqlW)){
			sql = sql.replace("SQLW", sqlW.substring(" and ".length()));
		}
		//DEBUG
		if(debug){
			System.out.println("delete sql: " + sql);
			LogsTool.logSet("sql debug", "【DELETE】 delete sql: " + sql);
			LogsTool.logSet("sql debug", "【DELETE】 delete params: " + JSON.toJSONString(deleteCondition));
		}
		try {
			res = jdbcOperate.update(sql, deleteCondition);
			//DEBUG
			if(debug){
				System.out.println("delete result: " + res);
				LogsTool.logSet("sql debug", "【DELETE】 delete result: " + res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(String wsql, Map<String, Object> deleteCondition) {
		Type type = getClass().getGenericSuperclass();
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
		return delete(wsql, deleteCondition, tableName);
	}

	@Override
	public int delete(String wsql, Map<String, Object> deleteCondition, String tableName) {
		int res = 0;
		if(Tools.isNullOrEmpty(wsql)){
			return res;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		if(deleteCondition == null || deleteCondition.isEmpty()){
			return res;
		}
		String sql = "DELETE FROM `"+tableName+"` where " + wsql;
		
		//DEBUG
		if(debug){
			System.out.println("wsql delete sql: " + sql);
			LogsTool.logSet("sql debug", "【DELETE】 wsql delete sql: " + sql);
			LogsTool.logSet("sql debug", "【DELETE】 wsql delete params: " + JSON.toJSONString(deleteCondition));
		}
		try {
			res = jdbcOperate.update(sql, deleteCondition);
			//DEBUG
			if(debug){
				System.out.println("wsql delete result: " + res);
				LogsTool.logSet("sql debug", "【DELETE】 wsql delete result: " + res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int batchDelete(String column, List<String> columnValues) {
		Type type = getClass().getGenericSuperclass();
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
		return batchDelete(column, columnValues, tableName);
	}

	@Override
	public int batchDelete(String column, List<String> columnValues, String tableName) {
		int res = 0;
		if(Tools.isNullOrEmpty(column)){
			return res;
		}
		if(columnValues == null || columnValues.size() == 0){
			return res;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		String sql = "DELETE FROM `"+tableName+"` WHERE `"+column+"`=:" + column;
		//DEBUG
		if(debug){
			System.out.println("batch delete sql: " + sql);
			LogsTool.logSet("sql debug", "【DELETE】 batch delete sql: " + sql);
			LogsTool.logSet("sql debug", "【DELETE】 batch delete params: " + JSON.toJSONString(columnValues));
		}
		try {
			int[] tmp = jdbcOperate.batchBasicType(sql, columnValues);
			for (int i : tmp) {
				res += i;
			}
			//DEBUG
			if(debug){
				System.out.println("batch delete result: " + res);
				LogsTool.logSet("sql debug", "【DELETE】 batch delete result: " + res);
			}
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}
	

	//TODO find
	@Override
	public List<T> findList() {
		String tableName = getTableName();
		return findList(tableName);
	}

	@Override
	public List<T> findList(String tableName) {
		return findList(0, -1, tableName);
	}

	@Override
	public List<T> findList(int start, int rows) {
		String tableName = getTableName();
		return findList(start, rows, tableName);
	}

	@Override
	public List<T> findList(int start, int rows, String tableName) {
		return findList(new HashMap<String, Object>(), start, rows, tableName);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findList(queryCondition, tableName);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition, String tableName) {
		return findList(queryCondition, 0, -1, tableName);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows) {
		String tableName = getTableName();
		return findList(queryCondition, start, rows, tableName);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findList(wsql, queryCondition, start, rows, tableName);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return  findList(wsql, queryCondition, tableName);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, String tableName) {
		return findList(wsql, queryCondition, 0, -1, tableName);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows) {
		String tableName = getTableName();
		return findList(wsql, queryCondition, start, rows, tableName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows, String tableName) {
		List<T> res = new ArrayList<T>();
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		if(queryCondition == null){
			return res;
		}
		if(Tools.isNullOrEmpty(wsql)){
			return res;
		}
		String sql = "SELECT * FROM `"+tableName+"` WHERE " + wsql;
		//-1 不参与分页
		if(rows != -1){
			sql += " limit " + start + ", " + rows;
		}
		//DEBUG
		if(debug){
			System.out.println("list find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 list find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 list find params: " + JSON.toJSONString(queryCondition));
		}
		try {
			Type type = getClass().getGenericSuperclass();
			Class<?>[] clsses = Tools.getGenericClass((ParameterizedType) type);
			Class<?> clss = clsses[0];
			res = (List<T>) jdbcOperate.queryObjectList(sql, clss, queryCondition);
			//DEBUG
			if(debug){
				System.out.println("find result: " + res);
				LogsTool.logSet("sql debug", "【QUERY】 list find result: " + JSON.toJSONString(res));
			}
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public T findObject(Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findObject(queryCondition, tableName);
	}
	
	@Override
	public T findObject(Map<String, Object> queryCondition, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findObject(wsql, queryCondition, tableName);
	}
	
	@Override
	public T findObject(String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findObject(wsql, queryCondition, tableName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findObject(String wsql, Map<String, Object> queryCondition, String tableName) {
		if(Tools.isNullOrEmpty(tableName)){
			return null;
		}
		if(queryCondition == null){
			return null;
		}
		if(Tools.isNullOrEmpty(wsql)){
			return null;
		}
		String sql = "SELECT * FROM `"+tableName+"` WHERE " + wsql;
		
		//DEBUG
		if(debug){
			System.out.println("object find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 object find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 object find params: " + JSON.toJSONString(queryCondition));
		}
		try {
			Type type = getClass().getGenericSuperclass();
			Class<?>[] clsses = Tools.getGenericClass((ParameterizedType) type);
			Class<?> clss = clsses[0];
			T res = (T) jdbcOperate.queryObject(sql, clss, queryCondition);
			//DEBUG
			if(debug){
				System.out.println("find result: " + res);
				LogsTool.logSet("sql debug", "【QUERY】 object find result: " + JSON.toJSONString(res));
			}
			return res;
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMapList(columns, queryCondition, tableName);
	}
	
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, String tableName) {
		return findMapList(columns, queryCondition, 0, -1, tableName);
	}
	
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start, int rows) {
		String tableName = getTableName();
		return findMapList(columns, queryCondition, start, rows, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start, int rows, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findMapList(columns, wsql, queryCondition, start, rows, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMapList(columns, wsql, queryCondition, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition, String tableName) {
		return findMapList(columns, wsql, queryCondition, 0, -1, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition,int start, int rows) {
		String tableName = getTableName();
		return findMapList(columns, wsql, queryCondition, start, rows, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition, int start, int rows, String tableName) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		if(queryCondition == null){
			return res;
		}
		if(Tools.isNullOrEmpty(wsql)){
			return res;
		}
		String sql = "SELECT FIELDS FROM `"+tableName+"` WHERE " + wsql;
		String fields = "";
		if(columns != null){
			for (String str : columns) {
				fields += ","+str;
			}
		}
		if(!Tools.isNullOrEmpty(fields)){
			sql = sql.replace("FIELDS", fields.substring(1));
		}else{
			sql = sql.replace("FIELDS", "*");
		}
		//-1 不参与分页
		if(rows != -1){
			sql += " limit " + start + ", " + rows;
		}
		
		//DEBUG
		if(debug){
			System.out.println("mapList find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 mapList find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 mapList find params: " + JSON.toJSONString(queryCondition));
		}
		try {
			res = jdbcOperate.queryMapList(sql, queryCondition);
			//DEBUG
			if(debug){
				System.out.println("mapList find result: " + res);
				LogsTool.logSet("sql debug", "【QUERY】 mapList find result: " + JSON.toJSONString(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMap(columns, queryCondition, tableName);
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findMap(columns, wsql, queryCondition, tableName);
	}
	
	@Override
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMap(columns, wsql, queryCondition, tableName);
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition, String tableName) {
		if(Tools.isNullOrEmpty(tableName)){
			return null;
		}
		if(queryCondition == null){
			return null;
		}
		if(Tools.isNullOrEmpty(wsql)){
			return null;
		}
		String sql = "SELECT FIELDS FROM `" + tableName + "` WHERE " + wsql;
		String fields = "";
		if(columns != null){
			for (String str : columns) {
				fields += ","+str;
			}
		}
		if(!Tools.isNullOrEmpty(fields)){
			sql = sql.replace("FIELDS", fields.substring(1));
		}else{
			sql = sql.replace("FIELDS", "*");
		}
		
		//DEBUG
		if(debug){
			System.out.println("map find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 map find sql: " + sql);
			LogsTool.logSet("sql debug", "【QUERY】 map find params: " + JSON.toJSONString(queryCondition));
		}
		try {
			Map<String, Object> res = jdbcOperate.queryMap(sql, queryCondition);
			//DEBUG
			if(debug){
				System.out.println("map find result: " + res);
				LogsTool.logSet("sql debug", "【QUERY】 map find result: " + JSON.toJSONString(res));
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//TODO size
	@Override
	public long size() {
		String tableName = getTableName();
		return size(tableName);
	}

	@Override
	public long size(String tableName) {
		return size(new HashMap<String, Object>(), tableName);
	}

	@Override
	public long size(Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return size(queryCondition, tableName);
	}

	@Override
	public long size(Map<String, Object> queryCondition, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return size(wsql, queryCondition, tableName);
	}

	@Override
	public long size(String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return size(wsql, queryCondition, tableName);
	}

	@Override
	public long size(String wsql, Map<String, Object> queryCondition, String tableName) {
		long res = 0;
		if(Tools.isNullOrEmpty(tableName)){
			return res;
		}
		if(queryCondition == null){
			return res;
		}
		if(Tools.isNullOrEmpty(wsql)){
			return res;
		}
		String sql = "SELECT count(1) FROM `"+tableName+"` WHERE " + wsql;
		//DEBUG
		if(debug){
			System.out.println("size sql: " + sql);
			LogsTool.logSet("sql debug", "【COUNT】 size sql: " + sql);
			LogsTool.logSet("sql debug", "【COUNT】 size params: " + JSON.toJSONString(queryCondition));
		}
		try {
			res = jdbcOperate.queryObject(sql, Long.class, queryCondition);
			//DEBUG
			if(debug){
				System.out.println("size result: " + res);
				LogsTool.logSet("sql debug", "【COUNT】 size result: " + JSON.toJSONString(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	private String getTableName(){
		Type type = getClass().getGenericSuperclass();
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
		return tableName;
	}


}
