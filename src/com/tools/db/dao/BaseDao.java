package com.tools.db.dao;


import java.util.List;
import java.util.Map;


/**
 * 当查询参数里包含wsql时，参数queryCondition不会编译为查询条件；
 * 当查询参数里不包含wsql时，参数queryCondition会编译为查询条件。
 * @author liujian
 */
public interface BaseDao<T> {
	
	public int save(T object);
	public int save(T object, String tableName);
	public int batchSave(List<T> objects);
	public int batchSave(List<T> objects, String tableName);
	
	public int modify(T object);
	public int modify(T object, String tableName);
	public int batchModify(List<T> objects);
	public int batchModify(List<T> objects, String tableName);
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue);
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue, String tableName);
	
	public int delete(Map<String, Object> deleteCondition);
	public int delete(Map<String, Object> deleteCondition, String tableName);
	public int delete(String wsql, Map<String, Object> deleteCondition);
	public int delete(String wsql, Map<String, Object> deleteCondition, String tableName);
	public int batchDelete(String column, List<String> columnValues);
	public int batchDelete(String column, List<String> columnValues, String tableName);
	
	public List<T> findList();
	public List<T> findList(String tableName);
	public List<T> findList(int start, int rows);
	public List<T> findList(int start, int rows, String tableName);
	public List<T> findList(Map<String, Object> queryCondition);
	public List<T> findList(Map<String, Object> queryCondition, String tableName);
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows);
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows, String tableName);
	public List<T> findList(String wsql, Map<String, Object> queryCondition);
	public List<T> findList(String wsql, Map<String, Object> queryCondition, String tableName);
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows);
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows, String tableName);
	
	public T findObject(Map<String, Object> queryCondition);
	public T findObject(Map<String, Object> queryCondition, String tableName);
	public T findObject(String wsql, Map<String, Object> queryCondition);
	public T findObject(String wsql, Map<String, Object> queryCondition, String tableName);
	
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition);
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, String tableName);
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start, int rows);
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start, int rows,  String tableName);
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition);
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition, String tableName);
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition, int start, int rows);
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition, int start, int rows,  String tableName);
	
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition);
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition, String tableName);
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition);
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition, String tableName);

	public long size();
	public long size(String tableName);
	public long size(Map<String, Object> queryCondition);
	public long size(Map<String, Object> queryCondition, String tableName);
	public long size(String wsql, Map<String, Object> queryCondition);
	public long size(String wsql, Map<String, Object> queryCondition, String tableName);
	
}
