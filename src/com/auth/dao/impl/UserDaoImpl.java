package com.auth.dao.impl;

import com.auth.dao.UserDao;
import com.auth.entity.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	
	public static void main(String[] args) {
		UserDaoImpl dao = new UserDaoImpl();
		User user = new User();
		user.setPid("test");
		user.setNick("test");
		dao.save(user);
	}
	
}
