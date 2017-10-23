package com.auth.service.impl;

import com.auth.dao.util.DB;
import com.auth.entity.MenuBtn;
import com.auth.service.MenuBtnService;
import com.tools.service.impl.AbstractBaseServiceImpl;

/**
 * com.tools.auto 自动生成  MenuBtnServiceImpl
 * @author liujian
 *
 */
public class MenuBtnServiceImpl extends AbstractBaseServiceImpl<MenuBtn> implements MenuBtnService {

	@Override
	public void initBaseDao() {
		this.baseDao = DB.MENUBTN_DAO;
	}
	
}
