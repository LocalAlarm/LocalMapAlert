<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>이메일 찾기</title>
    <script>
        function validateForm() {
            var nickname = document.getElementById("nickname").value;
            var email = document.getElementById("recoverEmail").value;
            var nicknamePattern = /^[a-zA-Z]{1,8}$|^[\u3131-\uD79D]{1,8}$/;
            var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

            if (!nicknamePattern.test(nickname)) {
                alert("유효한 닉네임을 입력하세요. 닉네임은 1자에서 8자 사이의 영문 또는 한글이어야 합니다.");
                document.getElementById("nickname").value = '';
                return false;
            }
            
            if (!emailPattern.test(email)) {
                alert("유효한 이메일 주소를 입력하세요.");
                document.getElementById("recoverEmail").value = '';
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
    <h1>이메일 찾기 페이지</h1>
    <form action="findEmailProcess" method="post" onsubmit="return validateForm()">
        <label for="nickname">닉네임:</label>
        <input type="text" id="nickname" name="nickname"><br><br>
        <label for="recoverEmail">복구 이메일:</label>
        <input type="text" id="recoverEmail" name="recoverEmail"><br><br>
        <input type="submit" value="찾기">
    </form>
</body>
</html>

