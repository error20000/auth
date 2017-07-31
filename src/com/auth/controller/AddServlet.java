package com.auth.controller;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

@HandlesTypes(TestController.class)
public class AddServlet implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> clsses, ServletContext arg1) throws ServletException {
		System.out.println("onStartup....");
		if(clsses != null){
			for (Class<?> clss : clsses) {
				System.out.println(clss.getName());
			}
		}
		AnnotationAwareOrderComparator
	}
       

}
