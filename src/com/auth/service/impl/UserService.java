package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.User;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class UserService extends AbstractBaseServiceImpl<User> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.USER_DAO;
	}
}
