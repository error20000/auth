<%@page import="com.tools.utils.Tips"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tools.web.ServletContainerInitializerImpl" %>
<%@ page import="com.tools.web.RequestMappingData" %>
<%@ page import="com.tools.annotation.*" %>
<%@ page import="com.tools.web.annotation.*" %>
<%@ page import="com.tools.jdbc.*" %>
<%@ page import="java.lang.reflect.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<style>
body, td {
font-family: 微软雅黑;
font-size: 12px;
line-height: 150%;
}
table {
width: 100%;
background-color: #ccc;
margin: 5px 0;
word-break: break-all;
}
td {
	background-color: #fff;
	padding: 3px;
padding-left: 10px;
}
thead td {
	text-align: left;
font-weight: bold;
background-color: #eee;
}
a:link, a:visited, a:active {
color: #015FB6;
text-decoration: none;
}
a:hover {
color: #E33E06;
}
.tablename {
margin-top: 30px;
}
.outdiv {
width: 1000px;
margin: 40px auto;
text-align: left;
}

</style>
</head>
<body>
<%
	String className = request.getParameter("type");
	Class<?> clzz = Class.forName(className);
	if(clzz.isAnnotationPresent(API.class) && clzz.isAnnotationPresent(RequestMapping.class)){
		API api = clzz.getAnnotation(API.class);
		RequestMapping reqMap = clzz.getAnnotation(RequestMapping.class);
		%>
<center>
<div class="outdiv">
<div class="tablename">
<b>API：<%=className %></b>
</div>
<%
   		String name = "";
   		if(!"".equals(api.name())){
   			name = api.name();
   		}else if(!"".equals(reqMap.name())){
   			name = reqMap.name();
   		}else{
   			name = clzz.getSimpleName();
   		}
   %>
<div>中文名称：<%=name %></div>
<div>说明：<%=api.info() %></div>
<a href='index.jsp' style='float: right;'>返回目录</a>
<%
	if(api.entity().length > 0){
		
		%>
<div>Entity说明：</div>
<table cellspacing="1" cellpadding="0">
 <thead>
  <tr>
   <td style="width: 40px;">序号</td>
   <td>中文名称</td>
   <td>英文名称</td>
   <td>数据类型</td>
   <td>长度</td>
   <td>允许空值</td>
   <td>主键</td>
   <td>默认值</td>
   <td>自增</td>
   <td>说明</td>
  </tr>
 </thead> 
 <tbody>
 <%
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
			%>
  <tr>
   <td style="text-align: center;"><%=j+1 %></td> 
   <td><%=node.get("name") %></td>
   <td><%=node.get("field") %></td>
   <td align="center"><%=node.get("fieldType") %></td>
   <td align="center"><%=node.get("length") %></td>
   <td align="center"><%=node.get("isNull") %></td>
   <td align="center"><%=node.get("isPid") %></td>
   <td align="center"><%=node.get("defaults") %></td>
   <td><%=node.get("autoInc") %></td>
   <td><p><%=node.get("info") %></p></td>
  </tr>
			<%
		}
		
 %>
 </tbody>
</table>
		<%
	}
%>

<div>Method说明：</div>
<table cellspacing="1" cellpadding="0">
 <thead>
  <tr>
   <td style="width: 40px;">序号</td>
   <td>中文名称</td>
   <td>Method</td>
   <td>URL</td>
   <td>请求类型</td>
   <td>Request</td>
   <td>Response</td>
   <td>说明</td>
  </tr>
 </thead> 
 <tbody>
<%
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
		%>
  <tr>
   <td style="text-align: center;"><%=j+1 %></td> 
   <td><%=node.get("name") %></td> 
   <td><%=node.get("method") %></td>
   <td align="center"><%
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
   				%>
   				<p><%=purl+curl %></p>
   				<%
   			}
   		}
   %></td>
   <td><%
   		RequestMethod[] types = mreqMap.method();
   		if(types.length == 0){
   			%>
   			<p><%="ALL"%></p>
   			<%
   		}else{
   			for(RequestMethod rm : types){
   				%>
   				<p><%=rm %></p>
   				<%
   			}
   		}
   %></td>
   <td><%
  		 ParamsInfo[] reqInfos = mapi.request();
  		for(ParamsInfo tmp : reqInfos){
  			%>
  			<p><%=tmp.name()+": "+ tmp.info().replace("（必填）", "<font color=\"red\">（必填）</font>")%></p>
  			<%
  		} 
   %></td>
   <td><%
  		 ParamsInfo[] respInfos = mapi.response();
  		for(ParamsInfo tmp : respInfos){
  			%>
  			<p><%=tmp.name()+": "+ tmp.info()%></p>
  			<%
  		} 
   %></td>
   <td><p><%=node.get("info") %></p></td>
  </tr>
		<%
		}
	}
%>
 </tbody>
</table>
<div>返回码说明：</div>
<table cellspacing="1" cellpadding="0">
 <thead>
  <tr>
   <td style="width: 40px;">序号</td>
   <td>code</td>
   <td>说明</td>
   <td></td>
  </tr>
 </thead> 
 <tbody>
 <%
 	for (int j = 0; j < Tips.values().length; j++)  {
 		Tips tip = Tips.values()[j];
 		%>
  <tr>
   <td style="text-align: center;"><%=j+1 %></td> 
   <td><%=tip.getCode() %></td> 
   <td><%=tip.getDescOriginal() %></td>
   <td></td>
  </tr>
 		
 		<%
 	}
 %>
 </tbody>
</table>
<%-- <c:if test="${data.interfaces != null}">
	<div>现有接口说明：</div>
	<table cellspacing="1" cellpadding="0">
	 <thead>
	  <tr>
	   <td style="width: 40px;">序号</td>
	   <td>名称</td>
	   <td>英文标识</td>
	   <td>接口地址</td>
	   <td>状态</td>
	   <td>说明</td>
	  </tr>
	 </thead> 
	 <tbody>
	 <c:forEach var="tmp" items="${data.interfaces}" varStatus="self">
	  <tr>
	   <td style="text-align: center;">${self.count}</td> 
	   <td>${tmp.name }</td> 
	   <td>${tmp.en }</td>
	   <td>${tmp.url }</td>
	   <td>${tmp.status == 0 ? "禁用" : "启用" }</td>
	   <td><c:forEach var="info" items="${tmp.info.split(\"\\\n\") }"><p>${info }</p></c:forEach></td>
	  </tr>
	  </c:forEach>
	 </tbody>
	</table>
</c:if> --%>
</div>
</center>
		
		<%
	}
%>
</body>
</html>

