package com.auth.dao.impl;

import com.auth.dao.BaseDao;
import com.auth.dao.util.JdbcOperateManager;
import com.tools.db.dao.impl.MysqlBaseDaoImpl;

public abstract class BaseDaoImpl<T> extends MysqlBaseDaoImpl<T> implements BaseDao<T> {
	
	
	@Override
	public void initJdbcOperate() {
		this.jdbcOperate = JdbcOperateManager.getJdbcOperate();
	}

	
}
