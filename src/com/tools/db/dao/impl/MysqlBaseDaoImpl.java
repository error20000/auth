package com.tools.db.dao.impl;

import java.util.List;
import java.util.Map;

import com.tools.db.dao.MysqlBaseDao;
import com.tools.jdbc.JdbcOperate;


public abstract class MysqlBaseDaoImpl<T> implements MysqlBaseDao<T> {
	
	protected JdbcOperate jdbcOperate;
	
	public abstract void initJdbcOperate();
	
	@Override
	public int save(T object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int save(T object, String tableName) {
		// TODO Auto-generated method stub
		return 0;
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
	public List<T> find(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> find(String sql, int start, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> find(String sql, Class<?> clss) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> find(String sql, Class<?> clss, int start, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long size(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> query(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
