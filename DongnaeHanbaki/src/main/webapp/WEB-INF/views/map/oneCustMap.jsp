  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
맵 VO : mapVO
마커타입 종류 : markerTypeList
마커리스트 : markerList 
댓글목록 : mapCommentsList
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/patials/commonHead.jsp"></jsp:include> <!-- 공통 헤더 파일 포함 -->
<style>
    /* 활성화된 탭의 배경색을 하얀색으로 변경 */
    .active {
      background-color: white !important;
    }
  </style>
<script>
	/* 수정창 이동 */
	function goOneCustomMap(){
		alert("수정 페이지로 이동합니다");
		location.href="updateCustMap";
	}
	/* 지도 삭제하기 */
	function deleteMap(){
		alert("지도를  삭제합니다");
		location.href="deleteCustMap?mapIdx=${mapVO.mapIdx}";
	}
	
	/*비동기 댓글작성하기 */
	function insertComment(frm) {
		/* alert("댓글을 작성합니다"); */
		if($("#writer").val() == null || $("#writer").val().trim() == ""){
			alert("로그인 데이터가 없습니다. 로그인이 필요합니다");
			return;
		}
		var formData = {
			mapIdx : $("#mapIdx").val(),
			writer : $("#writer").val(),
			content : $("#content").val()
		};

		console.log("formData : " + formData);
		console.log("frm : " + frm);

 		$.ajax({
			type : "POST",
			url : "/dongnae/insertComment",
			data : formData,
			success : function(response) {
				console.log("response : " + response);
				/* alert("댓글이 입력되었습니다"); // 성공 시 알림 창에 메시지 표시 */
				printCommentsList(); 
			},
			error : function(xhr, status, error) {
				console.log(xhr);
				console.log(status);
				console.log(error);
				alert("오류 발생: " + xhr.responseText); // 오류 시 알림 창에 오류 메시지 표시
			}
		});
		$("#content").val("");
		$("#content").focus();
	}
	
	/*  비동기 댓글 삭제하기 */
	function deleteComment(mapCommentIdx){
		console.log(mapCommentIdx);
		if(confirm("댓글을 삭제하시겠습니까")){
			
	/* 		if(!isLogin()){
				alert("로그인 데이터가 없습니다. 로그인이 필요합니다");
				return;
			}
			 */
			// 댓글 삭제처리 ajax
			$.ajax({
				type : "GET",
				url : "/dongnae/deleteComment",
				data : {mapCommentIdx: mapCommentIdx},
				success : function(response) {
					console.log("response : " + response);
					alert("댓글이 삭제되었습니다");
					printCommentsList(); 
				},
				error : function(xhr, status, error) {
					console.log(xhr);
					console.log(status);
					console.log(error);
					alert("오류 발생: " + xhr.responseText); // 오류 시 알림 창에 오류 메시지 표시
				}
			});
			
			
		}
	}
	
	/* 비동기로 댓글리스트 출력 */
	function printCommentsList(){
		let a ="";
		let mapIdx = ${mapVO.mapIdx};
		$.ajax({
			type : "GET",
			url : "/dongnae/getCommentList",
			data : {mapIdx: mapIdx},
			datatype: "JSON",
			success : function(response) {
				let count = 0;
				$(response).each(
					function(){
						count++;
						let wri = 
						console.log(this);
						a += '<div class="row m-2">';
						a += '	<div class="col-2">';
						a +=    	'<img src="#" alt="사진" id="writer-info-profile-img">';
						a +=    	'<a href="#">'+ this.writer +'</a>';
						a +=    	'<br>';
						a +=    	this.writeDate;
						a +=   '</div>';
						a +=    '<div class="vr p-0"></div>';
						a +=   '<div class="col-9 text-break">'+ this.content +'</div>';
						if(this.writer == "${user.email}"){
							a +=   '<button type="button" class="btn-close col" aria-label="Close" onclick="deleteComment('+ this.mapCommentIdx +')"></button>';
						}
						a += '</div>';
					});
				$("#printCommentsLIst").html(a);
				$("#commentButton").html('댓글 보기 (' + count + ')');
			}
		});
	}
	
	
</script>
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
			<h3> ${mapVO.title } </h3>
		</div>
		<div class="row gy-2">
			<!-- 만들어진 지도 표시 -->
			<div class="col-6 border" style="height: 600px;">
				<!-- 지도를 표시할 div 입니다 -->
				<div id="map" style="width:100%;height: 600px;"></div>
			</div>

			<!-- 커스텀맵 설명칸 -->
			<div class="col-6 border overflow-y-scroll p-0" style="height: 600px;">
			
			<ul class="nav nav-tabs" id="myTab" role="tablist">
			  <li class="nav-item" role="presentation">
			    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">맵 설명</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane" type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">마커 종류</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact-tab-pane" type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false">표시된 마커 보기</button>
			</ul>
			<div class="tab-content p-2" id="myTabContent">
			  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			    
			    <table class="table table-sm mt-1">
				  <tbody>
				    <tr>
				      <th> 제목</th>
				      <td>${mapVO.title}</td>
				    </tr>
				    <tr>
				      <th> 중심주소</th>
				      <td>(${mapVO.centerLongitude}, ${mapVO.centerLatitude})</td>
				    </tr>
				    <tr>
				      <th> 지도 크기</th>
				      <td>확대배율 : 3</td>
				    </tr>
				    <tr>
				      <th> 제작일</th>
				      <td>${mapVO.createDate }</td>
				    </tr>
				  </tbody>
				</table>
				<div>
			    <pre class="p-3  text-wrap">${mapVO.content }</pre>
				</div>
			  </div>
			  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">
			  
			  </div>
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
				  	<!-- 예시 마커 -->
				    <tr>
				      <th scope="row">1</th>
				      <td>찌개마커</td>
				      <td>맛잇네요</td>
				    </tr>
				    <tr>
				      <th scope="row">2</th>
				      <td>찌개마커</td>
				      <td>별로에요</td>
				    </tr>
				  </tbody>
				</table>
			  </div>
			</div>
			</div>
		</div> 
		<div class="col-11 py-3 pb-0">
		<!-- 커스텀맵 비공개 중일 경우 : 비공개 문구 출력 -->
		<c:if test='${mapVO.openYn.equals("0")} || ${mapVO.openYn == null}'>
				<h5>&nbsp;&nbsp;&nbsp;&nbsp;※ 비공개 된 커스텀 맵입니다</h5>
				<hr>
		</c:if>
		</div>
			<div class="btn-group" role="group" aria-label="Basic outlined example">
			  <button type="button" class="btn btn-outline-success" onclick="goOneCustomMap()">수정</button>
			  <button type="button" class="btn btn-outline-danger" onclick="deleteMap()">삭제</button>
			</div>
			<!-- 공개중에만 댓글 출력 -->
		<c:if test='${mapVO.openYn.equals("1")}'>
			<button id="commentButton" class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="collapse" data-bs-target="#commentsList" aria-expanded="false" aria-controls="collapseExample">
				댓글 보기 (${mapCommentsList.size() })
		    </button>
		    <hr>
		  	<!--  커스텀맵 공개중일 때만 댓글입력창 생성 -->
		    <div class="collapse" id="commentsList">
			    <form class="row mx-2">
					<div class="input-group p-0">
					  <button class="btn btn-outline-secondary col-2" type="button" id="button-addon1" onclick="insertComment(this.form)">댓글작성</button>
					  <textarea class="form-control" aria-label="With textarea" id="content"></textarea>
					  <input type="hidden" id="writer" value="${user.email }">
					  <input type="hidden" id="mapIdx" value="${mapVO.mapIdx }">
					</div>
				</form> 
				<!-- 예시댓글 -->
				<!-- <div class="row m-2">
					<div class="col-2">
		            	<img src="#" alt="사진" id="writer-info-profile-img">
		            	<a href="#">킴모씨</a>
		            	<br>
		            	2024.06.26
				    </div>
				    <div class="vr p-0"></div>
			        <div class="col-9">지도 잘 봤습니다</div>
			    </div> -->
		<c:if test="${mapCommentsList} == null">
			<div class="row m-2">
				<div class="text-center">- 아직 댓글이 없습니다 -</div>
			</div>
			
		 </c:if>
		 <div id="printCommentsLIst">
		  <c:forEach items="${mapCommentsList}" var="vo">
		      <div class="row m-2">
				<div class="col-2">
	            	<img src="#" alt="사진" id="writer-info-profile-img">
	            	<a href="#">${vo.writer }</a>
	            	<br>
	            	${vo.writeDate }
			    </div>
			    <div class="vr p-0"></div>
		        <div class="col-9 text-break">${vo.content }</div>
		        <!-- 작성자가 로그인유저와 일치하면 수정/삭제버튼 띄워줌 -->
		        <c:if test="${vo.writer == user.email }">
		        	<button type="button" class="btn-close col" onclick="deleteComment(${vo.mapCommentIdx})"></button>
		        </c:if>
			  </div>
		  </c:forEach>
		 </div>
		</div>
		</c:if>
		<div class="row">
			<div class="col p-3"><!-- 여백 --></div>
		</div>
		<!-- <form id="commentForm">
		    <input type="hidden" id="bbsIdx" value="9"> 
		    <label for="writer">작성자:</label>
		    <input type="text" id="writer" name="writer" required><br><br>
		    <label for="content">내용:</label><br>
		    <textarea id="content" name="content" rows="4" cols="50" required></textarea><br><br>
		    <button onclick="insertComment()">댓글 등록</button>
		</form> -->
	</div>
	
	
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e"></script>
<script>

var markerInfoList = [];
var lineList = [];
var preMap;
var level;
var latitude;
var longitude;

var map;


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
          console.log(title);
          var content = customMarker.content;
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
          markerInfoList = customMarker.markers;
          console.log(markerInfoList);
          lineList = customMarker.lines;
          console.log(lineList);
          
          initMap(level, latitude, longitude, markerInfoList, lineList);
          
      },
      error : function(error) {
         console.error('Error saving data:', error);
      }
	});
};

function initMap(level, latitude, longitude, markerInfoList, lineList) {
	console.log("map 설정");
 	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
 	mapOption = { 
 	    center: new kakao.maps.LatLng(longitude, latitude), // 지도의 중심좌표
 	    level: level // 지도의 확대 레벨
 	};
     map = new kakao.maps.Map(mapContainer, mapOption); 
     displayMap(markerInfoList, lineList);
 }

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
		marker.setMap(map);
        // 인포윈도우에 표시할 내용
        var iwContent = '<div style="padding:5px;">' + markers[i].content + '</div>';
		
        // 인포윈도우를 생성합니다.
        var infowindow = new kakao.maps.InfoWindow({
            content: iwContent
        });

        // 인포윈도우를 표시합니다.
        infowindow.open(map, marker);
        
        // 마커에 클릭 이벤트를 등록합니다.
        (function(marker, content) {
            kakao.maps.event.addListener(marker, 'click', function() {
                // 인포윈도우에 표시할 내용
                var iwContent = '<div style="padding:5px;">' + content + '</div>';

                // 인포윈도우를 생성합니다.
                var infowindow = new kakao.maps.InfoWindow({
                    content: iwContent
                });

                infowindow.open(map, marker); 
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

        polyline.setMap(map);
    }
}


</script>
</body>
</html>