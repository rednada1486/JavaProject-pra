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
<title>HISTORYDETAIL</title>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<style>
            #map {
                height: 100%;
            }

            html,
            body {
                height: 100%;
                margin: 0;
                padding: 0;
            }
</style>

</head>
<body>
		<%
			HandleSQLite handleSqlite = new HandleSQLite();
			handleSqlite.createHistoryTable();
			handleSqlite.createHistoryDetailTable();
			List<Wifi> result = new ArrayList<Wifi>();
			History myLocation = new History();

			
			boolean isEmpty = result.size()==0 ? true : false;
		
			String HistoryID = request.getParameter("HistoryID");
			
			if(HistoryID!=null){
		        try {
			        Integer.parseInt(HistoryID);
			        result = handleSqlite.getAllHistoryDetail(Integer.parseInt(HistoryID));
			        myLocation = handleSqlite.getOneHistory(Integer.parseInt(HistoryID));
			        System.out.println(result);
		        }catch(NumberFormatException e) {
		            System.out.println("It is not numerical string");
		        }
			}

		%>
		<div id="map"></div>
        <script>
            let labelIndex = 0;
            const locations = [
            	<% for(int i=0; i<result.size(); i++ ){ 
            		Wifi cur = result.get(i);
            	%>
            		{ place: "<%=cur.X_SWIFI_MAIN_NM %>" , lat: <%=cur.LAT %> , lng: <%=cur.LNT %>, distance: <%=cur.DISTANCE %> },	
                <% } %>
            ];
            

            function initMap() {
                const myLatLng = { lat: <%=myLocation.LAT %>, lng: <%=myLocation.LNT %>};
                const map = new google.maps.Map(
                    document.getElementById("map"),
                    {
                        zoom: 17,
                        center: myLatLng,
                    }
                );
                const svgMarker = {
                    path: "M10.453 14.016l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM12 2.016q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z",
                    fillColor: "blue",
                    fillOpacity: 0.8,
                    strokeWeight: 0,
                    rotation: 0,
                    scale: 3,
                    anchor: new google.maps.Point(15, 30),
                };

                const my = new google.maps.Marker({
                    position: myLatLng,
                    icon: svgMarker,
                    map,
                    title: "내 위치",
                    label: "내 위치",
                });
                
                const infoWindow = new google.maps.InfoWindow();

                my.addListener("mouseover", () => {
                    infoWindow.close();
                    infoWindow.setContent(marker.getTitle());
                    infoWindow.open(marker.getMap(), marker);
                });
                my.addListener("mouseout", () => {
                    infoWindow.close();
                    //infoWindow.setContent(marker.getTitle());
                    //infoWindow.open(marker.getMap(), marker);
                });


                
                
                for(i=0; i<locations.length; i++){
                	const marker = new google.maps.Marker({
                        map: map,
                        label: "wifi",
                        title: String(i+1)+". "+locations[i].place + " "+locations[i].distance+ "km 떨어져 있음",
                        position: new google.maps.LatLng(locations[i].lat, locations[i].lng),
                        optimized:false
                    });
                    marker.addListener("mouseover", () => {
                        infoWindow.close();
                        infoWindow.setContent(marker.getTitle());
                        infoWindow.open(marker.getMap(), marker);
                    });
                    marker.addListener("mouseout", () => {
                        infoWindow.close();
                    });
                }

            }

            window.initMap = initMap;
        </script>

        <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCAWxc7NIk3WtQLBOyPwt4rRsFru6LaWo&callback=initMap&v=weekly"
            defer
        ></script>
</body>
</html>