package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Menu;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class MenuService extends AbstractBaseServiceImpl<Menu> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.MENU_DAO;
	}
}
