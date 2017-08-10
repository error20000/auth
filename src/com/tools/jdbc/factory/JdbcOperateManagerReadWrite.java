package com.tools.jdbc.factory;

import java.io.File;

import javax.sql.DataSource;

import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

/**
 * 待完成
 * @author liujian
 *
 */
public class JdbcOperateManagerReadWrite {
	
	private static JdbcOperate read = null;
	private static JdbcOperate write = null;
	private static DataSource readSource = null;
	private static DataSource writeSource = null;
	
	public JdbcOperateManagerReadWrite(String readPath, String writePath){
		//读
		File readfile = new File(readPath);
		if(readfile.exists()){
			readSource = new C3P0PropertiesConfig(readfile).getDataSource();
		}else{
			readSource = new C3P0PropertiesConfig(readPath).getDataSource();
		}
		read = new JdbcOperate(readSource);
		
		//写
		File writefile = new File(writePath);
		if(writefile.exists()){
			writeSource = new C3P0PropertiesConfig(writefile).getDataSource();
		}else{
			writeSource = new C3P0PropertiesConfig(writePath).getDataSource();
		}
		read = new JdbcOperate(writeSource);
	}
	
	public static JdbcOperateManagerReadWrite newManager(String readPath, String writePath){
		return new JdbcOperateManagerReadWrite(readPath, writePath);
	}
	
	public JdbcOperate getReadJdbcOperate() {
		return read;
	}
	
	public JdbcOperate getWriteJdbcOperate() {
		return write;
	}
	
	public DataSource getReadDataSource() {
		return readSource;
	}
	
	public DataSource getWriteDataSource() {
		return writeSource;
	}

}
