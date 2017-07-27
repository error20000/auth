package com.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.auth.dao.BaseDao;
import com.auth.dao.UserDao;
import com.auth.dao.util.JdbcOperateManager;
import com.auth.entity.User;
import com.tools.db.dao.impl.MysqlThreadBaseDaoImpl;
import com.tools.utils.ResultKey;

public class TestThreadDaoImpl extends MysqlThreadBaseDaoImpl<User> {
	
	
	@Override
	public void initJdbcOperate() {
//		this.dataSource = JdbcOperateManager.getDataSource();
		this.jdbcOperate = JdbcOperateManager.getJdbcOperate();
		this.log = true;
//		this.count = 20;
	}
	
	public static void main(String[] args) {
		TestThreadDaoImpl dao = new TestThreadDaoImpl();
		//save
		dao.exeJob(new Runnable() {
			@Override
			public void run() {
				User user = new User();
				user.setPid("test12");
				user.setNick("test");
				dao.save(user);
				System.out.println(user.getPid());
			}
		});
		System.out.println("test");
		
//		for (int i = 0; i < 100; i++) {
//			dao.save2(i);
//		}
		
		
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
//		user.setNick("test112");
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
//		Map<String, Object> del = new HashMap<String, Object>();
//		del.put("pid", "test");
//		del.put("nick", "test");
//		dao.delete(del);
		
//		Map<String, Object> del1 = new HashMap<String, Object>();
//		del1.put("pidaa", "test111");
//		del1.put("nick", "test4");
//		dao.delete(" nick=:nick or pid=:pid", del1);

//		List<String> list = new ArrayList<>();
//		list.add("test1");
//		list.add("test2");
//		list.add("test3");
//		list.add("test4");
//		dao.batchDelete("pid", list);
		
		//find
//		dao.findList(" 1=1 group by pid", new HashMap<>());

//		Map<String, Object> cdt = new HashMap<String, Object>();
//		cdt.put("pidaa", "test");
//		User res = dao.findObject(cdt);
//		System.out.println(JSON.toJSONString(res));
		
		//size
//		Map<String, Object> cdt = new HashMap<String, Object>();
//		cdt.put("pid", "test");
//		dao.size(" pid=:pid limit 0, 100", cdt);
		

		//original
//		Map<String, Object> cdt = new HashMap<String, Object>();
//		cdt.put("pid", "test11");
//		try {
//			Map<String, Object> res = dao.jdbcOperate.queryMap(" select pid from s_user where pid=:pid", cdt);
//			System.out.println(JSON.toJSONString(res));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//map
//		Map<String, Object> cdt = new HashMap<String, Object>();
//		cdt.put("pid", "test");
//		List<String> list = new ArrayList<>();
//		list.add("count(1)");
//		list.add("pid");
//		list.add("nick");
//		dao.findMapList(list, cdt);
		
		
	}
	
}