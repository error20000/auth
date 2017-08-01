package com.tools.auto;

import com.auth.dao.BaseDao;
import com.auth.dao.util.JdbcOperateManager;
import com.tools.db.dao.impl.MysqlBaseDaoImpl;

/**
 * com.tools.auto 自动生成
 * @author liu
 *
 * @param <T>
 */
public abstract class BaseDaoImpl<T> extends MysqlBaseDaoImpl<T> implements BaseDao<T> {
	
	
	@Override
	public void initJdbcOperate() {
		this.jdbcOperate = JdbcOperateManager.getJdbcOperate();
		this.log = true;
	}

	
}
