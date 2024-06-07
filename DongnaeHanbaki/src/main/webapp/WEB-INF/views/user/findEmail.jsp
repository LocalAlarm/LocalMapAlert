<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>이메일 찾기</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
    <script>
        function validateForm() {
            var nickname = document.getElementById("nickname").value;
            var email = document.getElementById("recoverEmail").value;
            var nicknamePattern = /^[a-zA-Z]{1,8}$|^[\u3131-\uD79D]{1,8}$/;
            var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

            if (!nicknamePattern.test(nickname)) {
                alert("유효한 닉네임을 입력하세요. 닉네임은 1자에서 8자 사이의 영문 또는 한글이어야 합니다.");
                document.getElementById("nickname").value = '';
                return false;
            }
            
            if (!emailPattern.test(email)) {
                alert("유효한 이메일 주소를 입력하세요.");
                document.getElementById("recoverEmail").value = '';
                return false;
            }

            return true;
        }
        
        function goBack() {
            window.history.back();
        }
    </script>
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
<div class="loginWrapper">
    <div class="logo">
        <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png" alt="로고 이미지">
    </div> 
    <h4 align="center">이메일 찾기</h4>
    <hr>
    	<form action="findEmailProcess" method="post" onsubmit="return validateForm()">
        	<div class="mb-3">
		    	<input type="text" class="form-control" name="nickname" title="닉네임" placeholder="닉네임 입력" style="margin-bottom: 10px;">
		    </div>
		    
		    <div class="mb-3">
		    	<input type="text" class="form-control" name="recoverEmail" title="복구이메일" placeholder="복구이메일 입력" style="margin-bottom: 10px;">
		    </div>
		    
		    
		     <div class="d-flex justify-content-center mb-2 px-3">
	            <input type="submit" class="btn btn-primary me-2" style="background-color: #FF6347; border-color: #FF6347; color: white;" value="찾기">
	            <input type="button" class="btn btn-light ms-2" value="뒤로가기" onclick="goBack()">
	        </div>
            
		</form> 
</div> 
</body>
</html>
