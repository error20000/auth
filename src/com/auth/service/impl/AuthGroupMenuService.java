package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupMenu;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AuthGroupMenuService extends AbstractBaseServiceImpl<AuthGroupMenu> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_MENU_DAO;
	}
}
