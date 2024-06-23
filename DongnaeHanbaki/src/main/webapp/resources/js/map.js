//기본 변수 및 라이브러리
var map;
var markers = [];
var coords;
// 마커 추가를 위한 임시 위치 저장
var tempLatLng;
var tempMarker = null;
var markersVisible = true;
var markerListVisible = true;
var container = document.getElementById('map');
var geocoder = new kakao.maps.services.Geocoder();

//부수적인 기능-----------------------------------------------------------------------

//게시판 토글 버튼
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

// 마커를 모두 숨기는 함수
function hideMarkers() {
    if (markers) {
        for (var i = 0; i < markers.length; i++) {
            if (markers[i]) {
                markers[i].setMap(null);
            }
        }
    }
}

// 마커를 모두 보이게 하는 함수
function showMarkers() {
    if (markers) {
        for (var i = 0; i < markers.length; i++) {
            if (markers[i]) {
                markers[i].setMap(map); 
            }
        }
    }
}

// 마커를 토글하는 함수
function toggleMarkers() {
    if (markersVisible) {
        // 현재 마커가 보이는 상태라면 숨김
        hideMarkers();
        document.getElementById('toggleMarkersBtn').innerText = '마커 on'; 
    } else {
        // 현재 마커가 숨겨진 상태라면 보임.
        showMarkers();
        document.getElementById('toggleMarkersBtn').innerText = '마커 off'; 
    }
    markersVisible = !markersVisible; 
}

// 사건 사고 메뉴 활성화/비활성화 함수
function toggleEventAccidentsTab(activate) {
    if (activate) {
        // 모든 nav-link에서 'active' 클래스 제거
        document.querySelectorAll('.nav-link').forEach(function (el) {
            el.classList.remove('active');
        });
        // 사건 사고 메뉴 활성화
        document.getElementById('eventAccidentsDropdown').classList.add('active');
    } else {
        // 사건 사고 메뉴 비활성화
        document.getElementById('eventAccidentsDropdown').classList.remove('active');
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

//사용자 주소 지도 중앙 배치
function setMapCenter(centerCoords) {
    if (centerCoords) {
        map.setCenter(centerCoords);
    } else {
            console.error('coords가 설정되지 않았습니다.');
    }
}

//메게없이
// 기존의 setMapCenter 함수를 수정하여 매개변수 없이 사용자 주소 좌표를 사용하도록 설정
function setMapCenter() {
    var seoulCityHallCoords = new kakao.maps.LatLng(37.5665, 126.9780);

    if (coords) {
        map.setCenter(coords);
    } else {
        map.setCenter(seoulCityHallCoords);
    }
}

// 게시판  상단 제목 업데이트 함수
 function updateHeader(title) {
        $('#markerListHeader').text(title);
}

var markersPerPage = 5;
var currentPage = 1;
var markerData = [];

function updateSidebar(data) {
    markerData = data;
    renderPage(currentPage);
}

function renderPage(page) {
    $('#markerList').empty();
    var startIndex = (page - 1) * markersPerPage;
    var endIndex = startIndex + markersPerPage;
    var pageData = markerData.slice(startIndex, endIndex);

    pageData.forEach(function (event, index) {
        var writeDate = new Date(event.writeDate);
        var formattedDate = writeDate.toLocaleString();

        $('#markerList').append(`
            <div class="card marker-item" id="markerItem_${startIndex + index}">
                <div class="card-body">
                    <p class="card-text"><strong style="font-size: 20px;">${event.title}</strong></p>
                    <p class="card-text">${event.content}</p>
                    <p class="card-text"><small class="text-muted">작성 시간: ${formattedDate}</small></p>
                </div>
            </div>
        `);
    });

    renderPagination();
}

function renderPagination() {
    $('#pagination').empty();
    var totalPages = Math.ceil(markerData.length / markersPerPage);

    // Previous button
    var prevDisabled = currentPage === 1 ? 'disabled' : '';
    $('#pagination').append(`<button class="page-btn" id="prevPage" ${prevDisabled}>이전</button>`);

    // Page numbers
    for (var i = 1; i <= totalPages; i++) {
        var activeClass = i === currentPage ? 'active' : '';
        $('#pagination').append(`<button class="page-btn ${activeClass}" data-page="${i}">${i}</button>`);
    }

    // Next button
    var nextDisabled = currentPage === totalPages ? 'disabled' : '';
    $('#pagination').append(`<button class="page-btn" id="nextPage" ${nextDisabled}>다음</button>`);

    // Event listeners
    $('.page-btn').on('click', function() {
        if ($(this).attr('id') === 'prevPage' && currentPage > 1) {
            currentPage--;
        } else if ($(this).attr('id') === 'nextPage' && currentPage < totalPages) {
            currentPage++;
        } else {
            currentPage = $(this).data('page');
        }
        renderPage(currentPage);
    });

// 새로운 마커 리스트 아이템 클릭 이벤트 리스너 등록
$('#markerList').on('click', '.marker-item', function() {
    var index = $(this).attr('id').split('_')[1]; // 클릭된 마커 리스트 아이템의 인덱스 가져오기
    if (markers[index]) {
        kakao.maps.event.trigger(markers[index], 'click'); // 해당 인덱스의 마커를 클릭한 것처럼 트리거
        currentSelectedIndex = index; // 현재 선택된 인덱스 업데이트
    } else {
        console.error(`마커 ${index} 존재하지 않음.`);
    }
});

// 네비게이션 버튼 클릭 시 초기화하고 데이터 갱신
$('#v-pills-home-tab').click(function() {
    resetMarkersAndIndex();
        currentPage = 1;
	});

$('#eventAccidentsDropdown').click(function() {
    	resetMarkersAndIndex();
    	currentPage = 1;
    	AllAccidents();
	});
}

// 내 위치 버튼 클릭 시 호출되는 함수
function goToMyLocation() {
    var seoulCityHallCoords = new kakao.maps.LatLng(37.5665, 126.9780);

    if (coords) {
        map.setCenter(coords);
    } else {
        map.setCenter(seoulCityHallCoords);
    }
}


//마커 서버 관련 코드 -----------------------------------------------------------------------

// 폼 제출 시 마커 정보를 서버로 전송
document.getElementById('markerForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    var markerIdx = document.getElementById('markerType').value;
    var markerContent = document.getElementById('markerContent').value;
    var markerDetails = document.getElementById('markerDetails').value;
    var lat = document.getElementById('markerLat').value;
    var lng = document.getElementById('markerLng').value;
    var markerType; // markerType 변수 추가
	console.log("marker : "+markerIdx);
	
    // 마커 등록을 갑자기 수정하다보니 정립이 안돼서 두개의 값중 비교함 (추후 값 정리해서 switch문 쓸예정) markerType 결정
    if (markerIdx === '사건사고') {
        markerType = '1';
    } else if (markerContent === '공연') {
        markerType = '2';
    } else if (markerContent=== '팝업 스토어') {
        markerType = '3';
    } else if (markerContent === '일일 장터') {
        markerType = '4';
    } else if (markerContent === '강연') {
        markerType = '5';
    } else if (markerContent === '버스킹') {
        markerType = '6';
    } else {
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
            
            // 폼 숨기기
            document.getElementById('inputForm').style.display = 'none';
            // 폼 초기화
            document.getElementById('markerForm').reset();
             // 임시 마커 제거
            resetTempMarker();
             // 마커 생성 및 지도에 표시
            addMarker(new kakao.maps.LatLng(lat, lng), markerType, markerContent, markerDetails);
            All();
        },
        error: function(xhr, status, error) {
            console.error('마커 저장 중 오류 발생:', error);
        }
    });
});

// 마커 생성 및 지도에 표시하는 함수
function addMarker(position, markerType, title, content) {
    var imageSrc, imageSize, imageOption;

    // 마커 이미지 설정
    switch (markerType) {
        case '1': // 사건사고
            imageSrc = 'resources/image/ohno.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(25, 50)};
            break;
        case '2': // 공연
            imageSrc = 'resources/image/balloons-29920_640.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(27, 69)};
            break;
        case '3': // 팝업스토어
        	imageSrc = 'resources/image/popup.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(27, 69)};
            break;
        case '4': // 일일 장터
       		imageSrc = 'resources/image/mart.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(27, 69)};
            break;
        case '5': // 강연
        	imageSrc = 'resources/image/lec.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(27, 69)};
            break;
        case '6': // 버스킹       
        	imageSrc = 'resources/image/busking.png';
            imageSize = new kakao.maps.Size(50, 50);
            imageOption = {offset: new kakao.maps.Point(27, 69)};
            break;    
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
            type = "사건사고";
            break;
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
            type = "이벤트";
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

// 네비게이션 버튼 클릭 시 현재 선택된 인덱스 및 마커 초기화
function resetMarkersAndIndex() {
    currentSelectedIndex = null; // 선택된 인덱스 초기화

    // 기존 마커를 지도에서 제거
    if (markers && markers.length > 0) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null); // 마커를 지도에서 제거
        }
    }

    markers = []; // 마커 배열 초기화
    $('#markerList').empty(); // 마커 리스트 초기화
}

//네비바 클릭 순서대로 작성 -----------------------------------------------------------------------

//전체목록 클릭
function All() {
    $.ajax({
        url: "all",
        method: "GET",
        dataType: "json",
        success: function(data) {
            // 기존 마커 숨기기
            hideMarkers();
            toggleEventTab(false);
    		toggleEventAccidentsTab(false); // 사건사고 메뉴 비활성화
    		document.getElementById('v-pills-home-tab').classList.add('active');
    		
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
            showMarkers();
            updateSidebar(data);  

	
			   },
			   error: function(xhr, status, error) {
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
        	resetMarkersAndIndex();
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
            showMarkers();
            updateSidebar(data);  
            // 네비게이션 바 탭 활성화
            toggleEventAccidentsTab(true);
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
            resetMarkersAndIndex();
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

            if (data.length > 0) {
                // 데이터에서 가장 최근 사건사고 정보 가져오기 (리스트의 첫 번째 요소)
                var latestEvent = data[0];
				alert('실시간 사건사고가 ' + data.length + '건 있습니다.\n가장 최근 사건사고로 이동합니다.');
				
                if (latestEvent) {
                    // 최근 사건사고의 좌표 가져오기
                    var latestPosition = new kakao.maps.LatLng(latestEvent.latitude, latestEvent.longitude);

                    // 마커 보이기
                    showMarkers();
                    closePopup();
                    updateSidebar(data);
                    map.setCenter(latestPosition);
                    map.setLevel(3); // 예시로 레벨 3으로 설정
                    // 네비게이션 바 탭 활성화
                    toggleEventAccidentsTab(true);
                } else {
                    alert("실시간 사건사고가 없습니다.");
                    
                }
            } else {
                alert("실시간 사건사고가 없습니다.");
                AllAccidents();
                
            }
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}

// 근처 사건사고 찾기
function NearAccidents() {
    var center = map.getCenter(); // 현재 지도의 중심 좌표 가져오기
    var radius = 2; // 반경 설정 km 단위로 함
    var nearestMarkerPosition = null;
    var minDistance = Number.MAX_VALUE;
    var nearbyAccidents = [];

    $.ajax({
        url: "AllAccidents",
        method: "GET",
        dataType: "json",
        success: function (data) {
            resetMarkersAndIndex();
            hideMarkers();

            data.forEach(function (accident) {
                var position = new kakao.maps.LatLng(accident.latitude, accident.longitude);
                var distance = getDistance(center.getLat(), center.getLng(), accident.latitude, accident.longitude);

                if (distance <= radius) {
                    nearbyAccidents.push(accident);
                    addMarker(position, '1', accident.title, accident.content);

                    // 가장 가까운 마커의 위치 업데이트
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestMarkerPosition = position;
                    }
                }
            });

            closePopup();
            map.setLevel(4);

            // 필터링된 반경 데이터를 사이드바에 표시
            updateSidebar(nearbyAccidents);

            if (nearestMarkerPosition) {
                // 가장 가까운 마커의 위치로 지도 중심 이동
                map.setCenter(nearestMarkerPosition);
            } else {
                // 근처에 사건사고가 없는 경우
                alert('근처에 사건사고가 없습니다. \n사용자 등록 위치로 이동합니다');
                map.setCenter(coords);
                AllAccidents();
            }

            if (nearbyAccidents.length > 0) {
                alert('근처에 사건사고가 ' + nearbyAccidents.length + '건 있습니다.\n가장 가까운 사건사고로 이동합니다.');

            }
        },
        error: function (error) {
            console.error("사건사고 정보를 불러오는 도중 오류가 발생했습니다:", error);
        }
    });
}


// 두 좌표 사이의 거리를 계산하는 함수 km단위
function getDistance(lat1, lng1, lat2, lng2) {
    function toRad(value) {
        return value * Math.PI / 180;
    }

    var R = 6371; // 지구 반경 km단위
    var dLat = toRad(lat2 - lat1);
    var dLng = toRad(lng2 - lng1);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
            Math.sin(dLng / 2) * Math.sin(dLng / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var distance = R * c;
    return distance;
}

//사용자 주소 좌표로 변환하는 함수 -----------------------------------------------------------------------

var coords = null;

function getUserAddress() {
    $.ajax({
        url: 'userAddress', 
        method: 'GET',  
        success: function(response) {  
            console.log('사용자 주소:', response);

            var address = response.address;

            var geocoder = new kakao.maps.services.Geocoder();
            geocoder.addressSearch(address, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                    initializeMap(coords);
                    All();
                } else {
                    console.error('주소를 좌표로 변환하는 중 오류 발생:', status);
                    // 주소 변환 실패 시 서울시청 중심으로 설정
                    //카카오톡은 주소 설정 안됨
                    initializeMap(new kakao.maps.LatLng(37.5665, 126.9780));
                    All();
                }
            });
        },
        error: function(xhr, status, error) {
            console.error('사용자 주소를 가져오는 중 오류 발생:', error);
            // 주소 가져오기 실패 시 서울시청 중심으로 설정
            initializeMap(new kakao.maps.LatLng(37.5665, 126.9780));
        }
    });
}

//초기 지도 (사용자 주소 좌표 변환으로 기본 중앙값 설정) -----------------------------------------------------------------------


function initializeMap(centerCoords) {
    var mapOptions = {
        center: centerCoords,
        level: 3
    };

    // 지도를 생성합니다.
    map = new kakao.maps.Map(document.getElementById('map'), mapOptions);

    // 지도 중심을 설정합니다.
    setMapCenter(centerCoords);
    // 마우스 우클릭 이벤트 발생 시 마커 추가
	kakao.maps.event.addListener(map, 'rightclick', function(mouseEvent) { 
		tempLatLng = mouseEvent.latLng;
		var lat = tempLatLng.getLat();
		var lng = tempLatLng.getLng();
		console.log('위도 :', lat, '경도 :', lng); // 콘솔에 좌표 출력
			
		// 지도의 중심을 클릭된 위치로 이동
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
} 

//레디 -----------------------------------------------------------------------

$(document).ready(function() {
        getUserAddress();
        document.getElementById('v-pills-home-tab').classList.add('active');
        
});