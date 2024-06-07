<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필페이지</title>
</head>
<body>
    <%-- 세션에 저장된 UserVO 객체의 속성들 출력하기 --%>
    <h1>프로필 정보</h1>
    <p>이메일: ${sessionScope.userVO.email}</p>
    <p>이름: ${sessionScope.userVO.name}</p>
    <!-- 다른 속성들도 필요에 따라 추가하십시오. -->
</body>
</html>  
