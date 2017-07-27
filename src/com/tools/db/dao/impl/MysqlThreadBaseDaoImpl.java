package com.tools.db.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tools.jdbc.PrimaryKeyCondition;


public abstract class MysqlThreadBaseDaoImpl<T> extends MysqlBaseDaoImpl<T> {

	/**
	 * 线程数，默认：20
	 */
	protected int count = 20;
	
	private static ExecutorService service = null;

	public MysqlThreadBaseDaoImpl() {
		initJdbcOperate();
		service = Executors.newFixedThreadPool(count);
	}
	
	public void save2(T object) {
		MysqlBaseDaoImpl<T> parent = this;
		service.submit(new Runnable() {
			@Override
			public void run() {
				parent.save(object);
			}
		});
	}
	
	@Override
	public int save(T object, String tableName) {
		// TODO Auto-generated method stub
		return super.save(object, tableName);
	}

	@Override
	public int save(T object, List<PrimaryKeyCondition> pkeys, String tableName) {
		// TODO Auto-generated method stub
		return super.save(object, pkeys, tableName);
	}

	@Override
	public int batchSave(List<T> objects) {
		// TODO Auto-generated method stub
		return super.batchSave(objects);
	}

	@Override
	public int batchSave(List<T> objects, String tableName) {
		// TODO Auto-generated method stub
		return super.batchSave(objects, tableName);
	}

	@Override
	public int batchSave(List<T> objects, List<PrimaryKeyCondition> pkeys, String tableName) {
		// TODO Auto-generated method stub
		return super.batchSave(objects, pkeys, tableName);
	}

	@Override
	public int modify(T object) {
		// TODO Auto-generated method stub
		return super.modify(object);
	}

	@Override
	public int modify(T object, String tableName) {
		// TODO Auto-generated method stub
		return super.modify(object, tableName);
	}

	@Override
	public int modify(T object, List<PrimaryKeyCondition> pkeys, String tableName) {
		// TODO Auto-generated method stub
		return super.modify(object, pkeys, tableName);
	}

	@Override
	public int batchModify(List<T> objects) {
		// TODO Auto-generated method stub
		return super.batchModify(objects);
	}

	@Override
	public int batchModify(List<T> objects, String tableName) {
		// TODO Auto-generated method stub
		return super.batchModify(objects, tableName);
	}

	@Override
	public int batchModify(List<T> objects, List<PrimaryKeyCondition> pkeys, String tableName) {
		// TODO Auto-generated method stub
		return super.batchModify(objects, pkeys, tableName);
	}

	@Override
	public int modify(Map<String, Object> updateValue, Map<String, Object> updateCondition) {
		// TODO Auto-generated method stub
		return super.modify(updateValue, updateCondition);
	}

	@Override
	public int modify(Map<String, Object> updateValue, Map<String, Object> updateCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.modify(updateValue, updateCondition, tableName);
	}

	@Override
	public int delete(Map<String, Object> deleteCondition) {
		// TODO Auto-generated method stub
		return super.delete(deleteCondition);
	}

	@Override
	public int delete(Map<String, Object> deleteCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.delete(deleteCondition, tableName);
	}

	@Override
	public int delete(String wsql, Map<String, Object> deleteCondition) {
		// TODO Auto-generated method stub
		return super.delete(wsql, deleteCondition);
	}

	@Override
	public int delete(String wsql, Map<String, Object> deleteCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.delete(wsql, deleteCondition, tableName);
	}

	@Override
	public int batchDelete(String column, List<String> columnValues) {
		// TODO Auto-generated method stub
		return super.batchDelete(column, columnValues);
	}

	@Override
	public int batchDelete(String column, List<String> columnValues, String tableName) {
		// TODO Auto-generated method stub
		return super.batchDelete(column, columnValues, tableName);
	}

	@Override
	public List<T> findList() {
		// TODO Auto-generated method stub
		return super.findList();
	}

	@Override
	public List<T> findList(String tableName) {
		// TODO Auto-generated method stub
		return super.findList(tableName);
	}

	@Override
	public List<T> findList(int start, int rows) {
		// TODO Auto-generated method stub
		return super.findList(start, rows);
	}

	@Override
	public List<T> findList(int start, int rows, String tableName) {
		// TODO Auto-generated method stub
		return super.findList(start, rows, tableName);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findList(queryCondition);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.findList(queryCondition, tableName);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows) {
		// TODO Auto-generated method stub
		return super.findList(queryCondition, start, rows);
	}

	@Override
	public List<T> findList(Map<String, Object> queryCondition, int start, int rows, String tableName) {
		// TODO Auto-generated method stub
		return super.findList(queryCondition, start, rows, tableName);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findList(wsql, queryCondition);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.findList(wsql, queryCondition, tableName);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows) {
		// TODO Auto-generated method stub
		return super.findList(wsql, queryCondition, start, rows);
	}

	@Override
	public List<T> findList(String wsql, Map<String, Object> queryCondition, int start, int rows, String tableName) {
		// TODO Auto-generated method stub
		return super.findList(wsql, queryCondition, start, rows, tableName);
	}

	@Override
	public T findObject(Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findObject(queryCondition);
	}

	@Override
	public T findObject(Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.findObject(queryCondition, tableName);
	}

	@Override
	public T findObject(String wsql, Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findObject(wsql, queryCondition);
	}

	@Override
	public T findObject(String wsql, Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.findObject(wsql, queryCondition, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, queryCondition);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition,
			String tableName) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, queryCondition, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start,
			int rows) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, queryCondition, start, rows);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, Map<String, Object> queryCondition, int start,
			int rows, String tableName) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, queryCondition, start, rows, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql,
			Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, wsql, queryCondition);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition,
			String tableName) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, wsql, queryCondition, tableName);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition,
			int start, int rows) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, wsql, queryCondition, start, rows);
	}

	@Override
	public List<Map<String, Object>> findMapList(List<String> columns, String wsql, Map<String, Object> queryCondition,
			int start, int rows, String tableName) {
		// TODO Auto-generated method stub
		return super.findMapList(columns, wsql, queryCondition, start, rows, tableName);
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findMap(columns, queryCondition);
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.findMap(columns, queryCondition, tableName);
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.findMap(columns, wsql, queryCondition);
	}

	@Override
	public Map<String, Object> findMap(List<String> columns, String wsql, Map<String, Object> queryCondition,
			String tableName) {
		// TODO Auto-generated method stub
		return super.findMap(columns, wsql, queryCondition, tableName);
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public long size(String tableName) {
		// TODO Auto-generated method stub
		return super.size(tableName);
	}

	@Override
	public long size(Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.size(queryCondition);
	}

	@Override
	public long size(Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.size(queryCondition, tableName);
	}

	@Override
	public long size(String wsql, Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return super.size(wsql, queryCondition);
	}

	@Override
	public long size(String wsql, Map<String, Object> queryCondition, String tableName) {
		// TODO Auto-generated method stub
		return super.size(wsql, queryCondition, tableName);
	}
	
	

	
}
