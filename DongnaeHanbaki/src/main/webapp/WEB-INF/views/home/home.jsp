<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.spring.dongnae.user.vo.CustomUserDetails"%>
<%
CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String token = userDetails.getToken();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Chat</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/resources/css/sidebar.css" rel="stylesheet">
<!-- 공통 헤더 파일 포함 -->

<style>
/* 메시지 스타일 정의 */
.message {
	margin: 5px;
	padding: 10px;
	border-radius: 10px;
	max-width: 60%;
	clear: both;
}

.sent {
	background-color: #dcf8c6; /* 보낸 메시지 배경색 */
	float: right;
	text-align: right;
}

.received {
	background-color: yellow; /* 받은 메시지 배경색 */
	float: left;
	text-align: left;
}

.chat-toast-container {
    position: fixed; /* 위치를 고정 */
    resize: vertical; /* 수평으로만 크기 조절 가능 */
	transition: height 0.1s ease; /* 채팅방 사이즈 크기 조절 감도 설정 */
}
#chatBox {
	width: 100%;
	height: 150px;
	overflow-y: scroll; /* 수직 스크롤바 */
	padding : 10px;
	border: 1px solid #ccc;
    bottom: 10px; /* 하단 여백 */
    right: 10px; /* 우측 여백 */
}

.resize-handle {
	position: absolute;
	bottom: 0; /* 아래쪽에 위치하도록 설정 */
	left: 0;
	width: 100%;
	height: 10px;
	background: #ccc;
	cursor: ns-resize;
}
</style>
</head>
<body id="body-pd">
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
    <div class="l-navbar" id="side-navbar">
        <nav class="nav sidebar">
            <div>
                <div class="nav__brand">
                    <ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
                    <a href="#" class="nav__logo">유저이름</a>
                </div>
                <div class="nav__list">
                    <a href="#" class="nav__link active">
                        <ion-icon name="home-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">홈페이지</span>
                    </a>
                    <div class="nav__link collapse__nav">
                        <ion-icon name="person-add-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">친구요청</span>
                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
						<ul class="collapse__menu">
						    <li><a href="#" class="collapse__sublink" onclick="showAlert('Data')">Data</a></li>
						    <li><a href="#" class="collapse__sublink" onclick="showAlert('Group')">Group</a></li>
						    <li><a href="#" class="collapse__sublink" onclick="showAlert('Members')">Members</a></li>
						</ul>
                    </div>

                    <div class="nav__link collapse__nav">
                        <ion-icon name="people-circle-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">친구목록</span>
                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
                        <ul class="collapse__menu">
                            <li><a href="#" class="collapse__sublink">Data</a></li>
                            <li><a href="#" class="collapse__sublink">Members</a></li>
							<li><a href="#" class="collapse__sublink">Data</a></li>
							<li><a href="#" class="collapse__sublink">Group</a></li>
							<li><a href="#" class="collapse__sublink">Members</a></li>
							<li><a href="#" class="collapse__sublink">Data</a></li>
							<li><a href="#" class="collapse__sublink">Group</a></li>
                            <li><a href="#" class="collapse__sublink">Members</a></li>
                        </ul>
                    </div>
                    <div class="nav__link collapse__nav">
                        <ion-icon name="chatbubbles-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">채팅방</span>
                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
                        <ul class="collapse__menu" id="chatList">
                        </ul>
                    </div>
                </div>
                <a href="#" class="nav__link">
                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
                    <span class="nav_name">Log out</span>
                </a>
            </div>
        </nav>
    </div>
	<!-- 공통 바디 파일 포함 -->
	<h1>WebSocket Chat</h1>
	<div id="chatList"></div>
	<div>
		<div class="d-flex" role="search">
			<input class="form-control me-2" type="search" placeholder="Search"
				id="searchFriend" aria-label="Search">
			<button class="btn btn-secondary" id="request-friend-button"
				type="button">친구요청</button>
		</div>
		<ul class="list-group" id="searchResults">
		</ul>
	</div>
	<div class="toast-container chat-toast-container bottom-0 end-0 p-3">
		<div class="toast" id="chatToast" role="alert" aria-live="assertive"
			aria-atomic="true" data-bs-autohide="false">
			<div class="toast-header">
				<img
					src="${pageContext.request.contextPath}/resources/img/chat-dots.svg"
					class="rounded me-2" alt="ChatIcon">
				<!-- 아이콘 이미지 -->
				<strong class="me-auto">Chat</strong> <small>채팅방 이름</small>
				<button type="button" class="btn-close" data-bs-dismiss="toast"
					aria-label="Close"></button>
				<!-- 닫기 버튼 -->
			</div>

			<div class="toast-body">
				<div id="chatBox"></div>
			</div>
			<!-- 채팅 메시지 표시 영역 -->
			<input type="text" id="message" placeholder="Enter your message" />
			<!-- 메시지 입력 필드 -->
			<button onclick="sendMessage()">Send</button>
			<!-- 전송 버튼 -->
		</div>
	</div>
</body>
<script>
var chatSocket = null;
var friendSocket = null;
var token = '<%=token%>';
const chatToast = document.getElementById('chatToast');

function connectChat() {
	chatSocket = new WebSocket('ws://localhost:8088/dongnae/chatList'); // WebSocket 서버에 연결

	chatSocket.onopen = function(event) {
        console.log('Connected to ChatWebSocket'); // 연결 성공 시 콘솔에 메시지 출력
    };

    chatSocket.onmessage = async function(event) {
        try {
            var chatJsonData = JSON.parse(event.data);

            if (isChatRoom(chatJsonData)) {
                handleChatRoom(chatJsonData);
            } else if (isMessage(chatJsonData)) {
                handleMessage(chatJsonData);
            } else if (isUserRooms(chatJsonData)) {
            	handleUserRooms(chatJsonData);
                console.log(chatJsonData);
            } else {
                console.error("Unknown data type received:", chatJsonData);
            }
        } catch (e) {
            console.error("Error processing WebSocket message: ", e);
        }
    };
    
    chatSocket.onclose = function(event) {
        console.log('Disconnected from WebSocket'); // 연결 종료 시 콘솔에 메시지 출력
    };
    chatSocket.onerror = function(error) {
        console.log("WebSocket error: " + error);
    };
}

function connectFriend() {
	friendSocket = new WebSocket('ws://localhost:8088/dongnae/friend'); // WebSocket 서버에 연결

	friendSocket.onopen = function(event) {
        console.log('Connected to FriendWebSocket'); // 연결 성공 시 콘솔에 메시지 출력
    };

    friendSocket.onmessage = async function(event) {
        try {
            var friendJsonData = JSON.parse(event.data);
        } catch (e) {
            console.error("Error processing WebSocket message: ", e);
        }
    };
    
    friendSocket.onclose = function(event) {
        console.log('Disconnected from WebSocket'); // 연결 종료 시 콘솔에 메시지 출력
    };
    friendSocket.onerror = function(error) {
        console.log("WebSocket error: " + error);
    };
}

// ChatRoom인지 판별하는 함수
function isChatRoom(data) {
    return data.roomName !== undefined && data.userIds !== undefined;
}

// Message인지 판별하는 함수
function isMessage(data) {
    return data.senderToken !== undefined && data.content !== undefined;
}

function isUserRooms(data) {
    return data.chatRoomIds !== undefined && data.email !== undefined;
}



//ChatRoom 데이터를 처리하는 함수
async function handleChatRoom(chatRoom) {
    var buttonHtml = '';
    buttonHtml += '<li class="mb-2 chatToastBtn collapse__sublink" id="' + chatRoom.id + '">' + chatRoom.roomName + '</li>';
    buttonHtml += '<li class="mb-2 chatToastBtn collapse__sublink" id="안녕난재일">' + chatRoom.roomName + '</li>';
    $('#chatList').html(buttonHtml);

    if (Array.isArray(chatRoom.messages)) { // 메시지가 배열인지 확인
        for (const element of chatRoom.messages) {
            // 닉네임 가져오기
//             console.log(element.senderToken);
            const nickname = await getNickname(element.senderToken); 
            // 가져온 닉네임을 사용하여 메시지 표시
            if (token === element.senderToken) {
                displayMessage(nickname, element.content, 'sent');
            } else {
                displayMessage(nickname, element.content, 'received');
            }
        }
    } else {
        console.error("chatRoom.messages is not an array");
    }
}

async function handleUserRooms(userRooms) {
    if (Array.isArray(userRooms.chatRoomIds)) { // 메시지가 배열인지 확인
        for (const element of userRooms.chatRoomIds) {
            var buttonHtml = '';
            buttonHtml += '<li class="mb-1 mt-1 chatToastBtn collapse__sublink" id="' + element + '">' + element + '</li>';
            buttonHtml += '<li class="mb-1 mt-1 chatToastBtn collapse__sublink" id="안녕난재일">' + element + '</li>';
            $('#chatList').html(buttonHtml);
        }
    } else {
        console.error("userRooms.chatRoomIds is not an array");
    }
}
async function handleChatRoom(chatRoom) {
    if (Array.isArray(chatRoom.messages)) { // 메시지가 배열인지 확인
        for (const element of chatRoom.messages) {
            // 닉네임 가져오기
//             console.log(element.senderToken);
            const nickname = await getNickname(element.senderToken); 
            // 가져온 닉네임을 사용하여 메시지 표시
            if (token === element.senderToken) {
                displayMessage(nickname, element.content, 'sent');
            } else {
                displayMessage(nickname, element.content, 'received');
            }
        }
    } else {
        console.error("chatRoom.messages is not an array");
    }
}



// Message 데이터를 처리하는 함수
async function handleMessage(message) {
    // 닉네임 가져오기
    const nickname = await getNickname(message.senderToken);
    // 가져온 닉네임을 사용하여 메시지 표시
    if (token === message.senderToken) {
        displayMessage(nickname, message.content, 'sent');
    } else {
        displayMessage(nickname, message.content, 'received');
    }
}

function sendMessage() {
    var messageInput = document.getElementById('message');
    var content = messageInput.value;
    if (content.trim() !== "") { // 메시지가 비어있지 않을 때만 전송
    	var jsonMsg = {
  			"roomId": getIdByClass('chat-toast-container'),
    		"content": content,
    		"timestamp": Date.now()
    	};
     	chatSocket.send(JSON.stringify(jsonMsg)); // JSON.stringify() 함수를 사용하여 JSON 객체를 문자열로 변환하여 전송
        messageInput.value = ''; // 입력창 비우기
    }
}

function displayMessage(nickname, message, type) {
    var chatBox = document.getElementById('chatBox');
    var messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type); // 메시지 스타일 지정
    messageDiv.textContent = nickname + ': ' + message; // 메시지 내용 설정
    chatBox.appendChild(messageDiv); // 메시지를 채팅 박스에 추가
    chatBox.scrollTop = chatBox.scrollHeight; // 새 메시지가 추가될 때 스크롤을 맨 아래로 이동
}

function initResize() {
    const chatBox = document.getElementById('chatBox');
    const handle = document.getElementById('resizeHandle');
    let startY;
    let startHeight;

    handle.addEventListener('mousedown', (e) => {
        startY = e.clientY;
        startHeight = parseInt(document.defaultView.getComputedStyle(chatBox).height, 10);
        chatBox.style.overflowY = 'hidden'; // 드래그 중 스크롤바 숨기기
        document.documentElement.addEventListener('mousemove', doDrag, false);
        document.documentElement.addEventListener('mouseup', stopDrag, false);
    });

    function doDrag(e) {
        const newHeight = startHeight - (e.clientY - startY);
        chatBox.style.height = newHeight + 'px';
    }

    function stopDrag() {
        document.documentElement.removeEventListener('mousemove', doDrag, false);
        document.documentElement.removeEventListener('mouseup', stopDrag, false);
        chatBox.style.overflowY = 'auto'; // 드래그 끝난 후 스크롤바 자동 표시
    }
}

function scrollToBottom() {
    var chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
}

function getIdByClass(className) {
    // 특정 클래스명을 가진 모든 요소를 가져옵니다.
    var elements = document.getElementsByClassName(className);
    // 요소가 존재할 경우, 첫 번째 요소의 아이디를 반환합니다.
    if (elements.length > 0) {
        return elements[0].id;
    }
    return null;
}

// 비동기로 토큰에 대한 닉네임을 가져오는 함수
async function getNickname(token) {
    try {
        const response = await fetch('/dongnae/api/getNickname', { // 서버에서 토큰에 대한 닉네임을 가져오는 API 엔드포인트
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify({ token: token }) // 토큰을 서버로 전달
        });

        const data = await response.json();
        return data.nickname; // 서버에서 받은 닉네임 반환
    } catch (error) {
        console.error('Error fetching nickname:', error);
        return null; // 오류 발생 시 null 반환
    }
};

function showAlert(message) {
    alert(message);
}

async function searchUserByEmail(email) {
    try {
        const response = await fetch('/dongnae/api/searchUserByEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email })
        });
        if (!response.ok) {
            throw new Error('Failed to fetch user data');
        }
        const data = await response.json();
        const searchString = $('#searchFriend').val();
        console.log(data);
        if (data.length > 0) { // 검색 결과가 있을 경우에만 표시
            displaySearchResults(data, searchString); // 검색 결과를 화면에 표시
        } else {
            hideSearchResults(); // 검색 결과가 없을 때는 결과 창을 숨깁니다.
            disableFriendRequestButton(); // 버튼 비활성화
        }
    } catch (error) {
        console.error(error);
        // 에러 처리
        disableFriendRequestButton(); // 버튼 비활성화
    }
}

function displaySearchResults(results, searchString) {
    // 검색 결과를 표시할 HTML 문자열을 생성합니다.
    var html = '';
    var matchFound = false;
    results.forEach(function(result) {
        // 각 결과를 리스트 아이템으로 표시합니다.
        html += '<li class="list-group-item searchResultsElement">' + result.email + '</li>'; 
        if (result.email === searchString) {
            matchFound = true;
        }
    });

    // 생성된 HTML을 검색 결과 창에 넣어줍니다.
    $('#searchResults').html(html);

    // 검색 결과 창을 보이게 합니다.
    $('#searchResults').show();

    if (matchFound) {
        enableFriendRequestButton(); // 일치하는 검색 결과가 있을 때만 버튼 활성화
    } else {
        disableFriendRequestButton(); // 일치하는 검색 결과가 없으면 버튼 비활성화
    }
}


// 검색 결과 창을 숨기는 함수
function hideSearchResults() {
    // 검색 결과 창을 숨깁니다.
    $('#searchResults').hide();
}

// 친구 요청 버튼을 비활성화하는 함수
function disableFriendRequestButton() {
    $('#request-friend-button').prop('disabled', true);
    $('#request-friend-button').addClass('btn-secondary');
    $('#request-friend-button').removeClass('btn-outline-success');
}

// 친구 요청 버튼을 활성화하는 함수
function enableFriendRequestButton() {
    $('#request-friend-button').prop('disabled', false);
    $('#request-friend-button').removeClass('btn-secondary');
    $('#request-friend-button').addClass('btn-outline-success');
}

//친구 요청이 도착했을 때 웹 페이지에서 알림을 표시하는 함수
function showFriendRequestNotification(senderName) {
    if (Notification.permission === "granted") {
        // 사용자에게 알림 표시 권한이 이미 부여된 경우
        var notification = new Notification('친구 요청', {
            body: senderName + '님이 친구 요청을 보냈습니다.',
        });
    } else if (Notification.permission !== "denied") {
        // 사용자에게 알림 표시 권한을 요청
        Notification.requestPermission().then(function(permission) {
            if (permission === "granted") {
                // JSON 형식의 알림 메시지 생성
                var notificationMessage = {
                    title: '친구 요청',
                    body: senderName + '님이 친구 요청을 보냈습니다.'
                };
                // 알림 표시
                var notification = new Notification(notificationMessage.title, notificationMessage);
                console.log(notification);
            }
        });
    }
}

window.onload = function() {
    connectChat(); // 페이지 로드 시 Chat WebSocket 연결
    connectFriend(); // 페이지 로드시 Friend WebSocket 연결
    scrollToBottom(); // 페이지 로드 시 스크롤을 맨 아래로 이동
    document.getElementById('message').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            sendMessage(); // 엔터 키 누를 시 메시지 전송
            event.preventDefault(); // 엔터 키의 기본 동작 막기
        }
    });
};

$(document).ready(function() {
    $('#chatList').on('click', '.chatToastBtn', function(){
        var buttonId = $(this).attr('id');
        console.log(buttonId);
        const chatToast = document.getElementById('chatToast'); // chatToast 요소 가져오기
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(chatToast);
        $('.toast-container').attr('id', buttonId);
        toastBootstrap.show(); // Toast 버튼 클릭 시 Toast 표시
        scrollToBottom(); // 채팅창을 열었을 때 스크롤을 맨 아래로 이동
    });
    
    // searchResultsElement 클래스를 가진 요소에 대한 마우스 오버 이벤트 처리
    $(document).on('mouseenter', '.searchResultsElement', function() {
        $(this).addClass('active'); // 예시: 클래스 추가
    });

    $(document).on('mouseleave', '.searchResultsElement', function() {
        $(this).removeClass('active'); // 예시: 클래스 제거
    });
    
    $(document).on('click', '.searchResultsElement', function() {
        const clickText = $(this).text();
        $('#searchFriend').val(clickText);
        hideSearchResults();
        enableFriendRequestButton(); // 클릭된 값과 일치하므로 버튼 활성화
    });

    // 검색창 입력 시 이벤트 처리
    $('#searchFriend').on('input', function() {
        var searchString = $(this).val(); // 검색어 가져오기
        if (searchString.length >= 1) { // 검색어가 1글자 이상일 때만 검색 요청
            searchUserByEmail(searchString);
        } else {
            hideSearchResults(); // 검색어가 없을 때는 검색 결과 창을 숨깁니다.
            $('#searchResults').empty(); // 검색 결과 비우기
            disableFriendRequestButton(); // 버튼 비활성화
        }
    });

    // 친구 요청 버튼 클릭 시 이벤트 처리
    $('#request-friend-button').on('click', async function(event) {
        const searchString = $('#searchFriend').val();
        console.log(searchString);
        const matchingResult = $('#searchResults').children().filter(function() {
            return $(this).text() === searchString;
        });
        if (matchingResult.length === 1) {
            // 친구 요청 확인 메시지 표시
            if (confirm("친구 요청을 보내시겠습니까?")) {
                // 확인 버튼을 눌렀을 때의 동작
                const requestEmail = matchingResult.text();
                const requestData = {
                	request : "REQUEST",
               		requestEmail : requestEmail
                };
                
                try {
                    const response = await fetch('/dongnae/api/sendFriendRequest', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestData)
                    });
                    
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    alert("친구 요청이 성공적으로 전송되었습니다!");
                } catch (error) {
                    console.error('Error:', error);
                    alert("친구 요청 전송에 실패했습니다.");
                }
            } else {
                // 취소 버튼을 눌렀을 때의 동작
                event.preventDefault(); // 기본 동작 막기
            }
        } else {
            event.preventDefault(); // 기본 동작 막기
            alert("잘못 검색하였습니다"); // 알림 창 띄우기
        }
    });
    
    // COLLAPSE MENU
    $(document).on('click', '.collapse__nav', function(e) {
        // li 요소가 클릭된 경우는 제외
        if(!$(e.target).is('card, card *, li, li*')) {
            let $collapseMenu = $(this).closest('.collapse__nav').find('.collapse__menu');
            let $collapseLink = $(this).closest('.collapse__nav').find('.collapse__link');
            
            $collapseMenu.toggleClass('showCollapse');
            $collapseLink.toggleClass('rotate');
        }
    });
    // 사이드바를 확장하는 클릭 이벤트 리스너
    $('#nav-toggle').on('click', function(){
        $('#side-navbar').toggleClass('expander');
        $('#body-pd').toggleClass('body-pd');
        
        // 모든 collapse__menu에서 showCollapse 클래스 제거
        $('.collapse__menu').removeClass('showCollapse');
        $('.collapse__link').removeClass('rotate');
    });

    // 클릭된 메뉴를 active로 활성화 시키고, 기존의 active를 제거하는 코드
    $(document).on('click', '.nav__link', function(){
        $('.nav__link').removeClass('active');
        $(this).addClass('active');
    });
});


</script>
</html>

