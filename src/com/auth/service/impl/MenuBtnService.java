package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.MenuBtn;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class MenuBtnService extends AbstractBaseServiceImpl<MenuBtn> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.MENU_BTN_DAO;
	}
}
