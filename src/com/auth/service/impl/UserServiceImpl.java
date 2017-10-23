package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.User;
import com.auth.service.UserService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  UserServiceImpl
 * @author liujian
 *
 */
public class UserServiceImpl extends AbstractBaseServiceImpl<User> implements UserService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.USER_DAO;
	}
	
}
