package com.tools.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tools.controller.TestCon;
import com.tools.web.RequestMappingData;
import com.tools.web.ServletContainerInitializerImpl;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RequestMappingData> mapping = ServletContainerInitializerImpl.mapping;
		String reqPath = req.getRequestURI().replace(req.getContextPath(), "");
		System.out.println("req path"+reqPath);
		for (RequestMappingData mappingData : mapping) {
			String[] path = mappingData.getPath().length != 0 ? mappingData.getPath() : mappingData.getValue();
			for (int i = 0; i < path.length; i++) {
				if(reqPath.equals(path[i])){
					try {
						mappingData.getMethod().invoke(TestCon.class.newInstance());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
