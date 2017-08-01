package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupFunction;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AuthGroupFunctionService extends AbstractBaseServiceImpl<AuthGroupFunction> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_FUNCTION_DAO;
	}
}
