package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.Function;
import com.tools.service.AbstractBaseService;

public class FunctionService extends AbstractBaseService<Function> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.FUNCTION_DAO;
	}
}
