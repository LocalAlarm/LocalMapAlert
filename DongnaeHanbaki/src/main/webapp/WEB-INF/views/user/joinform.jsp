<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e&libraries=services"></script>
<style>
    .address-container {
        display: flex;
        align-items: center;
    }
    .address-container input[type="text"] {
        flex: 1;
        margin-right: 5px;
    }
</style>
<script>
	//중복체크
	var duplicateCheck = false;
	//회원가입 폼에 필수 입력값 없으면 회원가입 완료버튼 못가게 막으면 됨
	//나머지 함수도 체크하면 다 "" 처리
	//주소찾기
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
	
	function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 주소 정보를 해당 필드에 추가
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동
                document.getElementById("sample6_detailAddress").focus();

                // 지도에 주소 표시
                var geocoder = new daum.maps.services.Geocoder();

                // 주소로 좌표를 검색
                geocoder.addressSearch(addr, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {
                        var coords = new daum.maps.LatLng(results[0].y, results[0].x);

                        // 지도 표시
                        document.getElementById('map').style.display = "block";
                        map.relayout();
                        map.setCenter(coords);
                        marker.setPosition(coords);
                    }
                });
            }
        }).open();

        // 지도 클릭 이벤트 등록
        daum.maps.event.addListener(map, 'click', function(mouseEvent) {
            // 클릭한 위치의 좌표
            var latlng = mouseEvent.latLng;

            // 마커 위치를 클릭한 위치로 설정
            marker.setPosition(latlng);

            // 좌표로 주소 정보를 요청
            geocoder.coord2Address(latlng.getLng(), latlng.getLat(), function(result, status) {
                if (status === daum.maps.services.Status.OK) {
                    var address = result[0].address.address_name;

                    // 주소를 해당 필드에 추가
                    document.getElementById('sample6_address').value = address;
                    document.getElementById('sample6_detailAddress').value = '';
                    document.getElementById('sample6_extraAddress').value = '';
                }
            });
        });
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
			$("#email").focus();
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
				if (response === "duplicate") {
					alert("이미 사용중인 이메일입니다.");
					$("#email").focus();
				} else {
					if (confirm("이 아이디는 사용 가능합니다. \n사용하시겠습니까?")) {
						$("#email").prop("readonly", true);
	                    $("#emailDuplicate").attr("disabled", true);
	                    $("#emailDuplicate").prop("type", "hidden");
	                    $("#emailReset").attr("disabled", false);
	                    $("#emailReset").prop("type", "button");
	                    duplicateCheck = true;
	                }
					return false;
				}
			},
	        error: function (request, status,error) {
	            alert("ajax 실행 실패");
	            alert("code:" + request.status + "\n" + "error :" + error);
	        }
		});
	}
	
	//다시쓰기
	function resetEmail() {
		$("#email").prop("readonly", false);
		$("#email").val('');
		$("#emailDuplicate").attr("disabled", false);
		$("#emailDuplicate").prop("type", "button");
        $("#emailReset").attr("disabled", true);
        $("#emailReset").prop("type", "hidden");
	}

	// 비번 일치 확인
	function passwordOk(frm) {
		var password = frm.password.value;
		var passwordCheck = frm.passwordCheck.value;

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
		
		checkPassowrd = true;

	}
	
	
	function nicknameOk(frm) {
		var nickname = frm.nickname.value;
		
		// 닉네임 형식 검증을 위한 정규 표현식
		var nicknamePattern = /^[a-zA-Z]{1,8}$|^[\u3131-\uD79D]{1,8}$/;

		if (!nicknamePattern.test(nickname)) {
			alert("닉네임은 영어로 이루어진 8글자 이하의 형식이거나 한글로 이루어진 8글자 이하의 형식이어야 합니다.");
			return false;
		}
	}


	function joinValidate() {
		var email = $("#email").val().trim();
		var password = $("#password").val().trim();
		console.log(password);
		var nickname = $("#nickname").val().trim();
		var recoverEmail = $("#recoverEmail").val().trim();
		if (email == "") {
			alert("이메일을 입력해주세요!");
			$("#email").focus();
			return false;
		} else if (!duplicateCheck) {
			alert("이메일 중복체크해주세요!");
			$("#email").focus();
			return false;
		} else if (password == "") {
			alert("비밀번호를 입력해주세요!");
			$("#password").focus();
			return false;
		} else if (nickname == "") {
			alert("닉네임을 입력해주세요!");
			$("#nickname").focus();
			return false;
		}
	}
</script>
</head>
<body>
<div>
	<div>
		<h1>회원가입</h1>
		<form action="join" method="post" onsubmit="return joinValidate()">
			<table>
				<tr> 
					<td>
						<input type="email" id="email" name="email" title="이메일" placeholder="이메일 입력" />
						<input type="button" id="emailDuplicate" value="이메일 중복 체크" onclick="checkEmail(this.form)" />
						<input type="hidden" id="emailReset" value="다시 입력" onclick="resetEmail()" disabled />
					</td>
				</tr>
				<tr>
					<td>
						<input type="password" id="password" name="password" title="비밀번호" placeholder="비밀번호 입력">
						<button type="button" onclick="passwordVisibility('password', this)">비밀번호 보이기</button>
					</td>
				</tr>
				<tr>
					<td>
						<input type="password" id="passwordCheck" name="passwordCheck" title="비밀번호 확인" placeholder="비밀번호 확인" onblur="passwordOk(this.form)">
						<button type="button" onclick="passwordVisibility('passwordCheck', this)">비밀번호 보이기</button>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="nickname" name="nickname" title="닉네임" placeholder="닉네임 입력(영어나 한글로만 이루어진 8글자 이하)">
					</td>
				</tr>
				<tr>
                    <td>
                        <!-- 건희 -->
                        <div class="address-container">
                            <input type="text" name="address" id="sample6_address" placeholder="주소">
                            <input type="button" onclick="sample6_execDaumPostcode()" value="주소 찾기">
                        </div>
                        <input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소">
                        <input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목">
                        <div id="map" style="width:350px;height:350px;margin-top:10px;display:none"></div>
                    </td>
                </tr>
				<tr>
					<td>
						<input type="text" id="recoverEmail" name="recoverEmail" title="복구이메일" placeholder="복구이메일 입력">
					</td>
				</tr>
				<tr>
					<td>
						<input type="file" name="image">
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
