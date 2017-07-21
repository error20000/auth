package com.tools.jdbc.c3p0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0PropertiesConfig {

	private static ComboPooledDataSource dataSource = null;

	/**
	 * 使用默认配置。
	 * 必须要自己设置  jdbcUrl、user、password、driverClass 这四个属性
	 * 
	 * @author liujian
	 * 
     */
    public C3P0PropertiesConfig(){
    	init(this.getClass().getResourceAsStream("c3p0.properties"));
    }

    public C3P0PropertiesConfig(String proPath){
    	init(this.getClass().getResourceAsStream("/"+proPath));
    }
    
    public C3P0PropertiesConfig(File file){
    	try {
			init(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public C3P0PropertiesConfig(InputStream in){
		init(in);
    }
    
    /*public void init(InputStream in){
    	Properties properties = new Properties();
    	try {
			properties.load(in);
			if(dataSource == null){
				//dataSource资源只能初始化一次
		        dataSource = new ComboPooledDataSource();
		        
		        dataSource.setDriverClass(properties.getProperty("driverClass"));
		        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
		        dataSource.setUser(properties.getProperty("user"));
		        dataSource.setPassword(properties.getProperty("password"));
		        
		        dataSource.setInitialPoolSize(Tools.parseInt(properties.getProperty("initialPoolSize")));
		        dataSource.setMinPoolSize(Tools.parseInt(properties.getProperty("minPoolSize")));
		        dataSource.setMaxPoolSize(Tools.parseInt(properties.getProperty("maxPoolSize")));
		        dataSource.setMaxStatements(Tools.parseInt(properties.getProperty("maxStatements")));
		        dataSource.setAcquireIncrement(Tools.parseInt(properties.getProperty("acquireIncrement")));
		        dataSource.setAcquireRetryAttempts(Tools.parseInt(properties.getProperty("acquireRetryAttempts")));
		        dataSource.setAcquireRetryDelay(Tools.parseInt(properties.getProperty("acquireRetryDelay")));
		        dataSource.setIdleConnectionTestPeriod(Tools.parseInt(properties.getProperty("idleConnectionTestPeriod")));
		        dataSource.setMaxIdleTime(Tools.parseInt(properties.getProperty("maxIdleTime")));
		        dataSource.setCheckoutTimeout(Tools.parseInt(properties.getProperty("checkoutTimeout")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }*/
    
    public ComboPooledDataSource getDataSource(){
    	return dataSource;
    }
    
    public void init(InputStream in){
    	Properties properties = new Properties();
    	try {
			properties.load(in);
			if(dataSource == null){
				//dataSource资源只能初始化一次
		        dataSource = new ComboPooledDataSource();
		        Enumeration<?> keys = properties.keys();
		        while(keys.hasMoreElements()){
		        	String key = (String) keys.nextElement();
		        	if(key.startsWith("c3p0.")){
		        		key = key.replace("c3p0.", "");
		        	}else if(key.startsWith("test.")){
		        		continue;
		        	}
		        	Method[] methods = dataSource.getClass().getDeclaredMethods();
		        	for (Method method : methods) {
		        		String tmp = method.getName();
						if(tmp.startsWith("set") && tmp.substring("set".length()).equalsIgnoreCase(key)){
							method.invoke(dataSource, properties.getProperty(key));
							break;
						}
					}
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
