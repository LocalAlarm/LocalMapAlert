<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네한바퀴</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
    rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
    crossorigin="anonymous">
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
crossorigin="anonymous"></script>

<div class="modal" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">새 글 작성</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body ">
         <!-- 사용자 입력 폼 -->
      <form id="markerForm">
  		  <label for="markerType">마커선택</label>
          <select class="form-select" id="markerType" required>
              <option value="사건 사고">사건 사고</option>
              <option value="이벤트">이벤트</option>
          </select>
		  <div class="mb-3">
		    <label for="markerContent">내용</label>
               <input type="text" class="form-control" id="markerContent" required>
		  </div>
		  <div class="mb-3">
		    <label for="markerDetails">자세한 내용</label>
                <textarea class="form-control" id="markerDetails" rows="3" required></textarea>
		  </div>
		  <input type="hidden" id="markerLat">
            <input type="hidden" id="markerLng">
            <button type="submit" class="btn btn-primary">추가</button>
            <button type="button" class="btn btn-secondary" onclick="closeForm()">취소</button>
      </form>
      </div>
    </div>
  </div>
</div>

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
<!-- 사이드바 -->
<div class="container-fluid">
<div class="row" style="height: 500px;">
	<div class="col-5 bg-body-secondary p-3" style="height: 500px;">
	</div>
	
	<div class="vr"></div>
	
<!-- 지도를 표시할 div 입니다 -->
	<div class="col-5 bg-body-tertiary p-3" style="height: 500px;">
	</div>
</div>
</div>

</body>
</html>