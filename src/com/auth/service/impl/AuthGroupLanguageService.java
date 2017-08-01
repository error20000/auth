package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupLanguage;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class AuthGroupLanguageService extends AbstractBaseServiceImpl<AuthGroupLanguage> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_LANGUAGE_DAO;
	}
}
