package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Function;
import com.auth.service.FunctionService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  FunctionServiceImpl
 * @author liujian
 *
 */
public class FunctionServiceImpl extends AbstractBaseServiceImpl<Function> implements FunctionService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.FUNCTION_DAO;
	}
	
}
