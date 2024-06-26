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
    <link href="resources/css/map.css" rel="stylesheet">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
<!-- 사이드바 https://getbootstrap.kr/docs/5.1/components/navs-tabs/-->
<div class="container-fluid">
    <div class="row">
        <div class="col-2" id="sidebar">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <!-- 전체 목록 버튼 -->
                <button class="nav-link" id="v-pills-home-tab" data-bs-toggle="pill" data-bs-target="#v-pills-home" onclick="All()" type="button" role="tab" aria-controls="v-pills-home" aria-selected="true">전체 목록</button>

<!-- 사건 사고 메뉴 -->
<div class="dropdown">
    <a class="nav-link dropdown-toggle text-center"  id="eventAccidentsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        사건 사고
    </a>
    <ul class="dropdown-menu" aria-labelledby="eventAccidentsDropdown">
        <li><a class="dropdown-item" href="#" onclick="AllAccidents()">전체 사건사고</a></li>
        <li><a class="dropdown-item" href="#" onclick="RealTimeAccidents()">실시간 사건사고</a></li>
        <li><a class="dropdown-item" href="#" onclick="NearAccidents()">내 주변 사건사고</a></li>
    </ul>
</div>

<div class="dropdown">
    <a class="nav-link dropdown-toggle text-center" href="#" id="eventsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        이벤트
    </a>
    <ul class="dropdown-menu" aria-labelledby="eventsDropdown">
        <li><a class="dropdown-item" href="#" onclick="Events()">전체 행사 보기</a></li>
        <li><a class="dropdown-item" href="#" onclick="NearEvents()">내 근처 행사 찾기</a></li>
        <li><a class="dropdown-item" href="#" onclick="RealEvents()">진행중인 행사 찾기</a></li>
    </ul>
</div>
<button class="nav-link" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab" aria-controls="v-pills-settings" aria-selected="false">재난 문자 확인하기</button>
    <div id="disasterData" style="white-space: pre-wrap; margin-top: 20px;"></div>

            </div>
        </div>       
        <div id="map"></div> 
    </div>
</div>
    
<!-- 마커 리스트 -->
<div id="markerlist">
    <h2 class="p-3" id="markerListHeader">마커 목록</h2>
    <input type="text" id="searchInput" placeholder="검색어를 입력하세요" onkeyup="filterMarkers()" />
        <button id="searchButton">검색</button>
    <div id="markerList" class="p-3"></div>
    <div id="pagination" class="p-3"></div>
</div>
 
<!-- 마커 보이기 감추기 버튼 -->
<div class="overlay-button">
    <button id="toggleMarkersBtn" onclick="toggleMarkers()" class="btn btn-secondary btn-sm">마커 off</button>
    <button id="toggleMarkerListBtn" onclick="toggleMarkerList()" class="btn btn-secondary btn-sm">게시판 off</button>
</div>
<!-- 내 위치 버튼 -->
<div class="overlay-button-left">
    <button id="myLocationBtn" onclick="goToMyLocation()" class="btn btn-secondary btn-sm">내 위치</button>
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
            <div id="markerContentContainer">
                <input type="text" class="form-control" id="markerContent" required>
            </div>
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
        <div class="popup-header">
            <span class="popup-title" id="popupTitle"></span>
        </div>
        <div class="popup-body" id="popupContent"></div>
        <div class="popup-footer">
            <button onclick="closePopup()">Close</button>
        </div>
</div>

<!-- Kakao 지도 API 스크립트 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e&libraries=services"></script>

<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="resources/js/map.js"></script>
<script src="resources/js/mapevent.js"></script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
