package com.tools.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tools.utils.Tools;
import com.tools.web.annotation.RequestMethod;

public class RequstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatch(req, resp);
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp){
		AsyncContext async = req.startAsync(req,resp);  
		async.setTimeout(30*1000);
		async.start(new Runnable() {
			@Override
			public void run() {
				dispatch(req, resp);
			}
		});
		
	}
	
	private void dispatch(HttpServletRequest req, HttpServletResponse resp){
		List<RequestMappingData> mapping = ServletContainerInitializerImpl.mapping;
		String reqPath = req.getRequestURI().replace(req.getContextPath(), "");
		String reqMethod = req.getMethod();
		//allow
		/*String rpath = Tools.getBsaePath() + "allows/req.txt";
		List<String> content = new ArrayList<String>();
		File file = new File(rpath);
		if(file.exists()){
			//TODO
		}*/
		for (RequestMappingData mappingData : mapping) {
			//判断 requset method
			RequestMethod[] methods = mappingData.getReqMethod();
			boolean mFlag = false;
			if(methods.length == 0){
				mFlag = true;
			}else{
				for (int i = 0; i < methods.length; i++) {
					if(methods[i] == getRequestMethod(reqMethod)){
						mFlag = true;
						break;
					}
				}
			}

			//判断 requset path
			String[] path = mappingData.getPath().length != 0 ? mappingData.getPath() : mappingData.getValue();
			boolean pFlag = false;
			for (int i = 0; i < path.length; i++) {
				if(reqPath.equals(path[i])){
					pFlag = true;
					break;
				}
			}
			
			//判断 其他
			//暂未实现


			if(mFlag && pFlag){
				try {
//					mappingData.getMethod().invoke(mappingData.getClss().newInstance(), req, resp);
					//传入对应参数
					//待丰富参数
					Object res = null;
					Class<?>[] pclss = mappingData.getMethod().getParameterTypes();
					if(pclss.length == 0){
						mappingData.getMethod().invoke(mappingData.getClss().newInstance());
					}else{
						Object[] params = new Object[pclss.length];
						for (int i = 0; i < pclss.length; i++) {
							String parameterName = pclss[i].getSimpleName();
							switch (parameterName) {
							case "HttpServletRequest":
								params[i] = req;
								break;
							case "HttpServletResponse":
								params[i] = resp;
								break;

							default:
								
								break;
							}
						}
						res = mappingData.getMethod().invoke(mappingData.getClss().newInstance(), params);
					}
					if(res != null){
						Tools.output(resp, (String) res);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private RequestMethod getRequestMethod(String reqMethod){
		RequestMethod method = RequestMethod.GET;
		switch (reqMethod) {
		case "GET":
			method = RequestMethod.GET;
			break;
		case "HEAD":
			method = RequestMethod.HEAD;
			break;
		case "POST":
			method = RequestMethod.POST;
			break;
		case "PUT":
			method = RequestMethod.PUT;
			break;
		case "PATCH":
			method = RequestMethod.PATCH;
			break;
		case "DELETE":
			method = RequestMethod.DELETE;
			break;
		case "OPTIONS":
			method = RequestMethod.OPTIONS;
			break;
		case "TRACE":
			method = RequestMethod.TRACE;
			break;
		default:
			break;
		}
		return method;
	}

}
