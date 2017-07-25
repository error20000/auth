package com.auth.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth.dao.UserDao;
import com.auth.entity.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	
	public static void main(String[] args) {
		UserDaoImpl dao = new UserDaoImpl();
		//save
//		User user = new User();
//		user.setPid("test");
//		user.setNick("test");
//		dao.save(user);
		
		
//		List<User> list = new ArrayList<>();
//		User user1 = new User();
//		user1.setPid("test1");
//		user1.setNick("test1");
//		list.add(user1);
//		User user2 = new User();
//		user2.setPid("test2");
//		user2.setNick("test2");
//		list.add(user2);
//		User user3 = new User();
//		user3.setPid("test3");
//		user3.setNick("test3");
//		list.add(user3);
//		dao.batchSave(list);
		
		//modify
//		User user = new User();
//		user.setPid("test");
//		user.setNick("test1");
//		dao.modify(user);
		
//		List<User> list = new ArrayList<>();
//		User user1 = new User();
//		user1.setPid("test1");
//		user1.setNick("test11");
//		list.add(user1);
//		User user2 = new User();
//		user2.setPid("test2");
//		user2.setNick("test22");
//		list.add(user2);
//		User user3 = new User();
//		user3.setPid("test3");
//		user3.setNick("test33");
//		list.add(user3);
//		dao.batchModify(list);
		
//		Map<String, Object> uc = new HashMap<String, Object>();
//		uc.put("nick", "test");
//		Map<String, Object> uv = new HashMap<String, Object>();
//		uv.put("nick", "test4");
//		dao.modify(uc, uv);
		
		//delete
		Map<String, Object> del = new HashMap<String, Object>();
		del.put("pid", "test");
		del.put("nick", "test");
		dao.delete(del);
		
//		Map<String, Object> del1 = new HashMap<String, Object>();
//		del1.put("pidaa", "test111");
//		del1.put("nicka", "test4");
//		dao.delete(" nick=:nick or pid=:pid", del1);

//		List<String> list = new ArrayList<>();
//		list.add("test1");
//		list.add("test2");
//		list.add("test3");
//		list.add("test4");
//		dao.batchDelete("pid", list);
		
	}
	
}
