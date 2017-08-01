package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.App;
import com.auth.service.AppService;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AppServiceImpl extends AbstractBaseServiceImpl<App> implements AppService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.App_DAO;
	}
	
}
