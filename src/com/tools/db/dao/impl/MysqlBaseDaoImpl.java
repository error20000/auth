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
import java.util.UUID;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.tools.db.dao.MysqlBaseDao;
import com.tools.jdbc.Column;
import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.PrimaryKeyType;
import com.tools.jdbc.Table;
import com.tools.utils.LogsTool;
import com.tools.utils.ResultKey;
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
	/**
	 * 保存对象，返回int受影响条数。
	 * @param object 被保存对象
	 * @return int 1 成功，-1 失败
	 */
	@Override
	public int save(T object) {
		if(object == null){
			return 0;
		}
		String tableName = getTableName();
		return save(object, tableName);
	}

	/**
	 * 保存对象，返回int受影响条数。
	 * @param object 被保存对象
	 * @param tableName 表名
	 * @return int 1 成功，-1 失败
	 */
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
		//解析主键
		parsePrimateKey(object);
		
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

	/**
	 * 批量保存对象，返回int总受影响条数。
	 * @param objects 被保存对象，list集合
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int batchSave(List<T> objects) {
		if(objects == null || objects.size() == 0){
			return 0;
		}
		String tableName = getTableName();
		return batchSave(objects, tableName);
	}

	/**
	 * 批量保存对象，返回int总受影响条数。
	 * @param objects 被保存对象，list集合
	 * @param tableName 表名
	 * @return int 1 成功，0 失败
	 */
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
		//解析主键
		parsePrimateKey(object);
		
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
	
	private void parsePrimateKey(T object){
		List<Map<String, Object>> pks = getPrimaryKeys(object.getClass());
		for (Map<String, Object> node : pks) {
			switch ((PrimaryKeyType) node.get(ResultKey.KEYTYPE)) {
			case UUID:
				//处理主键为uuid、并且String类型的
				if("String".equals(node.get(ResultKey.TYPE))){
					String nameGet = "get"+node.get(ResultKey.FIELD).toString().substring(0, 1).toUpperCase()+node.get(ResultKey.FIELD).toString().substring(1);
					Method methdGet = null;
					Object valueGet = null;
					String nameSet = "set"+node.get(ResultKey.FIELD).toString().substring(0, 1).toUpperCase()+node.get(ResultKey.FIELD).toString().substring(1);
					Method methdSet = null;
					try {
						methdGet  = Tools.findMethod(object.getClass(), nameGet, 0)[0];
						valueGet = methdGet.invoke(object);
						methdSet  = Tools.findMethod(object.getClass(), nameSet, 1)[0];
						//如果主键为空自动创建一个md5的uuid
						if(Tools.isNullOrEmpty(valueGet)){
							methdSet.invoke(object, Tools.md5(UUID.randomUUID().toString()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
				
			case AUTO_INCREMENT:
				
				break;

			default:
				break;
			}
		}
	}

	//TODO modify
	/**
	 * 修改对象，返回int受影响条数。
	 * @param object 被修改对象。
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int modify(T object) {
		if(object == null){
			return 0;
		}
		String tableName = getTableName();
		return modify(object, tableName);
	}

	/**
	 * 修改对象，返回int受影响条数。
	 * @param object 被修改对象。
	 * @param tableName 表名
	 * @return int 1 成功，0 失败
	 */
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
	
	/**
	 * 批量修改对象，返回int总受影响条数。
	 * @param objects 被修改对象，list集合
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int batchModify(List<T> objects) {
		if(objects == null || objects.size() == 0){
			return 0;
		}
		String tableName = getTableName();
		return batchModify(objects, tableName);
	}
	
	/**
	 * 批量修改对象，返回int总受影响条数。
	 * @param objects 被修改对象，list集合
	 * @param tableName 表名
	 * @return int 1 成功，0 失败
	 */
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
			sql = sql.replace("SQLW", "");
		}
		return sql;
	}

	/**
	 * 条件修改，返回int受影响条数。
	 * @param updateValue 修改值
	 * @param updateCondition 修改条件
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int modify(Map<String, Object> updateValue, Map<String, Object> updateCondition) {
		//Type: MysqlBaseDaoImpl<T>, Class: MysqlBaseDaoImpl;
//		Type type = getClass().getGenericSuperclass();
////		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
////		Class<?> clss = (Class<?>) params[0];
//		String tableName = "";
//		try {
//			Class<?>[] clsses = Tools.getGenericClass((ParameterizedType) type);
//			Class<?> clss = clsses[0];
//			if(clss.isAnnotationPresent(Table.class)){
//				tableName = clss.getAnnotation(Table.class).value();
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		String tableName = getTableName();
		return modify(updateCondition, updateValue, tableName);
	}

	/**
	 * 条件修改，返回int受影响条数。
	 * @param updateValue 修改值
	 * @param updateCondition 修改条件
	 * @param tableName 表名
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int modify(Map<String, Object> updateValue, Map<String, Object> updateCondition, String tableName) {
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
	/**
	 * 条件删除，返回int受影响条数。
	 * @param deleteCondition 删除条件
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int delete(Map<String, Object> deleteCondition) {
		String tableName = getTableName();
		return delete(deleteCondition, tableName);
	}

	/**
	 * 条件删除，返回int受影响条数。
	 * @param deleteCondition 删除条件
	 * @param tableName 表名
	 * @return int 1 成功，0 失败
	 */
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

	/**
	 * 条件删除，返回int受影响条数。
	 * @param wsql 删除条件的where语句，不包括where。如 id=:id
	 * @param deleteCondition 删除条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int delete(String wsql, Map<String, Object> deleteCondition) {
		String tableName = getTableName();
		return delete(wsql, deleteCondition, tableName);
	}

	/**
	 * 条件删除，返回int受影响条数。
	 * @param wsql 删除条件的where语句，不包括where。如 id=:id
	 * @param deleteCondition 删除条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @param tableName 表名。
	 * @return int 1 成功，0 失败
	 */
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

	/**
	 * 批量删除，返回int受影响条数。
	 * @param column 数据表列名。如 id
	 * @param columnValues 值，list集合。
	 * @return int 1 成功，0 失败
	 */
	@Override
	public int batchDelete(String column, List<String> columnValues) {
		String tableName = getTableName();
		return batchDelete(column, columnValues, tableName);
	}

	/**
	 * 批量删除，返回int受影响条数。
	 * @param column 数据表列名。如 id
	 * @param columnValues 值，list集合。
	 * @param tableName 表名。
	 * @return int 1 成功，0 失败
	 */
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
	/**
	 * 查询数量，返回对象list集合。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList() {
		String tableName = getTableName();
		return findList(tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param tableName 表名。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(String tableName) {
		return findList(0, -1, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(int start, int rows) {
		String tableName = getTableName();
		return findList(start, rows, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @param tableName 表名。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(int start, int rows, String tableName) {
		return findList(new HashMap<String, Object>(), start, rows, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param queryCondition 查询条件。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findList(queryCondition, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param queryCondition 查询条件。
	 * @param tableName 表名。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(Map<String, Object> queryCondition, String tableName) {
		return findList(queryCondition, 0, -1, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param queryCondition 查询条件。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows) {
		String tableName = getTableName();
		return findList(queryCondition, start, rows, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param queryCondition 查询条件。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @param tableName 表名。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findList(wsql, queryCondition, start, rows, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return  findList(wsql, queryCondition, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @param tableName 表名。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, String tableName) {
		return findList(wsql, queryCondition, 0, -1, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @return list 对象集合。
	 */
	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows) {
		String tableName = getTableName();
		return findList(wsql, queryCondition, start, rows, tableName);
	}

	/**
	 * 查询数量，返回对象list集合。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @param tableName 表名。
	 * @return list 对象集合。
	 */
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
	
	/**
	 * 查询对象，返回对象。
	 * @param queryCondition 查询条件。
	 * @return T 对象。
	 */
	@Override
	public T findObject(Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findObject(queryCondition, tableName);
	}
	
	/**
	 * 查询对象，返回对象。
	 * @param queryCondition 查询条件。
	 * @param tableName 表名。
	 * @return T 对象。
	 */
	@Override
	public T findObject(Map<String, Object> queryCondition, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findObject(wsql, queryCondition, tableName);
	}
	
	/**
	 * 查询对象，返回对象。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @return T 对象。
	 */
	@Override
	public T findObject(String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findObject(wsql, queryCondition, tableName);
	}

	/**
	 * 查询对象，返回对象。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @param tableName 表名。
	 * @return T 对象。
	 */
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
	
	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param queryCondition 查询条件.
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMapList(columns, queryCondition, tableName);
	}
	
	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param queryCondition 查询条件.
	 * @param tableName 表名。
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, String tableName) {
		return findMapList(columns, queryCondition, 0, -1, tableName);
	}
	
	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param queryCondition 查询条件.
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start, int rows) {
		String tableName = getTableName();
		return findMapList(columns, queryCondition, start, rows, tableName);
	}

	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param queryCondition 查询条件.
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @param tableName 表名。
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start, int rows, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findMapList(columns, wsql, queryCondition, start, rows, tableName);
	}

	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMapList(columns, wsql, queryCondition, tableName);
	}

	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @param tableName 表名。
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition, String tableName) {
		return findMapList(columns, wsql, queryCondition, 0, -1, tableName);
	}

	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @return list maplist集合。
	 */
	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition,int start, int rows) {
		String tableName = getTableName();
		return findMapList(columns, wsql, queryCondition, start, rows, tableName);
	}

	/**
	 * 查询数量，返回maplist集合。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @param start 起始条数。
	 * @param rows 查询条数。
	 * @param tableName 表名。
	 * @return list maplist集合。
	 */
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

	/**
	 * 查询对象，返回map。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param queryCondition 查询条件。
	 * @return map map对象。
	 */
	@Override
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMap(columns, queryCondition, tableName);
	}

	/**
	 * 查询对象，返回map。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param queryCondition 查询条件。
	 * @param tableName 表名。
	 * @return map map对象。
	 */
	@Override
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return findMap(columns, wsql, queryCondition, tableName);
	}
	
	/**
	 * 查询对象，返回map。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @return map map对象。
	 */
	@Override
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return findMap(columns, wsql, queryCondition, tableName);
	}

	/**
	 * 查询对象，返回map。
	 * @param columns 需要返回的列名，默认*。 如 id、name、count(1)
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx。
	 * @param tableName 表名。
	 * @return map map对象。
	 */
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
	/**
	 * 查询条数，返回long总条数。
	 * @return long 总条数。
	 */
	@Override
	public long size() {
		String tableName = getTableName();
		return size(tableName);
	}

	/**
	 * 查询条数，返回long总条数。
	 * @param tableName 表名。
	 * @return long 总条数。
	 */
	@Override
	public long size(String tableName) {
		return size(new HashMap<String, Object>(), tableName);
	}

	/**
	 * 查询条数，返回long总条数。
	 * @param queryCondition 查询条件。
	 * @return long 总条数。
	 */
	@Override
	public long size(Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return size(queryCondition, tableName);
	}

	/**
	 * 查询条数，返回long总条数。
	 * @param queryCondition 查询条件。
	 * @param tableName 表名。
	 * @return long 总条数。
	 */
	@Override
	public long size(Map<String, Object> queryCondition, String tableName) {
		String wsql = " 1=1 ";
		for (String key : queryCondition.keySet()) {
			wsql += " and `"+key+"`=:"+key;
		}
		return size(wsql, queryCondition, tableName);
	}

	/**
	 * 查询条数，返回long总条数。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @return long 总条数。
	 */
	@Override
	public long size(String wsql, Map<String, Object> queryCondition) {
		String tableName = getTableName();
		return size(wsql, queryCondition, tableName);
	}

	/**
	 * 查询条数，返回long总条数。
	 * @param wsql 查询条件的where语句，不包括where。如 id=:id
	 * @param queryCondition 查询条件赋值，不参与拼接sql语句，不能为null。如 key：id，value：xxx
	 * @param tableName 表名
	 * @return long 总条数。
	 */
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
	
	private List<Map<String, Object>> getPrimaryKeys(Class<?> clss){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Field[] fields = Tools.getFields(clss);
		for (Field f : fields) {
			if(f.isAnnotationPresent(PrimaryKey.class)){
				Map<String, Object> node = new HashMap<String, Object>();
				node.put(ResultKey.FIELD, f.getName());
				node.put(ResultKey.TYPE, f.getType().getSimpleName());
				node.put(ResultKey.KEYTYPE, f.getAnnotation(PrimaryKey.class).type());
				list.add(node);
			}
		}
		return list;
	}


}
