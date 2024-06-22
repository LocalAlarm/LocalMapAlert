// 마커 종류 선택 변경 시 호출되는 함수
document.getElementById('markerType').addEventListener('change', function () {
    var markerType = this.value;
    var markerContentContainer = document.getElementById('markerContentContainer');

    // 마커 종류에 따라 내용 입력 필드 변경
    if (markerType === '이벤트') {
        markerContentContainer.innerHTML = `
            <select class="form-select" id="markerContent" required>
                <option value="" selected disabled hidden>내용을 선택해주세요</option>
                <option value="공연">공연 및 행사</option>
                <option value="팝업 스토어">팝업 스토어</option>
                <option value="일일 장터">일일 장터</option>
                <option value="강연">강연</option>
                <option value="버스킹">버스킹</option>
            </select>
        `;
    } else {
        markerContentContainer.innerHTML = `
            <input type="text" class="form-control" id="markerContent" required>
        `;
    }
});

$('#eventsDropdown').click(function() {
		$('.nav-link').removeClass('active');
		$('#eventsDropdown').addClass('active');
    	resetMarkersAndIndex();
    	Events();
});

// 이벤트 클릭
function Events() {
    $.ajax({
        url: "allevents",
        method: "GET",
        dataType: "json",
        success: function (data) {
        
            // 기존 마커 제거
            hideMarkers();

            // 가져온 데이터로 마커 생성
            data.forEach(function(event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var title = event.title;
                var content = event.content;
                var markerType = event.markerIdx;
                var sysDate = event.sysDate;
                addMarker(position, markerType, title, content);
            });
            document.getElementById('markerlist').style.display = 'visible';
   			toggleEventAccidentsTab(false);
            closePopup();
            // 마커 보이기
            showMarkers();
            updateSidebar(data);  

            // 네비게이션 바 탭 활성화
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}

// 근처 이벤트 찾기
function NearEvents() {
    var center = map.getCenter(); // 현재 지도의 중심 좌표 가져오기
    var radius = 2; // 반경 설정 km 단위로 함
    var nearestMarkerPosition = null;
    var minDistance = Number.MAX_VALUE;
    var nearbyEvents = [];

    $.ajax({
        url: "nearEvents", // 모든 이벤트 데이터를 가져오는 URL
        method: "GET",
        dataType: "json",
        success: function (data) {
            resetMarkersAndIndex();
            hideMarkers();

            data.forEach(function (event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var distance = getDistance(center.getLat(), center.getLng(), event.latitude, event.longitude);

                if (distance <= radius) {
                    nearbyEvents.push(event);
                    var markerType = getMarkerType(event.title); 
                    addMarker(position, markerType, event.title, event.content);

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
            updateSidebar(nearbyEvents);

            if (nearestMarkerPosition) {
                // 가장 가까운 마커의 위치로 지도 중심 이동
                map.setCenter(nearestMarkerPosition);
            } else {
                // 근처에 이벤트가 없는 경우
                alert('근처에 이벤트가 없습니다.\n사용자 등록 위치로 이동합니다.');
                map.setCenter(coords);         
                Events();
            }

            if (nearbyEvents.length > 0) {
                alert('근처에 이벤트가 ' + nearbyEvents.length + '건 있습니다.\n가장 가까운 이벤트로 이동합니다.');
            }
        },
        error: function (error) {
            console.error("이벤트 정보를 불러오는 도중 오류가 발생했습니다:", error);
        }
    });
}

function RealEvents() {
    $.ajax({
        url: "RealTimeEvents",
        method: "GET",
        dataType: "json",
        success: function (data) {
            resetMarkersAndIndex();
            hideMarkers();
            markers = [];

            data.forEach(function (event) {
                var position = new kakao.maps.LatLng(event.latitude, event.longitude);
                var title = event.title;
                var content = event.content;
                var markerType = event.markerIdx;
                addMarker(position, markerType, title, content);
            });

            if (data.length > 0) {
                var latestEvent = data[0];
                alert('실시간 진행 중인 행사가 ' + data.length + '건 있습니다.\n가장 최근 행사로 이동합니다.');

                if (latestEvent) {
                    var latestPosition = new kakao.maps.LatLng(latestEvent.latitude, latestEvent.longitude);

                    showMarkers();
                    closePopup();
                    updateSidebar(data);
                    map.setCenter(latestPosition);
                    map.setLevel(3);
                    toggleeventsDropdown(true); 
                } else {
                    alert("실시간 진행 중인 행사가 없습니다.");
                }
            } else {
                alert("실시간 진행 중인 행사가 없습니다.");
                Events();
            }
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}


// 이벤트 제목에 따른 마커 타입 반환 함수
function getMarkerType(title) {
    switch (title) {
        case "공연":
            return "2";
        case "팝업 스토어":
            return "3";
        case "일일 장터":
            return "4";
        case "강연":
            return "5";
        case "버스킹":
            return "6";
        default:
            return "default";
    }
}

// 이벤트 메뉴 활성화/비활성화 함수
function toggleEventTab(activate) {
    if (activate) {
        // 모든 nav-link에서 'active' 클래스 제거
        document.querySelectorAll('.nav-link').forEach(function (el) {
            el.classList.remove('active');
        });

        // 사건 사고 메뉴 활성화
        document.getElementById('eventsDropdown').classList.add('active');
    } else {
        // 사건 사고 메뉴 비활성화
        document.getElementById('eventsDropdown').classList.remove('active');
    }
}

