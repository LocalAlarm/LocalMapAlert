<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>흰 올빼미 지도</title>
 	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
    rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
    crossorigin="anonymous">
	<link href="${pageContext.request.contextPath}/resources/css/map.css" rel="stylesheet">
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
    <!-- 사이드바 https://getbootstrap.kr/docs/5.1/components/navs-tabs/-->
<div class="container-fluid">
    <div class="row">
        <div class="col-2" id="sidebar">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <button class="nav-link active" id="v-pills-home-tab" data-bs-toggle="pill" data-bs-target="#v-pills-home" onclick ="allMenu()" type="button" role="tab" aria-controls="v-pills-home" aria-selected="true">전체 목록</button>
                <button class="nav-link" id="v-pills-profile-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profile" onclick="loadEventAccidents()" type="button" role="tab" aria-controls="v-pills-profile" aria-selected="false">사건 사고</button>
                <button class="nav-link" id="v-pills-messages-tab" data-bs-toggle="pill" data-bs-target="#v-pills-messages" onclick="loadEvents()" type="button" role="tab" aria-controls="v-pills-messages" aria-selected="false">이벤트</button>
                <button class="nav-link" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab" aria-controls="v-pills-settings" aria-selected="false">나만의 지도</button>
            </div>
        </div>       
    </div>
</div>
    
    <!-- 지도 -->
    <div id="map"></div>
    
    <!-- 마커 보이기 감추기 버튼 -->
    <div class="overlay-button">
        <button onclick="hideMarkers()" class="btn btn-secondary btn-sm">마커 감추기</button>
        <button onclick="showMarkers()" class="btn btn-secondary btn-sm">마커 보이기</button>
    </div>
    
     <!-- 사용자 입력 폼 -->
    <div id="inputForm">
    	<form id="markerForm">
	        <div class="form-group">
	            <label for="markerType">마커 종류</label>
	            <select class="form-select" id="markerType" required>
	                <option value="" selected disabled hidden>마커 종류를 선택해주세요</option>
	                <option value="사건사고">사건사고</option>
	                <option value="이벤트">이벤트</option>
	            </select>
	        </div>
	        <div class="form-group">
	            <label for="markerContent">내용</label>
	            <input type="text" class="form-control" id="markerContent" required>
	        </div>
	        <div class="form-group">
	            <label for="markerDetails">자세한 내용</label>
	            <textarea class="form-control" id="markerDetails" rows="3" required></textarea>
	        </div>
	        <input type="hidden" id="markerLat">
	        <input type="hidden" id="markerLng">
	        <button type="submit" class="btn btn-primary">추가</button>
	        <button type="button" class="btn btn-secondary" onclick="closeForm()">취소</button>
	    </form>
	</div>
	
    <!-- 팝업 창 -->
    <div id="popup">
        <button id="popupClose" onclick="closePopup()">닫기</button>
        <div id="popupContent"></div>
    </div>

    <!-- Kakao 지도 API 스크립트 -->
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e"></script>
    <script>
        var contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/map.js"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>