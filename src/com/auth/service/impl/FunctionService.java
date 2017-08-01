package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Function;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class FunctionService extends AbstractBaseServiceImpl<Function> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.FUNCTION_DAO;
	}
}
