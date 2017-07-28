package com.tools.service;

import com.tools.db.dao.MysqlThreadBaseDao;

/**
 * service层基础接口，继承 AbstractBaseService
 * @author liujian
 *
 * @param <T>
 * @see com.tools.db.dao.AbstractBaseDao
 * @see com.tools.db.dao.MysqlThreadBaseDao
 * @see com.tools.service.AbstractBaseService
 */
public abstract class MysqlThreadBaseService<T> extends AbstractBaseService<T> {

	protected MysqlThreadBaseDao<T> baseDao;
	
	public MysqlThreadBaseService() {
		initBaseDao();
	}

	/**
	 * 如果需要使用事物，要初始化{@code MysqlThreadBaseDao}
	 */
	public abstract void initBaseDao();
	

	public void exeJob(Runnable runnable) {
		baseDao.exeJob(runnable);
	}

	
}
