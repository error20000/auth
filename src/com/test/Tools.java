package com.test;

import com.tools.auto.AutoCreateManager;
import com.tools.auto.Config;
import com.tools.auto.ConfigDB;

public class Tools {

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/auth?characterEncoding=utf8";
		String user = "root";
		String password = "123456";
		String driverClass = "com.mysql.jdbc.Driver";
		String prefix = "s_";
		String separator = "_";
		AutoCreateManager test =  new AutoCreateManager(new Config("com.auth"), new ConfigDB(jdbcUrl, user, password, driverClass, prefix, separator));
		test.start();
//		System.out.println(System.getProperty("user.dir"));
		
	}
	
}
