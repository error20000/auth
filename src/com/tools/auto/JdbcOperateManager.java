package com.tools.auto;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

/**
 * com.tools.auto 自动生成
 * @author liu
 *
 * @param <T>
 */
public class JdbcOperateManager {
	
	private static DataSource dataSource = null;
	private static JdbcOperate jdbcOperate = null;
	
	static{
		String dbpath = "db.properties";
		dataSource = new C3P0PropertiesConfig(dbpath).getDataSource();
		jdbcOperate = new JdbcOperate(dataSource);
	}
	
	public static JdbcOperate getJdbcOperate() {
		return jdbcOperate;
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	

}
