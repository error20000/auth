package com.tools.jdbc.factory;

import java.io.File;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

public class JdbcOperateManager {
	
	private static DataSource dataSource = null;
	private static JdbcOperate jdbcOperate = null;
	
	public JdbcOperateManager(String dbPath){
		File file = new File(dbPath);
		if(file.exists()){
			dataSource = new C3P0PropertiesConfig(file).getDataSource();
		}else{
			dataSource = new C3P0PropertiesConfig(dbPath).getDataSource();
		}
		jdbcOperate = new JdbcOperate(dataSource);
	}
	
	public static JdbcOperateManager newManager(String dbPath){
		return new JdbcOperateManager(dbPath);
	}
	
	public JdbcOperate getJdbcOperate() {
		return jdbcOperate;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	

}
