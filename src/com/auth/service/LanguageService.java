package com.auth.service;

import com.auth.dao.util.DB;
import com.auth.entity.Language;
import com.tools.service.AbstractBaseService;

public class LanguageService extends AbstractBaseService<Language> {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.LANGUAGE_DAO;
	}
}
