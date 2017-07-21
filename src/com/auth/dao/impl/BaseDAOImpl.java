package com.auth.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dirtree.dao.BaseDAO;
import com.dirtree.dao.util.DbUtils;
import com.dirtree.dao.util.ObjectVisitor;
import com.slib.annotation.Table;
import com.slib.jdbc.Token;
import com.slib.reflect.BeanUtils;

public abstract class BaseDAOImpl<E> implements BaseDAO<E> {
	
	protected ObjectVisitor visitor;
	
	public BaseDAOImpl() {
		initVisitor();
	}
	
	protected void initVisitor() {
		visitor = new ObjectVisitor(DbUtils.getJdbcTemplate(),
				BeanUtils.getEntityClass(this, 0));
	}
	
	@Override
	public int add(E entity) {
		// TODO Auto-generated method stub
		try {
			return visitor.save(entity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean batchAdd(List<E> entities) {
		// TODO Auto-generated method stub
		try {
			return visitor.batchSave(entities);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean batchUpdate(List<E> entities) {
		// TODO Auto-generated method stub
		try {
			return visitor.batchUpdate(entities);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean batchDelete(List<Integer> list) {
		try {
			return visitor.batchDelete(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public int change(E entity) {
		// TODO Auto-generated method stub
		try {
			return visitor.update(entity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<E> list() {
		// TODO Auto-generated method stub
		try {
			return visitor.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<E>();
	}
	
	@Override
	public List<E> list(Token token) {
		// TODO Auto-generated method stub
		try {
			return visitor.query(token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<E>();
	}
	
	@Override
	public List<E> list(String sql) {
		// TODO Auto-generated method stub
		try {
			return visitor.query(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<E>();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		try {
			return visitor.size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		try {
			return visitor.delete(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public E get(int id) {
		// TODO Auto-generated method stub
		try {
			return visitor.queryForObject(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public E get(Token token) {
		// TODO Auto-generated method stub
		try {
			return visitor.queryForObject(token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<E> list(String whereSql, int startRow, int count) {
		// TODO Auto-generated method stub
		try {
			return visitor.queryByWSql(whereSql, startRow, count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<E>();
	}

	@Override
	public int size(String whereSql) {
		// TODO Auto-generated method stub
		try {
			return visitor.sizeByWSql(whereSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<E> find(String wsql) {
		Type genType = getClass().getGenericSuperclass();  
	    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	    @SuppressWarnings("unchecked")
		Class<E> clss = (Class<E>) params[0];
	    String sql = "";
	    if(clss.isAnnotationPresent(Table.class)){
	    	Table table = clss.getAnnotation(Table.class);
	    	sql = "select * from `" + table.value() + "` where " + wsql;
	    }
		try {
			return visitor.getJdbcTemplate().query(sql, clss);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<E>();
	}
	
	@Override
	public List<?> find(String sql, Class<?> clss) {
		try {
			return visitor.getJdbcTemplate().query(sql, clss);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Object>();
	}
	
	@Override
	public E findOne(String wsql) {
		Type genType = getClass().getGenericSuperclass();  
	    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	    @SuppressWarnings("unchecked")
		Class<E> clss = (Class<E>) params[0];
	    String sql = "";
	    if(clss.isAnnotationPresent(Table.class)){
	    	Table table = clss.getAnnotation(Table.class);
	    	sql = "select * from `" + table.value() + "` where " + wsql;
	    }
		try {
			return (E) visitor.getJdbcTemplate().queryForObject(sql, clss);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int delete(long pid) {
		Type genType = getClass().getGenericSuperclass();  
	    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	    @SuppressWarnings("unchecked")
		Class<E> clss = (Class<E>) params[0];
	    String sql = "";
	    if(clss.isAnnotationPresent(Table.class)){
	    	Table table = clss.getAnnotation(Table.class);
	    	sql = "delete from `" + table.value() + "` where pid = " + pid;
	    }
		try {
			return visitor.getJdbcTemplate().update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int change(String wsql) {
		Type genType = getClass().getGenericSuperclass();  
	    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	    @SuppressWarnings("unchecked")
		Class<E> clss = (Class<E>) params[0];
	    String sql = "";
	    if(clss.isAnnotationPresent(Table.class)){
	    	Table table = clss.getAnnotation(Table.class);
	    	sql = "update `" + table.value() + "` " + wsql;
	    }
		try {
			return visitor.getJdbcTemplate().update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int delete(String wsql) {
		Type genType = getClass().getGenericSuperclass();  
	    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	    @SuppressWarnings("unchecked")
		Class<E> clss = (Class<E>) params[0];
	    String sql = "";
	    if(clss.isAnnotationPresent(Table.class)){
	    	Table table = clss.getAnnotation(Table.class);
	    	sql = "delete from `" + table.value() + "` where " + wsql;
	    }
		try {
			return visitor.getJdbcTemplate().update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
}
