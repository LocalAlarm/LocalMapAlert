<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 찾기 결과</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
   <style>
        .text-center {
            text-align: center;
        }
    </style>
    <script>
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
    <h4 align="center">이메일 찾기 결과</h4>
    <hr>
       <c:choose>
           <c:when test="${empty findEmail}">
               <div class="text-center">
                  <p>조회결과가 없습니다.</p>
              </div>
              
              <div class="d-flex justify-content-center mb-2 px-3">
                  <input type="button" class="btn btn-light ms-2" value="뒤로가기" onclick="goBack()">
              </div>
            </c:when>
            <c:otherwise>
               <div class="mb-3">
               <input type="text" class="form-control" style="margin-bottom: 10px;" value="${findEmail}" readonly="readonly">
            </div>
                <!-- 로그인 버튼 추가 -->
                <div class="d-flex justify-content-center mb-2 px-3">
                <input type="submit" class="btn btn-primary me-2" style="background-color: #FF6347; border-color: #FF6347; color: white;" value="로그인"   onclick="location.href='login'">
             </div>
              </c:otherwise>
        </c:choose>
</div>
</body>
</html>