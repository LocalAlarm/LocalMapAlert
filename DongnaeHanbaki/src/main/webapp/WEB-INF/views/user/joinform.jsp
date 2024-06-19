<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
<style>

    .mb-3 {
        margin-bottom: 1rem; /* Adjust this value to increase/decrease the spacing */
    }
</style>
<script>
   //중복체크
   var duplicateCheck = false;
   //인증번호입력체크
   var authenticCheck = false;
   //인증번호
   var code = "";
   //비번체크
   var passwordSurvey = false;
  	//닉네임체크
  	var nicknameSurvey = false;
   //회원가입 폼에 필수 입력값 없으면 회원가입 완료버튼 못가게 막으면 됨
   //나머지 함수도 체크하면 다 "" 처리
   //
   //주소찾기
   var mapContainer, mapOption, map, geocoder, marker;

   window.onload = function() {
      mapContainer = document.getElementById('map'); // 지도를 표시할 div
      mapOption = {
         center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
         level: 5 // 지도의 확대 레벨
      };

      // 지도를 미리 생성
      map = new daum.maps.Map(mapContainer, mapOption);
      // 주소-좌표 변환 객체를 생성
      geocoder = new daum.maps.services.Geocoder();
      // 마커를 미리 생성
      marker = new daum.maps.Marker({
         position: new daum.maps.LatLng(37.537187, 127.005476),
         map: map
      });
   }
   
   function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = '' + extraAddr + '';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_detailAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_detailAddress").value = '';
                }

                // 주소 정보를 해당 필드에 추가
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동
                document.getElementById("sample6_detailAddress").focus();

                // 지도에 주소 표시
                var geocoder = new daum.maps.services.Geocoder();

                // 주소로 좌표를 검색
                geocoder.addressSearch(addr, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {
                        var coords = new daum.maps.LatLng(results[0].y, results[0].x);

                        // 지도 표시
                        document.getElementById('map').style.display = "block";
                        map.relayout();
                        map.setCenter(coords);
                        marker.setPosition(coords);
                    }
                });
            }
        }).open();

        // 지도 클릭 이벤트 등록
        daum.maps.event.addListener(map, 'click', function(mouseEvent) {
            // 클릭한 위치의 좌표
            var latlng = mouseEvent.latLng;

            // 마커 위치를 클릭한 위치로 설정
            marker.setPosition(latlng);

            // 좌표로 주소 정보를 요청
            geocoder.coord2Address(latlng.getLng(), latlng.getLat(), function(result, status) {
                if (status === daum.maps.services.Status.OK) {
                    var address = result[0].address.address_name;

                    // 주소를 해당 필드에 추가
                    document.getElementById('sample6_address').value = address;
                    document.getElementById('sample6_detailAddress').value = '';
                }
            });
        });
    }
   // 뒤로가기
   function goBack() {
      window.history.back();
   }

   // 비밀번호 보이게 하기
   function passwordVisibility(inputFieldId, button) {
      var inputField = document.getElementById(inputFieldId);
      if (!inputField) {
         console.error(`Element with ID ${inputFieldId} not found.`);
         return;
      }
      var fieldType = inputField.getAttribute('type');
      if (fieldType === 'password') {
         inputField.setAttribute('type', 'text');
         button.textContent = '비밀번호 숨기기';
      } else {
         inputField.setAttribute('type', 'password');
         button.textContent = '비밀번호 보이기';
      }
   }

   // 이메일 형식 검사와 중복 체크
   function checkEmail(frm) {
      var checkEmail = frm.email.value;

      // 이메일 형식 검증을 위한 정규 표현식
      var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

      if (checkEmail.trim().length == 0) {
    	  showDangerAlert('오류', '이메일을 입력해주세요.', '');
         $("#email").focus();
         return false;
      }
      
      if (!emailPattern.test(checkEmail)) {
    	  showDangerAlert('오류', '올바른 이메일 형식을 입력해주세요', '');
         frm.email.value = '';
         return false;
      }

      $.ajax({
         type: "POST",
         url: "checkEmail",
         data: {
            email: checkEmail
         },
         success: function(response) {
            if (response === "duplicate") {
            	showDangerAlert('오류', '이미 사용중인 이메일입니다.', '');
               $("#email").focus();
            } else {
               if (confirm("이 아이디는 사용 가능합니다. \n사용하시겠습니까?")) {
                  	   $("#email").prop("readonly", true);
                       $("#emailDuplicate").attr("disabled", true);
                       $("#emailDuplicate").prop("type", "hidden");
                       $("#emailReset").attr("disabled", false);
                       $("#emailReset").prop("type", "button");
                       $("#authentic").prop("type", "button");
                       $("#authenticNumber").prop("type", "text");
                       duplicateCheck = true;
                   }
               return false;
            }
         },
           error: function (request, status,error) {
               alert("ajax 실행 실패");
               alert("code:" + request.status + "\n" + "error :" + error);
           }
      });
   }
   
   //다시쓰기
   function resetEmail() {
      $("#email").prop("readonly", false);
      $("#email").val('');
      $("#emailDuplicate").attr("disabled", false);
      $("#emailDuplicate").prop("type", "button");
      $("#emailReset").attr("disabled", true);
      $("#emailReset").prop("type", "hidden");
      $("#authentic").prop("type", "hidden");
      $("#authenticReset").prop("type", "hidden");
      $("#authenticNumber").val('');
      $("#authenticNumber").prop("type", "hidden");
      $("#authenticNumber").attr("disabled", true);
      $("#authenticWord").text("");
      authenticCheck = false;
      
      
   }
   
   
	//이메일 본인 인증
	function authenticEmail() {
		var email = $("#email").val().trim();
		if (email.length == 0) {
			alert("이메일을 입력해주세요!");
			$("#email").focus();
			return false;
		}
		
		//이메일인증api
		$.ajax({
			type : "POST",
			url : "mailAuthentic",
			data : {
				email : email
			},
			success : function(data) {
				console.log("data: " + data);
				$("#authenticNumber").attr("disabled", false);
				$("#authentic").prop("type", "hidden");
				$("#authenticReset").prop("type", "button");
				code = data;
			},
			error : function(request, status, error) {
				alert("ajax 실행 실패");
				alert("code:" + request.status + "\n" + "error :" + error);
			}

		});
	}
	// 인증번호 비교
	function authenticComp() {
	    var input = $("#authenticNumber").val().trim();
	    
	    if (input == code) {
	        $("#authenticWord").text("인증번호가 일치합니다.").css("color", "#0404B4");
	        authenticCheck = true;
	    } else {
	        $("#authenticWord").text("인증번호가 일치하지 않습니다.").css("color", "red");
	        authenticCheck = false;
	    }
	}
	
	//다시 인증
	function resetAuthentic() {
		$("#authentic").prop("type", "button");
		$("#authenticReset").prop("type", "hidden");
		$("#authenticNumber").val('');
   	    $("#authenticNumber").attr("disabled", true);
   	    $("#authenticWord").text("");
  	    authenticCheck = false;
	}


	// 비번 일치 확인
	function passwordOk(frm) {
		var password = frm.password.value;
		var passwordCheck = frm.passwordCheck.value;

		// 비밀번호 형식 검증을 위한 정규 표현식
		var passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

		if (!passwordPattern.test(password)) {
			$("#passwordWord").text("비밀번호는 영어 문자와 하나의 특수 문자를 포함하여 8글자 이상이어야 합니다.").css("color", "red");
			return false;
		}

		if (password !== passwordCheck) {
			$("#passwordWord").text("비밀번호가 일치하지 않습니다.").css("color", "red");
			return false;
		}
		
		$("#passwordWord").text("비밀번호가 일치합니다.").css("color", "#0404B4");
		passwordSurvey = true;
	}
	

	function nicknameOk(frm) {
		var nickname = frm.nickname.value;
		
		// 욕설 언어 필터링을 위한 정규 표현식
	    var profanityPattern = /ㅆㅂ|ㅅㅂ|ㄲㅈ|ㄳ|ㅅㄱ|ㅇㅁ|ㅅㄲ|ㄳㄲ|ㅈㄹ|ㅈㄴ|존나|졸라|씨발|죽어|뒤져|꺼져|지랄|가슴|슴가|유방|유두|꼭지|젖|엉디|엉덩이|궁디|염병|아가리|개새끼|새끼|시발|니애미|애미|엄마|섹스|야스|자지|보지|포르노|노|무현|일베|ㅇㅂ|년|tlqkf|toRL|fuck|shit|hell|mom|mother|sex|suck|shut|ass|pennis|pussy|nipple|faggot|bastard|idiot|whore|bitch|freak|gay|lesbian|av|porn/gi;
		
		// 닉네임 형식 검증을 위한 정규 표현식
		var nicknamePattern = /^[a-zA-Z]{1,8}$|^[\u3131-\uD79D]{1,8}$/;

		if (!nicknamePattern.test(nickname)) {
			$("#nicknameWord").text("닉네임은 영어로 이루어진 8글자 이하의 형식이거나 한글로 이루어진 8글자 이하의 형식이어야 합니다.").css("color", "red");
			nicknameSurvey = false; 
		}  else if (profanityPattern.test(nickname)) {
	        $("#nicknameWord").text("욕설(패드립/섹드립) 및 비방을 포함한 닉네임은 사용할 수 없습니다.").css("color", "red");
	        nicknameSurvey = false;
	    }  else {
			$("#nicknameWord").text("사용가능한 닉네임입니다.").css("color", "#0404B4");
			nicknameSurvey = true;
		}
	}

	function joinValidate() {
		var email = $("#email").val().trim();
		var password = $("#password").val().trim();
		var nickname = $("#nickname").val().trim();
		var recoverEmail = $("#recoverEmail").val().trim();
		if (email == "") {
			showDangerAlert('오류', '이메일을 입력해주세요!', '');
			$("#email").focus();
			return false;
		} else if (!duplicateCheck) {
			showDangerAlert('오류', '이메일 중복체크해주세요!', '');
			$("#email").focus();
			return false;
		} else if (!authenticCheck) {
			showDangerAlert('오류', '이메일 인증해주세요!', '');
			$("#authentic").focus();
			return false;
		} else if (password == "") {
			showDangerAlert('오류', '비밀번호를 입력해주세요!', '');
			$("#password").focus();
			return false;
		} else if (passwordSurvey == false) {
			showDangerAlert('오류', '비밀번호 확인과 비교해주세요!', '');
			$("#password").focus();
			return false;
		} else if (nickname == "") {
			showDangerAlert('오류', '닉네임을 입력해주세요!', '');
			$("#nickname").focus();
			return false;
		} else if (!nicknameSurvey) {
			showDangerAlert('오류', '닉네임을 확인해주세요!', '');
			$("#nickname").focus();
			return false;
		} else if (recoverEmail == "") {
			showDangerAlert('오류', '복구이메일을 입력해주세요!', '');
			$("#recoverEmail").focus();
			return false;
		}
		
		var formData = new FormData($("#joinForm")[0]);

	    $.ajax({
	        type: "POST",
	        url: "join",
	        data: formData,
	        contentType: false,
	        processData: false,
	        success: function(response) {
	            console.log("response : " + response);
	            if (response === "pass") {
	            	showSuccessAlert('성공', '회원가입이 완료되었습니다.', '');
	                window.location.href = 'login'; // 회원가입 후 로그인 페이지로 이동
	            } else {
	                alert(response);
	            }
	        },
	        error: function(request, status, error) {
	        	showDangerAlert('오류', '회원가입이 실패했습니다!', '');
	            console.error("code: " + request.status + "\n" + "error: " + error);
	        }
	    });

	    return false; 
	}
</script>
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
<div class="loginWrapper">
    <div class="logo">
        <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png" alt="로고 이미지">
    </div>
     <h2>회원가입</h2>
    <form action="join" id="joinForm" method="post" enctype="multipart/form-data" onsubmit="return joinValidate()">
        <div class="mb-3">
            <input type="email" class="form-control" id="email" name="email" title="이메일" placeholder="이메일 입력" style="margin-bottom: 10px;">
            <input type="button" class="btn btn-outline-info" id="emailDuplicate" value="이메일 중복 체크" onclick="checkEmail(this.form)" style="margin-top: auto;" />
            <input type="hidden" class="btn btn-outline-info" id="emailReset" value="다시 입력" onclick="resetEmail()" disabled />
        </div>
         <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="hidden" class="btn btn-outline-info mb-3" id="authentic" value="본인인증" onclick="authenticEmail()" style="margin-top: auto;">
            <input type="hidden" class="btn btn-outline-info mb-3" id="authenticReset" value="다시 인증하기" onclick="resetAuthentic()" />
            <input type="hidden" class="form-control" id="authenticNumber" name="authenticNumber" title="인증하기" placeholder="인증번호 입력" disabled onblur="authenticComp()">
            <span id="authenticWord"></span>
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="password" class="form-control" id="password" name="password" title="비밀번호" placeholder="비밀번호 입력">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="password" class="form-control" id="passwordCheck" name="passwordCheck" title="비밀번호 확인" placeholder="비밀번호 확인" onblur="passwordOk(this.form)">
            <span id="passwordWord"></span>
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="text" class="form-control" id="nickname" name="nickname" title="닉네임" placeholder="닉네임" onblur="nicknameOk(this.form)">
            <span id="nicknameWord"></span>
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="text" class="form-control" id="sample6_address" name="address" placeholder="주소" style="margin-bottom: 10px;">
            <input type="button" class="btn btn-outline-warning mb-3" value="주소 검색" onclick="sample6_execDaumPostcode()" style="margin-top: auto;"><br>
            <input type="text" class="form-control" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소">
<!--             <input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목"> -->
            <div id="map" style="width:270px;height:350px;  margin-top:10px;display:none"></div>
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="text" class="form-control" id="recoverEmail" name="recoverEmail" title="복구이메일" placeholder="복구이메일 입력">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="file" class="form-control" name="image" id="image" accept="image/*">
        </div>
        <div class="d-flex justify-content-center mb-2 px-3">
            <input type="submit" class="btn btn-primary me-2" style="background-color: #FF6347; border-color: #FF6347; color: white;" value="회원가입 하기">
            <input type="button" class="btn btn-light ms-2" value="뒤로가기" onclick="goBack()">
        </div>
    </form>
</div>
</body>
</html>