<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
<style>
	#container { width: 700px; margin: auto; }
	h1 { text-align: center; }
	table { border-collapse: collapse; }
	table, th, td {
		border: 1px solid black;
		margin: 0 auto;
	}
	th { background-color: orange; }
	.center { text-align: center; }
</style>
</head>
<body>
\${user } : ${user }<br>
\${userVO } : ${userVO }
 
<div id="container">
	<h3>테스트님 환영합니다...<a href="logout">로그아웃</a></h3>
	<h1><a href="map">지도보기</a></h1>
</div>

</body>
</html>








