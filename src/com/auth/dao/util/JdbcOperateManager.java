package com.auth.dao.util;

import java.io.File;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

public class JdbcOperateManager {
	
	private static DataSource dataSource = null;
	private static JdbcOperate jdbcOperate = null;
	
	static{
		String dbPath = "db.properties";
		File file = new File(dbPath);
		if(file.exists()){
			dataSource = new C3P0PropertiesConfig(file).getDataSource();
		}else{
			dataSource = new C3P0PropertiesConfig(dbPath).getDataSource();
		}
		jdbcOperate = new JdbcOperate(dataSource);
	}
	
	public static JdbcOperate getJdbcOperate() {
		return jdbcOperate;
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	

}
