package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Language;
import com.tools.service.impl.AbstractBaseServiceImpl;

public class LanguageService extends AbstractBaseServiceImpl<Language> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.LANGUAGE_DAO;
	}
}
