<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script>
    function goBack() {
        window.history.back();
    }
</script>
</head>
<body>
<div>
    <div>
    <h1>회원가입</h1>
        <form action="" method="post">
            <table>
                <tr> 
                    <td>
                        <input type="email" name="email" title="이메일" placeholder="이메일 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="password" title="비밀번호" placeholder="비밀번호 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="nickname" title="닉네임" placeholder="닉네임 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="address" title="주소" placeholder="주소 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="file" name="filename">
                    </td>
                </tr>
                <tr>
                    <td class="button" colspan="2">
                        <input type="submit" value="회원가입 완료">
                        <input type="button" value="뒤로가기" onclick="goBack()">
                    </td>
                </tr>
            </table>
        </form> 
    </div>
</div>
</body>
</html>
