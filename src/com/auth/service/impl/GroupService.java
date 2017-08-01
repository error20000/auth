package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Group;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class GroupService extends AbstractBaseServiceImpl<Group> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUP_DAO;
	}
}
