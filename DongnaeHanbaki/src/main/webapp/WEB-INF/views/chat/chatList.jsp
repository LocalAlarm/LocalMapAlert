<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/chat/createRoom" method="post">
    <input type="text" name="name" placeholder="채팅방 이름">
    <button type="submit">방 만들기</button>
</form>

<table>
    <c:forEach var="room" items="${roomList}">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/chatRoom?roomId=${room.roomId}">
                    ${room.name}
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>