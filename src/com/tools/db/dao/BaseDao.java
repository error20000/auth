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
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue);
	public int modify(Map<String, Object> updateCondition, Map<String, Object> updateValue, String tableName);
	public int modify(String wsql);
	public int modify(String wsql, String tableName);
	
	public int delete(Map<String, Object> deleteCondition);
	public int delete(Map<String, Object> deleteCondition, String tableName);
	public int delete(String wsql);
	public int delete(String wsql, String tableName);
	public int batchDelete(String fieldName, List<String> fieldValues);
	public int batchDelete(String fieldName, List<String> fieldValues, String tableName);
	
	public List<T> find();
	public List<T> find(int start, int rows);
	public List<T> find(String sql);
	public List<T> find(String sql, int start, int rows);
	public List<?> find(String sql, Class<?> clss);
	public List<?> find(String sql, Class<?> clss, int start, int rows);

	public long size();
	public long size(String sql);
	
}
