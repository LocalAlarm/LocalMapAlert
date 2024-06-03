<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<style>
body {
    background-image: url('https://images.unsplash.com/photo-1548345680-f5475ea5df84?q=80&w=2073&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'); /* 원하는 이미지 URL로 변경 */
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    height: 100vh;
    margin: 0;
    display: flex;
    flex-direction: column; /* 로고와 로그인 폼을 세로로 정렬하기 위해 추가 */
    align-items: center; /* 로고와 로그인 폼을 가운데로 정렬하기 위해 추가 */
    justify-content: center;
    color: white;
}

#kakaoImg {
    border: 1px solid lightgray;
    border-radius: 10px;
}
#kakaoImg:hover {
    opacity: 0.8;
    cursor: pointer;
}
.loginWrapper {
    width: 100%;
    max-width: 300px;
    padding: 15px;
    border: 1px solid lightgray;
    border-radius: 4px;
    background-color: rgba(90, 90, 90, 0.8); /* 배경색을 반투명하게 설정 */
    color: white;
    margin-top: 5px; /* 로고와 로그인 폼 사이의 간격을 조절하기 위해 수정 */
}


.loginWrapper label {
    color: white;
}

.logo {
    margin-bottom: -50px; /* 로고 아래 여백 조절 */
    text-align: center; /* 로고를 가운데 정렬 */
}

.logo img {
    width: 200px; /* 로고 이미지의 너비 설정 */
    height: auto; /* 로고 이미지의 높이 자동 조정 */
}



</style>
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
    <div class="loginWrapper">
	    <div class="logo">
	        <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717399397/mainLogo_hzmpm0.png" alt="로고 이미지">
	    </div>
        <form id="loginForm" action="login-proc" method="post">
             <div class="mb-3">
                <label for="email" class="form-label">이메일</label>
                <input type="email" class="form-control" id="email" name="username" >
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">비밀번호</label>
                <input type="password" class="form-control" id="password" name="password">
             </div>
             
             <div class="d-flex justify-content-center mb-2 px-3">
                <button type="submit" class="btn btn-primary me-2">로그인</button>
                <input type="button" class="btn btn-light ms-2" value="회원가입" onclick="location.href='join'">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             </div>
        </form>
        <div class="kakao-btn d-flex justify-content-center">
            <img
                src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
                width="222" alt="카카오 로그인 버튼" id="kakaoImg" onclick="loginWithKakao()" />
        </div>
    </div>

<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js"
  integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4" crossorigin="anonymous"></script>
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
