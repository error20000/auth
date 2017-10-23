package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.Language;
import com.auth.service.LanguageService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  LanguageServiceImpl
 * @author liujian
 *
 */
public class LanguageServiceImpl extends AbstractBaseServiceImpl<Language> implements LanguageService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.LANGUAGE_DAO;
	}
	
}
