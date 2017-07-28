package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupFunction;
import com.tools.service.AbstractBaseService;

public class AuthGroupFunctionService extends AbstractBaseService<AuthGroupFunction> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_FUNCTION_DAO;
	}
}
