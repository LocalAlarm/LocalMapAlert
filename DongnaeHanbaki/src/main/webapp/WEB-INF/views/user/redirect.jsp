<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 로그인 리디렉션</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div id="container">
        <h1>카카오 로그인 리디렉션 [redirect.jsp]</h1>
        <p id="token-result">토큰을 처리 중입니다...</p>
        <button class="api-btn" onclick="requestUserInfo()">
			style="visibility: hidden">사용자 정보 가져오기</button>
    </div>

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
