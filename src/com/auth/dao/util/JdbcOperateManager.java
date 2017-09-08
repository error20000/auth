package com.auth.dao.util;

import java.io.File;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

public class JdbcOperateManager {
	//主库配置
	private static DataSource dataSource = null;
	private static JdbcOperate jdbcOperate = null;
	//从库配置
	private static DataSource dataSourceSecond = null;
	private static JdbcOperate jdbcOperateSecond = null;
	
	static{
		String dbPath = "db.properties";
		C3P0PropertiesConfig config = null;
		File file = new File(dbPath);
		if(file.exists()){
			config = new C3P0PropertiesConfig(file);
			dataSource = config.getDataSource();
			dataSourceSecond = config.getDataSource();
		}else{
			config = new C3P0PropertiesConfig(dbPath);
			dataSource = config.getDataSource();
			dataSourceSecond = config.getDataSource();
		}
		jdbcOperate = new JdbcOperate(dataSource);
		jdbcOperateSecond = new JdbcOperate(dataSourceSecond);
	}
	
	public static JdbcOperate getJdbcOperate() {
		return jdbcOperate;
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	public static JdbcOperate getJdbcOperateSecond() {
		return jdbcOperateSecond;
	}
	
	public static DataSource getDataSourceSecond() {
		return dataSourceSecond;
	}

}
