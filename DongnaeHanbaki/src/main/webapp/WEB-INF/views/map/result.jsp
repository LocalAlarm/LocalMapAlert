<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결과 페이지</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>결과 페이지</h2>
        <%-- POST 방식으로 전달된 데이터를 받아오기 --%>
        <% String jsonData = request.getParameter("jsonData"); %>
        
        <%-- JSON 문자열을 JavaScript 객체로 변환 --%>
        <% if (jsonData != null && !jsonData.isEmpty()) { %>
            <script>
                var data = JSON.parse('<%= jsonData %>');
                console.log(data);

                // 데이터를 테이블로 표시하는 예시
                document.write("<table border='1'><tr><th>Key</th><th>Value</th></tr>");
                for (var key in data) {
                    document.write("<tr><td>" + key + "</td><td>" + data[key] + "</td></tr>");
                }
                document.write("</table>");
            </script>
        <% } else { %>
            <p>No data received.</p>
        <% } %>
    </div>
</body>
</html>
