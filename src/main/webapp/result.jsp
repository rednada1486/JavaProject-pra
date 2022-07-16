<%@page import="db.HandleSQLite"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <style>
        .result {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>
	<% 
		HandleSQLite handleSqlite = new HandleSQLite();
		String result = handleSqlite.getOpenApiWifiInfo();
	%>
	<div class="result">
		<h1><%= result %> </h1>
		<a href="index.jsp">홈으로가기</a>
	</div>
	
</body>
</html>