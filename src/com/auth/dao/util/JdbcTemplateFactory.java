package com.auth.dao.util;

import java.io.File;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.slib.jdbc.ConnectionFactory;
import com.slib.jdbc.JdbcTemplate;
import com.slib.util.ConfigProperties;

public class JdbcTemplateFactory {
	
	private JdbcTemplate jdbcTemplate;
	
	private JdbcTemplateFactory(String classpath) {
		init(classpath);
	}
	
	public static JdbcTemplateFactory newFactory(String dbProperties) {
		return new JdbcTemplateFactory(dbProperties);
	}
	
	protected void init(String classpath) {
		ConnectionFactory factory = new ConnectionFactory(new ComboPooledDataSource());
		
//		File file = new File("/usr/local/tomcat_config/dirtree/" + classpath);
//		ConfigProperties properties = new ConfigProperties(file);
		
		ConfigProperties properties = new ConfigProperties(classpath);
		
		factory.bindProperties(properties);
		properties.clear();
		jdbcTemplate = new JdbcTemplate(factory.getDataSource());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void closeDataSource() {
		if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
			ComboPooledDataSource ds = (ComboPooledDataSource)jdbcTemplate.getDataSource();
			ds.close();
		}
	}

}
