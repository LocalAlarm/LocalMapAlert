<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/patials/commonHead.jsp"></jsp:include>
 <style>
    /* 활성화된 탭의 배경색을 하얀색으로 변경 */
    .active {
      background-color: white !important;
    }
  </style>
<script>
// // 모델에서 가져온 JSON 문자열을 JavaScript 변수에 저장
// var customMarkerJson = '${customMarker}';

// // JSON 문자열을 JavaScript 객체로 변환
// var customMarker = JSON.parse(customMarkerJson);

// console.log(customMarker);
	function updateMap(frm){
		alert("게시글을 수정합니다");
	}
</script>
<!-- 
커스텀 맵 VO : mapVO
마커타입 종류 : markerTypeList
마커리스트 : markerList 
-->
</head>
<body>
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
        <a class="nav-link active" aria-current="page" href="#">Home</a>
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


	<div class="container">
		<div class="row p-3 text-center">
			<h3>커스텀 맵 편집 : 커스텀맵 1</h3>
		</div>
		<!-- 커스텀 맵 수정 창 -->
		<div class="row gy-2">
			<!-- 만들어진 지도 표시 -->
			<div class="col-6 border" style="height: 500px;">
				<!-- 지도를 표시할 div 입니다 -->
				<div id="map" style="width:100%;height:100%;"></div>
			</div>

			<!-- 커스텀맵 설명칸 -->
			<div class="col-6 border overflow-y-scroll p-0" style="height: 500px;">
			
			<ul class="nav nav-tabs" id="myTab" role="tablist">
			  <li class="nav-item" role="presentation">
			    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">맵 설명</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane" type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">마커 종류</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact-tab-pane" type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false" onclick="showMarkerList()">표시된 마커 보기</button>
			</ul>
			<div class="tab-content p-2" id="myTabContent">
			  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			    <!-- 맵 설정 편집 -->
			    <form class="p-2">
				  <div class="mb-3">
				    <label for="title" class="form-label">제목</label>
				    <input type="text" class="form-control" id="title">
				  </div>
				  <div class="mb-3">
				    <label for="center" class="form-label">중심좌표</label>
				    <input type="text" class="form-control" id="center">
				  </div>
				  <div class="mb-3">
				    <label for="viewLevel" class="form-label">확대배율</label>
				    <input type="range" class="form-range" min="0" max="14" id="viewLevel">
				  </div>
				  <div class="form-check form-switch mb-3">
				    <input class="form-check-input" type="checkbox" role="switch" id="opneYn">
				    <label class="form-check-label" for="opneYn">공개여부</label>
				  </div>
				  <div class="mb-3">
				    <label for="content" class="form-label">지도 설명</label>
				    <textarea class="form-control" id="content"></textarea>
				  </div>
				  <button type="submit" class="btn btn-success" onclick="updateMap(this.form)">수정완료</button>
				</form>

			  </div>
			  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">...</div>
			  <div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0">
			  	<table class="table table-sm table-hover">
				  <colgroup>
				  	<col style="width:3em;">
				  	<col style="width:20%;">
				  </colgroup>
				  <thead>
				    <tr>
				      <th scope="col">#</th>
				      <th scope="col">Marker</th>
				      <th scope="col">About</th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr>
				      <th scope="row">1</th>
				      <td>Mark</td>
				      <td>삼원타워 지하 : 고기가 이상함 별점2점</td>
				    </tr>
				    <tr>
				      <th scope="row">2</th>
				      <td>Jacob</td>
				      <td>두껍삼 : 묵은지찌개 맛있다 별점4점</td>
				    </tr>
				    <tr>
				      <th scope="row">3</th>
				      <td>Larry the Bird</td>
				      <td>육전식당 : 무난평범 별점3점</td>
				    </tr>
				  </tbody>
				</table>
			  </div>
			</div>
			</div>
		</div> 
		
		<div class="row gy-2 collapse" id="pre-map">
			<!-- 만들어진 지도 표시 -->
			<div class="col-12 p-2"></div>
			<div class="col-6 border" style="height: 500px;">
				<!-- 지도를 표시할 div 입니다 -->
				<div id="preMap" style="width:100%;height:100%;"></div>
			</div>

			<!-- 커스텀맵 설명칸 -->
			<div class="col-6 border overflow-y-scroll p-0" style="height: 500px;">
			
			<ul class="nav nav-tabs" id="pre-myTab" role="tablist">
			  <li class="nav-item" role="presentation">
			    <button class="nav-link active" id="pre-home-tab" data-bs-toggle="tab" data-bs-target="#pre-home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">맵 설명</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="pre-profile-tab" data-bs-toggle="tab" data-bs-target="#pre-profile-tab-pane" type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">마커 종류</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="pre-contact-tab" data-bs-toggle="tab" data-bs-target="#pre-contact-tab-pane" type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false" onclick="showMarkerList()">표시된 마커 보기</button>
			</ul>
			<div class="tab-content p-2" id="pre-myTabContent">
			  <div class="tab-pane fade show active" id="pre-home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			    <!-- 맵 설정 편집 -->
			    <form class="p-2">
				  <div class="mb-3">
				    <label for="pre-title" class="form-label">제목</label>
				    <input type="text" class="form-control" id="pre-title" disabled="disabled" value='customMarker.title'>
				  </div>
				  <div class="mb-3">
				    <label for="pre-center" class="form-label">중심좌표</label>
				    <input type="text" class="form-control" id="pre-center" disabled="disabled" value="customMarker.center">
				  </div>
				  <div class="mb-3">
				    <label for="pre-viewLevel" class="form-label">확대배율</label>
				    <input type="range" class="form-range" min="0" max="14" id="pre-viewLevel" disabled="disabled" value="customMarker.level">
				  </div>
				  <div class="form-check form-switch mb-3">
				    <input class="form-check-input" type="checkbox" role="switch" id="pre-opneYn" disabled="disabled">
				    <label class="form-check-label" for="pre-opneYn">공개여부</label>
				  </div>
				  <div class="mb-3">
				    <label for="pre-content" class="form-label">지도 설명</label>
				    <textarea class="form-control" id="pre-content" disabled="disabled" value="customMarker.content"></textarea>
				  </div>
				  <button type="submit" class="btn btn-success" disabled="disabled">수정 전 지도</button>
				</form>

			  </div>
			  <div class="tab-pane fade" id="pre-profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">...</div>
			  <div class="tab-pane fade" id="pre-contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0">
			  	<div id="showMarkerList"></div>
<!-- 			  	<table class="table table-sm table-hover"> -->
<%-- 				  <colgroup> --%>
<%-- 				  	<col style="width:3em;"> --%>
<%-- 				  	<col style="width:20%;"> --%>
<%-- 				  </colgroup> --%>
<!-- 				  <thead> -->
<!-- 				    <tr> -->
<!-- 				      <th scope="col">#</th> -->
<!-- 				      <th scope="col">Marker</th> -->
<!-- 				      <th scope="col">About</th> -->
<!-- 				    </tr> -->
<!-- 				  </thead> -->
<!-- 				  <tbody> -->
<!-- 				    <tr> -->
<!-- 				      <th scope="row">1</th> -->
<!-- 				      <td>Mark</td> -->
<!-- 				      <td>삼원타워 지하 : 고기가 이상함 별점2점</td> -->
<!-- 				    </tr> -->
<!-- 				    <tr> -->
<!-- 				      <th scope="row">2</th> -->
<!-- 				      <td>Jacob</td> -->
<!-- 				      <td>두껍삼 : 묵은지찌개 맛있다 별점4점</td> -->
<!-- 				    </tr> -->
<!-- 				    <tr> -->
<!-- 				      <th scope="row">3</th> -->
<!-- 				      <td>Larry the Bird</td> -->
<!-- 				      <td>육전식당 : 무난평범 별점3점</td> -->
<!-- 				    </tr> -->
<!-- 				  </tbody> -->
<!-- 				</table> -->
			  </div>
			</div>
			</div>
		</div> 
		<!-- <div class="row">
			<div class="col"></div>
			<div class="col-2 border border-top-0 text-center rounded-bottom shadow-sm" >
				<button class="btn" type="button" data-bs-toggle="collapse" data-bs-target="#pre-map" aria-expanded="false" aria-controls="multiCollapseExample2">수정 전 지도 보기</button>
			</div>
			<div class="col"></div>
		</div> -->
	<button class="btn border-top-0 btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#pre-map" 
	aria-expanded="false" aria-controls="multiCollapseExample2">수정 전 지도 보기</button>
		<div class="row">
			<div class="col p-3"><!-- 여백 --></div>
		</div>
	</div>
	
	
<script>
/* 변수선언 *****************************/
//var customOverlay; // 마커 클릭하면 뜨는 글 커스텁오버레이
//var myModal = new bootstrap.Modal('#exampleModal');
var markerInfoList = [];
var lineList = [];
var preMap;
var level;
var latitude;
var longitude;

window.onload = function(){
	var mapIdx = "${param.mapIdx}";
	console.log(mapIdx);
	$.ajax({
		type: 'GET',
		url: 'allMarker',
		data: { mapIdx: mapIdx },
	  success : function(customMarker) {
          console.log('Data saved successfully:', customMarker);
         
          var title = customMarker.title;
          level = customMarker.level;
          $('#pre-viewLevel').val(level);
          console.log(title);
          $('#pre-title').val(title);    
          var content = customMarker.content;
          $('#pre-content').val(title);
          var center = customMarker.center;
          // 괄호 제거하고 쉼표로 분리	
          var cleanedCoords = center.replace(/[()]/g, '');
          var parts = cleanedCoords.split(', ');

          // 위도와 경도 추출
          latitude = parseFloat(parts[0]);
          longitude = parseFloat(parts[1]);

          // 결과 출력
          console.log("Latitude: " + latitude);
          console.log("Longitude: " + longitude);
          $('#pre-center').val(center);
          markerInfoList = customMarker.markers;
          console.log(markerInfoList);
          lineList = customMarker.lines;
          console.log(lineList);
          
//           displayMap(level, latitude, longitude, markerInfoList, lineList);
          
      },
      error : function(error) {
         console.error('Error saving data:', error);
      }
	});
};

function initMap(level, latitude, longitude) {
	console.log("preMap 설정");
 	var mapContainer = document.getElementById('preMap'), // 지도를 표시할 div 
 	mapOption = { 
 	    center: new kakao.maps.LatLng(longitude, latitude), // 지도의 중심좌표
 	    level: level // 지도의 확대 레벨
 	};
     preMap = new kakao.maps.Map(mapContainer, mapOption); 
 }
//Bootstrap Collapse 이벤트에 초기 지도 설정
$('#pre-map').on('shown.bs.collapse', function () {
	console.log("수정전 지도 !");
    // 기존 지도의 중심 좌표와 확대 레벨을 사용하여 초기화
    if (markerInfoList.length > 0) {
    	console.log("수정전 지도 !");
        initMap(level, latitude, longitude);
        displayMap(markerInfoList, lineList);
    }
});

function displayMap(markers, lines) {
	markerInfoList = markers;
	lineList = lines;
	console.log("데이터 받음!");
	console.log(markerInfoList);
	console.log(lineList);
	
	//markers 배열의 각 요소에 접근
	for (var i = 0; i < markerInfoList.length; i++) {
	    console.log("Marker " + i + ":");
	    console.log("ID: " + markerInfoList[i].id);
	    console.log("Content: " + markerInfoList[i].content);
	}
	

    // 마커 표시
    for (var i = 0; i < markerInfoList.length; i++) {
        var markerPosition  = new kakao.maps.LatLng(markerInfoList[i].path.Ma, markerInfoList[i].path.La); 

        var marker = new kakao.maps.Marker({
            position: markerPosition
        });
		console.log(markerPosition);
        marker.setMap(preMap);

        // 인포윈도우에 표시할 내용
        var iwContent = '<div style="padding:5px;">' + markers[i].content + '</div>';

        // 인포윈도우를 생성합니다.
        var infowindow = new kakao.maps.InfoWindow({
            content: iwContent
        });

        // 인포윈도우를 표시합니다.
        infowindow.open(preMap, marker);
        
        // 마커에 클릭 이벤트를 등록합니다.
        (function(marker, content) {
            kakao.maps.event.addListener(marker, 'click', function() {
                // 인포윈도우에 표시할 내용
                var iwContent = '<div style="padding:5px;">' + content + '</div>';

                // 인포윈도우를 생성합니다.
                var infowindow = new kakao.maps.InfoWindow({
                    content: iwContent
                });

                infowindow.open(preMap, marker); 
            });
        })(marker, markerInfoList[i].content);
    }

    // 라인 표시
    for (var j = 0; j < lineList.length; j++) {
        var linePath = lineList[j].path.map(function(coord) {
            return new kakao.maps.LatLng(coord.Ma, coord.La);
        });
        var lineStyle = {
                strokeWeight: lineList[j].style.strokeWeight,
                strokeColor: lineList[j].style.strokeColor,
                strokeOpacity: lineList[j].style.strokeOpacity,
                strokeStyle: lineList[j].style.strokeStyle
            };

		console.log(linePath);
		console.log(lineStyle);
        var polyline = new kakao.maps.Polyline({
            path: linePath,
            strokeWeight: lineStyle.strokeWeight || 5, // 기본값 설정
            strokeColor: lineStyle.strokeColor || '#FF0000', // 기본값 설정
            strokeOpacity: lineStyle.strokeOpacity || 0.7, // 기본값 설정
            strokeStyle: lineStyle.strokeStyle || 'solid' // 기본값 설정
        });

        polyline.setMap(preMap);
    }
}

//마커리스트 출력
function showMarkerList() {
   console.log("마커리스트 출력!!");
   console.log(markerInfoList);
   
   if (markerInfoList.length != 0) {
	   console.log("마커리스트 info!!");
         var showMarkerInfoList = document.getElementById('showMarkerList');
         showMarkerInfoList.innerHTML = "";
		  // htmlContent 생성
		  var htmlContent = `
			 <div class="tab-pane fade" id="pre-contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0">
			  <table class="table table-sm table-hover">
		        <colgroup>
		          <col style="width:3em;">
		          <col style="width:20%;">
		        </colgroup>
		        <thead>
		          <tr>
		            <th scope="col">#</th>
		            <th scope="col">Marker</th>
		            <th scope="col">About</th>
		          </tr>
		        </thead>
		        <tbody>
		  `;
		
		  // markerInfoList 반복하여 각 마커 정보 추가
		  markerInfoList.forEach(function(marker, index) {
			console.log(marker.id);
		    htmlContent += `
		      <tr>
		        <th scope="row">${index + 1}</th>
		        <td>marker.id</td>
		        <td>marker.content</td>
		      </tr>
		    `;
		  });
		
		  // htmlContent 마무리
		  htmlContent += `
		        </tbody>
		      </table>
		     </div>
		  `;
		
		  // showMarkerList에 htmlContent 삽입
		  showMarkerInfoList.innerHTML = htmlContent;
   } else {
      console.log("마커없음!!");
      showDangerAlert("마커 오류", "마커가 없습니다!", '');
   }
}

function moveToMarker(markerId) {
   console.log("마커이동!!!");
   console.log(markerId);
   var markerMove = markerInfoList.find(function(marker) {
      return marker.id == markerId;
   });
   if (markerMove) {
      var moveLatLon = new kakao.maps.LatLng(markerMove.path.Ma, markerMove.path.La);
      map.panTo(moveLatLon);   
   } else {
      showDangerAlert("마커 오류", "해당 마커를 찾을 수 없습니다.", '마커 연결하기를 다시눌러주세요');
   }
}


</script>
</body>
</html>