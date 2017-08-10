package com.tools.jdbc.proxy;

import java.io.File;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;
import com.tools.jdbc.factory.JdbcOperateManagerReadWrite;

public class JdbcOperateManagerProxy {
	
	public static final String READ = "read";
	public static final String WRITE = "write";
	private static JdbcOperateManagerReadWrite manager = null;
	public static String readPath = "";
	public static String writePath = "";
	
	
	public static void init(){
		if(manager == null){
			manager = JdbcOperateManagerReadWrite.newManager(readPath, writePath);
		}
	}
	
	public static JdbcOperate getJdbcOperate(String type) {
		init();
		if(READ.equals(type)){
			return manager.getReadJdbcOperate();
		}else if(WRITE.equals(type)){
			return manager.getWriteJdbcOperate();
		}
		return null;
	}
	
	public static DataSource getDataSource(String type) {
		init();
		if(READ.equals(type)){
			return manager.getReadDataSource();
		}else if(WRITE.equals(type)){
			return manager.getWriteDataSource();
		}
		return null;
	}
	

}
