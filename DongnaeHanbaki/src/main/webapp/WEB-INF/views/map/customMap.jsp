<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네한바퀴</title>
<jsp:include page="/WEB-INF/patials/commonHead.jsp"></jsp:include>
<script>
	function goCreateCustomMap(){
		alert("새 커스텀맵 생성 페이지로 이동합니다");
		location.href="createMap";
	}
	
	function goSearch(){
		let a = document.getElementById("searchType").value;
		let b = document.getElementById("searchKeyword");
		
		console.log(a);
		console.log(b.value);
		
		switch (a) {
		case 'maptitle' :
			location.href="searchCustomMap?title=" + b.value;
			break;
		case 'mapContent' :
			location.href="searchCustomMap?content=" + b.value;
			break;
		case 'writer' :
			location.href="searchCustomMap?userEmail=" + b.value;
			break;
		default :
			location.href="searchCustomMap?title=" + b.value + "&content=" + b.value;
			break;
		}
		/* location.href="searchCustomMap?title=" + frm.title + "&content=" + frm.content; */

	}
	
	function Confirm() { 
		Swal.fire({
            title: '정말로 삭제하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '승인',
            cancelButtonText: '취소',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                // 사용자가 확인을 눌렀을 때 deleteCustMap 링크로 이동
                window.location.href = `deleteCustMap?mapIdx=${mapIdx}`;
            }
        });

     }
	
	function confirmOpenYn(mapIdx, checked) {
		console.log("공개비공개 바꾸기");
	    let newOpenYn = checked ? '1' : '0'; // 현재 상태와 반대로 설정
        let confirmationMessage = newOpenYn === '1' ? "정말로 공개로 변경하시겠습니까?" : "정말로 비공개로 변경하시겠습니까?";
        console.log(checked);
        console.log(newOpenYn);
        Swal.fire({
            title: confirmationMessage,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '승인',
            cancelButtonText: '취소',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
            	console.log("ddd");
                // 사용자가 확인을 눌렀을 때 updateOpenYn 링크로 이동
                window.location.href = `updateOpenYn?mapIdx=${mapIdx}&openYn=${newOpenYn}`;
            }
        });
    }
</script>
<style>
.mapTitle {
/*   width: 200px; */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;  /* 말줄임 적용 */
}
.mapContent {
  overflow: hidden;
  position: relative;
  line-height: 1.5em;
  max-height: 4.5em;
  margin-right: -1em;
  padding-right: 1em;
}

.mapContent:before {
  content: '...';
  position: absolute;
  right: 0;
  bottom: 0;
}

.mapContent:after {
  content: '';
  position: absolute;
  right: 0;
  width: 1em;
  height: 1em;
  margin-top: 0.2em;
  background: white;
}
 .active {
   background-color: white !important;
 }
</style>
</head>
<!-- 
공개된 커스텀맵 목록 : openCustomMapList
내 커스텀맵 목록 : myCustomMapLIst
 -->
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
    <form class="d-flex">
      <div class="input-group">
	    <select class="border rounded-start px-1" id="searchType">
	      <option selected>선택</option>
	      <option value="mapTitle">제목</option>
	      <option value="mapContent">내용</option>
	      <option value="writer">작성자</option>
	    </select>
        <input id="searchKeyword" class="form-control" type="text" placeholder="Search" aria-label="Search">
        <button type="button" class="btn btn-outline-success" onclick="goSearch()">Search</button>
      </div>
    </form>
  </div>
</nav>
<hr>

<!-- 커스텀맵 조회 -->
<div class="container-fluid">
<div class="row gy-2">
      <div class="col-6 border" style="height: 600px;">
        <div class="text-center p-3"><h3>다른 커스텀맵 보기</h3></div>
<!--         <div class="row g-1 p-2 border rounded mb-2">
		  <div class="col-3">
		    <h5>제목</h5>
		    <h6 class="text-body-secondary">제작자</h6>
		    <a href="oneCustMap?" class="card-link">자세히보기</a>
		  </div>
		  <div class="col-8">
		  	<pre>내용</pre>
		  </div>
        </div> -->
    <c:forEach items="${openCustomMapList}" var="vo">
	    <div class="row g-1 p-2 border rounded mb-2">
		  <div class="col-3">
		    <h5 class="mapTitle">${vo.title }</h5>
		    <h6 class="text-body-secondary">${vo.userEmail }</h6>
		    <a href="oneCustMap?mapIdx=${vo.mapIdx }" class="card-link">자세히보기</a>
		  </div>
		  <div class="col-8">
		  	<p class="px-2 mapContent">${vo.content }</p>
		  </div>
        </div>
    </c:forEach>
    </div>
      
	  <div class="col-6 border" style="height: 600px;">
	  
        <div class="text-center p-3"><h3>나의 커스텀맵</h3></div>
        
<!--         <div class="row g-1 p-2 border rounded mb-2">
		  <div class="col-3">
		    <h5 class="mapTitle">제목</h5>
		    <h6 class="text-body-secondary">제작자</h6>
		    <a href="oneCustMap?" class="card-link">자세히보기</a>
		    <a href="updateCustMap?" class="card-link">편집하기</a>
		  </div>
		  <div class="col-8">
		  	<pre>내용</pre>
		  </div>
        </div> -->
    <c:forEach items="${myCustomMapList}" var="vo">
	    <div class="row g-1 p-2 border rounded mb-2">
		  <div class="col-3">
		    <h5 class="mapTitle">${vo.title }</h5>
		    <h6 class="text-body-secondary">${vo.userEmail }</h6>
		    <a href="oneCustMap?mapIdx=${vo.mapIdx }" class="card-link">자세히보기</a>
		    <a href="updateCustMap?mapIdx=${vo.mapIdx }" class="card-link">편집하기</a>
		    <a href="#" class="card-link" onclick="Confirm(${vo.mapIdx});">삭제하기</a>
		    <div class="form-check form-switch">
                <input class="form-check-input"
                       type="checkbox"
                       id="openYn_${vo.mapIdx}"
                       ${vo.openYn == '1' ? 'checked' : ''}
                       onclick="confirmOpenYn(${vo.mapIdx}, this.checked)">
                <label class="form-check-label" for="openYn_${vo.mapIdx}">공개여부</label>
            </div>
		  </div>
		  <div class="col-8">
		  	<p class="px-2 mapContent">${vo.content }</p>
		  </div>
        </div>
    </c:forEach>
        
        <div class="d-grid gap-2 py-2">
	        <c:if test='${user == null}'>
			  <button class="btn btn-outline-secondary py-3" type="button" disabled>로그인이 필요합니다</button>
	        </c:if>
	        <c:if test='${user != null}'>
			  <button class="btn btn-outline-primary py-3" type="button" onclick="goCreateCustomMap()">새 커스텀맵 만들기</button>
	        </c:if>
		</div>
		
	  </div>
	    
</div>
</div>

</body>
</html>