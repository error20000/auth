package com.tools.web;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tools.controller.AbstractBaseController;
import com.tools.utils.Tools;
import com.tools.web.annotation.Controller;
import com.tools.web.annotation.RequestMapping;
import com.tools.web.annotation.RequestMethod;

@HandlesTypes(AbstractBaseController.class)
public class ServletContainerInitializerImpl implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> clsses, ServletContext arg1) throws ServletException {
		System.out.println("onStartup....");
		if(clsses != null){
			for (Class<?> clss : clsses) {
				System.out.println(clss.getName());
				if (!clss.isInterface() && !Modifier.isAbstract(clss.getModifiers()) &&
						AbstractBaseController.class.isAssignableFrom(clss)) {
					List<RequestMappingData> mappingList = parseMapping(clss);
					createServlet(mappingList);
				}
			}
		}
	}
    
	private List<RequestMappingData> parseMapping(Class<?> clss) throws ServletException{
		//判断是Controller，才去解析
		if(clss.isAnnotationPresent(Controller.class)){
			//获取类RequestMapping
			RequestMappingData parentData = null;
			if(clss.isAnnotationPresent(RequestMapping.class)){
				String name = clss.getAnnotation(RequestMapping.class).name();
				String[] value = clss.getAnnotation(RequestMapping.class).value();
				String[] path = clss.getAnnotation(RequestMapping.class).path();
				RequestMethod[] method = clss.getAnnotation(RequestMapping.class).method();
				String[] params = clss.getAnnotation(RequestMapping.class).params();
				String[] headers = clss.getAnnotation(RequestMapping.class).headers();
				String[] consumes = clss.getAnnotation(RequestMapping.class).consumes();
				String[] produces = clss.getAnnotation(RequestMapping.class).produces();
				parentData = new RequestMappingData();
				parentData.setName(name);
				parentData.setValue(value);
				parentData.setPath(path);
				parentData.setReqMethod(method);
				parentData.setParams(params);
				parentData.setHeaders(headers);
				parentData.setConsumes(consumes);
				parentData.setProduces(produces);
			}
			
			//获取方法RequestMapping
			List<RequestMappingData> list = new ArrayList<>();
			Method[] methods = Tools.getMethods(clss);
			for (Method m : methods) {
				if(m.isAnnotationPresent(RequestMapping.class)){
					String name = m.getAnnotation(RequestMapping.class).name();
					String[] value = m.getAnnotation(RequestMapping.class).value();
					String[] path = m.getAnnotation(RequestMapping.class).path();
					RequestMethod[] method = m.getAnnotation(RequestMapping.class).method();
					String[] params = m.getAnnotation(RequestMapping.class).params();
					String[] headers = m.getAnnotation(RequestMapping.class).headers();
					String[] consumes = m.getAnnotation(RequestMapping.class).consumes();
					String[] produces = m.getAnnotation(RequestMapping.class).produces();
					
					RequestMappingData data = new RequestMappingData();
					if(parentData != null){
						data = JSONObject.parseObject(JSON.toJSONString(parentData), RequestMappingData.class);
					}
					data.setName(data.getName()+"#"+name);
					//value
					List<String> valueTmp = new ArrayList<String>();
					for (int i = 0; i < data.getValue().length; i++) {
						for (int j = 0; j < value.length; j++) {
							valueTmp.add(data.getValue()[i] + value[j]);
						}
					}
					data.setValue(valueTmp.toArray(new String[valueTmp.size()]));
					//path
					List<String> pathTmp = new ArrayList<String>();
					for (int i = 0; i < data.getPath().length; i++) {
						for (int j = 0; j < path.length; j++) {
							pathTmp.add(data.getPath()[i] + path[j]);
						}
					}
					data.setPath(pathTmp.toArray(new String[pathTmp.size()]));
					//method
					if(data.getReqMethod().length != 0){
						List<RequestMethod> methodTmp = new ArrayList<RequestMethod>();
						for (int i = 0; i < data.getReqMethod().length; i++) {
							boolean flag = false;
							for (int j = 0; j < method.length; j++) {
								if(data.getReqMethod()[i] == method[j]){
									flag = true;
									break;
								}
							}
							if(flag){
								methodTmp.add(data.getReqMethod()[i]);
							}
						}
						if(methodTmp.size() == 0) methodTmp.add(null);
					}else{
						data.setReqMethod(method);
					}
					//以下暂不支持
					data.setParams(params);
					data.setHeaders(headers);
					data.setConsumes(consumes);
					data.setProduces(produces);
					
					data.setMethod(m);
					list.add(data);
				}
			}
			//检测path唯一
			Map<String, Integer> hash = new HashMap<String, Integer>();
			for (RequestMappingData node : list) {
				if(node.getPath().length != 0){
					for (int i = 0; i < node.getPath().length; i++) {
						Integer count = hash.get(node.getPath()[i]);
						if(count == null){
							hash.put(node.getPath()[i], 1);
						}else{
							throw new ServletException("parse url mapping error!!", new Throwable());
						}
					}
				}else{
					for (int i = 0; i < node.getValue().length; i++) {
						Integer count = hash.get(node.getValue()[i]);
						if(count == null){
							hash.put(node.getValue()[i], 1);
						}else{
							throw new ServletException("parse url mapping error, "+node.getValue()[i]+" not only.", new Throwable());
						}
					}
				}
			}
			
			return list;
		}
		return null;
	}
	
	private void createServlet(List<RequestMappingData> mappingList){
		if(mappingList != null){
			for (RequestMappingData requestMappingData : mappingList) {
				
			}
		}
	}
	

}
