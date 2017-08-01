package com.tools.auto;

import com.auth.dao.AppDao;
import com.auth.dao.AuthGroupAppDao;
import com.auth.dao.AuthGroupFunctionDao;
import com.auth.dao.AuthGroupLanguageDao;
import com.auth.dao.AuthGroupMenuBtnDao;
import com.auth.dao.AuthGroupMenuDao;
import com.auth.dao.AuthUserGroupDao;
import com.auth.dao.FunctionDao;
import com.auth.dao.GroupDao;
import com.auth.dao.LanguageDao;
import com.auth.dao.MenuBtnDao;
import com.auth.dao.MenuDao;
import com.auth.dao.UserDao;
import com.auth.dao.impl.AppDaoImpl;
import com.auth.dao.impl.AuthGroupAppDaoImpl;
import com.auth.dao.impl.AuthGroupFunctionDaoImpl;
import com.auth.dao.impl.AuthGroupLanguageDaoImpl;
import com.auth.dao.impl.AuthGroupMenuBtnDaoImpl;
import com.auth.dao.impl.AuthGroupMenuDaoImpl;
import com.auth.dao.impl.AuthUserGroupDaoImpl;
import com.auth.dao.impl.FunctionDaoImpl;
import com.auth.dao.impl.GroupDaoImpl;
import com.auth.dao.impl.LanguageDaoImpl;
import com.auth.dao.impl.MenuBtnDaoImpl;
import com.auth.dao.impl.MenuDaoImpl;
import com.auth.dao.impl.UserDaoImpl;

public class DB {
	public static final UserDao USER_DAO = new UserDaoImpl();
	public static final AppDao App_DAO = new AppDaoImpl();
	public static final MenuDao MENU_DAO = new MenuDaoImpl();
	public static final MenuBtnDao MENU_BTN_DAO = new MenuBtnDaoImpl();
	public static final LanguageDao LANGUAGE_DAO = new LanguageDaoImpl();
	public static final FunctionDao FUNCTION_DAO = new FunctionDaoImpl();
	public static final GroupDao GROUP_DAO = new GroupDaoImpl();
	public static final AuthUserGroupDao AUTH_USER_GROUP_DAO = new AuthUserGroupDaoImpl();
	public static final AuthGroupAppDao AUTH_GROUP_APP_DAO = new AuthGroupAppDaoImpl();
	public static final AuthGroupFunctionDao AUTH_GROUP_FUNCTION_DAO = new AuthGroupFunctionDaoImpl();
	public static final AuthGroupLanguageDao AUTH_GROUP_LANGUAGE_DAO = new AuthGroupLanguageDaoImpl();
	public static final AuthGroupMenuDao AUTH_GROUP_MENU_DAO = new AuthGroupMenuDaoImpl();
	public static final AuthGroupMenuBtnDao AUTH_GROUP_MENU_BTN_DAO = new AuthGroupMenuBtnDaoImpl();
	
}
