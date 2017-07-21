package com.auth.dao.util;

import com.slib.jdbc.JdbcTemplate;
import com.slib.jdbc.mysql.MySqlObjectExecutor;

public class ObjectVisitor extends MySqlObjectExecutor {
	
	public ObjectVisitor(JdbcTemplate jdbcTemplate, Class<?> beanClass) {
		super.setJdbcTemplate(jdbcTemplate);
		super.setBeanClass(beanClass);
	}

}
