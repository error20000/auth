package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.GroupFunction;
import com.auth.service.GroupFunctionService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  GroupFunctionServiceImpl
 * @author liujian
 *
 */
public class GroupFunctionServiceImpl extends AbstractBaseServiceImpl<GroupFunction> implements GroupFunctionService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUPFUNCTION_DAO;
	}
	
}
