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

// 마우스 우클릭 이벤트 발생 시 마커 추가
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
    
    resetTempMarker()
});

// 폼 제출 시 마커 정보를 서버로 전송
document.getElementById('markerForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    var markerIdx = document.getElementById('markerType').value;
    var markerContent = document.getElementById('markerContent').value;
    var markerDetails = document.getElementById('markerDetails').value;
    var lat = document.getElementById('markerLat').value;
    var lng = document.getElementById('markerLng').value;
    var markerType; // markerType 변수 추가

    // markerIdx에 따라 markerType 결정
    switch (markerIdx) {
        case '이벤트':
            markerType = '1';
            break;
        case '사건사고':
            markerType = '2';
            break;
        default:
            console.error('Unknown marker type:', markerIdx);
            return;
    }

    // 서버에 전송할 데이터를 JSON 형식으로 저장
    var markerData = {
        markerIdx: markerType,
        title: markerContent,
        content: markerDetails,
        latitude: lat,
        longitude: lng
    };

    $.ajax({
        url: 'saveM', 
        method: 'POST', 
        contentType: 'application/json', 
        data: JSON.stringify(markerData), 
        success: function(response) {
            console.log('마커가 저장 성공:', response);
            console.log(markerData);
            
            // 폼 숨기기
            document.getElementById('inputForm').style.display = 'none';
            // 폼 초기화
            document.getElementById('markerForm').reset();
             // 마커 생성 및 지도에 표시
            addMarker(new kakao.maps.LatLng(lat, lng), markerType, markerContent, markerDetails);
        },
        error: function(xhr, status, error) {
            console.error('마커 저장 중 오류 발생:', error);
        }
    });
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
    var imageSrc, imageSize, imageOption;
	console.log(markerType);
    // 마커 이미지 설정
    switch (markerType) {
        case '1': // 이벤트
            imageSrc = contextPath + '/resources/image/balloons-29920_640.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(27, 69)};
            break;
        case '2': // 사건사고
            imageSrc = contextPath + '/resources/image/ohno.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(25, 50)};
            break;
        default:
            console.error('Unknown marker type:', markerType);
            return;
    }

    // 마커 이미지 생성
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

    var marker = new kakao.maps.Marker({
        position: position,
        map: map,
        image: markerImage
    });
    markers.push(marker); // 배열에 마커 추가

    // 최대 글자 수 설정
    var maxLength = 10;
    var trimmedContent = content.length > maxLength ? content.substring(0, maxLength) + "..." : content;

    // 마커에 표시할 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: '<div>' + content + '</div>' // 표시내용
    });

    // 마커에 mouseover 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseover', function () {
        infowindow.open(map, marker);
    });

    // 마커에 mouseout 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
    });

    // 마커에 click 이벤트 등록
    kakao.maps.event.addListener(marker, 'click', function () {
        // 지도 중심을 마커 위치로 이동하고 레벨을 1로 설정
        map.setCenter(marker.getPosition());
        map.setLevel(1);

        // 팝업 창 내용 설정 및 표시
        document.getElementById('popupContent').innerText = detailedContent;
        document.getElementById('popup').style.display = 'block';
    });
}

//전체목록 클릭
function All() {
    $.ajax({
        url: "all",
        method: "GET",
        dataType: "json",
        success: function(data) {
            // 기존 마커 숨기기
            hideMarkers();
            console.log(data); // 데이터 확인용 로그

            // 가져온 데이터로 마커 생성
            data.forEach(function(event) {
                var position = new kakao.maps.LatLng(event.LATITUDE, event.LONGITUDE);
                var content = event.CONTENT + " : " + event.TITLE;
                var detailedContent = event.CONTENT + " : " + event.TITLE + "\n자세한 내용 : " + event.TITLE;

                // MAKER_IDX 값에 따라 markerType 결정
                var markerType = event.MAKER_IDX == 1 ? '1' : '2';
                addMarker(position, markerType, content, detailedContent);     
            });

            // 마커 보이기
            showMarkers();

            // 네비게이션 바 탭 활성화
            $('#v-pills-home-tab').tab('show');

        },
        error: function(xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
       }
   });
}



// 페이지 로드될 때 전체 목록 표시
$(document).ready(function() {
    All();
});


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
// 사건사고 클릭
function EventAccidents() {
    $.ajax({
        url: "EventAccidents",
        method: "GET",
        dataType: "json",
        success: function (data) {
            // 기존 마커 제거
            hideMarkers();
            markers = [];

            // 가져온 데이터로 마커 생성
            data.forEach(function (event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var content = event.title + " : " + event.content;
                var detailedContent = event.content + " : " + event.title + "\n자세한 내용 : " + event.title;
                var markerType = event.markerIdx;
                addMarker(position, markerType, content, detailedContent);
            });

            // 마커 보이기
            showMarkers();

            // 네비게이션 바 탭 활성화
            $('#v-pills-profile-tab').tab('show');
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}

// 이벤트 클릭
function Events() {
    $.ajax({
        url: "Events",
        method: "GET",
        dataType: "json",
        success: function (data) {
            // 기존 마커 제거
            hideMarkers();
            markers = [];

            // 가져온 데이터로 마커 생성
            data.forEach(function (event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var content = event.title + " : " + event.content;
                var detailedContent = event.content + " : " + event.title + "\n자세한 내용 : " + event.title;
                var markerType = event.markerIdx;
                addMarker(position, markerType, content, detailedContent);
            });

            // 마커 보이기
            showMarkers();

            // 네비게이션 바 탭 활성화
            $('#v-pills-messages-tab').tab('show');
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}


function All() {
    $.ajax({
        url: "all",
        method: "GET",
        dataType: "json",
        success: function(data) {
            // 기존 마커 숨기기
            hideMarkers();
            console.log(data); // 데이터 확인용 로그

            // 가져온 데이터로 마커 생성
            data.forEach(function(event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var content = event.title + " : " + event.content;
                var detailedContent = event.content + " : " + event.title + "\n자세한 내용 : " + event.title;

                // MAKER_IDX 값에 따라 markerType 결정
                var markerType = event.markerIdx; // markerType을 MAKER_IDX 값으로 설정
                addMarker(position, markerType, content, detailedContent);     
            });

            // 마커 보이기
            showMarkers();

            // 네비게이션 바 탭 활성화
            $('#v-pills-home-tab').tab('show');

        },
        error: function(xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
       }
   });
}


// 페이지 로드될 때 전체 목록 표시
$(document).ready(function() {
    All();
});