<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 찾기 결과</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }

        .container {
            text-align: center;
        }

        .resultBox {
            display: inline-block;
            padding: 20px;
            border: 2px solid #000;
            border-radius: 10px;
            background-color: #fff;
        }

        p {
            margin: 10px 0;
        }

        input[type="submit"] {
            padding: 10px 20px;
            margin-top: 10px;
            background-color: #FF6347;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>이메일 찾기 결과</h1>
        <div class="resultBox">
            <c:choose>
                <c:when test="${empty findEmail}">
                    <p>조회결과가 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <p>${findEmail}</p>
                    <!-- 로그인 버튼 추가 -->
                    <input type="submit" value="로그인"  onclick="location.href='login'">
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
