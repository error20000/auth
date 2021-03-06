package com.auth.dao.util;

import java.io.File;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

/**
 * com.tools.auto 自动生成
 * @author liujian
 *
 * @param <T>
 */
public class JdbcOperateManager {
	//主库配置
	private static DataSource dataSource = null;
	private static JdbcOperate jdbcOperate = null;
	//从库配置
	private static DataSource dataSourceSecond = null;
	private static JdbcOperate jdbcOperateSecond = null;
	
	static{
		String dbPath = "autodb.properties";
		String dbPathSecond = "";
		if("".equals(dbPathSecond)){
			//主从库一样
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
		
		}else{
			//主从库单独配置
			C3P0PropertiesConfig config = null;
			File file = new File(dbPath);
			File fileSecond = new File(dbPathSecond);
			if(file.exists() && fileSecond.exists()){
				config = new C3P0PropertiesConfig(file);
				dataSource = config.getDataSource();
				config = new C3P0PropertiesConfig(fileSecond);
				dataSourceSecond = config.getDataSource();
			}else{
				config = new C3P0PropertiesConfig(dbPath);
				dataSource = config.getDataSource();
				config = new C3P0PropertiesConfig(dbPathSecond);
				dataSourceSecond = config.getDataSource();
			}
			jdbcOperate = new JdbcOperate(dataSource);
			jdbcOperateSecond = new JdbcOperate(dataSourceSecond);
		}
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
