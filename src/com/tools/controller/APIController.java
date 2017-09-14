package com.tools.controller;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tools.annotation.API;
import com.tools.annotation.Excel;
import com.tools.annotation.ParamsInfo;
import com.tools.jdbc.PrimaryKey;
import com.tools.jdbc.PrimaryKeyType;
import com.tools.utils.Tips;
import com.tools.web.RequestMappingData;
import com.tools.web.ServletContainerInitializerImpl;
import com.tools.web.annotation.Controller;
import com.tools.web.annotation.RequestMapping;
import com.tools.web.annotation.RequestMethod;

/**
 * @author liujian
 *
 */
@Controller
@RequestMapping(name="APIController", path={"/docs"})
public class APIController{

	@RequestMapping(name="index", path={"/index.jsp"})
	public void index(HttpServletRequest req, HttpServletResponse resp){
		PrintWriter out = null;
		try {
			resp.setContentType("text/html; charset=utf-8");
		      out = resp.getWriter();

		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");
		      out.write("<html>\r\n");
		      out.write("<head>\r\n");
		      out.write("<title>API结构目录 </title>\r\n");
		      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
		      out.write("<style>\r\n");
		      out.write("body, td {\r\n");
		      out.write("font-family: 微软雅黑;\r\n");
		      out.write("font-size: 12px;\r\n");
		      out.write("line-height: 150%;\r\n");
		      out.write("}\r\n");
		      out.write("table {\r\n");
		      out.write("width: 100%;\r\n");
		      out.write("background-color: #ccc;\r\n");
		      out.write("margin: 5px 0;\r\n");
		      out.write("word-break: break-all;\r\n");
		      out.write("}\r\n");
		      out.write("td {\r\n");
		      out.write("\tbackground-color: #fff;\r\n");
		      out.write("\tpadding: 3px;\r\n");
		      out.write("padding-left: 10px;\r\n");
		      out.write("}\r\n");
		      out.write("thead td {\r\n");
		      out.write("\ttext-align: left;\r\n");
		      out.write("font-weight: bold;\r\n");
		      out.write("background-color: #eee;\r\n");
		      out.write("}\r\n");
		      out.write("a:link, a:visited, a:active {\r\n");
		      out.write("color: #015FB6;\r\n");
		      out.write("text-decoration: none;\r\n");
		      out.write("}\r\n");
		      out.write("a:hover {\r\n");
		      out.write("color: #E33E06;\r\n");
		      out.write("}\r\n");
		      out.write(".tablename {\r\n");
		      out.write("margin-top: 30px;\r\n");
		      out.write("}\r\n");
		      out.write(".outdiv {\r\n");
		      out.write("width: 1000px;\r\n");
		      out.write("margin: 40px auto;\r\n");
		      out.write("text-align: left;\r\n");
		      out.write("}\r\n");
		      out.write("\r\n");
		      out.write("</style>\r\n");
		      out.write("</head>\r\n");
		      out.write("<body>\r\n");
		      out.write("<center>\r\n");
		      out.write("<div class=\"outdiv\">\r\n");
		      out.write("<h2 style='text-align: center; line-height: 40px;'>API结构目录</h2>\r\n");
		      out.write("<table cellspacing=\"1\" cellpadding=\"0\">\r\n");
		      out.write(" <colgroup>\r\n");
		      out.write("  <col width=\"50px\">\r\n");
		      out.write("  <col width=\"240px\">\r\n");
		      out.write("  <col width=\"240px\">\r\n");
		      out.write("  <col width=\"200px\">\r\n");
		      out.write("  <col width=\"270px\">\r\n");
		      out.write(" </colgroup> \r\n");
		      out.write(" <thead>\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td>序号</td>\r\n");
		      out.write("   <td>名称</td>\r\n");
		      out.write("   <td>类</td>\r\n");
		      out.write("   <td>urL</td>\r\n");
		      out.write("   <td>说明</td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write(" </thead> \r\n");
		      out.write(" <tbody>\r\n");

			Map<String, String> hash = new HashMap<String, String>();
			List<RequestMappingData> mapping = new ArrayList<RequestMappingData>();
			List<RequestMappingData> tmp = ServletContainerInitializerImpl.mapping;
			for(RequestMappingData mappingData : tmp){
				if(hash.get(mappingData.getClss().getName()) == null){
					mapping.add(mappingData);
					hash.put(mappingData.getClss().getName(), "1");
				}
			}
			
			for(int i = 0; i < mapping.size(); i++){
				RequestMappingData mappingData = mapping.get(i);
				Class<?> clzz = mappingData.getClss();
				if(clzz.isAnnotationPresent(API.class) && clzz.isAnnotationPresent(RequestMapping.class)){
					API api = clzz.getAnnotation(API.class);
					RequestMapping reqMap = clzz.getAnnotation(RequestMapping.class);
					
		      out.write("\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"text-align: center;\">");
		      out.print(i+1 );
		      out.write("</td>\r\n");
		      out.write("   ");

		   		String name = "";
		   		if(!"".equals(api.name())){
		   			name = api.name();
		   		}else if(!"".equals(reqMap.name())){
		   			name = reqMap.name();
		   		}else{
		   			name = clzz.getSimpleName();
		   		}
		   
		      out.write("\r\n");
		      out.write("   <td><a href=\"detail.jsp?type=");
		      out.print(clzz.getName() );
		      out.write('"');
		      out.write('>');
		      out.print(name );
		      out.write("</a></td>\r\n");
		      out.write("   <td>");
		      out.print(clzz.getName() );
		      out.write("</td> \r\n");
		      out.write("   <td>");

		   		String[] path = null;
		   		if(reqMap.path().length > 0){
		   			path = reqMap.path();
		   		}else {
		   			path = reqMap.value();
		   		}
		   		for(int j = 0; j < path.length; j++){
		   			
		      out.write("\r\n");
		      out.write("   \t\t\t<p>");
		      out.print(path[j]+"" );
		      out.write("</p>\r\n");
		      out.write("   \t\t\t");

		   		}
		   
		      out.write("</td>\r\n");
		      out.write("   <td><p>");
		      out.print(api.info() );
		      out.write("</p></td> \r\n");
		      out.write("  </tr> \r\n");
		      out.write("\t\t\t");

				}
			}

		      out.write("\r\n");
		      out.write("\r\n");
		      out.write(" </tbody>\r\n");
		      out.write("</table>\r\n");
		      out.write("</div>\r\n");
		      out.write("</center>\r\n");
		      out.write("</body>\r\n");
		      out.write("</html>\r\n");
		      out.write("\r\n");
		    } catch (Exception e) {
		      e.printStackTrace();
		    } 
	}
	
	@RequestMapping(name="detail", path={"/detail.jsp"})
	public void detail(HttpServletRequest req, HttpServletResponse resp){
		PrintWriter out = null;
		try {
			resp.setContentType("text/html; charset=utf-8");
		      out = resp.getWriter();

		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");
		      out.write("<html>\r\n");
		      out.write("<head>\r\n");
		      out.write("<title>详情</title>\r\n");
		      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
		      out.write("<style>\r\n");
		      out.write("body, td {\r\n");
		      out.write("font-family: 微软雅黑;\r\n");
		      out.write("font-size: 12px;\r\n");
		      out.write("line-height: 150%;\r\n");
		      out.write("}\r\n");
		      out.write("table {\r\n");
		      out.write("width: 100%;\r\n");
		      out.write("background-color: #ccc;\r\n");
		      out.write("margin: 5px 0;\r\n");
		      out.write("word-break: break-all;\r\n");
		      out.write("}\r\n");
		      out.write("td {\r\n");
		      out.write("\tbackground-color: #fff;\r\n");
		      out.write("\tpadding: 3px;\r\n");
		      out.write("padding-left: 10px;\r\n");
		      out.write("}\r\n");
		      out.write("thead td {\r\n");
		      out.write("\ttext-align: left;\r\n");
		      out.write("font-weight: bold;\r\n");
		      out.write("background-color: #eee;\r\n");
		      out.write("}\r\n");
		      out.write("a:link, a:visited, a:active {\r\n");
		      out.write("color: #015FB6;\r\n");
		      out.write("text-decoration: none;\r\n");
		      out.write("}\r\n");
		      out.write("a:hover {\r\n");
		      out.write("color: #E33E06;\r\n");
		      out.write("}\r\n");
		      out.write(".tablename {\r\n");
		      out.write("margin-top: 30px;\r\n");
		      out.write("}\r\n");
		      out.write(".outdiv {\r\n");
		      out.write("width: 1000px;\r\n");
		      out.write("margin: 40px auto;\r\n");
		      out.write("text-align: left;\r\n");
		      out.write("}\r\n");
		      out.write("\r\n");
		      out.write("</style>\r\n");
		      out.write("</head>\r\n");
		      out.write("<body>\r\n");

			String className = req.getParameter("type");
			Class<?> clzz = Class.forName(className);
			if(clzz.isAnnotationPresent(API.class) && clzz.isAnnotationPresent(RequestMapping.class)){
				API api = clzz.getAnnotation(API.class);
				RequestMapping reqMap = clzz.getAnnotation(RequestMapping.class);
				
		      out.write("\r\n");
		      out.write("<center>\r\n");
		      out.write("<div class=\"outdiv\">\r\n");
		      out.write("<div class=\"tablename\">\r\n");
		      out.write("<b>API：");
		      out.print(className );
		      out.write("</b>\r\n");
		      out.write("</div>\r\n");

		   		String name = "";
		   		if(!"".equals(api.name())){
		   			name = api.name();
		   		}else if(!"".equals(reqMap.name())){
		   			name = reqMap.name();
		   		}else{
		   			name = clzz.getSimpleName();
		   		}
		   
		      out.write("\r\n");
		      out.write("<div>中文名称：");
		      out.print(name );
		      out.write("</div>\r\n");
		      out.write("<div>说明：");
		      out.print(api.info() );
		      out.write("</div>\r\n");
		      out.write("<a href='index.jsp' style='float: right;'>返回目录</a>\r\n");

			if(api.entity().length > 0){
				
				
		      out.write("\r\n");
		      out.write("<div>Entity说明：</div>\r\n");
		      out.write("<table cellspacing=\"1\" cellpadding=\"0\">\r\n");
		      out.write(" <thead>\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"width: 40px;\">序号</td>\r\n");
		      out.write("   <td>中文名称</td>\r\n");
		      out.write("   <td>英文名称</td>\r\n");
		      out.write("   <td>数据类型</td>\r\n");
		      out.write("   <td>长度</td>\r\n");
		      out.write("   <td>允许空值</td>\r\n");
		      out.write("   <td>主键</td>\r\n");
		      out.write("   <td>默认值</td>\r\n");
		      out.write("   <td>自增</td>\r\n");
		      out.write("   <td>说明</td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write(" </thead> \r\n");
		      out.write(" <tbody>\r\n");
		      out.write(" ");

			   Field[] fields = api.entity()[0].getDeclaredFields();
				for(int j = 0; j < fields.length; j++){
					Field f = fields[j];
					Map<String, Object> node = new HashMap<String, Object>();
					node.put("name", "");
					node.put("field", f.getName());
					node.put("fieldType", f.getType().getSimpleName());
					node.put("length", "");
					node.put("isNull", "");
					node.put("isPid", "pid".equals(f.getName()) ?  "Y" : "");
					node.put("defaults", "");
					node.put("autoInc", "");
					node.put("info", "");
					if(f.isAnnotationPresent(Excel.class)){
						Excel excel = f.getAnnotation(Excel.class);
						String str = excel.name().replace("：", " ").replace(":", " ").replace("\t", " ").replace("\n", " ").split(" ")[0];
						node.put("name", str); 
						node.put("info", excel.name());
						node.put("defaults", excel.value());
					}
					if(f.isAnnotationPresent(PrimaryKey.class)){
						PrimaryKey pkey = f.getAnnotation(PrimaryKey.class);
						node.put("isNull", "N");
						node.put("autoInc", PrimaryKeyType.AUTO_INCREMENT.equals(pkey.type()) ? "Y" : "");
					}
					if(f.isAnnotationPresent(API.class)){
						API fapi = f.getAnnotation(API.class);
						node.put("info", fapi.info());
					}
					
		      out.write("\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"text-align: center;\">");
		      out.print(j+1 );
		      out.write("</td> \r\n");
		      out.write("   <td>");
		      out.print(node.get("name") );
		      out.write("</td>\r\n");
		      out.write("   <td>");
		      out.print(node.get("field") );
		      out.write("</td>\r\n");
		      out.write("   <td align=\"center\">");
		      out.print(node.get("fieldType") );
		      out.write("</td>\r\n");
		      out.write("   <td align=\"center\">");
		      out.print(node.get("length") );
		      out.write("</td>\r\n");
		      out.write("   <td align=\"center\">");
		      out.print(node.get("isNull") );
		      out.write("</td>\r\n");
		      out.write("   <td align=\"center\">");
		      out.print(node.get("isPid") );
		      out.write("</td>\r\n");
		      out.write("   <td align=\"center\">");
		      out.print(node.get("defaults") );
		      out.write("</td>\r\n");
		      out.write("   <td>");
		      out.print(node.get("autoInc") );
		      out.write("</td>\r\n");
		      out.write("   <td><p>");
		      out.print(node.get("info") );
		      out.write("</p></td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write("\t\t\t");

				}
				
		 
		      out.write("\r\n");
		      out.write(" </tbody>\r\n");
		      out.write("</table>\r\n");
		      out.write("\t\t");

			}

		      out.write("\r\n");
		      out.write("\r\n");
		      out.write("<div>Method说明：</div>\r\n");
		      out.write("<table cellspacing=\"1\" cellpadding=\"0\">\r\n");
		      out.write(" <thead>\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"width: 40px;\">序号</td>\r\n");
		      out.write("   <td>中文名称</td>\r\n");
		      out.write("   <td>Method</td>\r\n");
		      out.write("   <td>URL</td>\r\n");
		      out.write("   <td>请求类型</td>\r\n");
		      out.write("   <td>Request</td>\r\n");
		      out.write("   <td>Response</td>\r\n");
		      out.write("   <td>说明</td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write(" </thead> \r\n");
		      out.write(" <tbody>\r\n");

			Method[] methods = clzz.getMethods();
			for(int j = 0; j < methods.length; j++){
				Method m = methods[j];
				if(m.isAnnotationPresent(API.class) && m.isAnnotationPresent(RequestMapping.class)){
					API mapi = m.getAnnotation(API.class);
					RequestMapping mreqMap = m.getAnnotation(RequestMapping.class);
					
					String mtmp = "";
			   		if(!"".equals(mapi.name())){
			   			mtmp = mapi.name();
			   		}else if(!"".equals(mreqMap.name())){
			   			mtmp = mreqMap.name();
			   		}else{
			   			mtmp = m.getName();
			   		}
					
					Map<String, Object> node = new HashMap<String, Object>();
					node.put("name", mtmp);
					node.put("method", m.getName());
					node.put("type", "");
					node.put("request", "");
					node.put("response", "");
					node.put("info", mapi.info());
				
		      out.write("\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"text-align: center;\">");
		      out.print(j+1 );
		      out.write("</td> \r\n");
		      out.write("   <td>");
		      out.print(node.get("name") );
		      out.write("</td> \r\n");
		      out.write("   <td>");
		      out.print(node.get("method") );
		      out.write("</td>\r\n");
		      out.write("   <td align=\"center\">");

		   		//api	path
		   		String[] path = null;
				if(reqMap.path().length > 0){
					path = reqMap.path();
				}else {
					path = reqMap.value();
				}
				//metnod	path
				String[] mpath = null;
				if(mreqMap.path().length > 0){
					mpath = mreqMap.path();
				}else {
					mpath = mreqMap.value();
				}
		   		for(String purl : path){
		   			for(String curl : mpath){
		   				
		      out.write("\r\n");
		      out.write("   \t\t\t\t<p>");
		      out.print(purl+curl );
		      out.write("</p>\r\n");
		      out.write("   \t\t\t\t");

		   			}
		   		}
		   
		      out.write("</td>\r\n");
		      out.write("   <td>");

		   		RequestMethod[] types = mreqMap.method();
		   		if(types.length == 0){
		   			
		      out.write("\r\n");
		      out.write("   \t\t\t<p>");
		      out.print("ALL");
		      out.write("</p>\r\n");
		      out.write("   \t\t\t");

		   		}else{
		   			for(RequestMethod rm : types){
		   				
		      out.write("\r\n");
		      out.write("   \t\t\t\t<p>");
		      out.print(rm );
		      out.write("</p>\r\n");
		      out.write("   \t\t\t\t");

		   			}
		   		}
		   
		      out.write("</td>\r\n");
		      out.write("   <td>");

		  		 ParamsInfo[] reqInfos = mapi.request();
		  		for(ParamsInfo tmp : reqInfos){
		  			
		      out.write("\r\n");
		      out.write("  \t\t\t<p>");
		      out.print(tmp.name()+": "+ tmp.info().replace("（必填）", "<font color=\"red\">（必填）</font>"));
		      out.write("</p>\r\n");
		      out.write("  \t\t\t");

		  		} 
		   
		      out.write("</td>\r\n");
		      out.write("   <td>");

		  		 ParamsInfo[] respInfos = mapi.response();
		  		for(ParamsInfo tmp : respInfos){
		  			
		      out.write("\r\n");
		      out.write("  \t\t\t<p>");
		      out.print(tmp.name()+": "+ tmp.info());
		      out.write("</p>\r\n");
		      out.write("  \t\t\t");

		  		} 
		   
		      out.write("</td>\r\n");
		      out.write("   <td><p>");
		      out.print(node.get("info") );
		      out.write("</p></td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write("\t\t");

				}
			}

		      out.write("\r\n");
		      out.write(" </tbody>\r\n");
		      out.write("</table>\r\n");
		      out.write("<div>返回码说明：</div>\r\n");
		      out.write("<table cellspacing=\"1\" cellpadding=\"0\">\r\n");
		      out.write(" <thead>\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"width: 40px;\">序号</td>\r\n");
		      out.write("   <td>code</td>\r\n");
		      out.write("   <td>说明</td>\r\n");
		      out.write("   <td></td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write(" </thead> \r\n");
		      out.write(" <tbody>\r\n");
		      out.write(" ");

		 	for (int j = 0; j < Tips.values().length; j++)  {
		 		Tips tip = Tips.values()[j];
		 		
		      out.write("\r\n");
		      out.write("  <tr>\r\n");
		      out.write("   <td style=\"text-align: center;\">");
		      out.print(j+1 );
		      out.write("</td> \r\n");
		      out.write("   <td>");
		      out.print(tip.getCode() );
		      out.write("</td> \r\n");
		      out.write("   <td>");
		      out.print(tip.getDescOriginal() );
		      out.write("</td>\r\n");
		      out.write("   <td></td>\r\n");
		      out.write("  </tr>\r\n");
		      out.write(" \t\t\r\n");
		      out.write(" \t\t");

		 	}
		 
		      out.write("\r\n");
		      out.write(" </tbody>\r\n");
		      out.write("</table>\r\n");
		      out.write("\r\n");
		      out.write("</div>\r\n");
		      out.write("</center>\r\n");
		      out.write("\t\t\r\n");
		      out.write("\t\t");

			}

		      out.write("\r\n");
		      out.write("</body>\r\n");
		      out.write("</html>\r\n");
		      out.write("\r\n");
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
}
