<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/login.css">
<script>
	   //프로필 수정이면 주제 바꾸기 -> 이메일값도받아서 default로넣음? 그럼의미가있나?
	  window.onload = function() {
		    var profileValue = document.getElementById("profile").value;
		    console.log(profileValue);
		    if (profileValue !== null && profileValue.trim() !== "") {
		        $("#subject").text("비밀번호 수정");
		    }
	  }

	   //이메일 찾기 버튼 클릭 체크
	   var findEamilCheck = false;
	   //인증번호 체크
	   var authenticCheck = false;
	   //인증번호 비교 변수
	   var code = "";
	   //비번 확인 비교 
	   var passwordSurvey = false;
	   
       function goBack() {
            window.history.back();
        }
       //이메일 찾기
       function findPasswordByEmail() {
    	   var email = $("#email").val().trim();
    	   console.log(email);
    	   $.ajax({
    		   type: "POST",
    		   url:  "findEmail",
    		   data: {
    			   email: email
    		   },
    		   success: function(data) {
    			   console.log("data: " + data);
    			   if (data == null) {
    				   alert("가입된 이메일이 없습니다.");
    				   return false;
    			   }
    			   alert("이메일 확인");
    			   $("#button-addon2").attr("disabled", false);
    			   findEamilCheck = true;
    		   },
    		   error: function (request, status,error) {
                   alert("ajax 실행 실패");
                   alert("code:" + request.status + "\n" + "error :" + error);
               }
    	   });
       }
       //이메일로 인증하기 -> 일단 아이디이메일로 복구이메일 추가
       function authentic() {
    	   var email = $("#email").val().trim();
    	   $.ajax({
    		   type: "POST",
    		   url: "mailAuthentic",
    		   data: {
    			   email: email
    		   },
    		   success: function(data) {
    			   console.log("data: " + data);
    			   $("#authenticNumber").attr("disabled", false);
    			   $("#button-addon2").css("display", "none");
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
	   	        $("#password").attr("disabled", false);
	   	        $("#passwordCheck").attr("disabled", false);
	   	        authenticCheck = true;
	   	    } else {
	   	        $("#authenticWord").text("인증번호가 일치하지 않습니다.").css("color", "red");
	   	        authenticCheck = false;
	   	    }
	   	}
	   	
	   	//다시 인증
	   	function resetAuthentic() {
	   		$("#authenticReset").prop("type", "hidden");
	   		$("#authenticNumber").val('');
	      	$("#authenticNumber").attr("disabled", true);
	      	$("#authenticWord").text("");
	      	$("#button-addon2").css("display", "inline-block");
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
			$("#changePassword").attr("disabled", false);
			passwordSurvey = true;
		}
	 	
       //비밀번호 찾기 검증정책
       function findPasswordValidate() {
    	   var email = $("#email").val().trim();
    	   if (email == "") {
   			alert("이메일을 입력해주세요!");
   			$("#email").focus();
   			return false;
   		  } else if (!findEamilCheck) {
   			alert("이메일 찾기 해주세요!");
			$("#email").focus();
			return false;
   		  } else if (!authenticCheck) {
			alert("인증번호 받기 해주세요!");
			$("#button-addon2").focus();
			return false;
		  } else if (password == "") {
			alert("비밀번호를 입력해주세요!");
			$("#password").focus();
			return false;
		  } else if (passwordSurvey == false) {
			alert("비밀번호 확인과 비교해주세요!");
			$("#password").focus();
			return false;
		  }
    	  alert("비밀번호가 변경되었습니다"); 
       }
       
</script>
</head>

<body>
	<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
	<div class="loginWrapper">
		<div class="logo">
			<img
				src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png"
				alt="로고 이미지">
		</div>
		<h4 id="subject" align="center">비밀번호 찾기</h4>
		<hr>
		<form action="passwordChange" method="post"
			onsubmit="return findPasswordValidate()">
			<!-- 프로필 수정 중 비밀번호 수정-->
			<input type="hidden" id="profile" name="profile" value="${profile}">
			<div class="mb-3">
				<input type="text" class="form-control" id="email" name="email"
					title="아이디" placeholder="아이디 입력" style="margin-bottom: 10px;">
				<input type="button" class="btn btn-outline-info" value="아이디 찾기"
					onclick="findPasswordByEmail()" style="margin-top: auto;">
			</div>

			<div class="input-group mb-3">
				<input type="text" class="form-control" id="authenticNumber"
					placeholder="인증번호 입력" aria-label="인증번호 입력"
					aria-describedby="button-addon2" disabled onblur="authenticComp()">
				<button class="btn btn-dark" type="button" id="button-addon2"
					onclick="authentic()" disabled>인증번호 받기</button>
				<input type="hidden" class="btn btn-outline-info"
					id="authenticReset" value="다시 인증하기" onclick="resetAuthentic()" />
				<span id="authenticWord"></span>
			</div>

			<!-- 			새 비밀번호 입력 및 확인 -->
			<div class="mb-3" style="margin-bottom: 20px !important;">
				<input type="password" class="form-control" id="password"
					name="password" title="새 비밀번호 입력" placeholder="새 비밀번호 입력"
					disabled>
			</div>

			<div class="mb-3" style="margin-bottom: 20px !important;">
				<input type="password" class="form-control" id="passwordCheck"
					name="passwordCheck" title="비밀번호 확인" placeholder="비밀번호 확인" onblur="passwordOk(this.form)"
					disabled>
				<span id="passwordWord"></span>
			</div>

			<div class="d-flex justify-content-center mb-2 px-3">
				<input type="submit" class="btn btn-primary me-2" id="changePassword"
					style="background-color: #FF6347; border-color: #FF6347; color: white;"
					value="비밀번호 변경" disabled> 
				<input type="button" class="btn btn-light ms-2" value="뒤로가기" onclick="goBack()">
			</div>
		</form>
	</div>
</body>
</html>