package com.tools.db.dao;


import java.util.List;


public interface MysqlBaseDao<T> extends BaseDao<T> {
	
	//原生SQL
	public List<T> query(String sql);
	public int update(String sql);
	
}
