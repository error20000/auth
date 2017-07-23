package com.tools.db.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.tools.db.dao.MysqlBaseDao;
import com.tools.jdbc.JdbcOperate;


public abstract class MysqlBaseDaoImpl<T> implements MysqlBaseDao<T> {
	
	protected DataSource dataSource = null;
	protected JdbcOperate jdbcOperate = null;
	
	/**
	 * 如果需要使用事物，要初始化dataSource
	 */
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
