package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupMenuBtn;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AuthGroupMenuBtnService extends AbstractBaseServiceImpl<AuthGroupMenuBtn> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_MENU_BTN_DAO;
	}
}
