package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.GroupLanguage;
import com.auth.service.GroupLanguageService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  GroupLanguageServiceImpl
 * @author liujian
 *
 */
public class GroupLanguageServiceImpl extends AbstractBaseServiceImpl<GroupLanguage> implements GroupLanguageService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUPLANGUAGE_DAO;
	}
	
}
