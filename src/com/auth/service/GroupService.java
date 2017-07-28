package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.Group;
import com.tools.service.AbstractBaseService;

public class GroupService extends AbstractBaseService<Group> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUP_DAO;
	}
}
