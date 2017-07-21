package com.auth.dao.util;

import com.auth.dao.UserDao;
import com.auth.dao.impl.UserDaoImpl;

public class DB {
	public static final UserDao USER_DAO = new UserDaoImpl();
	
}
