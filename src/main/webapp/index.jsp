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
    table{
        width: 100%;
    }

    th {
        background-color: #04AA6D;
        color: white;
    }
    tr:nth-child(even) {background-color: #f2f2f2;}
   	tr:hover {background-color: gray;}
    #wifi-items{
        border: 0.1px solid rgb(209, 206, 206);
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .before{
        margin: 10px 0 10px 0px;
    }

</style>
</head>
<body>
<body>
		<%
			HandleSQLite handleSqlite = new HandleSQLite();
			String LAT = request.getParameter("LAT");
			String LNT = request.getParameter("LNT");
			List<Wifi> result = new ArrayList<Wifi>();
			boolean isEmpty = false;
			if(LAT!=null && LNT != null){
				handleSqlite.createWifiTableAgain();
				result = handleSqlite.getNearByWifiInfo(Double.parseDouble(LAT), Double.parseDouble(LNT));
				isEmpty = result.size()==0 ? true : false;
			}
		%>



        <h1>와이파이 정보 구하기</h1>
        <a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="result.jsp"> Open API 와이파이 정보 가져오기</a>
        <p></p>
        <form action="index.jsp" id="form">
				LAT:
                <input name="LAT" autocomplete="0.0" id="LAT" type="number" step="0.0000001" value=<%=LAT%> required/>
                LNT:
                <input name="LNT" autocomplete="0.0" id="LNT" type="number" step="0.0000001" value=<%=LNT%>  required/>
                <button id="location">내 위치 가져오기</button>
                <button type="submit">근처 WIFI 정보 보기</button>
        </form>
        <p></p>
        
        <div id="wifi-items">
            <table>
                <thead>
                    <tr>
                        <th>거리(Km)</th>
                        <th>관리번호</th>
                        <th>자치구</th>
                        <th>와이파이명</th>
                        <th>도로명주소</th>
                        <th>상세주소</th>
                        <th>비밀번호</th>
                        <th>설치위치(층)</th>
                        <th>설치유형</th>
                        <th>설치기관</th>
                        <th>서비스구분</th>
                        <th>망종류</th>
                        <th>설치년도</th>
                        <th>실내외구분</th>
                        <th>WIFI접속환경</th>
                        <th>X좌표</th>
                        <th>Y좌표</th>
                        <th>작업일자</th>
                    </tr>
                </thead>
                <tbody>
					<% for(Wifi cur : result){ %>
						<tr>
							<td> <%= cur.DISTANCE %> </td>
							<td> <%= cur.X_SWIFI_MGR_NO %> </td>
							<td> <%= cur.X_SWIFI_WRDOFC %> </td>
							<td> <%= cur.X_SWIFI_MAIN_NM %> </td>
							<td> <%= cur.X_SWIFI_ADRES1 %> </td>
							<td> <%= cur.X_SWIFI_ADRES2 %> </td>
							<td>  </td>
							<td> <%= cur.X_SWIFI_INSTL_FLOOR %> </td>
							<td> <%= cur.X_SWIFI_INSTL_TY %> </td>
							<td> <%= cur.X_SWIFI_INSTL_MBY %> </td>
							<td> <%= cur.X_SWIFI_SVC_SE %> </td>
							<td> <%= cur.X_SWIFI_CMCWR %> </td>
							<td> <%= cur.X_SWIFI_CNSTC_YEAR %> </td>
							<td> <%= cur.X_SWIFI_INOUT_DOOR %> </td>
							<td> <%= cur.X_SWIFI_REMARS3 %> </td>
							<td> <%= cur.LAT %> </td>
							<td> <%= cur.LNT %> </td>
							<td> <%= cur.WORK_DTTM %> </td>
							
						
						</tr>
					<%} %>


                </tbody>
            </table>
            <% if(LAT==null || LNT == null){ %>
	            <div class="before">
	                <span>위치 정보 입력 후 검색하세요</span>
	            </div>
            <% } else{ %> 
            <% } %> 
            
            
            <% if(isEmpty){ %>
	            <div>
	                <span>히스토리가 없습니다.</span>
	            </div>
            <%} %>
            
            
            
        </div>
        <script>
            const button = document.querySelector("#location");
            const form = document.querySelector("#form");
            const LatInput = document.querySelector("#LAT");
            const LntInput = document.querySelector("#LNT");
            function onGeoOK(position){
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;
                LatInput.value = lat;
                LntInput.value = lon;
            }

            function onnGeoError(){
                alert("Can't find your Location")
            }
            
            
            function getLocation(event){
                event.preventDefault();
                navigator.geolocation.getCurrentPosition(onGeoOK,onnGeoError);
                

            }
            function handleSubmit(event){
            	event.preventDefault();
            }
            button.addEventListener("click",getLocation);
            
            
            //form.addEventListener("submit",handleSubmit)
        </script>

    </body>
</body>
</html>