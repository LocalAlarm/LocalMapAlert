<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="WEB-INF/patials/commonHead.jsp"></jsp:include> <!-- 공통 헤더 파일 포함 -->
<script type="text/javascript">
	$('document').ready(function() {
	    $("#commentForm").submit(function(event) {
	        event.preventDefault(); // 기본 form submit 동작 막기
	
	        var formData = {
	            bbsIdx: $("#bbsIdx").val(),
	            writer: $("#writer").val(),
	            content: $("#content").val()
	        };
	
	        $.ajax({
	            type: "POST",
	            url: "/dongnae/insertComment",
	            data: formData,
	            success: function(response) {
	            	console.log("response : " + response);
	                alert(response); // 성공 시 알림 창에 메시지 표시
	            },
	            error: function(xhr, status, error) {
	            	console.log(xhr);
	            	console.log(status);
	            	console.log(error);
	                alert("오류 발생: " + xhr.responseText); // 오류 시 알림 창에 오류 메시지 표시
	            }
	        });
	    });
	});
</script>
</head>
<body>
<jsp:include page="WEB-INF/patials/commonBody.jsp"></jsp:include>

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
				<div class="col-auto">
					<div id="map" style="width: 100%; height: 60vh;"></div>
				</div>
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
				<div class="col py-3">
					<h5>&nbsp;&nbsp;&nbsp;&nbsp;댓글보기(0개)</h5>
					<hr>
				</div>
			</div>
			<div class="row m-1">
				<div class="col-2">
	            	<img src="#" alt="사진" id="writer-info-profile-img">
	            	<a href="#">킴모씨</a>
	            	<br>
	            	모월모일모시
			    </div>
			    <div class="vr"></div>
		        <div class="col-auto">훌륭하고 감동적인 지도였어요</div>
			</div>
			<div class="row m-1">
				<div class="col-2">
	            	<img src="#" alt="사진" id="writer-info-profile-img">
	            	<a href="#">킴모씨</a>
	            	<br>
	            	모월모일모시
			    </div>
			    <div class="vr"></div>
		        <div class="col-auto">훌륭하고 감동적인 지도였어요</div>
			</div>
		<div class="row">
			<div class="col p-3">
				(공백)
			</div>
		</div>
		<form id="commentForm">
		    <input type="hidden" id="bbsIdx" value="9"> 
		    <input type="hidden" id="writer" value="test18@naver.com"><br><br>
		    <label for="content">내용:</label><br>
		    <textarea id="content" name="content" rows="4" cols="50" required></textarea><br><br>
		    <button type="submit">댓글 등록</button>
		</form>
	</div>
</body>
</html>