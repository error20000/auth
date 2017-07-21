package com.tools.db.dao;

import java.util.List;

public interface MongoBaseDao<T> extends BaseDao<T> {
	
	//原生Mongo
	public List<T> query(String sql);
	public int update(String sql);
}
