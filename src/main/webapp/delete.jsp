<%@page import="java.util.ArrayList"%>
<%@page import="wifi.Wifi"%>
<%@page import="java.util.List"%>
<%@page import="db.HandleSQLite"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		HandleSQLite handleSqlite = new HandleSQLite();
		String ID = request.getParameter("ID");
        try {
	        Double.parseDouble(ID);
	        handleSqlite.deleteHistoryData(Integer.parseInt(ID));
	        handleSqlite.deleteHistoryDetail(Integer.parseInt(ID));
        }catch(NumberFormatException e) {
            System.out.println("It is not numerical string");
        }
	%>
	
	<script type="text/javascript">
		location.href="history.jsp";
	</script>
</body>
</html>