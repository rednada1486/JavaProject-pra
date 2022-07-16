<%@page import="db.History"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="wifi.Wifi"%>
<%@page import="db.HandleSQLite"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    table {
        width: 100%;
    }
    th,
    th,
    td {
        border: 0.1px solid white;
    }

    th {
        background-color: #04aa6d;
        color: white;
    }
    tr:nth-child(even) {
        background-color: #f2f2f2;
        
    }
    tr:hover {background-color: gray;}
    

    #wifi-items {
        border: 0.1px solid rgb(209, 206, 206);
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .before {
        margin: 10px 0 10px 0px;
    }
    .last{
    	display:flex;
    	justify-content: center;
    
    
    }
    .btn{
    	text-decoration-line: none;
    }
    #btn{
       	position:absolute;
    	z-index:2;
   	}
    
</style>
</head>

    <body>
		<%
			HandleSQLite handleSqlite = new HandleSQLite();
			handleSqlite.createHistoryTable();
			List<History> result = new ArrayList<History>();
			result = handleSqlite.getAllHistory();
			
			boolean isEmpty = result.size()==0 ? true : false;
		
			String ID = request.getParameter("ID");
			
			if(ID!=null){
		        try {
			        Double.parseDouble(ID);
			        handleSqlite.deleteHistoryData(Integer.parseInt(ID));
		        }catch(NumberFormatException e) {
		            System.out.println("It is not numerical string");
		        }
			}

		%>
    
    
    
        <h1>와이파이 정보 구하기</h1>
        <a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="result.jsp"> Open API 와이파이 정보 가져오기</a>
        <p></p>

        <p></p>

        <div id="wifi-items">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>X좌표</th>
                        <th>Y좌표</th>
                        <th>조회일자</th>
                        <th>비고</th>
                    </tr>
                </thead>
                <tbody>
					<% for(History cur : result){ %>
						<tr>
							<td class="td<%= cur.ID %>"> <%= cur.ID %> </td>
							<td class="td<%= cur.ID %>"> <%= cur.LAT %> </td>
							<td class="td<%= cur.ID %>"> <%= cur.LNT %> </td>
							<td class="td<%= cur.ID %>"> <%= cur.DATE %> </td>
							<td class="last"> 
								<button id="btn"><a class="btn" href="delete.jsp?ID=<%=cur.ID %>">삭제</a></button>
							</td>		
						</tr>
					<%} %>
                </tbody>
            </table>
            <% if(isEmpty){ %>
	            <div >
	                <span>히스토리가 없습니다.</span>
	            </div>
            <%} %>
        </div>
        <script>
      		<% for(History cur : result){ %>
	        	document.querySelectorAll(".td<%= cur.ID %>").forEach((tr)=>{
	        		tr.addEventListener("click",(event)=>{
		        		console.log("버튼 눌림");
	        			window.open("historyDetail.jsp?HistoryID=<%= cur.ID %>", "_blank");
	        		})
	        	})
        	<%} %>
        </script>
    </body>
</html>