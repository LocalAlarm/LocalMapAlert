<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네한바퀴</title>
<jsp:include page="/WEB-INF/patials/commonHead.jsp"></jsp:include>
</head>

<body>
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

<!-- 커스텀맵 조회 -->
<div class="container-fluid">
<div class="row gy-2">
	  <!-- 만들어진 지도 표시 -->
      <div class="col-6 border" style="height: 600px;">      
      <!-- 지도를 표시할 div 입니다 -->
		<div class="col-auto">
			<div id="map" style="width:100%;height:60vh;"></div>
		</div>
      </div>
      
	  <div class="col-6 border overflow-y-scroll" style="height: 600px;">
	  
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
			  <input type="text" class="form-control" placeholder="중심 주소를 입력해 주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
			  <button class="btn btn-outline-secondary" onclick="sample6_execDaumPostcode()" type="button" id="button-addon2">검색</button>
			</div>
			<label for="customRange3" class="form-label">지도 크기</label>
			<input type="range" class="form-range" min="0" max="4" id="customRange3">
			<div class="form-floating py-2">
			  <input type="text" class="form-control" id="title" placeholder="Password">
			  <label for="title">제목</label>
			</div>
			<div class="form-floating py-2">
			  <input type="text" class="form-control" id="content" placeholder="Password">
			  <label for="content">내용</label>
			</div>
			<!-- 저장/초기화 버튼 -->
	 <button type="button" class="btn btn-outline-primary">지도 생성</button>
	 <button type="button" class="btn btn-outline-danger">취소</button>
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
			  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">...</div>
			  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">...</div>
			  <div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0">...</div>
			</div>
		</div>
		</div>
	  
	 
	</div>
</div>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d15ec03b27e51b25ee4bac136a965d54&libraries=services,clusterer,drawing"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;
            
            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 주소 정보를 해당 필드에 추가
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동
            document.getElementById("sample6_detailAddress").focus();

            // 지도에 주소 표시
            var geocoder = new daum.maps.services.Geocoder();

            // 주소로 좌표를 검색
            geocoder.addressSearch(addr, function(results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {
                    var coords = new daum.maps.LatLng(results[0].y, results[0].x);

                    // 지도 표시
                    document.getElementById('map').style.display = "block";
                    map.relayout();
                    map.setCenter(coords);
                    marker.setPosition(coords);
                }
            });
        }
    }).open();

</script>
</body>
</html>