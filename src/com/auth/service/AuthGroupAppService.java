package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupApp;
import com.tools.service.AbstractBaseService;

public class AuthGroupAppService extends AbstractBaseService<AuthGroupApp> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_APP_DAO;
	}
}
