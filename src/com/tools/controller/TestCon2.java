package com.tools.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tools.web.annotation.Controller;
import com.tools.web.annotation.RequestMapping;
import com.tools.web.annotation.RequestMethod;

@Controller
@RequestMapping(name="TestCon2", value={"/user2","/app2"})
public class TestCon2 {


	@RequestMapping(name="getUser", value={"/add","/addUser"})
	public void add(){
		System.out.println("add2");
	}
	
	@RequestMapping(name="getUser", value={"/add1","/addUser1"}, method={RequestMethod.POST, RequestMethod.GET})
	public void modify(HttpServletResponse resp, HttpServletRequest req){
		System.out.println("modify2");
	}
}
