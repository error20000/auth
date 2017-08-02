package com.auth.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.auth.entity.App;
import com.auth.service.AppService;
import com.auth.service.impl.AppServiceImpl;
import com.tools.utils.ResultKey;
import com.tools.utils.Tips;
import com.tools.utils.Tools;
import com.tools.web.annotation.Controller;
import com.tools.web.annotation.RequestMapping;

@Controller
@RequestMapping(name="AppController", path={"/app"})
public class AppController{
	
	private AppService service = new AppServiceImpl();
	
	//登录验证
	private Map<String, Object> verifyLogin(HttpServletRequest req){
		//TODO 需要自己修改验证
		
		return null;
	}
	
	//保存验证
	private Map<String, Object> verifySave(HttpServletRequest req){
		//TODO 需要自己修改验证
		
		return null;
	}
	
	//修改验证
	private Map<String, Object> verifyModify(HttpServletRequest req){
		//TODO 需要自己修改验证
		
		return null;
	}
	
	//删除验证
	private Map<String, Object> verifyDelete(HttpServletRequest req){
		//TODO 需要自己修改验证
		
		return null;
	}

	
	//查询验证
	private Map<String, Object> verifyQuery(HttpServletRequest req){
		//TODO 需要自己修改验证
		
		return null;
	}
	
	
	@RequestMapping(name="save", path={"/add"})
	public String save(HttpServletRequest req, HttpServletResponse resp){
		//验证
		Map<String, Object> vMap = verifySave(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		App obj = Tools.getReqParamsToObject(req, new App());
		int res = service.save(obj);
		if(res > 0){
			json.put(ResultKey.CODE, Tips.ERROR1.getCode());
			json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		}else{
			json.put(ResultKey.CODE, Tips.ERROR0);
			json.put(ResultKey.MSG, Tips.ERROR0.getDesc());
		}
		return json.toJSONString();
	}
	
	@RequestMapping(name="modify", path={"/modify"})
	public String modify(HttpServletRequest req, HttpServletResponse resp){
		//验证
		Map<String, Object> vMap = verifyModify(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		App obj = Tools.getReqParamsToObject(req, new App());
		int res = service.modify(obj);
		if(res > 0){
			json.put(ResultKey.CODE, Tips.ERROR1.getCode());
			json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		}else{
			json.put(ResultKey.CODE, Tips.ERROR0);
			json.put(ResultKey.MSG, Tips.ERROR0.getDesc());
		}
		return json.toJSONString();
	}
	
	@RequestMapping(name="delete", path={"/delete"})
	public String delete(HttpServletRequest req, HttpServletResponse resp){
		//验证
		Map<String, Object> vMap = verifyDelete(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		int res = service.delete(condition);
		if(res > 0){
			json.put(ResultKey.CODE, Tips.ERROR1.getCode());
			json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		}else{
			json.put(ResultKey.CODE, Tips.ERROR0);
			json.put(ResultKey.MSG, Tips.ERROR0.getDesc());
		}
		return json.toJSONString();
	}
	
	@RequestMapping(name="query", path={"/query"})
	public String query(HttpServletRequest req, HttpServletResponse resp){

		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		vMap = Tools.verifyParam("page", Tools.getReqParam(req, "page"), 1, 0, true);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		vMap = Tools.verifyParam("rows", Tools.getReqParam(req, "rows"), 1, 4, true); // 0 - 9999
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		int start = "0".equals(page) ? 0 : (Tools.parseInt(page) - 1 ) * Tools.parseInt(rows);
		List<App> res = service.findList(condition, start, Tools.parseInt(rows));
		long total = service.size(condition);
		json.put(ResultKey.CODE, Tips.ERROR1.getCode());
		json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		json.put(ResultKey.TOTAL, total);
		json.put(ResultKey.DATA, res);
		return json.toJSONString();
	}
	
	@RequestMapping(name="list", path={"/list"})
	public String list(HttpServletRequest req, HttpServletResponse resp){

		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		List<App> res = service.findList(condition);
		json.put(ResultKey.CODE, Tips.ERROR1.getCode());
		json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		json.put(ResultKey.DATA, res);
		return json.toJSONString();
	}
	
	@RequestMapping(name="find", path={"/find"})
	public String find(HttpServletRequest req, HttpServletResponse resp){

		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		List<App> res = service.findList(condition);
		json.put(ResultKey.CODE, Tips.ERROR1.getCode());
		json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		json.put(ResultKey.DATA, res);
		return json.toJSONString();
	}
	
	@RequestMapping(name="findOne", path={"/findOne"})
	public String findOne(HttpServletRequest req, HttpServletResponse resp){

		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		App res = service.findObject(condition);
		json.put(ResultKey.CODE, Tips.ERROR1.getCode());
		json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		json.put(ResultKey.DATA, res);
		return json.toJSONString();
	}
	
	@RequestMapping(name="excel", path={"/excel"})
	public void excel(HttpServletRequest req, HttpServletResponse resp){

		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			Tools.output(resp, JSONObject.toJSONString(vMap));
			return;
		}
		//执行
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		List<App> res = service.findList(condition);
		String name = "";
		resp.addHeader("Content-Disposition","attachment;filename="+name+".csv");
		// response.addHeader("Content-Length", "" + JSONArray.fromObject(list).toString().getBytes().length);
		resp.setContentType("application/octet-stream;charset=utf-8");
		try {
			OutputStream toClient = new BufferedOutputStream(resp.getOutputStream());
			toClient.write("".getBytes("utf-8"));
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(name="excelHeader", path={"/excelHeader"})
	public String excelHeader(HttpServletRequest req, HttpServletResponse resp){

		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			return JSONObject.toJSONString(vMap);
		}
		//执行
		JSONObject json = new JSONObject();
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		App res = service.findObject(condition);
		json.put(ResultKey.CODE, Tips.ERROR1.getCode());
		json.put(ResultKey.MSG, Tips.ERROR1.getDesc());
		json.put(ResultKey.DATA, res);
		return json.toJSONString();
	}
	
	@RequestMapping(name="excelFree", path={"/excelFree"})
	public void excelFree(HttpServletRequest req, HttpServletResponse resp){

		//验证
		Map<String, Object> vMap = verifyQuery(req);
		if(vMap != null){
			Tools.output(resp, JSONObject.toJSONString(vMap));
			return;
		}
		//执行
		Map<String, Object> condition = Tools.getReqParamsToMap(req);
		List<App> res = service.findList(condition);
		String name = "";
		resp.addHeader("Content-Disposition","attachment;filename="+name+".csv");
		// response.addHeader("Content-Length", "" + JSONArray.fromObject(list).toString().getBytes().length);
		resp.setContentType("application/octet-stream;charset=utf-8");
		try {
			OutputStream toClient = new BufferedOutputStream(resp.getOutputStream());
			toClient.write("".getBytes("utf-8"));
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
