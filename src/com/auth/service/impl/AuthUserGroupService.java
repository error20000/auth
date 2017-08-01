package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.AuthUserGroup;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AuthUserGroupService extends AbstractBaseServiceImpl<AuthUserGroup> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_USER_GROUP_DAO;
	}
}
