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
	/* 수정창 이동 */
	function goOneCustomMap(){
		alert("수정 페이지로 이동합니다");
	}
	/* 삭제하기 */
	function deleteMap(){
		alert("지도를  삭제합니다");
	}
	/* 댓글작성하기 */
	function insertComment(frm) {
            alert("댓글을 작성합니다");

            var formData = {
                mapIdx: $("#mapIdx").val(),
                writer: $("#writer").val(),
                content: $("#content").val()
            };
            
            console.log(formData);
        
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
        }

        $(document).ready(function() {
            $("#commentForm").submit(function(event) {
                event.preventDefault();
                insertComment(this);
            });
        });
</script>
<!-- 
맵 VO : mapVO
댓글목록 : commentsList 
-->

<jsp:include page="WEB-INF/patials/commonHead.jsp"></jsp:include> <!-- 공통 헤더 파일 포함 -->
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
<!-- 
커스텀 맵 VO : mapVO
마커타입 종류 : markerTypeList
마커리스트 : markerList 
-->
	<div class="container">
		<div class="row p-3 text-center">
			<h3>제목 : 맛?집 지도</h3>
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
			  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			    
			    <table class="table table-sm mt-1">
				  <tbody>
				    <tr>
				      <th> 제목</th>
				      <td>지도의 제목</td>
				    </tr>
				    <tr>
				      <th> 중심주소</th>
				      <td>모도 모시 모구</td>
				    </tr>
				    <tr>
				      <th> 지도 크기</th>
				      <td>확대배율 : 3</td>
				    </tr>
				  </tbody>
				</table>
			    <pre class="p-3">강남역 주면 음식점
먹어본곳 표시함
			    </pre>
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
		<c:if test='${mapVO.openYn }.equals("0") || ${mapVO.openYn } == null'>
				<h5>&nbsp;&nbsp;&nbsp;&nbsp;※ 비공개 된 커스텀 맵입니다</h5>
				<hr>
		</c:if>
		</div>
			<div class="btn-group" role="group" aria-label="Basic outlined example">
			  <button type="button" class="btn btn-outline-success" onclick="goOneCustomMap()">수정</button>
			  <button type="button" class="btn btn-outline-danger" onclick="deleteMap()">삭제</button>
			</div>
			<!-- 공개중에만 댓글 출력 -->
		<%-- <c:if test='${mapVO.openYn }.equals("0") '> --%>
			<button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="collapse" data-bs-target="#commentsList" aria-expanded="false" aria-controls="collapseExample">
				댓글 보기(1)
		    </button>
		    <hr>
		  	<!--  커스텀맵 공개중일 때만 댓글입력창 생성 -->
		    <div class="collapse" id="commentsList">
			    <form class="row mx-2" id="commentForm">
				    <div class="input-group p-0">
				        <button class="btn btn-outline-secondary col-2" type="button" id="button-addon1" onclick="insertComment(this.form)">댓글작성</button>
				        <textarea class="form-control" aria-label="With textarea" id="content"></textarea>
				        <input type="hidden" id="writer" value="test18@naver.com">
				        <input type="hidden" id="mapIdx" value="1">
				    </div>
				</form>
				<!-- 예시댓글 -->
				<c:forEach items="${commentList}" var="comment">
					<div class="row m-2">
						<div class="col-2">
			            	<img src="#" alt="사진" id="writer-info-profile-img">
			            	<a href="#">${comment.writer}</a>
			            	<br>
			            	${comment.writeDate}
					    </div>
					    <div class="vr p-0"></div>
				        <div class="col-9">${comment.content}</div>
				    </div>
			    </c:forEach>
<%-- 		  <c:forEach items="mapCommentsList" var="vo">
<%-- 		      <div class="row m-2"> --%>
<%-- 				<div class="col-2"> --%>
<%-- 	            	<img src="#" alt="사진" id="writer-info-profile-img"> --%>
<%-- 	            	<a href="#">${vo.writer }</a> --%>
<%-- 	            	<br> --%>
<%-- 	            	${vo.writeDate } --%>
<%-- 			    </div> --%>
<%-- 			    <div class="vr p-0"></div> --%>
<%-- 		        <div class="col-9">${vo.content }</div> --%>
<%-- 			  </div> --%>
<%-- 		  </c:forEach> --%> 
			</div>
		<%-- </c:if> --%>
		<div class="row">
			<div class="col p-3"><!-- 여백 --></div>
		</div>
	</div>
	<table>
        <thead>
            <tr>
                <th>댓글 번호</th>
                <th>맵 번호</th>
                <th>작성자</th>
                <th>내용</th>
                <th>작성 날짜</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="comment" items="${commentList}">
                <tr>
                    <td>${comment.mapCommentIdx}</td>
                    <td>${comment.mapIdx}</td>
                    <td>${comment.writer}</td>
                    <td>${comment.content}</td>
                    <td>${comment.writeDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	
	
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