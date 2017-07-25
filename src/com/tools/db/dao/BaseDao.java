package com.tools.db.dao;


import java.util.List;
import java.util.Map;


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
	
	public List<T> find();
	public List<T> find(String tableName);
	public List<T> find(int start, int rows);
	public List<T> find(int start, int rows, String tableName);
	public List<T> find(Map<String, Object> queryCondition);
	public List<T> find(Map<String, Object> queryCondition, String tableName);
	public List<T> find(Map<String, Object> queryCondition, int start, int rows);
	public List<T> find(Map<String, Object> queryCondition, int start, int rows, String tableName);
	public List<T> find(String wsql, Map<String, Object> queryCondition);
	public List<T> find(String wsql, Map<String, Object> queryCondition, String tableName);
	public List<T> find(String wsql, Map<String, Object> queryCondition, int start, int rows);
	public List<T> find(String wsql, Map<String, Object> queryCondition, int start, int rows, String tableName);

	public long size();
	public long size(String tableName);
	public long size(Map<String, Object> queryCondition);
	public long size(Map<String, Object> queryCondition, String tableName);
	public long size(String wsql, Map<String, Object> queryCondition);
	public long size(String wsql, Map<String, Object> queryCondition, String tableName);
	
}
