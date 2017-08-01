package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupApp;
import com.auth.service.AuthGroupAppService;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AuthGroupAppServiceImpl extends AbstractBaseServiceImpl<AuthGroupApp> implements AuthGroupAppService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_APP_DAO;
	}
}
