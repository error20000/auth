package com.auth.dao.util;

import com.dirtree.dao.AppDAO;
import com.dirtree.dao.ChannelDAO;
import com.dirtree.dao.LogDAO;
import com.dirtree.dao.MenuDAO;
import com.dirtree.dao.ServerDAO;
import com.dirtree.dao.UserAppDAO;
import com.dirtree.dao.UserChannelDAO;
import com.dirtree.dao.UserDAO;
import com.dirtree.dao.UserMenuDAO;
import com.dirtree.dao.UserServerDAO;
import com.dirtree.dao.WhiteListDAO;
import com.dirtree.dao.impl.AppDAOImpl;
import com.dirtree.dao.impl.ChannelDAOImpl;
import com.dirtree.dao.impl.LogDAOImpl;
import com.dirtree.dao.impl.MenuDAOImpl;
import com.dirtree.dao.impl.ServerDAOImpl;
import com.dirtree.dao.impl.UserAppDAOImpl;
import com.dirtree.dao.impl.UserChannelDAOImpl;
import com.dirtree.dao.impl.UserDAOImpl;
import com.dirtree.dao.impl.UserMenuDAOImpl;
import com.dirtree.dao.impl.UserServerDAOImpl;
import com.dirtree.dao.impl.WhiteListDAOImpl;
import com.slib.pool.ObjectPool;

public class DB {
	public static final UserDAO USER_DAO = ObjectPool.get(UserDAOImpl.class);
	public static final AppDAO APP_DAO = ObjectPool.get(AppDAOImpl.class);
	public static final ChannelDAO CHANNEL_DAO = ObjectPool.get(ChannelDAOImpl.class);
	public static final ServerDAO SERVER_DAO = ObjectPool.get(ServerDAOImpl.class);
	public static final MenuDAO MENU_DAO = ObjectPool.get(MenuDAOImpl.class);
	public static final WhiteListDAO WHITELIST_DAO = ObjectPool.get(WhiteListDAOImpl.class);
	public static final UserAppDAO USER_APP_DAO = ObjectPool.get(UserAppDAOImpl.class);
	public static final UserChannelDAO USER_CHANNEL_DAO = ObjectPool.get(UserChannelDAOImpl.class);
	public static final UserServerDAO USER_SERVER_DAO = ObjectPool.get(UserServerDAOImpl.class);
	public static final UserMenuDAO USER_MENU_DAO = ObjectPool.get(UserMenuDAOImpl.class);
	public static final LogDAO LOG_DAO = ObjectPool.get(LogDAOImpl.class);
	
}
