package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Menu;
import com.auth.service.MenuService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  MenuServiceImpl
 * @author liujian
 *
 */
public class MenuServiceImpl extends AbstractBaseServiceImpl<Menu> implements MenuService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.MENU_DAO;
	}
	
}
