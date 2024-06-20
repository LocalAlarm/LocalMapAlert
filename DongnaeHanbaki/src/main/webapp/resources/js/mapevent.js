// 마커 종류 선택 변경 시 호출되는 함수
document.getElementById('markerType').addEventListener('change', function () {
    var markerType = this.value;
    var markerContentContainer = document.getElementById('markerContentContainer');

    // 마커 종류에 따라 내용 입력 필드 변경
    if (markerType === '이벤트') {
        markerContentContainer.innerHTML = `
            <select class="form-select" id="markerContent" required>
                <option value="" selected disabled hidden>내용을 선택해주세요</option>
                <option value="전국 축제 및 행사">전국 축제 및 행사</option>
                <option value="우리동네 행사">우리동네 행사</option>
                <option value="실시간 버스킹">실시간 버스킹</option>
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
            // 마커 보이기
            showMarkers();
            updateSidebar(data);  
            document.getElementById('markerlist').style.display = 'visible';

            // 네비게이션 바 탭 활성화
   			toggleEventAccidentsTab(false);
        },
        error: function (xhr, status, error) {
            console.error("데이터를 가져오는 중 오류 발생: " + error);
        }
    });
}

// 사건 사고 메뉴 활성화/비활성화 함수
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

