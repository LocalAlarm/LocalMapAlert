<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
    rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
    crossorigin="anonymous">
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
			<h3>제목 : 커스텀맵 1</h3>
		</div>
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
			  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">...</div>
			  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">...</div>
			  <div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0">...</div>
			</div>
			</div>
		</div> 
		<!-- 댓글창 -->
			<div class="row">
				<div class="col-11 py-3">
					<h5>&nbsp;&nbsp;&nbsp;&nbsp;댓글보기(0개)</h5>
					<div class="col btn-group" role="group" aria-label="Basic radio toggle button group">
					  <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
					  <label class="btn btn-outline-secondary" for="btnradio1">최신순</label>
					  <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
					  <label class="btn btn-outline-secondary" for="btnradio3">작성순</label>
					</div>
					<hr>
				</div>
			</div>
			<form class="row mx-2 my-3">
				<div class="input-group p-0">
				  <button class="btn btn-outline-primary col-2" type="button" id="button-addon1">댓글작성</button>
				  <textarea class="form-control" aria-label="With textarea" id="content"></textarea>
				  <input type="hidden" id="writer" value="${userVO.email }">
				  <input type="hidden" id="mapIdx" value="${mapVO.mapIdx }">
				</div>
			</form>
			<div class="row m-2">
				<div class="col-2">
	            	<img src="#" alt="사진" id="writer-info-profile-img">
	            	<a href="#">킴모씨</a>
	            	<br>
	            	모월모일모시
			    </div>
				    <div class="vr p-0"></div>
		        <div class="col-9">
		        	<span>훌륭하고 감동적인 지도였어요.....................................................................................................,...................................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ...........................................................................
		        ..................................................................
		        	</span>
		        </div>
			</div>
			<div class="row m-2">
				<div class="col-2">
	            	<img src="#" alt="사진" id="writer-info-profile-img">
	            	<a href="#">킴모씨</a>
	            	<br>
	            	모월모일모시
			    </div>
			    <div class="vr p-0"></div>
		        <div class="col-9">훌륭하고 감동적인 지도였어요</div>
			</div>
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