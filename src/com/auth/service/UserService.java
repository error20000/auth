package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.User;
import com.tools.service.AbstractBaseService;

public class UserService extends AbstractBaseService<User> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.USER_DAO;
	}
}
