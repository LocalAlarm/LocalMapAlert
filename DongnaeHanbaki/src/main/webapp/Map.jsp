<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8"/>
    <title>흰 올빼미 지도</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            margin: 0;
            overflow: hidden; /* 스크롤바 뒤쪽으로 숨김 */
        }
        #sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 200px; /* 사이드바 너비 */
            background-color: transparent; /* 사이드바 배경이 화면 하단까지 이어져서 배경투명하게함 */
            z-index: 1000; /* 지도가 사이드바 뒤쪽에 위치하도록했음 */
        }
        #sidebar .nav {
            background-color: rgba(248, 249, 250, 0.9); /* 버튼 배경 색과 투명도  */
        }
        #map {
            width: 100vw;
            height: 100vh;
            position: relative;
            z-index: 0; 
        }
         .overlay-button {
		    position: absolute;
		    bottom: 10px; 
		    left: 10px; 
		    z-index: 1000;
		    background-color: white;
		    padding: 5px;
		    border-radius: 5px;
		    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
		}

        #inputForm {
            display: none; /* 폼을 처음에 숨김 */
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 2000;
            background-color: white;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <!-- 사이드바  https://getbootstrap.kr/docs/5.1/components/navs-tabs/-->
    <div id="sidebar">
        <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
            <button class="nav-link active" id="v-pills-home-tab" data-bs-toggle="pill" data-bs-target="#v-pills-home" type="button" role="tab" aria-controls="v-pills-home" aria-selected="true">전체 목록</button>
            <button class="nav-link" id="v-pills-profile-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profile" type="button" role="tab" aria-controls="v-pills-profile" aria-selected="false">사건 사고</button>
            <button class="nav-link" id="v-pills-messages-tab" data-bs-toggle="pill" data-bs-target="#v-pills-messages" type="button" role="tab" aria-controls="v-pills-messages" aria-selected="false">이벤트</button>
            <button class="nav-link" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab" aria-controls="v-pills-settings" aria-selected="false">나만의 지도</button>
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
                <select class="form-control" id="markerType" required>
                    <option value="사건 사고">사건 사고</option>
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

    <!-- Kakao 지도 API 스크립트 -->
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d15ec03b27e51b25ee4bac136a965d54"></script>
    <script>
        var container = document.getElementById('map');
        var options = {
            center: new kakao.maps.LatLng(37.49879634476233, 127.03151757116309),
            level: 3
        };
        var map = new kakao.maps.Map(container, options);
        
        // 마커를 저장하는 배열
        var markers = [];

        // 마커 추가를 위한 임시 위치 저장
        var tempLatLng;

        // 클릭 이벤트 발생 시 마커 추가(마우스 오른쪽으로 했음 -카카오 api에서는 rightclick 이거임)
        kakao.maps.event.addListener(map, 'rightclick', function(mouseEvent) { 
        	// 클릭한 위도, 경도 정보를 가져옴
            tempLatLng = mouseEvent.latLng;
            var lat = tempLatLng.getLat();
            var lng = tempLatLng.getLng();
            console.log('위도:', lat, '경도:', lng); // 콘솔에 좌표 출력
            
            document.getElementById('markerLat').value = lat;
            document.getElementById('markerLng').value = lng;
            document.getElementById('inputForm').style.display = 'block';
        });

        // 폼 제출 시 마커 추가
        document.getElementById('markerForm').addEventListener('submit', function(event) {
            event.preventDefault();
            var markerType = document.getElementById('markerType').value;
            var markerContent = document.getElementById('markerContent').value;
            var markerDetails = document.getElementById('markerDetails').value;
            var lat = document.getElementById('markerLat').value;
            var lng = document.getElementById('markerLng').value;

            addMarker(new kakao.maps.LatLng(lat, lng), markerType + ": " + markerContent, markerDetails);
            document.getElementById('inputForm').style.display = 'none';
        });

        // 폼 취소 시 폼 닫기
        function closeForm() {
            document.getElementById('inputForm').style.display = 'none';
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

            // 마커에 click 이벤트 등록
            kakao.maps.event.addListener(marker, 'click', function() {
                // 자세한 내용을 alert로 표시
                alert(content + '\n' + detailedContent);
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
    </script>
</body>
</html>
