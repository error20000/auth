package com.test;


import com.auth.dao.UserDao;
import com.auth.dao.impl.UserDaoImpl;
import com.auth.entity.User;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class TestService extends AbstractBaseServiceImpl<User> {

	private static UserDao dao = new UserDaoImpl();
	@Override
	public void initBaseDao() {
		this.baseDao = dao;
	}

	public static void main(String[] args) {
		TestService test = new TestService();
		User user = new User();
		user.setPid("test12");
		user.setNick("test");
		test.save(user);
	}

}
