package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.Menu;
import com.tools.service.AbstractBaseService;

public class MenuService extends AbstractBaseService<Menu> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.MENU_DAO;
	}
}
