<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>한마바퀴</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
    rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
    crossorigin="anonymous">
<style>
    .wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
    .info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
    .info .close:hover {cursor: pointer;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;margin: 13px 0 0 90px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
    .info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
    .info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
    .info .link {color: #5085BB;}

	.popupContent {
	    display: inline-block;
	    position: relative;
	    background: #ccc;
	    height: 50px;
	    width: 120px;
	    margin: 0 auto 10px;
	}
	.popupContent:after {
	    content: '';
	    position: absolute;
	    border-top: 10px solid #ccc;
	    border-right: 5px solid transparent;
	    border-left: 5px solid transparent;
	    bottom: -9px;
	    left: 5px;
	}
</style>
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

<!-- 페이지 위 -->
<div class="p-5 text-center">
  <h1>동네한바퀴</h1>
</div>

<!-- 사이드바 -->
<div class="container-fluid">
<div class="row">
<div class="col-2" id="sidebar">
	<ul class="nav flex-column">
	  <li class="nav-item">
	    <a class="nav-link active" aria-current="page" href="#">전체</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">사건사고</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">이벤트</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link disabled" aria-disabled="true">커스텀지도</a>
	  </li>
	</ul>
</div>

<!-- 지도를 표시할 div 입니다 -->
<div class="col-8">
	<div id="map" style="width:100%;height:100vh;"></div>
	</div>
	</div>
</div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d15ec03b27e51b25ee4bac136a965d54&libraries=services,clusterer,drawing"></script>
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

/* 다각형 표시하기 *************************/
// 다각형 객체를 구성할 좌표배열입니다 
var path = [
	new kakao.maps.LatLng(33.45086654081833, 126.56906858718982),
	new kakao.maps.LatLng(33.45010890948828, 126.56898629127468),
	new kakao.maps.LatLng(33.44979857909499, 126.57049357211622),
	new kakao.maps.LatLng(33.450137483918496, 126.57202991943016),
	new kakao.maps.LatLng(33.450706188506054, 126.57223147947938),
	new kakao.maps.LatLng(33.45164068091554, 126.5713126693152)
];

var hole = [
	new kakao.maps.LatLng(33.4506262491095, 126.56997323165163),
	new kakao.maps.LatLng(33.45029422800042, 126.57042659659218),
	new kakao.maps.LatLng(33.45032339792896, 126.5710395101452),
	new kakao.maps.LatLng(33.450622037218295, 126.57136070280123),
	new kakao.maps.LatLng(33.450964416902046, 126.57129448564594),
	new kakao.maps.LatLng(33.4510527150534, 126.57075627706975)
];

// 다각형을 생성하고 지도에 표시합니다
var polygon = new kakao.maps.Polygon({
	map: map,
    path: [path, hole], // 좌표 배열의 배열로 하나의 다각형을 표시할 수 있습니다
    strokeWeight: 2,
    strokeColor: '#b26bb2',
    strokeOpacity: 0.8,
    fillColor: '#f9f',
    fillOpacity: 0.7 
});

/* 마커 표시하기 ***********************/
// 마커를 저장하는 배열
var markers = [];

// 마커 추가를 위한 임시 위치 저장
var tempLatLng;

// 클릭 이벤트 발생 시 마커 추가
kakao.maps.event.addListener(map, 'rightclick', function(mouseEvent) { 
    tempLatLng = mouseEvent.latLng;
    var lat = tempLatLng.getLat();
    var lng = tempLatLng.getLng();
    panTo(lat, lng);
    console.log('위도:', lat, '경도:', lng); // 콘솔에 좌표 출력
    
    document.getElementById('markerLat').value = lat;
    document.getElementById('markerLng').value = lng;

	//마커작성모달폼 띄우기
	document.getElementById('markerForm').reset();
    myModal.show();
	
});

// 폼 제출 시 마커 추가
document.getElementById('markerForm').addEventListener('submit', function(event) {
    event.preventDefault();
    var markerType = document.getElementById('markerType').value;
    var markerContent = document.getElementById('markerContent').value;
    var markerDetails = document.getElementById('markerDetails').value;
    var lat = document.getElementById('markerLat').value;
    var lng = document.getElementById('markerLng').value;

    addMarker(new kakao.maps.LatLng(lat, lng), markerType + ": " + markerContent, "자세한 내용 :" +markerDetails);
    myModal.hide();
});

// 폼 취소 시 폼 닫기
function closeForm() {
	myModal.hide();
}

// 마커 생성 및 지도에 표시하는 함수
function addMarker(position, content, detailedContent) {
    var marker = new kakao.maps.Marker({
        position: position,
        map: map
    });
    markers.push(marker); // 배열에 마커 추가

    // 마커에 표시할 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: '<div>' + content + '</div>' // 표시내용
    });

    // 마커에 mouseover 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        infowindow.open(map, marker);
    });

    // 마커에 mouseout 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });

    // 마커에 click 이벤트 등록 - 팝업 창으로 정보 표시
    kakao.maps.event.addListener(marker, 'click', function() {
        openPopup(marker.getPosition(), content, detailedContent);
    });
}

// 배열에 추가된 마커들을 지도에 표시, 삭제
function setMarkers(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }            
}

// 마커 보이기
function showMarkers() {
    setMarkers(map);    
}

// 마커 감추기
function hideMarkers() {
    setMarkers(null);    
}

/* 마커 클릭으로 게시글 띄우기 ****************************/
//팝업 창 열기
function openPopup(position, content, detailedContent) {
	var popupContent = '<div class="wrap">' + 
	    '    <div class="info">' + 
	    '        <div class="title">' + 
	             content + 
	    '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' + 
	    '        </div>' + 
	    '        <div class="body">' + 
	    '            <div class="img">' +
	    '           </div>' + 
	    '            <div class="desc">' + 
	    '                <div class="ellipsis">' + detailedContent + '</div>' + 
	    '                <div class="jibun ellipsis"></div>' + 
	    '                <div><a target="_blank" class="link">링크</a></div>' + 
	    '            </div>' + 
	    '        </div>' + 
	    '    </div>' +    
	    '</div>';
		
	
	if(customOverlay != null) {closeOverlay();}
	// 커스텀 오버레이를 생성합니다d
	customOverlay = new kakao.maps.CustomOverlay({
	    position: position,
	    content: popupContent,
	    xAnchor: 0.3,
	    yAnchor: 0.91
	});
	customOverlay.setMap(map);
}

function closeOverlay() {
	customOverlay.setMap(null);     
}

function panTo(newCenterLat, newCenterlng) {
    // 이동할 위도 경도 위치 지정
    var moveLatLon = new kakao.maps.LatLng(newCenterLat, newCenterlng);
    
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);            
}


</script>

</body>
</html>
