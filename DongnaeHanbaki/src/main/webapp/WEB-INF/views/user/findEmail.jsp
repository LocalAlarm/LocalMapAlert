<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>이메일 찾기</title>
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

        .form-box {
            display: inline-block;
            padding: 20px;
            border: 2px solid #000;
            border-radius: 10px;
            background-color: #fff;
        }

        /* 입력 필드의 테두리 둥글게 */
        input[type="text"] {
            padding: 5px;
            margin: 10px 0;
            width: 100%;
            box-sizing: border-box;
            border-radius: 5px; /* 둥글게 만들기 */
            border: 1px solid #ccc; /* 테두리 선 스타일 설정 */
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
    <div class="container">
        <h1>이메일 찾기</h1>
        <div class="form-box">
            <form action="findEmailProcess" method="post" onsubmit="return validateForm()">
                <label for="nickname">닉네임</label>
                <input type="text" id="nickname" name="nickname"><br><br>
                <label for="recoverEmail">복구 이메일</label>
                <input type="text" id="recoverEmail" name="recoverEmail"><br><br>
                <input type="submit" value="찾기">
            </form>
        </div>
    </div>
</body>
</html>
