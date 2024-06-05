<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 찾기 결과</title>
    <style>
        .resultBox {
            border: 1px solid #ccc;
            padding: 20px;
            margin: 20px;
        }
    </style>
</head>
<body>
    <h1>이메일 찾기 결과</h1>
    <div class="resultBox">
        <c:choose>
            <c:when test="${empty findEmail}">
                <p>조회결과가 없습니다.</p>
            </c:when>
            <c:otherwise>
                <p>${findEmail}</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>