package com.tools.auto.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.tools.jdbc.JdbcOperate;
import com.tools.jdbc.c3p0.C3P0PropertiesConfig;

public class TableManager {
	
	private static DataSource dataSource = null;
	private static JdbcOperate jdbcOperate = null;
	
	static{
		String dbpath = "db.properties";
		dataSource = new C3P0PropertiesConfig(dbpath).getDataSource();
		jdbcOperate = new JdbcOperate(dataSource);
	}
	
	public List<String> getTableNames() {
		String sql = "show tables";
		PreparedStatement statement = getStatement(sql, null);
		List<String> tables = new ArrayList<String>();
		ResultSet rs = null;
		Remote.outMessage("分析数据库，分析中。。。。。。");
		try {
			rs = statement.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
			Remote.outMessage("分析成功！共获得：" + tables.size() + " 张表。");
		} catch (SQLException e) {
			Remote.outMessage("分析失败：" + e.getMessage());
		}finally{
			try {
				if(rs!=null){
					rs.close();
					rs = null;
				}
				if (statement != null) {
					statement.close();
					statement = null;
				}
				if(connection!=null){
					connection.close();
					connection = null;
				}
				Remote.outMessage("关闭连接！");
			} catch (SQLException e) {
				Remote.outMessage("关闭连接失败：" + e.getMessage());
			}
		}
		return tables;
	}
	
	public Structure getTableInfo(String table) {
		String sql = "show full columns from " + table;
		Structure structure = null;
		try {
			Map<String, Object> map = jdbcOperate.queryMap(sql);
			System.out.println(JSON.toJSONString(map));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return structure;
	}
	
	public List<Index> getIndexInfo(String table) {
		List<Index> indexs = null;
		indexs = new ArrayList<Index>();
		Index index = null;
		String sql = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		sql = "SHOW INDEX FROM  " + table;
		statement = getStatement(sql, null);
		Remote.outMessage(table + " 表索引，获取中。。。。。。");
		try {
			rs = statement.executeQuery();
			while (rs.next()) {
				index = new Index();
				index.setColumnName(rs.getString("Column_name"));
				index.setIndexName(rs.getString("Key_name"));
				index.setIndexType(rs.getString("Index_type"));
				index.setIsUnique(rs.getInt("Non_unique")==0?1:0);
				indexs.add(index);
			}
			Remote.outMessage("成功获取：" + table + " 表索引！");
		} catch (SQLException e) {
			Remote.outMessage(table + " 表索引，获取失败：" + e.getMessage());
		} finally {
			try {
				if(rs!=null){
					rs.close();
					rs = null;
				}
				if (statement != null) {
					statement.close();
					statement = null;
				}
				if(connection!=null){
					connection.close();
					connection = null;
				}
				Remote.outMessage("关闭连接！");
			} catch (SQLException e) {
				Remote.outMessage("关闭连接失败：" + e.getMessage());
			}
		}
		return indexs;
	}
	
	public static void main(String[] args) {
		TableManager manager = new TableManager();
		manager.getTableInfo("s_app");
	}
	
}
