<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
    rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
    crossorigin="anonymous">
<script>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
crossorigin="anonymous"></script>

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
				<div id="map" style="width:100%;height:70vh;"></div>
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
			    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact-tab-pane" type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false">표시된 마커 보기</button>
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
		
		<!-- 기존지도 비교용 출력 -->
		<div class="row gy-2 collapse" id="pre-map">
			<!-- 만들어진 지도 표시 -->
			<div class="col-12 p-2"></div>
			<div class="col-6 border" style="height: 500px;">
				<!-- 지도를 표시할 div 입니다 -->
				<div id="map" style="width:100%;height:70vh;"></div>
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
			    <button class="nav-link" id="pre-contact-tab" data-bs-toggle="tab" data-bs-target="#pre-contact-tab-pane" type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false">표시된 마커 보기</button>
			</ul>
			<div class="tab-content p-2" id="pre-myTabContent">
			  <div class="tab-pane fade show active" id="pre-home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			    <!-- 맵 설정 편집 -->
			    <form class="p-2">
				  <div class="mb-3">
				    <label for="pre-title" class="form-label">제목</label>
				    <input type="text" class="form-control" id="pre-title" disabled="disabled">
				  </div>
				  <div class="mb-3">
				    <label for="pre-center" class="form-label">중심좌표</label>
				    <input type="text" class="form-control" id="pre-center" disabled="disabled">
				  </div>
				  <div class="mb-3">
				    <label for="pre-viewLevel" class="form-label">확대배율</label>
				    <input type="range" class="form-range" min="0" max="14" id="pre-viewLevel" disabled="disabled">
				  </div>
				  <div class="form-check form-switch mb-3">
				    <input class="form-check-input" type="checkbox" role="switch" id="pre-opneYn" disabled="disabled">
				    <label class="form-check-label" for="pre-opneYn">공개여부</label>
				  </div>
				  <div class="mb-3">
				    <label for="pre-content" class="form-label">지도 설명</label>
				    <textarea class="form-control" id="pre-content" disabled="disabled"></textarea>
				  </div>
				  <button type="submit" class="btn btn-success" disabled="disabled">수정 전 지도</button>
				</form>

			  </div>
			  <div class="tab-pane fade" id="pre-profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">...</div>
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
	
	
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e"></script>
<script>

/* 변수선언 *****************************/
var customOverlay; // 마커 클릭하면 뜨는 글 커스텁오버레이
var myModal = new bootstrap.Modal('#exampleModal');

/* 지도 표시하기 ************************/
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 
</script>
</body>
</html>