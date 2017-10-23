package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.GroupApp;
import com.auth.service.GroupAppService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  GroupAppServiceImpl
 * @author liujian
 *
 */
public class GroupAppServiceImpl extends AbstractBaseServiceImpl<GroupApp> implements GroupAppService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUPAPP_DAO;
	}
	
}
