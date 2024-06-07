<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 로그인 리디렉션</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
</head>
<style>
body {
    background-image: url('https://images.unsplash.com/photo-1548345680-f5475ea5df84?q=80&w=2946&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    height: 100vh;
    margin: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: white;
}

#kakaoImg {
    /* border: 1px solid lightgray; */
    /* border-radius: 10px; */
}
#kakaoImg:hover {
    opacity: 0.8;
    cursor: pointer;
}
.loginWrapper {
    width: 100%;
    max-width: 350px; /* 더 넓게 설정 */
    padding: 20px; /* 더 넓은 패딩 */
    border: 1px solid rgba(255, 255, 255, 0.3);
    border-radius: 10px;
    background-color: rgba(0, 0, 0, 0.7); /* 더 어두운 반투명 배경 */
    color: white;
    margin-top: 5px;
}

.loginWrapper label {
    color: white;
}

.logo {
    margin-bottom: -30px; /* 로고 아래 여백 조절 */
    text-align: center;
}

.logo img {
    width: 200px;
    height: auto;
}
</style>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
	<div class="loginWrapper">
		<div class="logo">
			<img
				src="https://res-console.cloudinary.com/dyjklyydu/thumbnails/v1/image/upload/v1717463449/64-Z64Sk7ZWc67CU7YC0X18xXy1yZW1vdmViZy1wcmV2aWV3X2Nnam95NQ==/drilldown"
				alt="로고 이미지">
		</div>
		<form id="loginForm">
			<div class="mb-3">
				<label for="email" class="form-label">이메일</label> <input
					type="email" class="form-control" id="email" name="username">
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">비밀번호</label> <input
					type="password" class="form-control" id="password" name="password">
			</div>

			<div class="d-flex justify-content-center mb-2 px-3">
				<button type="submit" class="btn btn-primary me-2"
					style="background-color: #FF6347; border-color: #FF6347; color: white;">로그인</button>
				<input type="button" class="btn btn-secondary ms-2" value="회원가입"
					onclick="location.href='join'"> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />
			</div>
		</form>
		<!-- 버튼을 스피너로 바꾸기 -->
		<div class="kakao-btn d-flex justify-content-center">
			<button class="btn btn-primary" type="button" disabled>
				<span class="spinner-border spinner-border-sm" role="status"
					aria-hidden="true"  onclick="loginWithKakao()"></span> Loading...
			</button>
		</div>
	</div>

<!-- 	    <div id="container"> -->
<!--         <h1>카카오 로그인 리디렉션 [redirect.jsp]</h1> -->
        <p id="token-result">토큰을 처리 중입니다...</p>
<!--         <button class="api-btn" onclick="requestUserInfo()"> -->
<!-- 			style="visibility: hidden">사용자 정보 가져오기</button> -->
<!--     </div> -->

<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js"
  integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4" crossorigin="anonymous"></script>
<script>
    Kakao.init('6ba5718e3a47f0f8291a79529aae8d8e'); // 사용하려는 앱의 JavaScript 키 입력

    // URL에서 쿼리 파라미터를 추출하는 함수
    function getQueryParam(param) {
      let urlParams = new URLSearchParams(window.location.search);
      return urlParams.get(param);
    }

    // 인증 코드 가져오기
    let code = getQueryParam('code');
    if (code) {
      // 인증 코드를 사용하여 토큰 요청
      fetch(`https://kauth.kakao.com/oauth/token`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
          grant_type: 'authorization_code',
          client_id: '6ba5718e3a47f0f8291a79529aae8d8e',
          redirect_uri: 'http://localhost:8088/dongnae/redirect',
          code: code, 
        })
      })
      .then(response => response.json())
      .then(data => {
        if (data.access_token) {
          console.log(data);
          Kakao.Auth.setAccessToken(data.access_token);
          document.querySelector('#token-result').innerText = '토큰: ' + data.access_token;
          // 사용자 정보 요청
          requestUserInfo();
          
        } else {
          document.querySelector('#token-result').innerText = '토큰 요청에 실패했습니다.';
        }
      })
      .catch(err => {
        console.error(err);
        document.querySelector('#token-result').innerText = '토큰 요청에 실패했습니다.';
      });
    }

    function requestUserInfo() {
      Kakao.API.request({
        url: '/v2/user/me',
      })
        .then(function(res) {
//           var jsonRes = JSON.stringify(res)
//           console.log(jsonRes);
		  var userInfo = {
       		 email: res.kakao_account.email,
      	     nickname: res.kakao_account.profile.nickname,
             thumbnail_image_url: res.kakao_account.profile.thumbnail_image_url
        	 };
		  var jsonRes = JSON.stringify(userInfo)
          console.log(jsonRes);
          //페이지이동
          $.ajax({
        	  url: "kakaoData",
        	  type: "post",
        	  async: false,
        	  contentType: "application/json; charset=utf-8",
        	  dataType: "json",
        	  data: jsonRes,
        	  success: function (data) {
        		  console.log(data);
        		  if (data) {
        			 window.location.replace("kakaomain"); 
        		  }
        	  },
	          error: function(jqXHR, textStatus, errorThrown) {
	            console.error("AJAX request failed: " + textStatus + ", " + errorThrown);
	          }
          });
        })
        .catch(function(err) {
          alert('failed to request user information: ' + JSON.stringify(err));
        });
    }

    // 아래는 데모를 위한 UI 코드입니다.
    function displayToken() {
      var token = getCookie('authorize-access-token');

      if(token) {
        Kakao.Auth.setAccessToken(token);
        document.querySelector('#token-result').innerText = 'login success, ready to request API';
        document.querySelector('button.api-btn').style.visibility = 'visible';
      }
    }

    function getCookie(name) {
      var parts = document.cookie.split(name + '=');
      if (parts.length === 2) { return parts[1].split(';')[0]; }
    }

    displayToken();
  </script>


</body>
</html>
