package com.auth.dao.util;

//import
import com.auth.dao.impl.UserGroupDaoImpl;
import com.auth.dao.UserGroupDao;
import com.auth.dao.impl.UserDaoImpl;
import com.auth.dao.UserDao;
import com.auth.dao.impl.MenuBtnDaoImpl;
import com.auth.dao.MenuBtnDao;
import com.auth.dao.impl.MenuDaoImpl;
import com.auth.dao.MenuDao;
import com.auth.dao.impl.LanguageDaoImpl;
import com.auth.dao.LanguageDao;
import com.auth.dao.impl.GroupMenuBtnDaoImpl;
import com.auth.dao.GroupMenuBtnDao;
import com.auth.dao.impl.GroupMenuDaoImpl;
import com.auth.dao.GroupMenuDao;
import com.auth.dao.impl.GroupLanguageDaoImpl;
import com.auth.dao.GroupLanguageDao;
import com.auth.dao.impl.GroupFunctionDaoImpl;
import com.auth.dao.GroupFunctionDao;
import com.auth.dao.impl.GroupAppDaoImpl;
import com.auth.dao.GroupAppDao;
import com.auth.dao.impl.GroupDaoImpl;
import com.auth.dao.GroupDao;
import com.auth.dao.impl.FunctionDaoImpl;
import com.auth.dao.FunctionDao;
import com.auth.dao.impl.AppDaoImpl;
import com.auth.dao.AppDao;

/**
 * com.tools.auto 自动生成  DB
 * @author liujian
 *
 */
public class DB {
	
	//dao
	public static final UserGroupDao USERGROUP_DAO = new UserGroupDaoImpl();
	public static final UserDao USER_DAO = new UserDaoImpl();
	public static final MenuBtnDao MENUBTN_DAO = new MenuBtnDaoImpl();
	public static final MenuDao MENU_DAO = new MenuDaoImpl();
	public static final LanguageDao LANGUAGE_DAO = new LanguageDaoImpl();
	public static final GroupMenuBtnDao GROUPMENUBTN_DAO = new GroupMenuBtnDaoImpl();
	public static final GroupMenuDao GROUPMENU_DAO = new GroupMenuDaoImpl();
	public static final GroupLanguageDao GROUPLANGUAGE_DAO = new GroupLanguageDaoImpl();
	public static final GroupFunctionDao GROUPFUNCTION_DAO = new GroupFunctionDaoImpl();
	public static final GroupAppDao GROUPAPP_DAO = new GroupAppDaoImpl();
	public static final GroupDao GROUP_DAO = new GroupDaoImpl();
	public static final FunctionDao FUNCTION_DAO = new FunctionDaoImpl();
	public static final AppDao APP_DAO = new AppDaoImpl();
	
}
