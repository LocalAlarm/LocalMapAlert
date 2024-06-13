<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.spring.dongnae.user.vo.CustomUserDetails"%>
<%
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();
String token = userDetails.getToken();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Chat</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
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

#chatBox {
	overflow-y: scroll; /* 수직 스크롤바 */
	width: 100%;
	height: 150px;
	padding: 10px;
	border: 1px solid #ccc;
	position: relative;
	resize: vertical; /* 수평으로만 크기 조절 가능 */
	transition: height 0.1s ease; /* 채팅방 사이즈 크기 조절 감도 설정 */
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
<body>
	<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
	<!-- 공통 바디 파일 포함 -->
	<h1>WebSocket Chat</h1>
	<div id="chatList"></div>
	<form class="d-flex" role="search">
		<input class="form-control me-2" type="search" placeholder="Search"
			aria-label="Search">
		<button class="btn btn-outline-success" type="submit">Search</button>
	</form>
	<!-- 		<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="$2a$10$qXOdXhvKATGwm6KtxTVpa.JWafliXcMUj4VjILwO494navv.FlOSS">d@naver.com</button> -->
	<!-- 	<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="$2a$10$sPByjFU1EdQXoezpmKgTkOaRLH7DD7wn56vdHRow9IEveZqU2IgIW">qwe123@naver.com</button> -->
	<!-- 	<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="$2a$10$XS3FPzVS7s96.jKZYsy2i.fa..rvH/Kgjmw.Qj3efBZDHEWVsEBbO">d1@naver.com</button> -->
	<!-- Toast 버튼 -->
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

            if (isChatRoom(jsonData)) {
                handleChatRoom(jsonData);
            } else if (isMessage(jsonData)) {
                handleMessage(jsonData);
            } else {
                console.error("Unknown data type received:", jsonData);
            }
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
//ChatRoom 데이터를 처리하는 함수
async function handleChatRoom(chatRoom) {
    var buttonHtml = '';
    buttonHtml += '<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="' + chatRoom.id + '">' + chatRoom.roomName + '</button>';
    $('#chatList').html(buttonHtml);

    if (Array.isArray(chatRoom.messages)) { // 메시지가 배열인지 확인
        for (const element of chatRoom.messages) {
            // 닉네임 가져오기
            const nickname = await getNickname(element.senderToken);
            console.log(nickname);
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
    console.log(nickname);
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
    	console.log(jsonMsg);
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
                'Content-Type': 'application/json'
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

window.onload = function() {
    connectChat(); // 페이지 로드 시 WebSocket 연결
    connectFriend();
    scrollToBottom(); // 페이지 로드 시 스크롤을 맨 아래로 이동
    document.getElementById('message').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            sendMessage(); // 엔터 키 누를 시 메시지 전송
            event.preventDefault(); // 엔터 키의 기본 동작 막기
        }
    });
};

$(document).ready(function(){
    $('#chatList').on('click', '.chatToastBtn', function(){
        var buttonId = $(this).attr('id');
        const chatToast = document.getElementById('chatToast'); // chatToast 요소 가져오기
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(chatToast)
        $('.toast-container').attr('id', buttonId);
        toastBootstrap.show() // Toast 버튼 클릭 시 Toast 표시
        scrollToBottom(); // 채팅창을 열었을 때 스크롤을 맨 아래로 이동
    });
});

</script>
</html>
