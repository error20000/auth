
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tools.web.ServletContainerInitializerImpl" %>
<%@ page import="com.tools.web.RequestMappingData" %>
<%@ page import="com.tools.annotation.API" %>
<%@ page import="com.tools.web.annotation.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>API结构目录 </title>
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
<center>
<div class="outdiv">
<h2 style='text-align: center; line-height: 40px;'>API结构目录</h2>
<table cellspacing="1" cellpadding="0">
 <colgroup>
  <col width="50px">
  <col width="240px">
  <col width="240px">
  <col width="200px">
  <col width="270px">
 </colgroup> 
 <thead>
  <tr>
   <td>序号</td>
   <td>名称</td>
   <td>类</td>
   <td>urL</td>
   <td>说明</td>
  </tr>
 </thead> 
 <tbody>
<%
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
			%>
  <tr>
   <td style="text-align: center;"><%=i+1 %></td>
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
   <td><a href="detail.json?type=<%=clzz.getName() %>"><%=name %></a></td>
   <td><%=clzz.getName() %></td> 
   <td><%
   		String[] path = null;
   		if(reqMap.path().length > 0){
   			path = reqMap.path();
   		}else {
   			path = reqMap.value();
   		}
   		for(int j = 0; j < path.length; j++){
   			%>
   			<p><%=path[j]+"" %></p>
   			<%
   		}
   %></td>
   <td><p><%=api.info() %></p></td> 
  </tr> 
			<%
		}
	}
%>

 </tbody>
</table>
</div>
</center>
</body>
</html>

