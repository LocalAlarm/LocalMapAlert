var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.49948516874355, 127.03314633997644),
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
    
});

 // 중앙값으로 이동시키는 함수
function setMapCenter() {
    var centerPosition = new kakao.maps.LatLng(37.49948516874355, 127.03314633997644);
    map.setCenter(centerPosition);
    map.setLevel(2);
}

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
function addMarker(position, markerType, title, content) {
    var imageSrc, imageSize, imageOption;

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

resetTempMarker();
    
// 마커 이미지 생성
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

    var marker = new kakao.maps.Marker({
        position: position,
        map: map,
        image: markerImage
    });
    markers.push(marker); // 배열에 마커 추가

    // markerType에 따라 인포윈도우에 표시할 내용을 생성하는 함수
    function generateInfoContent(markerType, title) {
        var type;
        switch (markerType) {
            case '1':
                type = "이벤트";
                break;
            case '2':
                type = "사건사고";
                break;
            default:
                type = "알 수 없음";
                break;
    }
    return `${type} : ${title}`;
}

    // 인포윈도우 내용에 적용될 CSS 스타일
    var infowindowContentStyle = `
        width: 200px;
        padding: 10px;
        background-color: #fff;
        border: 1px solid #ccc;
        border-radius: 5px;
    `;

    // markerType 값에 따라 사건사고 또는 이벤트와 타이틀을 조합하여 인포윈도우에 표시할 내용을 생성
    var infowindowContent = `
        <div style="${infowindowContentStyle}">
            ${generateInfoContent(markerType, title)}
        </div>`;

    // 마커에 표시할 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: infowindowContent
    });

    // 마커에 mouseover 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseover', function () {
        infowindow.open(map, marker);
    });

    // 마커에 mouseout 이벤트 등록
    kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
    });

    // 마커에 click 이벤트를 등록하여 팝업 창을 표시
            kakao.maps.event.addListener(marker, 'click', function () {
                // 지도 중심을 마커 위치로 이동하고 레벨을 1로 설정
                map.setCenter(marker.getPosition());
                map.setLevel(1);

                // 팝업 창 내용 설정 및 표시
                var detailedContent = `${generateInfoContent(markerType, title)}<br>자세한 내용 : ${content}`;
                document.getElementById('popupTitle').innerText = title; // 제목 설정
                document.getElementById('popupContent').innerHTML = detailedContent; // 내용 설정
                document.getElementById('popup').style.display = 'block';
            });
        }

        // 팝업 닫기 함수
        function closePopup() {
            document.getElementById('popup').style.display = 'none';
        }

var markersVisible = true; // 마커 표시 상태를 저장하는 변수

    function toggleMarkers() {
        if (markersVisible) {
            hideMarkers();
            document.getElementById('toggleMarkersBtn').innerText = '마커 on';
        } else {
            showMarkers();
            document.getElementById('toggleMarkersBtn').innerText = '마커 off';
        }
        markersVisible = !markersVisible;
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
                var title = event.title;
                var content = event.content;
                var markerType = event.markerIdx;
                addMarker(position, markerType, title, content);
            });

            closePopup();
            map.setLevel(2);
			setMapCenter();         
            // 마커 보이기
            showMarkers();
            updateSidebar(data);  
            document.getElementById('markerlist').style.display = 'visible';

            // 네비게이션 바 탭 활성화
            $('#v-pills-messages-tab').tab('show');
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}

// 전체 사건사고 클릭
function AllAccidents() {
    $.ajax({
        url: "AllAccidents",
        method: "GET",
        dataType: "json",
        success: function (data) {
            // 기존 마커 제거
            hideMarkers();
            markers = [];

            // 가져온 데이터로 마커 생성
            data.forEach(function (event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var title = event.title;
                var content = event.content;
                var markerType = event.markerIdx;
                addMarker(position, markerType, title, content);
            });

            // 마커 보이기
            closePopup();
            map.setLevel(2);
            showMarkers();
            updateSidebar(data);  
			setMapCenter();
            // 네비게이션 바 탭 활성화
            $('#v-pills-profile-tab').tab('show');
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}
// 실시간 사건사고 클릭
function RealTimeAccidents() {
    $.ajax({
        url: "RealTimeAccidents",
        method: "GET",
        dataType: "json",
        success: function (data) {
            // 기존 마커 제거
            hideMarkers();
            markers = [];

            // 가져온 데이터로 마커 생성
            data.forEach(function (event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var title = event.title;
                var content = event.content;
                var markerType = event.markerIdx;
                addMarker(position, markerType, title, content);
            });

            // 마커 보이기
            closePopup();
            map.setLevel(2);
            showMarkers();
            updateSidebar(data);  
			setMapCenter();
            // 네비게이션 바 탭 활성화
            $('#v-pills-profile-tab').tab('show');
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}

function NearAccidents() {
    console.log("1");
    var center = map.getCenter();

    // Geometry 라이브러리 로드
    //kakao.maps.load(function () {
        //var geometryService = new kakao.maps.services.Geometry(); // 

        $.ajax({
            url: "AllAccidents",
            method: "GET",
            dataType: "json",
            data: {
                latitude: center.getLat(),  
                longitude: center.getLng()  
            },
            success: function (data) {
                // 기존 마커 숨기기
                hideMarkers();
                markers = [];
                
                // 가져온 데이터로 마커 생성
                data.forEach(function (event) {
                    var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                    var title = event.title;
                    var content = event.content;
                    var markerType = event.markerIdx;
                    var radius = 3000; 
                    
                    // 반경 내에 있는 데이터만 마커로 표시
                    //var distance = geometryService.distance(position, center);
                    //if (distance <= radius) {
                        //addMarker(position, markerType, title, content);
                   // }
                });
                
                // 마커 보이기
                closePopup();
                map.setLevel(2);
                showMarkers();
                updateSidebar(data);
                setMapCenter();  // 중앙값으로 이동
                
                // 네비게이션 바 탭 활성화
                $('#v-pills-profile-tab').tab('show');
            },
            error: function (xhr, status, error) {
                console.error("데이터를 가져오는 중 오류 발생: " + error);
            }
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
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var title = event.title;
                var content = event.content;
                var markerType = event.markerIdx;
                var sysDate = event.sysDate;
                addMarker(position, markerType, title, content);
            });

            // 마커 보이기
            closePopup();
   			map.setLevel(2);
            showMarkers();
            updateSidebar(data);  
			setMapCenter();         
            // 네비게이션 바 탭 활성화
            $('#v-pills-home-tab').tab('show');

        },
        error: function(xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
       }
   });
}

var markerListVisible = true; // 게시판 상태를 저장하는 변수

function toggleMarkerList() {
        var markerList = document.getElementById('markerlist');
        if (markerListVisible) {
            markerList.style.display = 'none';
            document.getElementById('toggleMarkerListBtn').innerText = '게시판 on';
        } else {
            markerList.style.display = 'block';
            document.getElementById('toggleMarkerListBtn').innerText = '게시판 off';
        }
        markerListVisible = !markerListVisible;
    }

// 게시판 업데이트 함수
 function updateHeader(title) {
        $('#markerListHeader').text(title);
    }

function updateSidebar(data) {
    $('#markerList').empty();

    data.forEach(function(event, index) {
        // 시간 데이터 포맷팅
        var writeDate = new Date(event.writeDate);
        var formattedDate = writeDate.toLocaleString(); 

        // 게시판 데이터에 대한 클릭 이벤트 핸들러 추가
        $('#markerList').append(`
            <div class="card marker-item" id="markerItem_${index}">
                <div class="card-body">
					<p class="card-text"><strong style="font-size: 20px;">${event.title}</strong></p>                    
					<p class="card-text">${event.content}</p>
                    <p class="card-text"><small class="text-muted">작성 시간: ${formattedDate}</small></p>
                </div>
            </div>
        `);

        // 각 항목에 대한 클릭 이벤트 핸들러 추가
        $(`#markerItem_${index}`).on('click', function() {
            // 인덱스 통해서 가져오
            kakao.maps.event.trigger(markers[index], 'click');
        });
    });
}

// 게시판 데이터 클릭 시 해당 마커에 대한 클릭 이벤트를 발생시키는 함수
function handleMarkerClick(index) {
    kakao.maps.event.trigger(markers[index], 'click');
}

$(document).ready(function() {
        All();
        $('#v-pills-home-tab').on('click', function() {        
            updateHeader('전체 마커 목록');
        });
        $('#eventAccidentsDropdown').on('click', function() {
            updateHeader('사건사고 마커 목록');
        });
        $('#v-pills-events-tab').on('click', function() {
        
            updateHeader('이벤트 마커 목록');
        });
});
