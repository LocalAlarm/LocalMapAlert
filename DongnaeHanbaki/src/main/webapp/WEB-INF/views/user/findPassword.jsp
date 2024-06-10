<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
<script>
       function goBack() {
            window.history.back();
        }
       //이메일 찾기
       function findPasswordByEmail() {
    	   var email = $("#id").val().trim();
    	   console.log(email);
    	   $.ajax({
    		   type: "POST",
    		   url:  "findEmail",
    		   data: {
    			   email: email
    		   },
    		   success: function(data) {
    			   console.log("data: " + data);
    		   }
    	   })
       }
       
</script>
</head>

<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
<div class="loginWrapper">
    <div class="logo">
        <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png" alt="로고 이미지">
    </div>
     <h4 align="center">비밀번호 찾기</h4>
     <hr>
    <form action="join" method="post">
        <div class="mb-3">
            <input type="text" class="form-control" id="id" name="id" title="아이디" placeholder="아이디 입력" style="margin-bottom: 10px;">
            <input type="button" class="btn btn-outline-info" value="아이디 찾기" onclick="findPasswordByEmail()" style="margin-top: auto;">
        </div>
        
       <div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="인증번호 입력" aria-label="인증번호 입력" aria-describedby="button-addon2" disabled>
		  <button class="btn btn-dark" type="button" id="button-addon2" disabled>인증번호 받기</button>
		</div>
       
<!-- 			새 비밀번호 입력 및 확인 -->
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="password" class="form-control" id="password-input" name="password-input" title="새 비밀번호 입력" placeholder="새 비밀번호 입력" disabled>
        </div>
        
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="password" class="form-control" id="password-check" name="password-check" title="비밀번호 확인" placeholder="비밀번호 확인">
        </div>
        
        <div class="d-flex justify-content-center mb-2 px-3">
            <input type="submit" class="btn btn-primary me-2" style="background-color: #FF6347; border-color: #FF6347; color: white;" value="비밀번호 변경" onclick="return checkEmail(this.form)">
            <input type="button" class="btn btn-light ms-2" value="뒤로가기" onclick="goBack()">
        </div>
    </form>
</div>
</body>
</html>