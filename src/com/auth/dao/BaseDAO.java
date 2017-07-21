package com.auth.dao;

import java.util.List;

import com.slib.jdbc.Token;

public interface BaseDAO<E> {
	
	int add(E entity);
	boolean batchAdd(List<E> entities);
	boolean batchUpdate(List<E> entities);
	boolean batchDelete(List<Integer> list);
	List<E> list();
	List<E> list(Token token);
	List<E> list(String sql);
	List<E> list(String whereSql, int startRow, int count);
	int change(E entity);
	int size();
	int size(String whereSql);
	int delete(int id);
	E get(int id);
	E get(Token token);
	List<E> find(String wsql);
	List<?> find(String sql, Class<?> clss);
	E findOne(String wsql);
	int delete(long pid);
	int change(String wsql);
	int delete(String wsql);
}
