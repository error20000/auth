package com.auth.dao.util;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

public class JdbcOperateManager {
	
	private static JdbcOperate jdbcOperate;
	private static DataSource dataSource;
	
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
