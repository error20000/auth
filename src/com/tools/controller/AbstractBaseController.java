package com.tools.controller;

import com.tools.web.annotation.RequestMapping;

public abstract class AbstractBaseController {
	

	@RequestMapping(name="del", value={"/del","/delUser"})
	public void del(){
		System.out.println("del");
	}
	
}
