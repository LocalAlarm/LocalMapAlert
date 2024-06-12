<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <div class="col-6 border" style="height: 600px;">
        <div class="text-center p-1"><h3>다른 커스텀맵 보기</h3></div>
	    <div class="row g-1 p-2 border rounded mb-2">
		  <div class="col-3">
		    <h5>커스텀맵1</h5>
		    <h6>제작자 : 둥둥</h6>
		  </div>
		  <div class="col-8">
		  	<p>천방지축 어리둥절 빙글빙글 돌아가는 짱구의 하루 우리의 짱구는 정말 못말려</p>
		  </div>
		  <div>
		    <a href="#" class="card-link">자세히보기</a>
		    <a href="#" class="card-link">편집하기</a>
		  </div>
        </div>
        
	    <div class="row g-1 p-2 border rounded  mb-2">
		  <div class="col-3">
		    <h5>커스텀맵1</h5>
		    <h6>제작자 : 둥둥</h6>
		  </div>
		  <div class="col-8">
		  	<p>천방지축 어리둥절 빙글빙글 돌아가는 짱구의 하루 우리의 짱구는 정말 못말려</p>
		  </div>
		    <div>
		    <a href="#" class="card-link">자세히보기</a>
		    <a href="#" class="card-link">편집하기</a>
		  </div>
        </div>
      </div>
      
	  <div class="col-6 border" style="height: 600px;">
	  
        <div class="text-center p-1"><h3>나의 커스텀맵</h3></div>
        <div class="row g-1 p-2 border rounded mb-2">
		  <div class="col-3">
		    <h5>대동여지도</h5>
		    <h6>제작자 : 김정호</h6>
		  </div>
		  
		  <div class="col-8">
		  	<p>고산자(古山子) 김정호가 1861년 제작한 한반도의 지도이며, 지도첩이다. 3건이 대한민국의 보물로 지정되어 있으며...</p>
		  </div>
		  <div>
		    <a href="#" class="card-link">자세히보기</a>
		    <a href="#" class="card-link">편집하기</a>
		  </div>
        </div>
        
        <div class="d-grid gap-2 py-2">
		  <button class="btn btn-outline-primary py-3" type="button">새 커스텀맵 만들기</button>
		</div>
		
	  </div>
	    
</div>
</div>

</body>
</html>