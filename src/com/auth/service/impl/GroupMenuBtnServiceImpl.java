package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.GroupMenuBtn;
import com.auth.service.GroupMenuBtnService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  GroupMenuBtnServiceImpl
 * @author liujian
 *
 */
public class GroupMenuBtnServiceImpl extends AbstractBaseServiceImpl<GroupMenuBtn> implements GroupMenuBtnService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.GROUPMENUBTN_DAO;
	}
	
}
