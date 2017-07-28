package com.tools.controller;

import com.tools.web.annotation.Controller;
import com.tools.web.annotation.RequestMapping;

@Controller
@RequestMapping(name="TestCon", value={"/user","/app"})
public class TestCon extends AbstractBaseController{


	@RequestMapping(name="getUser", value={"/add","/addUser"})
	public void add(){}
	
	@RequestMapping(name="getUser", value={"/add1","/addUser1"})
	public void modify(){}
}
