<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e&libraries=services"></script>
<style>
    .mb-3 {
        margin-bottom: 1rem; /* Adjust this value to increase/decrease the spacing */
    }
</style>
<script>
    var mapContainer, mapOption, map, geocoder, marker;

    window.onload = function() {
        mapContainer = document.getElementById('map'); // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

        // 지도를 미리 생성
        map = new daum.maps.Map(mapContainer, mapOption);
        // 주소-좌표 변환 객체를 생성
        geocoder = new daum.maps.services.Geocoder();
        // 마커를 미리 생성
        marker = new daum.maps.Marker({
            position: new daum.maps.LatLng(37.537187, 127.005476),
            map: map
        });
    }

    function sample5_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("sample5_address").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(addr, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {
                        var result = results[0]; // 첫번째 결과의 값을 활용

                        // 해당 주소에 대한 좌표를 받아서
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        // 지도를 보여준다.
                        mapContainer.style.display = "block";
                        map.relayout();
                        // 지도 중심을 변경한다.
                        map.setCenter(coords);
                        // 마커를 결과값으로 받은 위치로 옮긴다.
                        marker.setPosition(coords);
                    }
                });
            }
        }).open();
    }

    // 뒤로가기
    function goBack() {
        window.history.back();
    }

    // 비밀번호 보이게 하기
    function passwordVisibility(inputFieldId, button) {
        var inputField = document.getElementById(inputFieldId);
        if (!inputField) {
            console.error(`Element with ID ${inputFieldId} not found.`);
            return;
        }
        var fieldType = inputField.getAttribute('type');
        if (fieldType === 'password') {
            inputField.setAttribute('type', 'text');
            button.textContent = '비밀번호 숨기기';
        } else {
            inputField.setAttribute('type', 'password');
            button.textContent = '비밀번호 보이기';
        }
    }

    // 이메일 형식 검사와 중복 체크
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

    // 비번 일치 확인
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
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
<div class="loginWrapper">
    <div class="logo">
        <img src="https://res.cloudinary.com/dyjklyydu/image/upload/v1717463449/%EB%8F%99%EB%84%A4%ED%95%9C%EB%B0%94%ED%80%B4__1_-removebg-preview_cgjoy5.png" alt="로고 이미지">
    </div>
     <h2>회원가입</h2>
    <form action="join" method="post">
        <div class="mb-3">
            <input type="email" class="form-control" name="email" title="이메일" placeholder="이메일 입력" style="margin-bottom: 10px;">
            <input type="button" class="btn btn-outline-info" value="이메일 중복 체크" onclick="checkEmail(this.form)" style="margin-top: auto;">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="password" class="form-control" id="password" name="password" title="비밀번호" placeholder="비밀번호 입력">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="password" class="form-control" id="passwordCheck" name="passwordCheck" title="비밀번호 확인" placeholder="비밀번호 확인">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="text" class="form-control" name="nickname" title="닉네임" placeholder="닉네임">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="text" class="form-control" id="sample5_address" placeholder="주소" style="margin-bottom: 10px;">
            <input type="button" class="btn btn-outline-warning" value="주소 검색" onclick="sample5_execDaumPostcode()" style="margin-top: auto;"><br>
            <div id="map" style="width:350px;height:350px;margin-top:10px;display:none"></div>
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="text" class="form-control" name="recoverEmail" title="복구이메일" placeholder="복구이메일 입력">
        </div>
        <div class="mb-3" style="margin-bottom: 20px !important;">
            <input type="file" class="form-control" name="image">
        </div>
        <div class="d-flex justify-content-center mb-2 px-3">
            <input type="submit" class="btn btn-primary me-2" style="background-color: #FF6347; border-color: #FF6347; color: white;" value="회원가입 하기" onclick="return checkEmail(this.form)">
            <input type="button" class="btn btn-light ms-2" value="뒤로가기" onclick="goBack()">
        </div>
    </form>
</div>
</body>
</html>