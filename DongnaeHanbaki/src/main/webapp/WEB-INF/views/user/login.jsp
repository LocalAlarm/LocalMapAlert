<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
    <div class="loginWrapper">
	    <div class="logo">
		    <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png" alt="로고 이미지">
		</div>

		<form id="loginForm" action="authenticate" method="post">
			<div class="mb-3">
				<label for="email" class="form-label">이메일</label> <input
					type="email" class="form-control" id="email" name="email">
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">비밀번호</label> <input
					type="password" class="form-control" id="password" name="password">
			</div>

			<div class="d-flex justify-content-center mb-2 px-3">
				<button type="submit" class="btn btn-primary me-2"
					style="background-color: #FF6347; border-color: #FF6347; color: white;">로그인</button>
				<input type="button" class="btn btn-light ms-2" value="회원가입"
					onclick="location.href='joinform'"> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />
			</div>
		</form>

		<div class="kakao-btn d-flex justify-content-center">
			<img
				src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
				width="222" alt="카카오 로그인 버튼" id="kakaoImg"
				onclick="loginWithKakao()" />
		</div>
	</div>
	
		<!-- 이메일, 비밀번호 찾기 링크 추가 -->
      <div class="d-flex justify-content-center mt-2">
          <a href="findEmail" style="color: white;">이메일 찾기</a>
          <span style="color: white; margin: 0 10px;">/</span>
          <a href="findPassword" style="color: white;">비밀번호 찾기</a>
      </div>

	<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js"
		integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4"
		crossorigin="anonymous"></script>
	<script>
  Kakao.init('6ba5718e3a47f0f8291a79529aae8d8e'); // 사용하려는 앱의 JavaScript 키 입력

  function loginWithKakao() {
    Kakao.Auth.authorize({
      redirectUri: 'http://localhost:8088/dongnae/redirect',
      state: 'userme'
    });
  }
</script>

</body>
</html>
