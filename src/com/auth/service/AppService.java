package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.App;
import com.tools.service.AbstractBaseService;

public class AppService extends AbstractBaseService<App> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.App_DAO;
	}
}
