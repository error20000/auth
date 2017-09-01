package com.tools.auto;

import com.tools.utils.Tools;

/**
 * 自动生成管理器。
 * @author liujian
 *
 * @see com.tools.auto.AutoCreate
 */
public class AutoCreateManager {
	
	//数据库配置
	private AutoCreate autoCreate = null;
	
	public AutoCreateManager(){
		autoCreate = new AutoCreate();
	}
	
	public AutoCreateManager(Config config, ConfigDB dbConfig){
		autoCreate = new AutoCreate(config, dbConfig);
	}
	
	public AutoCreateManager(String packge, String dbPath){
		autoCreate = new AutoCreate(packge, dbPath);
	}
	
	public AutoCreateManager(String packge, String dbPath, String prefix, String separator){
		autoCreate = new AutoCreate(packge, dbPath, prefix, separator);
	}
	
	public void setPackge(String packge){
		autoCreate.setPackge(packge);
	} 
	
	public void setDbPath(String dbPath){
		autoCreate.setDbPath(dbPath);
	}
	
	public void setDbPathSecond(String dbPathSecond){
		autoCreate.setDbPathSecond(dbPathSecond);
	}
	
	public void setPrefix(String prefix){
		autoCreate.setPrefix(prefix);
	} 
	
	public void setSeparator(String separator){
		autoCreate.setSeparator(separator);
	} 
	
	public void setChartset(String chartset){
		autoCreate.setChartset(chartset);
	} 
	
	/**
	 * 整库创建
	 */
	public void start(){
		if(autoCreate == null){
			return;
		}
		autoCreate.start();
	} 
	
	/**
	 * 整表创建
	 * @param tableName	表名
	 */
	public void start(String tableName){
		if(autoCreate == null){
			return;
		}
		if(Tools.isNullOrEmpty(tableName)){
			return;
		}
		autoCreate.createTable(tableName);
	}

}
