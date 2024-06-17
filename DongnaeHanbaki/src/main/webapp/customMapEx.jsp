<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네한바퀴</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#sendDataButton").click(function() {
                var mapData = {
                    "markers": [
                        {"id": 0, "path": {"La": 126.77333126632949, "Ma": 37.65842137448871}, "info": "1122"},
                        {"id": 1, "path": {"La": 126.77362271131453, "Ma": 37.658545818915314}, "info": "1123"},
                        {"id": 2, "path": {"La": 126.77338010193198, "Ma": 37.65820072819518}, "info": "1124"}
                    ],
                    "lines": [
                        {"path": [{"La": 126.77354851990991, "Ma": 37.657786600760595}, {"La": 126.77308173775924, "Ma": 37.65848846824803}], "style": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1}},
                        {"path": [{"La": 126.77360355590741, "Ma": 37.65832278958293}, {"La": 126.77303753079272, "Ma": 37.65811898136258}], "style": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1}}
                    ],
                    "rects": [
                        {"type": "rectangle", "sPoint": {"x": 126.7734094257516, "y": 37.65880669362166}, "ePoint": {"x": 126.77357915274462, "y": 37.65889486507901}, "coordinate": "wgs84", "options": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1, "fillColor": "#39f", "fillOpacity": 0.5}},
                        {"type": "rectangle", "sPoint": {"x": 126.7735733015792, "y": 37.65895566999815}, "ePoint": {"x": 126.77359867073687, "y": 37.65899851524164}, "coordinate": "wgs84", "options": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1, "fillColor": "#39f", "fillOpacity": 0.5}}
                    ],
                    "circles": [
                        {"type": "circle", "sPoint": {"x": 126.77354118333105, "y": 37.657740097563774}, "ePoint": {"x": 126.77410441760894, "y": 37.65819004288002}, "center": {"x": 126.77382279962025, "y": 37.65796507055961}, "coordinate": "wgs84", "options": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1, "fillColor": "#39f", "fillOpacity": 0.5}, "radius": 24.909837414746484},
                        {"type": "circle", "sPoint": {"x": 126.77364115897556, "y": 37.658445403051374}, "ePoint": {"x": 126.77392716912496, "y": 37.65867388346759}, "center": {"x": 126.77378416383114, "y": 37.658559643346564}, "coordinate": "wgs84", "options": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1, "fillColor": "#39f", "fillOpacity": 0.5}, "radius": 12.649110641358256}
                    ],
                    "polys": [
                        {"path": [{"La": 126.77304057794413, "Ma": 37.65897942317726}, {"La": 126.77314566811654, "Ma": 37.65889403222691}, {"La": 126.77303517203775, "Ma": 37.65889381967651}, {"La": 126.77312566413075, "Ma": 37.658950305004794}], "style": {"strokeColor": "#39f", "strokeWeight": 3, "strokeStyle": "solid", "strokeOpacity": 1, "fillColor": "#39f", "fillOpacity": 0.5}}
                    ],
                    "center": "(126.773395756933, 37.6583633851777)",
                    "level": "1",
                    "title": "맵의 제목",
                    "content": "맵의 내용"
                };

                $.ajax({
                    url: '/dongnae/submitMapData',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(mapData),
                    success: function(response) {
                    	console.log(mapData);
                        alert(response);
                    },
                    error: function(error) {
                        console.error('Error:', error);
                    }
                });
            });
        });
    </script>
</head>
<body>
    <h2>Send Map Data</h2>
    <button id="sendDataButton">Send Data</button>
</body>
</html>