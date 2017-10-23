package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.GroupMenu;
import com.auth.service.GroupMenuService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  GroupMenuServiceImpl
 * @author liujian
 *
 */
public class GroupMenuServiceImpl extends AbstractBaseServiceImpl<GroupMenu> implements GroupMenuService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUPMENU_DAO;
	}
	
}
