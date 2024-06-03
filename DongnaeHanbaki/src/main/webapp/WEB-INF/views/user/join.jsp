<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	
	// 뒤로가기
    function goBack() {
        window.history.back();
    }
	
	//비밀번호 보이게하기
    function passwordVisibility(inputField, button) {
        var fieldType = inputField.getAttribute('type');
        if (fieldType === 'password') {
            inputField.setAttribute('type', 'text');
            button.textContent = '비밀번호 숨기기';
        } else {
            inputField.setAttribute('type', 'password');
            button.textContent = '비밀번호 보이기';
        }
    }
	
	// 이메일 형식 검사와 중복체크
    function checkEmail(frm) {
        var checkEmail = frm.email.value;
        
        // 이메일 형식 검증을 위한 정규 표현식
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        
        if (checkEmail.trim().length == 0) {
            alert("이메일을 입력해주세요!");
            return false;
        }
        
        if (!emailPattern.test(checkEmail)) {
            alert("잘못된 이메일 형식입니다. 올바른 이메일을 입력해주세요.");
            
            frm.email.value = '';
            return false;
        }
        
        $.ajax({
            type: "POST",
            url: "checkEmail",
            data: {
                email: checkEmail 
            },
            success: function(response) {
                if (response === checkEmail) {
                    alert("이미 사용중인 이메일입니다.");
                } else {
                    alert("사용 가능한 이메일입니다.");
                }
            }
        });
    }
	
	//비번 일치 확인
    function passwordOk(frm) {
        var password = frm.password.value;
        var passwordCheck = frm.passwordCheck.value;
        var nickname = frm.nickname.value;
	
     // 비밀번호 형식 검증을 위한 정규 표현식
        var passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

        if (!passwordPattern.test(password)) {
            alert("비밀번호는 영어 문자와 하나의 특수 문자를 포함하여 8글자 이상이어야 합니다.");
            
         // 비밀번호와 비밀번호 확인 필드를 초기화
            frm.password.value = '';
            frm.passwordCheck.value = '';
            return false;
        }
        
        if (password !== passwordCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            
            // 비밀번호와 비밀번호 확인 필드를 초기화
            frm.password.value = '';
            frm.passwordCheck.value = '';
            return false;
        }
        
     // 닉네임 형식 검증을 위한 정규 표현식
       var nicknamePattern = /^[a-zA-Z]{1,8}$|^[\u3131-\uD79D]{1,8}$/;
        
        if (!nicknamePattern.test(nickname)) {
            alert("닉네임은 영어로 이루어진 8글자 이하의 형식이거나 한글로 이루어진 8글자 이하의 형식이어야 합니다.");
            return false;
        }

        return true;
    }
</script>
</head>
<body>
<div>
    <div>
        <h1>회원가입</h1>
        <form action="joinOk" method="post" onsubmit="return passwordOk(this)">
            <table>
                <tr> 
                    <td>
                        <input type="text" name="email" title="이메일" placeholder="이메일 입력">
                        <input type="button" value="이메일 중복 체크" onclick="checkEmail(this.form)">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="password" id="password" title="비밀번호" placeholder="비밀번호 입력(영어 문자와 하나의 특수 문자를 포함하여 8글자 이상)">
                        <button type="button" onclick="passwordVisibility(document.getElementById('password'), this)">비밀번호 보이기</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="passwordCheck" id="passwordCheck" title="비밀번호 확인" placeholder="비밀번호 확인">
                        <button type="button" onclick="passwordVisibility(document.getElementById('passwordCheck'), this)">비밀번호 보이기</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="nickname" title="닉네임" placeholder="닉네임 입력(영어나 한글로만 이루어진 8글자 이하)">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="address" title="주소" placeholder="주소 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="detailAddress" title="상제주소" placeholder="상세주소 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="recoverEmail" title="복구이메일" placeholder="복구이메일 입력">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="file" name="image">
                    </td>
                </tr>
                <tr>
                    <td class="button" colspan="2">
                        <input type="submit" value="회원가입 완료" onclick="return checkEmail(this.form)">
                        <input type="button" value="뒤로가기" onclick="goBack()">
                    </td>
                </tr>
            </table>
        </form> 
    </div>
</div>
</body>
</html>
