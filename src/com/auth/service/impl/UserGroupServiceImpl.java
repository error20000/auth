package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.UserGroup;
import com.auth.service.UserGroupService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  UserGroupServiceImpl
 * @author liujian
 *
 */
public class UserGroupServiceImpl extends AbstractBaseServiceImpl<UserGroup> implements UserGroupService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.USERGROUP_DAO;
	}
	
}
