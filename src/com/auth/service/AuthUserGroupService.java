package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.AuthUserGroup;
import com.tools.service.AbstractBaseService;

public class AuthUserGroupService extends AbstractBaseService<AuthUserGroup> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_USER_GROUP_DAO;
	}
}
