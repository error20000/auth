package com.tools.db.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tools.jdbc.PrimaryKeyCondition;

/**
 * 继承{@code MysqlBaseDaoImpl} ，提供固定线程池。默认： 20。
 * @author liujian
 *
 * @param <T>
 * @see com.tools.db.dao.impl.MysqlBaseDaoImpl
 */
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
	
	/**
	 * 使用线程池执行CRUD
	 * @param runnable CRUD任务
	 */
	public void exeJob(Runnable runnable) {
		service.submit(runnable);
	}
	

	
}
