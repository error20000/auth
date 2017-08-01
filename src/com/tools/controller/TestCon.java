package com.tools.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tools.web.annotation.RequestMapping;
import com.tools.web.annotation.RequestMethod;

@RequestMapping(name="TestCon", value={"/user","/app"}, method={RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
public class TestCon extends AbstractBaseController{


	@RequestMapping(name="getUser", value={"/add","/addUser"}, method={RequestMethod.HEAD})
	public void add(){
		System.out.println("add");
	}
	
	@RequestMapping(name="getUser", value={"/add1","/addUser1"}, method={RequestMethod.POST, RequestMethod.GET})
	public void modify(HttpServletResponse resp, HttpServletRequest req){
		System.out.println("modify");
	}
}
