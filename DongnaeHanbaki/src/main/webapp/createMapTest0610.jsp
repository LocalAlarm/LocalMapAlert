<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네한바퀴</title>
<jsp:include page="/WEB-INF/patials/commonHead.jsp"></jsp:include>
<style>
.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:800px;}
#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:280px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#fff;}
#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}

#markerPopup {
       position: fixed;
       top: 50%;
       left: 50%;
       transform: translate(-50%, -50%);
       padding: 20px;
       background: white;
       border: 1px solid #ccc;
       box-shadow: 0 0 10px rgba(0,0,0,0.5);
       z-index: 1000;
   }
</style>
</head>


<body id="body-pd">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<jsp:include page="/WEB-INF/patials/commonBody.jsp"></jsp:include>

<hr>
<!-- 페이지 위 -->
<div class="p-3 text-center">
  <h1>동네한바퀴</h1>
</div>
<hr>
<!-- 네비바 -->
<nav class="navbar navbar-expand-lg">
  <div class="container-fluid">
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-link" aria-current="page" href="#">Home</a>
        <a class="nav-link" href="#">커스텀맵</a>
        <a class="nav-link" href="#">Pricing</a>
        <a class="nav-link disabled" aria-disabled="true">Disabled</a>
      </div>
    </div>
    <form class="d-flex" role="search">
      <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success" type="submit">Search</button>
    </form>
  </div>
</nav>
<hr>

<!-- 커스텀맵 조회 -->
<div class="container-fluid">
<div class="row gy-2">
	  <!-- 만들어진 지도 표시 -->
      <div class="col-6 border" style="height: 800px;">      
      	<!-- 지도를 표시할 div 입니다 -->
		<div class="col-auto" style="height: 100%;">
		<div class="map_wrap p-2">
		    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
			<!-- 마커 표시는 지우고 리스트에 선택하면 마커이동(중앙이동) 마커컨트로롤러 -->
		    <div id="menu_wrap" class="bg_white" style="display: none;">
		        <ul id="placesList">
		        </ul>
		        <div id="pagination"></div>
		    </div>
		</div>
		</div>
      </div>
      
	  <div class="col-6 border overflow-y-scroll" style="height: 800px;">
	  
	  <!-- 커스텀맵 만들기 -->
        <div class="text-center p-1"><h3>나의 커스텀맵</h3></div>
        
		<!-- 지도 만들기 폼 -->
        <div class="row g-1 p-2 mb-3 border rounded">
        <div class="d-grid gap-2 py-2">
		  <button class="btn btn-outline-dark py-3" type="button" data-bs-toggle="collapse" 
		  data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">커스텀맵 설정하기</button>
		</div>
		<form class="collapse show" id="collapseExample">
			<div class="input-group mb-3  py-2">
			   <form onsubmit="searchPlaces(); return false;">
			  	<input type="text" class="form-control" id="keyword" placeholder="중심 주소를 입력해 주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
			  	<button class="btn btn-outline-secondary" onclick="openMap()" type="button" id="button-addon2">검색</button>
			   </form>
			</div>
			<span class="card" id="address"></span>
			<br>
			<span class="card" id="coords"></span>
			<br>
			<label for="customRange3" class="form-label">지도 크기</label>
			<input type="range" class="form-range" min="0" max="14" id="customRange3">
			<div class="form-floating py-2">
			 <div class="form-group">
		        <label for="title">제목</label>
		        <input type="text" class="form-control" id="title" placeholder="제목을 입력해주세요">
		    </div>
			</div>
			<div class="form-floating py-2">
				 <div class="form-group">
				  <label for="content">내용</label>
				  <input type="text" class="form-control" id="content" placeholder="설명을 입력해주세요">
				</div>
			</div>
			<!-- 저장/초기화 버튼 -->
	 <button type="button" class="btn btn-outline-primary" onclick="saveMap(1)">지도 생성</button>
	 <button type="button" class="btn btn-outline-danger">취소</button>
	 <button type="button" class="btn btn-outline-danger">마커 지우기</button>
		</form>
		</div>
		
		
		
        <div class="row g-1 p-2 mb-3 border rounded">
        <div class="d-grid gap-2 py-2">
		  <button class="btn btn-outline-dark py-3" type="button" data-bs-toggle="collapse" 
		  data-bs-target="#collapseExample2" aria-expanded="false" aria-controls="collapseExample2">마커 설정하기</button>
		</div>
		<div class="collapse" id="collapseExample2">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
			  <li class="nav-item" role="presentation">
			    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">기본 마커</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane" type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">커스텀 마커</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact-tab-pane" type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false">표시된 마커 보기</button>
			</ul>
			<div class="tab-content" id="myTabContent">
			  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0" style="background-color: white;">
				  <!-- 여기다 tool박스나 드로잉 라이브러리 연결 -->...
				  <p>
				    <button onclick="selectOverlay('MARKER')">마커</button>
				    <button onclick="selectOverlay('POLYLINE')">선</button>
				    <button onclick="selectOverlay('CIRCLE')">원</button>
				    <button onclick="selectOverlay('RECTANGLE')">사각형</button>
				    <button onclick="selectOverlay('POLYGON')">다각형</button>
				  </p>
			  </div>
			  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0" style="background-color: white;">...</div>
			  <div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0" style="background-color: white;">...</div>
			</div>
		</div>
		</div>
	  
	 
	</div>
</div>
</div>
<script>
	//마커를 담을 배열입니다
	var markers = [];
	//주소찾기
	var mapContainer, mapOption, map, geocoder, marker;
	// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
	var infowindow;
	
	//커스텀 마커들 리스트 
	var markerList = []; 
	var lineList = [];
	var rectList = [];
	var ployList = [];
	var circleList = [];
	
	//마커 인포 리스트
	var markerInfoList = [];
	//마커 고유 아이디
	var markerIdCounter = 0; 
	//마커 현재 정보 
	var currentInfo = null;
	
	mapContainer = document.getElementById('map'); // 지도를 표시할 div 
	
	mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};
	
	infowindow = new kakao.maps.InfoWindow({
		zIndex : 1
	});
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다;
	// Drawing Manager를 생성할 때 사용할 옵션입니다
	var options = { // Drawing Manager를 생성할 때 사용할 옵션입니다
		    map: map, // Drawing Manager로 그리기 요소를 그릴 map 객체입니다
		    drawingMode: [ // drawing manager로 제공할 그리기 요소 모드입니다
		        kakao.maps.drawing.OverlayType.MARKER,
		        kakao.maps.drawing.OverlayType.POLYLINE,
		        kakao.maps.drawing.OverlayType.RECTANGLE,
		        kakao.maps.drawing.OverlayType.CIRCLE,
		        kakao.maps.drawing.OverlayType.POLYGON
		    ],
		    // 사용자에게 제공할 그리기 가이드 툴팁입니다
		    // 사용자에게 도형을 그릴때, 드래그할때, 수정할때 가이드 툴팁을 표시하도록 설정합니다
		    guideTooltip: ['draw', 'drag', 'edit'], 
		    markerOptions: { // 마커 옵션입니다 
		        draggable: true, // 마커를 그리고 나서 드래그 가능하게 합니다 
		        removable: false // 마커를 삭제 할 수 있도록 x 버튼이 표시됩니다  
		    },
		    polylineOptions: { // 선 옵션입니다
		        draggable: true, // 그린 후 드래그가 가능하도록 설정합니다
		        removable: true, // 그린 후 삭제 할 수 있도록 x 버튼이 표시됩니다
		        editable: true, // 그린 후 수정할 수 있도록 설정합니다 
		        strokeColor: '#39f', // 선 색
		        hintStrokeStyle: 'dash', // 그리중 마우스를 따라다니는 보조선의 선 스타일
		        hintStrokeOpacity: 0.5  // 그리중 마우스를 따라다니는 보조선의 투명도
		    },
		    rectangleOptions: {
		        draggable: true,
		        removable: true,
		        editable: true,
		        strokeColor: '#39f', // 외곽선 색
		        fillColor: '#39f', // 채우기 색
		        fillOpacity: 0.5 // 채우기색 투명도
		    },
		    circleOptions: {
		        draggable: true,
		        removable: true,
		        editable: true,
		        strokeColor: '#39f',
		        fillColor: '#39f',
		        fillOpacity: 0.5
		    },
		    polygonOptions: {
		        draggable: true,
		        removable: true,
		        editable: true,
		        strokeColor: '#39f',
		        fillColor: '#39f',
		        fillOpacity: 0.5,
		        hintStrokeStyle: 'dash',
		        hintStrokeOpacity: 0.5
		    }
	};

	var manager = new kakao.maps.drawing.DrawingManager(options);
// 	var info = new daum.maps.CustomOverlay({
// 	    xAnchor: 0.2,
// 	    yAnchor: 1.5,
// 	    content: '<div style="padding:10px; background-color:white; border:1px solid #ccc; border-radius:5px; width:200px;">' +
//         '<h4 style="margin:0; padding:0 0 10px 0; border-bottom:1px solid #ccc;">Marker Info</h4>' +
//         '<button onclick="alert(\'Button clicked!\')">내용쓰기</button>' +
//         '</div>'
// 	});
	
	manager.addListener('drawend', function (e) {
	    if (e.overlayType === daum.maps.drawing.OverlayType.MARKER) {
	        var marker = e.target;
	        var markerId = markerIdCounter++; //마커 아이디 생성
	        
// 	        markerInfoList = [];
	        //마커마다 info 유지
	        var info = new daum.maps.CustomOverlay({
	    	    xAnchor: 0.2,
	    	    yAnchor: 1.5,
	    	    content: '<div id="' + markerId + '" style="padding:10px; background-color:white; border:1px solid #ccc; border-radius:5px; width:200px;">' +
	            '<h4 style="margin:0; padding:0 0 10px 0; border-bottom:1px solid #ccc;">Marker Info</h4>' +
	            '<p id="marker-info-"' + markerId + '>123</p>' +
	            '<button onclick="markerContent(\'' + markerId + '\')">내용쓰기</button>' +
	            '</div>'
	    	});
	    	 // 여기서 <p> 태그의 내용을 가져옵니다.
	        var markerInfoElement = document.getElementById('marker-info-' + markerId);
	        if (markerInfoElement) {
	            var markerInfoContent = markerInfoElement.innerHTML; // 또는 .textContent
	            console.log("Marker Info Content:", markerInfoContent);
	        }
	        console.log("마커인포값!");
	        markerInfoList.push({
	        	id: markerId,
	        	path: marker.getPosition(),
	        	info: "1123"
	        });
	        console.log(markerInfoList);
// 	        daum.maps.event.addListener(marker, 'mouseout', function () {
// 	            info.setMap(null);
// 	        });
// 	        daum.maps.event.addListener(marker, 'mousedown', function () {
// 	            info.setMap(null);
// 	        });
// 	        daum.maps.event.addListener(marker, 'mouseup', function () {
// 	            info.setMap(map);
// 	            info.setPosition(marker.getPosition());
// 	        });
			//클릭시 info닫기
	        daum.maps.event.addListener(marker, 'click', function () {
	        	info.setMap(null);
	        });
	     	// 마커가 제거될 때 info도 제거되도록 설정
	        kakao.maps.event.addListener(marker, 'rightclick', function () {
	            // 마커를 우클릭하면 해당 마커와 info를 제거
	            marker.setMap(null); // 마커를 지도에서 제거
	            info.setMap(null);   // info를 지도에서 제거
	            console.log(marker._index);
	            markerInfoList = markerInfoList.filter(function(item) {
	                return item.id !== marker._index;
	            });
	            console.log("마커제거!");
	            console.log(markerInfoList);
	        });
	    }
	});
	function openMap() {
		console.log(map.getLevel());
		geocoder = new daum.maps.services.Geocoder();
		ps = new kakao.maps.services.Places(); // 장소 검색 객체를 생성합니다
		marker = new daum.maps.Marker({
			position : new daum.maps.LatLng(37.537187, 127.005476),
			map : map
		});
		document.getElementById('menu_wrap').style.display = "block";		
		
		// 키워드로 장소를 검색합니다
		searchPlaces();

		// 지도 크기 조절
		var rangeInput = document.getElementById('customRange3');
		rangeInput.addEventListener('input', function() {
			const value = this.value;
			console.log('Range 값:', value);
			map.setLevel(value);
		});
	
		
	}
	
	// 버튼 클릭 시 호출되는 핸들러 입니다
	function selectOverlay(type) {
// 		console.log(manager);
	    // 그리기 중이면 그리기를 취소합니다
	    manager.cancel();

	    // 클릭한 그리기 요소 타입을 선택합니다
	    manager.select(kakao.maps.drawing.OverlayType[type]);
	    
// 	    overlays = []; // 지도에 그려진 도형을 담을 배열
	    
	    // Drawing Manager에서 그려진 데이터 정보를 가져옵니다 
	    var data = manager.getData();
	    // 지도에 그려진 도형이 있다면 모두 지웁니다
// 	    removeOverlays();
	    
	    
	    // 지도에 가져온 데이터로 도형들을 그립니다 -> 데이터 뽑는 역할
	    drawMarker(data[kakao.maps.drawing.OverlayType.MARKER]);
	    drawPolyline(data[kakao.maps.drawing.OverlayType.POLYLINE]);
	    drawRectangle(data[kakao.maps.drawing.OverlayType.RECTANGLE]);
	    drawCircle(data[kakao.maps.drawing.OverlayType.CIRCLE]);
	    drawPolygon(data[kakao.maps.drawing.OverlayType.POLYGON]);
	    
	    saveMap();
	}
	// Drawing Manager에서 가져온 데이터 중 마커를 아래 지도에 표시하는 함수입니다
	function drawMarker(markers) {
	    var len = markers.length, i = 0;
	    markerList = []; // 마커값을 담을 배열 초기화
	    for (; i < len; i++) {
// 	        var marker = new kakao.maps.Marker({
// 	            map: map, 
// 	            position: new kakao.maps.LatLng(markers[i].y, markers[i].x), 
// 	            zIndex: markers[i].zIndex
// 	        });
		var markerPosition = new kakao.maps.LatLng(markers[i].y, markers[i].x);
        markerList.push(markerPosition);
// 	        overlays.push(marker);
	    }
	    console.log(markerList); // 마커 좌표값 리스트 출력
	    
	}
	
	// Drawing Manager에서 가져온 데이터 중 선을 아래 지도에 표시하는 함수입니다
	function drawPolyline(lines) {
	    var len = lines.length, i = 0;
 		lineList = []; //선 초기화
	    for (; i < len; i++) {
	        var path = pointsToPath(lines[i].points);
	        var style = lines[i].options;
// 	        var polyline = new kakao.maps.Polyline({
// 	            map: map,
// 	            path: path,
// 	            strokeColor: style.strokeColor,
// 	            strokeOpacity: style.strokeOpacity,
// 	            strokeStyle: style.strokeStyle,
// 	            strokeWeight: style.strokeWeight
// 	        });

// 	        overlays.push(polyline);
	        lineList.push({
	            path: path,
	            style: style
	        });
	    }
	    console.log(lineList);
	}

	// Drawing Manager에서 가져온 데이터 중 사각형을 아래 지도에 표시하는 함수입니다
	function drawRectangle(rects) {
	    var len = rects.length, i = 0;
		rectList = []; //사각형 초기화
	    for (; i < len; i++) {
// 	        var style = rects[i].options;
// 	        var rect = new kakao.maps.Rectangle({
// 	            map: map, 
// 	            bounds: new kakao.maps.LatLngBounds(
// 	                new kakao.maps.LatLng(rects[i].sPoint.y, rects[i].sPoint.x),
// 	                new kakao.maps.LatLng(rects[i].ePoint.y, rects[i].ePoint.x)
// 	            ), 
// 	            strokeColor: style.strokeColor,
// 	            strokeOpacity: style.strokeOpacity,
// 	            strokeStyle: style.strokeStyle,
// 	            strokeWeight: style.strokeWeight,
// 	            fillColor: style.fillColor,
// 	            fillOpacity: style.fillOpacity
// 	        });

// 	        overlays.push(rect);
			rectList.push(rects[i]);
	    }
	    console.log(rectList);
	}

	// Drawing Manager에서 가져온 데이터 중 원을 아래 지도에 표시하는 함수입니다
	function drawCircle(circles) {
	    var len = circles.length, i = 0;
		circleList = []; //원 초기화
	    for (; i < len; i++) {
// 	        var style = circles[i].options;
// 	        var circle = new kakao.maps.Circle({
// 	            map: map, 
// 	            center: new kakao.maps.LatLng(circles[i].center.y, circles[i].center.x), 
// 	            radius: circles[i].radius,
// 	            strokeColor: style.strokeColor,
// 	            strokeOpacity: style.strokeOpacity,
// 	            strokeStyle: style.strokeStyle,
// 	            strokeWeight: style.strokeWeight,
// 	            fillColor: style.fillColor,
// 	            fillOpacity: style.fillOpacity
// 	        });

// 	        overlays.push(circle);
			circleList.push(circles[i]);
	    }
	    console.log(circleList);
	}

	// Drawing Manager에서 가져온 데이터 중 다각형을 아래 지도에 표시하는 함수입니다
	function drawPolygon(polygons) {
	    var len = polygons.length, i = 0;
		ployList = []; //다각형 초기화
	    for (; i < len; i++) {
	        var path = pointsToPath(polygons[i].points);
	        var style = polygons[i].options;
// 	        var polygon = new kakao.maps.Polygon({
// 	            map: map,
// 	            path: path,
// 	            strokeColor: style.strokeColor,
// 	            strokeOpacity: style.strokeOpacity,
// 	            strokeStyle: style.strokeStyle,
// 	            strokeWeight: style.strokeWeight,
// 	            fillColor: style.fillColor,
// 	            fillOpacity: style.fillOpacity
// 	        });

// 	        overlays.push(polygon);
			ployList.push({
	            path: path,
	            style: style
	        });
	    }
	    console.log(ployList);
	}

	// Drawing Manager에서 가져온 데이터 중 
	// 선과 다각형의 꼭지점 정보를 kakao.maps.LatLng객체로 생성하고 배열로 반환하는 함수입니다 
	function pointsToPath(points) {
	    var len = points.length, 
	        path = [], 
	        i = 0;

	    for (; i < len; i++) { 
	        var latlng = new kakao.maps.LatLng(points[i].y, points[i].x);
	        path.push(latlng); 
	    }

	    return path;
	} 
	
	//지도에 그려진 도형이 있다면 모두 지웁니다
	function removeOverlays() {
	    var len = overlays.length, i = 0;

	    for (; i < len; i++) {
	        overlays[i].setMap(null);
	    }

	    overlays = [];
	}
	
	//키워드 검색을 요청하는 함수입니다
	function searchPlaces() {

		var keyword = document.getElementById('keyword').value;

		if (!keyword.replace(/^\s+|\s+$/g, '')) {
			alert('키워드를 입력해주세요!');
			return false;
		}

		// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
		ps.keywordSearch(keyword, placesSearchCB);
	}

	// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
	function placesSearchCB(data, status, pagination) {
		if (status === kakao.maps.services.Status.OK) {

			// 정상적으로 검색이 완료됐으면
			// 검색 목록과 마커를 표출합니다
			displayPlaces(data);

			// 페이지 번호를 표출합니다
			displayPagination(pagination);

		} else if (status === kakao.maps.services.Status.ZERO_RESULT) {

			alert('검색 결과가 존재하지 않습니다.');
			return;

		} else if (status === kakao.maps.services.Status.ERROR) {

			alert('검색 결과 중 오류가 발생했습니다.');
			return;

		}
	}

	//검색 결과 목록과 마커를 표출하는 함수입니다
	function displayPlaces(places) {

		var listEl = document.getElementById('placesList'), menuEl = document
				.getElementById('menu_wrap'), fragment = document
				.createDocumentFragment(), bounds = new kakao.maps.LatLngBounds(), listStr = '';
		
		// 검색 결과 목록에 추가된 항목들을 제거합니다
		removeAllChildNods(listEl);

		// 지도에 표시되고 있는 마커를 제거합니다
		removeMarker();
		
		var newListItem = document.createElement('li');
		// li 요소 내부에 추가할 내용 설정
		newListItem.innerHTML = '<ion-icon id="closePlaceList" name="close-outline" style="font-size: 24px; cursor: pointer;" onclick="closeMap()" ></ion-icon>';
		// placesList에 새로 생성한 li 요소 추가
		document.getElementById("placesList").appendChild(newListItem);
		
		for (var i = 0; i < places.length; i++) {

			// 마커를 생성하고 지도에 표시합니다 -> 장소들의 위치값 저장
			var placePosition = new kakao.maps.LatLng(places[i].y,
					places[i].x), marker = addMarker(placePosition, i), itemEl = getListItem(
					i, places[i], placePosition); // 검색 결과 항목 Element를 생성합니다

			console.log("장소:::");
			console.log(placePosition);
			// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			// LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(placePosition);

			// 마커와 검색결과 항목에 mouseover 했을때
			// 해당 장소에 인포윈도우에 장소명을 표시합니다
			// mouseout 했을 때는 인포윈도우를 닫습니다
			(function(marker, title) {
				kakao.maps.event.addListener(marker, 'mouseover',
						function() {
							displayInfowindow(marker, title);
						});

				kakao.maps.event.addListener(marker, 'mouseout',
						function() {
							infowindow.close();
						});

				//             itemEl.onmouseover =  function () {
				//                 displayInfowindow(marker, title);
				//             };

				itemEl.onmouseout = function() {
					infowindow.close();
				};

				// 마커 클릭 이벤트 등록
				kakao.maps.event.addListener(marker, 'click', function() {
					displayInfowindow(marker, title);
					map.setCenter(marker.getPosition());
				});

				itemEl.onclick = function() {
					displayInfowindow(marker, title);
					map.setCenter(marker.getPosition());
				};

			})(marker, places[i].place_name);
			fragment.appendChild(itemEl);
		}
		// 검색결과 항목들을 검색결과 목록 Element에 추가합니다
		listEl.appendChild(fragment);
		menuEl.scrollTop = 0;

		// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		map.setBounds(bounds);
	}

	//검색결과 항목을 Element로 반환하는 함수입니다
	function getListItem(index, places, placePosition) {
		console.log("장소 엘리먼트!!");
		console.log(places);
		var el = document.createElement('li'), itemStr = '<span class="markerbg marker_'
				+ (index + 1)
				+ '"></span>'
				+ '<div class="info">'
				+ '   <h5>' + places.place_name + '</h5>';
// 				+ '   <span class="coords">(' + places.x + ', ' + places.y + ')</span>';

		if (places.road_address_name) {
			itemStr += '    <span>' + places.road_address_name + '</span>'
					+ '   <span class="jibun gray">' + places.address_name
					+ '</span>';
		} else {
			itemStr += '    <span>' + places.address_name + '</span>';
		}

		itemStr += '  <span class="tel">' + places.phone + '</span>'
				+ '</div>';

		el.innerHTML = itemStr;
		el.className = 'item';

		// 'info' div를 클릭할 때마다 마커의 위치를 업데이트합니다
		el.querySelector('.info').addEventListener('click', function() {
							// 기존 마커의 위치를 업데이트합니다
							marker.setPosition(placePosition);
							// 지도의 중심을 새로운 위치로 설정합니다
							map.setCenter(placePosition);
							// 주소업데이트
							document.getElementById('address').textContent = places.address_name;
							 // 좌표 업데이트
					        document.getElementById('coords').textContent = '(' + places.x + ', ' + places.y + ')';
						});

		return el;
	}

	// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
	function addMarker(position, idx, title) {
// 		console.log("dddddddddddddd : " + position);
		var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
		imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
		imgOptions = {
			spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
			spriteOrigin : new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
			offset : new kakao.maps.Point(13, 37)
		// 마커 좌표에 일치시킬 이미지 내에서의 좌표
		}, markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize,
				imgOptions), marker = new kakao.maps.Marker({
			position : position, // 마커의 위치
			image : markerImage
		});

		markers.push(marker); // 배열에 생성된 마커를 추가합니다

		return marker;
	}

	// 지도 위에 표시되고 있는 마커를 모두 제거합니다
	function removeMarker() {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = [];
		marker.setMap(null);
	}

	// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
	function displayPagination(pagination) {
		var paginationEl = document.getElementById('pagination'), fragment = document
				.createDocumentFragment(), i;

		// 기존에 추가된 페이지번호를 삭제합니다
		while (paginationEl.hasChildNodes()) {
			paginationEl.removeChild(paginationEl.lastChild);
		}

		for (i = 1; i <= pagination.last; i++) {
			var el = document.createElement('a');
			el.href = "#";
			el.innerHTML = i;

			if (i === pagination.current) {
				el.className = 'on';
			} else {
				el.onclick = (function(i) {
					return function() {
						pagination.gotoPage(i);
					}
				})(i);
			}

			fragment.appendChild(el);
		}
		paginationEl.appendChild(fragment);
	}

	// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
	// 인포윈도우에 장소명을 표시합니다
	function displayInfowindow(marker, title) {
		var content = '<div style="padding:5px;z-index:1;">' + title
				+ '</div>';
		infowindow.setContent(content);
		infowindow.open(map, marker);
	}

	// 검색결과 목록의 자식 Element를 제거하는 함수입니다
	function removeAllChildNods(el) {
		while (el.hasChildNodes()) {
			el.removeChild(el.lastChild);
		}
	}
	
	function closeMap() {
		removeMarker();
		document.getElementById("menu_wrap").style.display = "none";
	}
	
	function saveMap(check) {
		console.log("지도생성!!!");
		markerList = markerInfoList; // 중복이긴함 두개가 일단 markerlist에 복사 추후 markerinfolist로 받을예쩡 (선, 원 등 다른것도 적용예정)
		console.log(markerList); // 예시로 콘솔에 출력
		console.log(rectList);
		var center = document.getElementById("coords");
		rangeInput = document.getElementById('customRange3');
		var title = document.getElementById("title");
		var content = document.getElementById("content");
		console.log('지도생성 Range 값:', rangeInput.value);
		console.log(center.textContent);
		console.log(title.value);
		console.log(content.value);
		
		if (check == "1") {
			console.log("커스텀 맵 저장!!!");
			$.ajax({
				type: 'POST',
                url: 'saveMap',
                data: JSON.stringify({markers: markerList, lines: lineList
                					, rects: rectList, circles: circleList
                					, polys: ployList
                					, center: center.textContent
                					, level: rangeInput.value
                					, title: title.value
                					, content: content.value}),
                contentType: 'application/json; charset=UTF-8', 
                success: function(response) {
                    console.log('Data saved successfully:', response);
                },
                error: function(error) {
                    console.error('Error saving data:', error);
                }
			});
		}
	}
	
	
	//마커 내용 쓰기
	function markerContent(markerId) {
		alert("임건희 시발아 코딩좀해 미친년아 너는 최종ppt당첨이다 이색기야");
// 		alert("내용쓰기!!ㅋㅋㅎㅎ");
		// 현재 클릭된 마커의 info 객체를 저장
		console.log("마커받았음!");
		console.log(markerId);
		console.log(markerInfoList);
	    currentInfo = markerInfoList.find(function(item) {
	        return item.id == markerId;
	    });
    	console.log("현재 마커!!!");
        console.log(currentInfo);
        if (currentInfo) {
            // 팝업을 표시
            document.getElementById('markerPopup').style.display = 'block';
        } else {
            console.log("해당하는 마커를 찾을 수 없습니다.");
        }
	}
	// 팝업 창 닫기
	function closePopup() {
	    document.getElementById('markerPopup').style.display = 'none';
	}

	
	// 팝업 창에서 내용을 저장
	function saveMarkerContent() {
	    var content = document.getElementById('markerInfoDetail').value;
	    console.log(content);
	    if (currentInfo) {
	    	console.log("save!!!!!!!!!");
	        // 기존 info content 업데이트
	        currentInfo.setContent('<div style="padding:10px; background-color:white; border:1px solid #ccc; border-radius:5px; width:200px;">' +
	                               '<h4 style="margin:0; padding:0 0 10px 0; border-bottom:1px solid #ccc;">Marker Info</h4>' +
	                               '<p>' + content + '</p>' +
	                               '<button onclick="markerContent(this)">내용쓰기</button>' +
	                               '</div>');
	        content: '<div id="' + markerId + '" style="padding:10px; background-color:white; border:1px solid #ccc; border-radius:5px; width:200px;">' +
            '<h4 style="margin:0; padding:0 0 10px 0; border-bottom:1px solid #ccc;">Marker Info</h4>' +
            '<p id="marker-info-"' + markerId + '></p>' +
            '<button onclick="markerContent(this)">내용쓰기</button>' +
            '</div>'
	        console.log(currentInfo);
	    }
	    closePopup();
	}
	
	
	
</script>
<div id="markerPopup" style="display: none;">
    <h1>마커 내용 쓰기</h1>
    <textarea id="markerInfoDetail" rows="4" cols="50"></textarea>
    <br/>
    <button onclick="saveMarkerContent()">저장</button>
    <button onclick="closePopup()">닫기</button>
</div>

</body>
</html>