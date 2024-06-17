<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.spring.dongnae.user.vo.CustomUserDetails"%>
<%@ page import="org.springframework.security.core.Authentication" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Chat</title>
<script src="${pageContext.request.contextPath}/resources/js/sidebar/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sidebar/chat.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sidebar/friend.js"></script>
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
<%
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
CustomUserDetails userDetails = null;
boolean isLogin = !authentication.getPrincipal().toString().equals("anonymousUser");
if (isLogin) {
	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
} else {
	userDetails = new CustomUserDetails();	
}
request.setAttribute("isLogin", isLogin);
request.setAttribute("userDetails", userDetails);
%>
<script>
var chatSocket = null;
var friendSocket = null;
var token = null;
const chatToast = document.getElementById('chatToast');
</script>
<body id="body-pd">
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
    <div class="l-navbar" id="side-navbar">
        <nav class="nav sidebar">
        <c:choose>
            <c:when test="${isLogin}">
                <!-- 로그인 상태일 때 표시할 사이드바 -->
                <div>
	                <div class="nav__brand">
						<ion-icon name="apps-outline" class="nav__toggle" id="nav-toggle" alt="menu-icon"></ion-icon>
	                    <a href="/dongnae/main" class="nav__logo">
	                    <img src="<%= userDetails.getImage()%>" alt="UserProfileImg" style="width: 35px; height: 35px; border-radius: 50%; margin-right: 10px;">
	                    <%= userDetails.getNickname()%>
	                    </a>
	                </div>
	                <div class="nav__list">
	                    <a href="/dongnae/map" class="nav__link active">
	                        <ion-icon name="home-outline" class="nav__icon"></ion-icon>
	                        <span class="nav_name">홈페이지</span>
	                    </a>
	                    <div class="nav__link collapse__nav">
				            <ion-icon name="person-add-outline" class="nav__icon"></ion-icon>
				            <span class="nav_name">친구요청</span>
				            <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
				            <ul class="collapse__menu" id="friend-requests">
				                <!-- 친구 목록 여기에 뜸. -->
				            </ul>
				        </div>
	
	                    <div class="nav__link collapse__nav">
	                        <ion-icon name="people-circle-outline" class="nav__icon"></ion-icon>
	                        <span class="nav_name">친구목록</span>
	                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
	                        <ul class="collapse__menu" id="friendList" style="overflow: visible;">
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
	                <a href="/dongnae/login?logout class="nav__link">
	                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
	                    <span class="nav_name">Log out</span>
	                </a>
            	</div>
            </c:when>
            <c:otherwise>
                <!-- 로그인 상태가 아닐 때 표시할 사이드바 -->
                <a href="/dongnae/login">Login</a>
                <a href="/register">Register</a>
            </c:otherwise>
        </c:choose>
        </nav>
    </div>
	<!-- 공통 바디 파일 포함 -->
	<h1>WebSocket Chat</h1>
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
				<img src="${pageContext.request.contextPath}/resources/svg/chat-dots.svg" class="rounded me-2" alt="ChatIcon">
				<!-- 아이콘 이미지 -->
				<strong class="me-auto">Chat</strong> <small>채팅방 이름</small>
				<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
				<!-- 닫기 버튼 -->
			</div>

			<div class="toast-body">
				<div id="chatBox"></div>
			</div>
			<!-- 채팅 메시지 표시 영역 -->
			<input type="text" id="message" placeholder="Enter your message" />
			<!-- 메시지 입력 필드 -->
			<img src="${pageContext.request.contextPath}/resources/svg/paper-plane-outline.svg" class="rounded me-2" style="height: 50px; width: 50px;" alt="ChatIcon">
			<button onclick="sendMessage()">Send</button>
			<!-- 전송 버튼 -->
		</div>
	</div>
</body>
<script>
$(document).ready(function() {
    connectChat(); // 페이지 로드 시 Chat WebSocket 연결
    connectFriend(); // 페이지 로드시 Friend WebSocket 연결
    initializeChatToast();
    initializeSearchEvents();
    initializeFriendRequest();
    initializeCollapseMenu();
    initializeSidebarToggle();
    initializeMenuActivation(); // 클릭된 메뉴를 active로 활성화 시키고, 기존의 active를 제거하는 코드
    handleMessageEnterPress();
    //receiveFriendRequests(); // 친구 요청란에 받은 데이터값을 보여주는 코드
});
</script>
</html>