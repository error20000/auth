package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.MenuBtn;
import com.tools.service.AbstractBaseService;

public class MenuBtnService extends AbstractBaseService<MenuBtn> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.MENU_BTN_DAO;
	}
}
