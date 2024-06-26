<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필페이지</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<style>
    .popup {
        display: none;
        position: fixed;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        padding: 20px;
        background-color: white;
        box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.2);
        border-radius: 10px;
        z-index: 1000;
        width: 80%;
        max-width: 400px;
    }

    .popup input[type="text"],
    .popup input[type="email"],
    .popup input[type="file"] {
        width: calc(100% - 20px);
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .popup button {
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .popup button.close {
        background-color: #f44336;
    }

    .popup button:hover {
        background-color: #45a049;
    }

    .popup button.close:hover {
        background-color: #e53935;
    }

    .overlay {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 500;
    }

    #map {
        width: 100%;
        height: 350px;
        margin-top: 10px;
        display: none;
        border-radius: 4px;
    }

    #nicknameCheckWord,
    #recoverEmailCheckWord {
        font-size: 0.9em;
        margin-top: -10px;
        margin-bottom: 10px;
        display: block;
    }
    
    .btn.btn-outline-warning {
        border: 1px solid #FFC107;
        background-color: transparent;
        color: #FFC107;
        padding: 10px 20px;
        border-radius: 4px;
        cursor: pointer;
        transition: all 0.3s;
    }

    .btn.btn-outline-warning:hover {
        background-color: #FFC107;
        color: white;
    }
    
</style>
<!-- 테이블 스타일 - 건희  -->
<style>
        body {
            font-family: Arial, sans-serif;
        }
        .profile-table {
            width: 600px;
            margin: 20px auto;
            border: 1px solid #dddddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-collapse: separate;
            border-spacing: 0;
        }
        .profile-table th, .profile-table td {
            padding: 10px;
            border: none;
        }
        .profile-table th {
            background-color: #f7f7f7;
            font-size: 20px;
            font-weight: 500;
            text-align: left;
            padding-left: 20px;
            border-bottom: 1px solid #dddddd;
        }
        .profile-table td {
            text-align: left;
            font-size: 16px;
            border-bottom: 1px solid #dddddd;
        }
        .profile-table tr:last-child td {
		    border-bottom: none;
		}
        .profile-table a {
            color: #1a73e8;
            text-decoration: none;
        }
        .profile-table .profile-photo {
            width: 65px;
            height: 65px;
            border-radius: 50%;
            object-fit: cover;
        }
        .profile-table .profile-row {
            display: flex;
            align-items: center;
        }
        .profile-table .profile-icon {
            margin-right: 10px;
        }
        .profile-table .profile-value {
            flex: 1;
            font-size: 13px;
        }
        .profile-table .profile-value2 {
	        flex: 1;
	        font-size: 16px;
	    }
        .profile-table .profile-link {
            text-align: right;
            font-size: 14px;
            color: #1a73e8;
        }
        .clickable-row {
            cursor: pointer;
        }
        .clickable-row:hover {
            background-color: #f0f0f0;
        }
        .clickable-row:active {
            background-color: #e0e0e0;
        }
</style>
<script>
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
                       extraAddr = '' + extraAddr + '';
                   }
                   // 조합된 참고항목을 해당 필드에 넣는다.
                   document.getElementById("sample6_detailAddress").value = extraAddr;
               
               } else {
                   document.getElementById("sample6_detailAddress").value = '';
               }

               // 주소 정보를 해당 필드에 추가
               document.getElementById("sample6_address").value = addr;
               // 커서를 상세주소 필드로 이동
//                document.getElementById("sample6_detailAddress").focus();

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
               }
           });
       });
   }
   

	function popup(idx) {
		var Label = "";
		switch(idx) {
			case 1: 
				Label = "새 주소를 입력하세요: ";
				$("#popupInput").prop("type", "hidden");
				$("#nickname-text").prop("type", "hidden");
				$("#nicknameCheckWord").text("");
				$("#sample6_address").prop("type", "text");
				$("#sample6_detailAddress").prop("type", "text");
				$("#findAddress").prop("type", "button");
				$("#recoverEmail-text").prop("type", "hidden");
				$("#recoverEmailCheckWord").text("");
				$("#imageUpdate").prop("type", "hidden");
				$("#saveUpdate").attr("disabled", false);
				break;
			case 2: 
				Label = "새 상세주소를 입력하세요: ";
				$("#popupInput").prop("type", "text");
				$("#sample6_address").prop("type", "hidden");
				$("#sample6_detailAddress").prop("type", "hidden");
				$("#map").css("display", "none");
				$("#findAddress").prop("type", "hidden");
				$("#nickname-text").prop("type", "hidden");
				$("#nicknameCheckWord").text("");
				$("#recoverEmail-text").prop("type", "hidden");
				$("#recoverEmailCheckWord").text("");
				$("#imageUpdate").prop("type", "hidden");
				$("#saveUpdate").attr("disabled", false);
				break;
			case 3: 
				Label = "새 닉네임을 입력하세요: ";
				$("#popupInput").prop("type", "hidden");
				$("#sample6_address").prop("type", "hidden");
				$("#sample6_detailAddress").prop("type", "hidden");
				$("#map").css("display", "none");
				$("#findAddress").prop("type", "hidden");
				$("#nickname-text").prop("type", "text");
				$("#nicknameCheckWord").text("");
				$("#recoverEmail-text").prop("type", "hidden");
				$("#recoverEmailCheckWord").text("");
				$("#imageUpdate").prop("type", "hidden");
				break;
			case 4: 
				Label = "새 복구 이메일을 입력하세요: ";
				$("#popupInput").prop("type", "hidden");
				$("#sample6_address").prop("type", "hidden");
				$("#sample6_detailAddress").prop("type", "hidden");
				$("#map").css("display", "none");
				$("#findAddress").prop("type", "hidden");
				$("#nickname-text").prop("type", "hidden");
				$("#nicknameCheckWord").text("");
				$("#recoverEmail-text").prop("type", "text");
				$("#imageUpdate").prop("type", "hidden");
				break;
			case 5:
				Label = "새 이미지를 업로드하세요: ";
				$("#imageUpdate").prop("type", "file");
				$("#popupInput").prop("type", "hidden");
				$("#sample6_address").prop("type", "hidden");
				$("#sample6_detailAddress").prop("type", "hidden");
				$("#map").css("display", "none");
				$("#findAddress").prop("type", "hidden");
				$("#nickname-text").prop("type", "hidden");
				$("#nicknameCheckWord").text("");
				$("#recoverEmail-text").prop("type", "hidden");
				$("#recoverEmailCheckWord").text("");
				$("#saveUpdate").attr("disabled", false);
				break;
			case 6:
				if (confirm("비밀번호를 수정하면 다시 로그인합니다. \n수정하시겠습니까?")) {
					location.href="findPassword?profile=profile";
				} else {
					break;
				}
		}
		if (idx == "6") {
			closePopup();
		} else {
			$("#popupLabel").text(Label);
			$("#popupInput").data("idx", idx);	
			$(".popup, .overlay").show();
		}
	}
	
	function updateProfile() {
		var idx = $("#popupInput").data("idx");
		var email = $("#email").text().trim();
		var formData = new FormData();
		formData.append("email", email);
		formData.append("idx", idx);
		if (idx == "1") {
			var address = $("#sample6_address").val();
			var detailAddress = $("#sample6_detailAddress").val();
			formData.append("address", address);
			formData.append("detailAddress", detailAddress);
		}
		if (idx == "2") {
			var detailAddress = $("#popupInput").val();
			console.log(nickname);
			formData.append("newValue", detailAddress);
		}
		if (idx == "3") {
			var nickname = $("#nickname-text").val();
			console.log(nickname);
			formData.append("newValue", nickname);
		}
		if (idx == "4") {
			var recoverEmail = $("#recoverEmail-text").val();
			formData.append("newValue", recoverEmail);
		}
		if (idx == "5") {
			formData.append("image", $("#imageUpdate")[0].files[0]);
		} else {
			var newValue = $("#popupInput").val().trim();
			var data = {
				email : email,
				idx : idx,
				newValue : newValue
			};
		}
		// 파일 전송 AJAX 요청
		$.ajax({
			type : "POST",
			url : "updateProfile",
			data : formData,
			processData : false,
			contentType : false,
			success : function(response) {
				console.log(response);
				showSuccessAlert('성공', '프로필이 업데이트 되었습니다.', '');
				location.reload();
			},
			error : function(request, status, error) {
				showDangerAlert('오류', '프로필 업데이트에 실패했습니다!', '');
				console.error("Error:", request.status, error);
			}
		});
		$(".popup, .overlay").hide();
	}

	function closePopup() {
		$(".popup, .overlay").hide();
	}

	function nicknameOk() {
		var nicknameCheck = $("#nickname-text").val();
		console.log(nicknameCheck);
		// 욕설 언어 필터링을 위한 정규 표현식
	    var profanityPattern = /ㅆㅂ|ㅅㅂ|ㄲㅈ|ㄳ|ㅅㄱ|ㅇㅁ|ㅅㄲ|ㄳㄲ|ㅈㄹ|ㅈㄴ|존나|졸라|씨발|죽어|뒤져|꺼져|지랄|가슴|슴가|유방|유두|꼭지|젖|엉디|엉덩이|궁디|염병|아가리|개새끼|새끼|시발|니애미|애미|엄마|섹스|야스|자지|보지|포르노|노|무현|일베|ㅇㅂ|년|tlqkf|toRL|fuck|shit|hell|mom|mother|sex|suck|shut|ass|pennis|pussy|nipple|faggot|bastard|idiot|whore|bitch|freak|gay|lesbian|av|porn/gi;
		// 닉네임 형식 검증을 위한 정규 표현식
		var nicknamePattern = /^[a-zA-Z]{1,8}$|^[\u3131-\uD79D]{1,8}$/;
		console.log(nicknamePattern);
		if (!nicknamePattern.test(nicknameCheck)) {
			console.log("ttttttt");
			$("#nicknameCheckWord").text(
					"닉네임은 영어로 이루어진 8글자 이하의 형식이거나 한글로 이루어진 8글자 이하의 형식이어야 합니다.")
					.css("color", "red");
			$("#nickname-text").val('');
			$("#nickname-text").focus();
			$("#saveUpdate").attr("disabled", true);
		} else if (profanityPattern.test(nicknameCheck)) {
			$("#nicknameCheckWord").text(
					"욕설(패드립/섹드립) 및 비방을 포함한 닉네임은 사용할 수 없습니다.")
					.css("color", "red");
			$("#nickname-text").val('');
			$("#nickname-text").focus();
			$("#saveUpdate").attr("disabled", true);
		} else {
			console.log("ffffffff");
			$("#nicknameCheckWord").text("올바른 형식입니다.").css("color", "#0404B4");
			$("#saveUpdate").attr("disabled", false);
		}

	}

	function checkEmail() {
		var recoverEmail = $("#recoverEmail-text").val();
		console.log(recoverEmail);
		// 이메일 형식 검증을 위한 정규 표현식
		var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
		console.log(emailPattern);
		if (!emailPattern.test(recoverEmail)) {
			console.log("ttttttt");
			$("#recoverEmailCheckWord").text("잘못된 이메일 형식입니다.").css("color",
					"red");
			$("#recoverEmail-text").val('');
			$("#recoverEmail-text").focus();
			$("#saveUpdate").attr("disabled", true);
		} else {
			console.log("ffffffff");
			$("#recoverEmailCheckWord").text("올바른 형식입니다.").css("color",
					"#0404B4");
			$("#saveUpdate").attr("disabled", false);
		}
	}
</script>
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
    <table class="profile-table">
        <thead>
            <tr>
                <th colspan="2">기본 정보</th>
            </tr>
        </thead>
        <tbody>
            <tr class="clickable-row" id="fixImage" onclick="popup(5)">
                <td class="profile-row">
                	<div class="profile-value">프로필 이미지</div>
                    <div class="profile-icon">
                        <img src="${user.image}" alt="프로필 사진" class="profile-photo">
                    </div>
                </td>
                <td class="profile-link">></td>
            </tr>
            <tr class="clickable-row" id="fixNickname" onclick="popup(3)">
                <td class="profile-row">
                    <div class="profile-value">닉네임</div>
                    <div class="profile-value2" id="nickname">${user.nickname}</div>
                </td>
                <td class="profile-link">></td>
            </tr>
        </tbody>
    </table>
    
    <table class="profile-table">
        <thead>
            <tr>
                <th colspan="2">연락처 정보</th>
            </tr>
        </thead>
        <tbody>
            <tr class="clickable-row">
                <td class="profile-row">
                	<div class="profile-value">이메일</div>
                    <div class="profile-value2" id="email">${user.email}</div>
                </td>
                <td class="profile-link">></td>
            </tr>
            <tr class="clickable-row" id="fixRecoverEmail" onclick="popup(4)">
                <td class="profile-row">
                    <div class="profile-value">복구 이메일</div>
                    <div class="profile-value2">${user.recoverEmail}</div>
                </td>
                <td class="profile-link">></td>
            </tr>
        </tbody>
    </table>
    
    <table class="profile-table">
        <thead>
            <tr>
                <th colspan="2">주소</th>
            </tr>
        </thead>
        <tbody>
            <tr class="clickable-row" id="fixAddress" onclick="popup(1)">
                <td class="profile-row">
                	<div class="profile-value">주소</div>
                   <div class="profile-value2">${user.address}</div>
                </td>
                <td class="profile-link">></td>
            </tr>
            <tr class="clickable-row" id="fixDetailAddress" onclick="popup(2)">
                <td class="profile-row">
                    <div class="profile-value">상세주소</div>
                    <div class="profile-value2">${user.detailAddress}</div>
                </td>
                <td class="profile-link">></td>
            </tr>
        </tbody>
    </table>
    
    <table class="profile-table">
        <thead>
            <tr>
                <th colspan="2">비밀번호</th>
            </tr>
        </thead>
        <tbody>
            <tr class="clickable-row" id="fixPassword" onclick="popup(6)">
                <td class="profile-row">
                    <div class="profile-value">비밀번호</div>
                    <div class="profile-value2">안전한 비밀번호를 사용하세요.</div>
                </td>
                <td class="profile-link">></td>
            </tr>
        </tbody>
	</table> 
	
	<table class="profile-table">
        <thead>
            <tr>
                <th colspan="2">지도</th>
            </tr>
        </thead>
        <tbody>
            <tr class="clickable-row" onclick="location.href='map'">
                <td class="profile-row">
                    <div class="profile-value">지도 바로가기</div>
                </td>
                <td class="profile-link">></td>
            </tr>
            <tr class="clickable-row" onclick="location.href='customMap'">
                <td class="profile-row">
                    <div class="profile-value">커스텀 지도 바로가기</div>
                </td>
                <td class="profile-link">></td>
            </tr>
        </tbody>
	</table> 
	
    <div class="popup">
     <p id="popupLabel"></p>
     <input type="text" id="popupInput">
     <input type="hidden" class="form-control" id="sample6_address" name="address" placeholder="주소" style="margin-bottom: 10px;" disabled>
     <input type="hidden" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소">
     <input type="hidden" class="btn btn-outline-warning" id="findAddress" value="주소 검색" onclick="sample6_execDaumPostcode()" style="margin-top: auto;">
     <div id="map" style="width:270px;height:350px;  margin-top:10px;display:none"></div>
     <input type="text" class="form-control" id="nickname-text" name="nickname" title="닉네임" placeholder="닉네임" onblur="nicknameOk()">
     <span id="nicknameCheckWord"></span><br>
     <input type="email" class="form-control" id="recoverEmail-text" name="recoverEmail" title="복구이메일" placeholder="복구이메일 입력" onblur="checkEmail()">
     <span id="recoverEmailCheckWord"></span><br>
     <input type="file" class="form-control" name="image" id="imageUpdate" accept="image/*">
     <button id="saveUpdate" onclick="updateProfile()" disabled>저장</button>
     <button class="close" onclick="closePopup()">닫기</button>
    </div>
    <div class="overlay" onclick="closePopup()"></div>
</body>
</html>
