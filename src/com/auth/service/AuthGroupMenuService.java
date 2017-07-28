package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupMenu;
import com.tools.service.AbstractBaseService;

public class AuthGroupMenuService extends AbstractBaseService<AuthGroupMenu> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_MENU_DAO;
	}
}
