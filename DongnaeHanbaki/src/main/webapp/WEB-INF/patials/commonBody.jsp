<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.spring.dongnae.user.vo.CustomUserDetails"%>
<%@ page import="org.springframework.security.core.Authentication" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<!--  팝업창 오픈소스  -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<!-- Kakao 지도 API 스크립트 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e&libraries=services,clusterer,drawing"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- IONICONS -->
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

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
var isLogin = <%= isLogin%>;
const chatToast = document.getElementById('chatToast');
</script>
<div class="l-navbar" id="side-navbar">
	<nav class="nav sidebar" style="height: 100vh; overflow-y: auto;">
		<c:choose>
			<c:when test="${isLogin}">
				<!-- 로그인 상태일 때 표시할 사이드바 -->
				<div>
					<div class="nav__brand">
						<ion-icon name="apps-outline" class="nav__toggle" id="nav-toggle" alt="menu-icon"></ion-icon>
						<a href="/dongnae/main" class="nav__logo" style="text-decoration: none;">
							<img src="<%=userDetails.getImage()%>" alt="UserProfileImg" style="width: 35px; height: 35px; border-radius: 50%; margin-right: 10px;">
							<%=userDetails.getNickname()%>
						</a>
					</div>
					<div class="nav__list">
						<a href="/dongnae/map" class="nav__link">
							<ion-icon name="home-outline" class="nav__icon"></ion-icon>
							<span class="nav_name">홈페이지</span>
						</a>
						<div class="nav__link collapse__nav" id="nav__friend-request">
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send-plus-fill" viewBox="0 0 16 16">
								<path d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 1.59 2.498C8 14 8 13 8 12.5a4.5 4.5 0 0 1 5.026-4.47zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471z"/>
								<path d="M16 12.5a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0m-3.5-2a.5.5 0 0 0-.5.5v1h-1a.5.5 0 0 0 0 1h1v1a.5.5 0 0 0 1 0v-1h1a.5.5 0 0 0 0-1h-1v-1a.5.5 0 0 0-.5-.5"/>
							</svg>
							<span class="nav_name">친구요청하기</span>
						</div>
						<div class="nav__link collapse__nav">
							<ion-icon name="person-add-outline" class="nav__icon"></ion-icon>
							<span class="nav_name">친구요청</span>
							<ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
							<ul class="collapse__menu" id="friend-requests">
								<!-- 친구 요청 목록 여기에 뜸. -->
							</ul>
						</div>

						<div class="nav__link collapse__nav">
							<ion-icon name="people-circle-outline" class="nav__icon"></ion-icon>
							<span class="nav_name">친구목록</span>
							<ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
							<ul class="collapse__menu" id="friendList" style="overflow: visible;">
								<!-- 친구 목록이 들어올 자리 -->
							</ul>
						</div>
						<div class="nav__link collapse__nav">
							<ion-icon name="chatbubbles-outline" class="nav__icon"></ion-icon>
							<span class="nav_name">모임</span>
							<ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
							<ul class="collapse__menu" id="moimList">
								<!-- 모임 리스트가 들어올 자리 -->
							</ul>
						</div>
						<div class="nav__link collapse__nav" id="nav__create-moim">
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-add-fill" viewBox="0 0 16 16">
								<path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 1 1-1 0v-1h-1a.5.5 0 1 1 0-1h1v-1a.5.5 0 0 1 1 0"/>
								<path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293z"/>
								<path d="m8 3.293 4.712 4.712A4.5 4.5 0 0 0 8.758 15H3.5A1.5 1.5 0 0 1 2 13.5V9.293z"/>
							</svg>
							<span class="nav_name">모임 만들기</span>
						</div>
					</div>
					<a href="/dongnae/login?logout" class="nav__link">
						<ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
						<span class="nav_name">로그아웃</span>
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

<!-- 채팅 토스트 -->
<div class="toast-container chat-toast-container bottom-0 end-0 p-3">
	<div class="toast" id="chatToast" role="alert" aria-live="assertive"
		aria-atomic="true" data-bs-autohide="false">
		<div class="toast-header">
			<img
				src="${pageContext.request.contextPath}/resources/svg/chat-dots.svg"
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
		<img
			src="${pageContext.request.contextPath}/resources/svg/paper-plane-outline.svg"
			class="rounded me-2" style="height: 50px; width: 50px;"
			alt="ChatIcon">
		<button onclick="sendMessage()">Send</button>
		<!-- 전송 버튼 -->
	</div>
</div>

<!-- 친구 요청 보내는 모달 창 -->
<div class="modal fade" id="friendRequestModal" tabindex="-1" aria-labelledby="friendRequestModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content" style="height: 365px;">
			<div class="modal-header">
				<h5 class="modal-title" id="friendRequestModalLabel">친구 요청</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="d-flex" role="search">
					<input class="form-control me-2" type="search" placeholder="Search"
						id="searchFriend" aria-label="Search">
					<button class="btn btn-secondary" id="request-friend-button" type="button">친구요청</button>
				</div>
				<ul class="list-group" id="searchResults">
				</ul>
			</div>
		</div>
	</div>
</div>

<!-- 모임 모집 모달창 -->
<div class="modal fade" id="createMoimModal" tabindex="-1" aria-labelledby="createMoimModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="createMoimModalLabel">모임 만들기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form enctype="multipart/form-data">
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="createMoimModal-title" name="title">
						<label for="createMoimModal-title">모임 이름을 입력해주세요!</label>
					</div>
					<div class="form-floating mb-3">
						<textarea class="form-control" placeholder="모임을 소개해주세요!" id="createMoimModal-introduce" name="introduce" style="height: 150px;"></textarea>
						<label for="floatingTextarea">모임을 소개해주세요!</label>
					</div>
					<input type="file" class="form-control mb-3" id="createMoimModal-profilePic" name="profilePic" accept="image/*">
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="submitCreateMoimForm()">만들기</button>
			</div>
		</div>
	</div>
</div>

<!-- 모임 모달 -->
<div class="modal fade" id="moim-modal" tabindex="-1" aria-labelledby="friendRequestModalLabel" aria-hidden="true" >
    <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="moim-modal-title"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <!-- 왼쪽 8개 열 -->
                        <div class="col-md-8" id="left-content" style="height: 500px; overflow-y:auto;">
                            <!-- 왼쪽 내용이 들어갈 자리 -->
                            <!-- 게시물 목록이 들어간다 -->
                            <table class="table">
                            	<thead>
                            		<tr>
										<th scope="col">게시글</th>
										<th scope="col">작성자</th>
                            		</tr>
                            	</thead>
                            	<tbody id="moim-board-list">
                            	
                            	</tbody>
                            </table>
                        </div>
                        <!-- 오른쪽 4개 열 -->
                        <div class="col-md-4" id="right-content" style="height: 500px; overflow-y:auto;">
                            <!-- 오른쪽 내용이 들어갈 자리 -->
                            <!-- 채팅이 들어간다 -->
                            <div id="chatArea">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="openMoimPostModal">작성하기</button>
            </div>
        </div>
    </div>
</div>

<!-- 모임 게시물 작성 모달 구조 -->
<div class="modal fade" id="moim-post-modal" tabindex="-1" aria-labelledby="postModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="postModalLabel">게시물 작성</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="postForm" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="postTitle" class="form-label">제목</label>
                        <input type="text" class="form-control" id="postMoimTitle" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="postContent" class="form-label">내용</label>
                        <textarea class="form-control" id="postMoimContent" rows="3" name="content" required></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="postImages" class="form-label">이미지 파일</label>
                        <input type="file" class="form-control" id="postMoimImages" accept="image/*" name="images" multiple>
                    </div>
                    <button type="submit" class="btn btn-primary">작성</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 게시글 상세 정보 모달 -->
<div class="modal fade" id="moim-post-detail-modal" tabindex="-1" aria-labelledby="postDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="post-detail-title"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	<p id="post-detail-content"></p>
                <div class="card" style="display: flex; align-items: center; justify-content: flex-end; border: 1px solid #ddd; padding: 10px; margin-bottom: 15px;">
                    <p id="post-detail-author" style="margin: 0;"></p>
                </div>
                <div id="post-detail-carousel-container">
                    <!-- Carousel structure -->
                    <div id="post-detail-carousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner" id="post-detail-carousel-inner"></div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#post-detail-carousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#post-detail-carousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
                <div id="post-detail-comments-section">
                    <button class="btn btn-secondary mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseComments" aria-expanded="false" aria-controls="collapseComments">
                        댓글 목록
                    </button>
                    <div class="collapse" id="collapseComments">
                        <div class="card" id="post-detail-comments-list" style="max-height: 200px; overflow-y: auto; padding: 10px; margin-bottom: 15px;">
                            <!-- Comments will be dynamically added here -->
                        </div>
                    </div>
					<div class="input-group mb-3">
						<input type="text" id="post-detail-comment-input" class="form-control" placeholder="댓글을 작성해보세요!">
						<button class="btn btn-primary" id="post-detail-comment-submit" type="button">작성</button>
					</div>
				</div>
            </div>
        </div>
    </div>
</div>

<script>
	$(document).ready(function() {
		// 로그인 상테에서만 소켓을 연결하고 채팅을 활성화하기 위한 코드.
		if (isLogin) {
			/* 			connectChat(); // 페이지 로드 시 Chat WebSocket 연결
			 connectFriend(); // 페이지 로드시 Friend WebSocket 연결
			 initializeChatToast();
			 initializeSearchEvents();
			 initializeFriendRequest();
			 handleMessageEnterPress();
			 friendRequestModal(); */
			connectMoim();
			createMoimModalFunction();
			initializeMoimModal();
		}
		initializeCollapseMenu();
		initializeSidebarToggle();
		initializeMenuActivation(); // 클릭된 메뉴를 active로 활성화 시키고, 기존의 active를 제거하는 코드
		//receiveFriendRequests(); // 친구 요청란에 받은 데이터값을 보여주는 코드
	});
</script>