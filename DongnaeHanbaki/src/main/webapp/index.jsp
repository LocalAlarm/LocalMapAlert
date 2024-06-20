<%@page import="com.spring.dongnae.user.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	// 커스텀맵 기능 개발중에 쓸 임시로그인 데이터 
  	UserVO user = new UserVO();
	user.setEmail("adminEmail");
	user.setNickname("관리자");
	session.setAttribute("user", user); 
%>
    <title>index</title>
</head>
<body>
    <h2>index</h2>
    <a href="login">로그인</a>
    <a href="map">지도보기</a>
    <a href="customMap">커스텀맵 보기</a>
    <form action="login" method="post">
        <label for="id">ID:</label>
        <input type="text" id="id" name="id">
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password">
        <br>
        <br>
        <input type="submit" value="Login">
    </form>
</body>
</html>

