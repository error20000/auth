package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Group;
import com.auth.service.GroupService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  GroupServiceImpl
 * @author liujian
 *
 */
public class GroupServiceImpl extends AbstractBaseServiceImpl<Group> implements GroupService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUP_DAO;
	}
	
}
