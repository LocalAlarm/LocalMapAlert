var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.49879634476233, 127.03151757116309),
    level: 2
};
var map = new kakao.maps.Map(container, options);

// 마커를 저장하는 배열
var markers = [];

// 마커 추가를 위한 임시 위치 저장
var tempLatLng;
var tempMarker = null;

// 클릭 이벤트 발생 시 마커 추가
kakao.maps.event.addListener(map, 'rightclick', function(mouseEvent) { 
    tempLatLng = mouseEvent.latLng;
    var lat = tempLatLng.getLat();
    var lng = tempLatLng.getLng();
    console.log('위도 :', lat, '경도 :', lng); // 콘솔에 좌표 출력

    // 지도의 중심을 클릭된 위치로 이동
    map.setLevel(2);
    map.setCenter(tempLatLng);

    document.getElementById('markerLat').value = lat;
    document.getElementById('markerLng').value = lng;
    document.getElementById('inputForm').style.display = 'block';

    // 임시 마커 생성
    if (tempMarker) {
        tempMarker.setMap(null);
    }
    tempMarker = new kakao.maps.Marker({
        position: tempLatLng,
        map: map
    });
});

// 폼 제출 시 마커 추가
document.getElementById('markerForm').addEventListener('submit', function(event) {
    event.preventDefault();
    var markerType = document.getElementById('markerType').value;
    var markerContent = document.getElementById('markerContent').value;
    var markerDetails = document.getElementById('markerDetails').value;
    var lat = document.getElementById('markerLat').value;
    var lng = document.getElementById('markerLng').value;
	console.log(markerType);
	console.log(markerContent);
	addMarker(new kakao.maps.LatLng(lat, lng), markerType, markerType + " : " + markerContent, markerType + " : " + markerContent + "\n자세한 내용 : " + markerDetails);
    document.getElementById('inputForm').style.display = 'none';
    resetTempMarker();
    resetForm(); 
});

// 폼 취소 시 폼 닫기
function closeForm() {
    document.getElementById('inputForm').style.display = 'none';
    resetTempMarker(); // 임시 마커 제거
    resetForm();
}

// 임시 마커 제거 함수
function resetTempMarker() {
    if (tempMarker !== null) {
        tempMarker.setMap(null);
        tempMarker = null;
    }
}

// 마커 생성 및 지도에 표시하는 함수
function addMarker(position, markerType, content, detailedContent) {
    var markerImage = null;
    var imageSrc, imageSize, imageOption;

    // 마커 이미지 설정
    if (markerType === '사건 사고') {
        imageSrc = contextPath + '/resources/image/ohno.png'; 
        imageSize = new kakao.maps.Size(60, 60);
        imageOption = {offset: new kakao.maps.Point(27, 69)};
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
    }
	
	 // 최대 글자 수 설정
    var maxLength = 10;
    var trimmedContent = content.length > maxLength ? content.substring(0, maxLength) + "..." : content;
	
    var marker = new kakao.maps.Marker({
        position: position,
        map: map,
        image: markerImage
    });
    markers.push(marker); // 배열에 마커 추가

    // 마커에 표시할 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content : '<div>' + content + '</div>' // 표시내용
    });

    // 마커에 mouseover 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        infowindow.open(map, marker);
    });

    // 마커에 mouseout 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });

    // 마커에 click 이벤트 등록
     kakao.maps.event.addListener(marker, 'click', function() {
        // 지도 중심을 마커 위치로 이동하고 레벨을 1로 설정
        map.setCenter(marker.getPosition());
        map.setLevel(1);

        // 팝업 창 내용 설정 및 표시
        document.getElementById('popupContent').innerText = detailedContent;
        document.getElementById('popup').style.display = 'block';
    });
}

// 마커를 모두 숨기는 함수
function hideMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }        
}

// 마커를 모두 보이는 함수
function showMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }        
}

// 팝업 창 닫기
function closePopup() {
    document.getElementById('popup').style.display = 'none';
}

// 폼 초기화
function resetForm() {
    document.getElementById('markerForm').reset();
}
//사건사고 클릭
function loadEventAccidents() {
        $.ajax({
            url: contextPath + "/getEventAccidents",
            method: "GET",
            dataType: "json",
            success: function(data) {
                // 기존 마커 제거
                hideMarkers();
                markers = [];

                // 가져온 데이터로 마커 생성
                var contentHtml = ''; // 새로운 콘텐츠를 위한 변수
                data.forEach(function(event) {
                    var position = new kakao.maps.LatLng(event.latitude, event.logitude);
                    var content = event.title + " : " + event.content;
                    var detailedContent = event.title + " : " + event.content + "\n자세한 내용 : " + event.details;
                    addMarker(position, "사건 사고", content, detailedContent);
                    
                    // 각 이벤트 사고를 HTML 콘텐츠로 추가
                    contentHtml += '<div>' + detailedContent + '</div>';
                });

                // 마커 보이기
                showMarkers();

                // 네비게이션 바 탭 활성화
                $('#v-pills-profile-tab').tab('show');

                // 콘텐츠 업데이트
                $('#event-accidents-content').html(contentHtml);
	            },
	            error: function(xhr, status, error) {
	                console.error("데이터를 가져오는 중 오류 발생: " + error);
	            }
	        });
	    }
	    

