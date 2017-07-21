package com.auth.dao.util;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

public class JdbcOperateFactory {
	
	private static JdbcOperate jdbcOperate;
	
	static{
		String dbpath = "db.properties";
		jdbcOperate = new JdbcOperate(new C3P0PropertiesConfig(dbpath).getDataSource());
	}
	
	public static JdbcOperate getJdbcOperate() {
		return jdbcOperate;
	}
	

}
