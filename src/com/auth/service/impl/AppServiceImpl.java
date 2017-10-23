package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.App;
import com.auth.service.AppService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  AppServiceImpl
 * @author liujian
 *
 */
public class AppServiceImpl extends AbstractBaseServiceImpl<App> implements AppService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.APP_DAO;
	}
	
}
