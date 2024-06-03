<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
#container {
	width: 700px;
	margin: auto;
}

h1 {
	text-align: center;
}

table {
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid black;
	margin: 0 auto;
}

th {
	background-color: orange;
}

.center {
	text-align: center;
}

.kakao-btn {
	text-align: center;
	margin-top: 20px;
	cursor: pointer;
}

.kakao-btn:hover {
	opacity: 0.8;
}
</style>
</head>
<body>

\${user } : ${user }<br>
\${userVO } : ${userVO }

	<div id="container">
		<h1>로그인 [login.jsp]</h1>
		<form id="loginForm" action="login-proc" method="post">
			<table>
				<tr>
					<th>아이디</th>
					<td><input type="text" name="email" value="${user.email }"></td>
				</tr>
				<tr>
					<th>패스워드</th>
					<td><input type="password" name="password"
						value="${user.password }"></td>
				</tr>
				<tr>
					<td colspan="2" class="center"><input type="submit"
						value="로그인">
          <input type="button" value="회원가입" onclick="location.href='join'">
            </td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		<div class="kakao-btn" onclick="loginWithKakao()">
			<img
				src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
				width="222" alt="카카오 로그인 버튼" />
		</div>

<!-- 		<p id="token-result"> -->
<!-- 			토큰: <span id="token-value"></span> -->
<!-- 		</p> -->
<!-- 		<button class="api-btn" onclick="requestUserInfo()" -->
<!-- 			style="visibility: hidden">사용자 정보 가져오기</button> -->

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

//   function requestUserInfo() {
//     Kakao.API.request({
//       url: '/v2/user/me',
//     })
//       .then(function(res) {
//         alert(JSON.stringify(res));
//       })
//       .catch(function(err) {
//         alert(
//           'failed to request user information: ' + JSON.stringify(err)
//         );
//       });
//   }

//   // 아래는 데모를 위한 UI 코드입니다.
//   displayToken();
//   function displayToken() {
//     var token = getCookie('authorize-access-token');

//     if(token) {
//       Kakao.Auth.setAccessToken(token);
//       document.querySelector('#token-result').innerText = 'login success, ready to request API';
//       document.querySelector('button.api-btn').style.visibility = 'visible';
//     }
//   }

//   function getCookie(name) {
//     var parts = document.cookie.split(name + '=');
//     if (parts.length === 2) { return parts[1].split(';')[0]; }
//   }
</script>

</body>
</html>
