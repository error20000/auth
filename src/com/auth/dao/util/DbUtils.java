package com.auth.dao.util;

import com.slib.jdbc.JdbcTemplate;

public class DbUtils {
	
	private static JdbcTemplateFactory factory = JdbcTemplateFactory.newFactory("db.properties");
	
	public static JdbcTemplate getJdbcTemplate() {
		return factory.getJdbcTemplate();
	}
	
	public static void closeDataSource() {
		factory.closeDataSource();
	}

}
