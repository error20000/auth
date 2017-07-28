package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.AuthGroupLanguage;
import com.tools.service.AbstractBaseService;

public class AuthGroupLanguageService extends AbstractBaseService<AuthGroupLanguage> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.AUTH_GROUP_LANGUAGE_DAO;
	}
}
