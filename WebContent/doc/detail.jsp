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
<a href='list.jsp' style='float: right;'>返回目录</a>
<%
	if(api.entity().length > 0){
		
		%>
<div>Entity说明：</div>
<table cellspacing="1" cellpadding="0">
 <thead>
  <tr>
   <td style="width: 40px;">序号</td>
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
				node.put("name", excel.name()); 
			}
			if(f.isAnnotationPresent(PrimaryKey.class)){
				PrimaryKey pkey = f.getAnnotation(PrimaryKey.class);
				node.put("isNull", "N");
				node.put("autoInc", PrimaryKeyType.AUTO_INCREMENT.equals(pkey.type()) ? "Y" : "");
			}
			%>
  <tr>
   <td style="text-align: center;"><%=j+1 %></td> 
   <td><%=node.get("field") %></td>
   <td align="center"><%=node.get("fieldType") %></td>
   <td align="center"><%=node.get("length") %></td>
   <td align="center"><%=node.get("isNull") %></td>
   <td align="center"><%=node.get("isPid") %></td>
   <td align="center"><%=node.get("defaults") %></td>
   <td><%=node.get("autoInc") %></td>
   <td><p><%=node.get("name") %></p></td>
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
   <td>URI</td>
   <td>URL</td>
   <td>请求类型</td>
   <td>参数</td>
   <td>返回值</td>
   <td>说明</td>
  </tr>
 </thead> 
 <tbody>
 <c:forEach var="tmp" items="${data.method}" varStatus="self">
  <tr>
   <td style="text-align: center;">${self.count}</td> 
   <td>${tmp.name }</td> 
   <td>${tmp.method }</td>
   <td><c:forEach var="puri" items="${data.parent.uri }"><c:forEach var="uri" items="${tmp.uri }"><p>${puri }${uri }</p></c:forEach></c:forEach></td>
   <td align="center"><c:forEach var="post" items="${tmp.type }"><p>${post }</p></c:forEach></td>
   <td><c:forEach var="req" items="${tmp.request }"><p>${req }</p></c:forEach></td>
   <td><c:forEach var="res" items="${tmp.response }"><p>${res }</p></c:forEach></td>
   <td><c:forEach var="info" items="${tmp.info }"><p>${info }</p></c:forEach></td>
  </tr>
  </c:forEach>
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
  <tr>
   <td style="text-align: center;">1</td> 
   <td>大于0</td> 
   <td>成功</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">2</td> 
   <td>0</td> 
   <td>失败</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">3</td> 
   <td>-1</td> 
   <td>失败</td>
   <td>未登录等</td>
  </tr>
  <tr>
   <td style="text-align: center;">4</td> 
   <td>-2</td> 
   <td>无效</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">5</td> 
   <td>-3</td> 
   <td>没有权限</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">6</td> 
   <td>-4</td> 
   <td>效验失败</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">7</td> 
   <td>-5</td> 
   <td>不在活动时间段</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">8</td> 
   <td>-6</td> 
   <td>已参加过该活动</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">9</td> 
   <td>-7</td> 
   <td>次数已用完</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">10</td> 
   <td>-8</td> 
   <td>图形验证码错误</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">11</td> 
   <td>-9</td> 
   <td>短信验证码错误</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">12</td> 
   <td>-10</td> 
   <td>系统错误</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">13</td> 
   <td>-11</td> 
   <td>需提供图形验证码</td>
   <td></td>
  </tr>
  <tr>
   <td style="text-align: center;">14</td> 
   <td>-12</td> 
   <td>已发送，稍后再试</td>
   <td></td>
  </tr>
 </tbody>
</table>
<c:if test="${data.interfaces != null}">
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
</c:if>
</div>
</center>
		
		<%
	}
%>
</body>
</html>

