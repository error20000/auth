package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupMenuBtn;
import com.tools.service.AbstractBaseService;

public class AuthGroupMenuBtnService extends AbstractBaseService<AuthGroupMenuBtn> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_MENU_BTN_DAO;
	}
}
