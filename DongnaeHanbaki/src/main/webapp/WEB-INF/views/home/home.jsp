<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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


<script>
var socket = null;

function connect() {
    socket = new WebSocket('ws://localhost:8088/dongnae/chat'); // WebSocket 서버에 연결
    socket.onopen = function(event) {
        console.log('Connected to WebSocket'); // 연결 성공 시 콘솔에 메시지 출력
    };
    socket.onmessage = function(event) {
        var jsonMessage = JSON.parse(event.data);
        displayMessage(jsonMessage.fromNickName, jsonMessage.message, 'received');
    };
    socket.onclose = function(event) {
        console.log('Disconnected from WebSocket'); // 연결 종료 시 콘솔에 메시지 출력
    };
    socket.onerror = function(error) {
        console.log("WebSocket error: " + error);
    };
}

function sendMessage() {
    var messageInput = document.getElementById('message');
    var message = messageInput.value;
    if (message.trim() !== "") { // 메시지가 비어있지 않을 때만 전송
        socket.send(message); // WebSocket을 통해 메시지 전송
        displayMessage("닉네임", message, 'sent'); // 보낸 메시지를 화면에 표시 (주석 처리됨)
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

window.onload = function() {
    connect(); // 페이지 로드 시 WebSocket 연결
    initResize(); // 페이지 로드 시 크기 조절 기능 초기화
    scrollToBottom(); // 페이지 로드 시 스크롤을 맨 아래로 이동
    document.getElementById('message').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            sendMessage(); // 엔터 키 누를 시 메시지 전송
            event.preventDefault(); // 엔터 키의 기본 동작 막기
        }
    });
};
</script>
</head>
<body>
	<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
	<!-- 공통 바디 파일 포함 -->
	<h1>WebSocket Chat</h1>

	<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="123">채팅시작</button>
	<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="456">채팅시작</button>
	<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="789">채팅시작</button>
	<button type="button" class="btn btn-primary mb-2 chatToastBtn" id="357">채팅시작</button>
	<!-- Toast 버튼 -->
	<div class="toast-container bottom-0 end-0 p-3">
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
				<div id="chatBox">
					<div id="resizeHandle" class="resize-handle"></div>
				</div>
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
// const chatToastTrigger = document.getElementById('chatToastBtn')
const chatToast = document.getElementById('chatToast')

$(document).ready(function(){
    $(".chatToastBtn").click(function(){
        var buttonId = $(this).attr('id');
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(chatToast)
        toastBootstrap.show() // Toast 버튼 클릭 시 Toast 표시
        scrollToBottom(); // 채팅창을 열었을 때 스크롤을 맨 아래로 이동
    });
});

// if (chatToastTrigger) {
//     const toastBootstrap = bootstrap.Toast.getOrCreateInstance(chatToast)
//     chatToastTrigger.addEventListener('click', () => {
//         toastBootstrap.show() // Toast 버튼 클릭 시 Toast 표시
//         scrollToBottom(); // 채팅창을 열었을 때 스크롤을 맨 아래로 이동
//     })
// }

</script>
</html>
