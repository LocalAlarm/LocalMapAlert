<%@page import="com.spring.dongnae.user.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	// 커스텀맵 기능 개발중에 쓸 임시로그인 데이터 
/*   	UserVO user = new UserVO();
	user.setEmail("adminEmail");
	user.setNickname("관리자");
	session.setAttribute("user", user);  */
%>
    <title>index</title>
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css"> --%>
<style>
	body {
    background-color: #ffffff;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
    font-family: Arial, sans-serif;
	}
	
	.container {
	    text-align: center;
	}
	
	.logo img {
	    width: 300px;
	    height: auto;
	    margin-bottom: 20px;
	}
	
	.search-box {
	    display: flex;
	    justify-content: center;
	    margin-bottom: 20px;
	}
	
	.search-box input[type="text"] {
	    width: 400px;
	    padding: 10px;
	    border: 1px solid #ddd;
	    border-radius: 24px 0 0 24px;
	    outline: none;
	}
	
	.search-box button {
	    padding: 10px 20px;
	    border: none;
	    background-color: #4CAF50;
	    color: white;
	    border-radius: 0 24px 24px 0;
	    cursor: pointer;
	}
	
	.search-box button:hover {
	    background-color: #45a049;
	}
	
	.shortcut {
	    margin-top: 20px;
	}
	
	.shortcut button {
	    padding: 10px 20px;
	    border: 1px solid #ddd;
	    background-color: #f8f8f8;
	    color: #333;
	    border-radius: 24px;
	    cursor: pointer;
	}
	
	.shortcut button:hover {
	    background-color: #e7e7e7;
	}
	
</style>
</head>
<body>
<!--     <h2>index</h2> -->
<!--     <a href="login">로그인</a> -->
<!--     <a href="map">지도보기</a> -->
<!--     <a href="customMap">커스텀맵 보기</a> -->
<!--     <form action="login" method="post"> -->
<!--         <label for="id">ID:</label> -->
<!--         <input type="text" id="id" name="id"> -->
<!--         <br> -->
<!--         <label for="password">Password:</label> -->
<!--         <input type="password" id="password" name="password"> -->
<!--         <br> -->
<!--         <br> -->
<!--         <input type="submit" value="Login"> -->
<!--     </form> -->
    <div class="container">
        <div class="logo">
            <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png" alt="로고 이미지">
        </div>
        <div class="search-box">
            <input type="text" placeholder="검색 또는 URL 입력">
            <button type="submit">검색</button>
        </div>
        <div class="shortcut">
            <button>바로가기 추가</button>
            <button onclick="location.href='login'">로그인</button>
        </div>
    </div>
</body>
</html>

