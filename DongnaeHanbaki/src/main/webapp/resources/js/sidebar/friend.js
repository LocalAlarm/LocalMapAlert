function friendFunction() {
    connectFriend();
    approveFriendRequest();
    rejectFriendReqeust();
    initializeSearchUsersEvents();
    initializeFriendRequest();
    friendRequestModal();
    deleteFriend();
}

// 친구 추가, 제거, 요청 기능과 관련된 js파일
function connectFriend() {
    friendSocket = new WebSocket('ws://localhost:8088/dongnae/friend'); // WebSocket 서버에 연결

    friendSocket.onopen = function (event) {
        console.log('Connected to FriendWebSocket'); // 연결 성공 시 콘솔에 메시지 출력
    };

    friendSocket.onmessage = function (event) {
        var JsonData = JSON.parse(event.data);
        try {
            loadFriendList();
            loadFriendRequests();
        } catch (e) {
            console.error("Error processing WebSocket message: ", e);
        }
    };

    friendSocket.onclose = function (event) {
        console.log('Disconnected from WebSocket'); // 연결 종료 시 콘솔에 메시지 출력
    };
    friendSocket.onerror = function (error) {
        console.log("WebSocket error: " + error);
    };
}

// 친구 요청 받은 데이터를 불러오기 함수
function loadFriendRequests() {
    $.ajax({
        url: '/dongnae/friendData/friendRequest',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            try {
                const container = $('#friend-requests');
                container.empty();
                var friendRequestHtml = '';
                response.forEach(data => {
                    friendRequestHtml += `<li class "mb-1 mt-1 collapse__sublink" req-data-token="${data.email}">
                                            <ion-icon name="add" class="friendApprove collapse__sublink"></ion-icon>
                                            <ion-icon name="trash-outline" class="friendReject collapse__sublink"></ion-icon>
                                            ${data.email}
                                        </li>`;
                })
                $('#friend-requests').html(friendRequestHtml);
            } catch (error) {
                console.error('Error parsing response JSON:', error);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error fetching friend requests:', errorThrown);
            console.error('Status:', textStatus);
            console.error('Response:', jqXHR.responseText);
            // 추가 로그
            console.error('Full error details:', jqXHR);
        }
    });
}

async function loadFriendList() {
    $.ajax({
        type: "POST",
        url: "/dongnae/friendData/friendIds",
        success: function(data) {
            var friendList = $("#friendList");
            friendList.empty(); // 기존 목록을 비움

            if (data && data.length > 0) {
                $.each(data, function(index, user) {
                    console.log(data);
                    var listItem = $(`<li class="mb-1 mt-1 chatToastBtn collapse__sublink side-friendlist" data-token="${user.chatRoomId}">${user.roomName}</li>`);
                    friendList.append(listItem);
                });
            }
        },
        error: function(xhr, status, error) {
            showDangerAlert("오류 발생", "친구 요청을 가져오는 데 실패했습니다.", "");
        }
    });
}

function approveFriendRequest() {
    $(document).on('click', '.friendApprove', function () {
        var requestEmail = $(this).parent().attr('req-data-token');
        $.ajax({
            url: '/dongnae/friendData/approveFriendRequest', // 요청을 처리할 서버의 URL 경로
            type: 'POST', // POST 방식으로 요청
            contentType: 'application/json', // 요청의 Content-Type 설정
            data: JSON.stringify({ requestEmail: requestEmail }), // 요청 본문에 요청 ID를 포함하여 전송
            success: function (response) {
                console.log('Success:', response);
                // 친구 요청 목록을 갱신하는 함수 호출
                loadFriendRequests();
                loadFriendList();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error:', textStatus, errorThrown);
                showDangerAlert('오류 발생!', '친구 요청 수락 중 오류가 발생하였습니다.', '다시 시도해주세요.');
            }
        });
    });
}

function rejectFriendReqeust() {
    $(document).on('click', '.friendReject', function() {
        console.log("거절버튼");
        var requestEmail = $(this).parent().attr('id');
        
        $.ajax({
            url: '/dongnae/friendData/rejectFriendRequest',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ requestEmail: requestEmail }),
            success: function (response) {
                loadFriendRequests();
                loadFriendList();
            }, 
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error:', textStatus, errorThrown);
                showDangerAlert('오류 발생!', '친구 데이터 처리 중 오류가 발생하였습니다.', '다시 시도해주세요.');
            }
        })
    })
}

//친구 요청이 도착했을 때 웹 페이지에서 알림을 표시하는 함수
function showFriendRequestNotification(senderName) {
    if (Notification.permission === "granted") {
        // 사용자에게 알림 표시 권한이 이미 부여된 경우
        var notification = new Notification('친구 요청', {
            body: senderName + '님이 친구 요청을 보냈습니다.',
        });
    } else if (Notification.permission !== "denied") {
        // 사용자에게 알림 표시 권한을 요청
        Notification.requestPermission().then(function (permission) {
            if (permission === "granted") {
                // JSON 형식의 알림 메시지 생성
                var notificationMessage = {
                    title: '친구 요청',
                    body: senderName + '님이 친구 요청을 보냈습니다.'
                };
                // 알림 표시
                var notification = new Notification(notificationMessage.title, notificationMessage);
                console.log(notification);
            }
        });
    }
}

function initializeSearchUsersEvents() {
    // searchResultsElement 클래스를 가진 요소에 대한 마우스 오버 이벤트 처리
    $(document).on('mouseenter', '.searchResultsElement', function () {
        $(this).addClass('active');
    });

    $(document).on('mouseleave', '.searchResultsElement', function () {
        $(this).removeClass('active');
    });

    $(document).on('click', '.searchResultsElement', function () {
        const clickText = $(this).text();
        $('#searchFriend').val(clickText);
        hideSearchResults();
        enableFriendRequestButton(); // 클릭된 값과 일치하므로 버튼 활성화
    });

    // 검색창 입력 시 이벤트 처리
    $('#searchFriend').on('input', function () {
        var searchString = $(this).val(); // 검색어 가져오기
        if (searchString.length >= 1) { // 검색어가 1글자 이상일 때만 검색 요청
            searchUserByEmail(searchString);
        } else {
            hideSearchResults(); // 검색어가 없을 때는 검색 결과 창을 숨깁니다.
            $('#searchResults').empty(); // 검색 결과 비우기
            disableFriendRequestButton(); // 버튼 비활성화
        }
    });
}

async function initializeFriendRequest() {
    // 친구 요청 버튼 클릭 시 이벤트 처리
    $('#request-friend-button').on('click', async function (event) {
        const searchString = $('#searchFriend').val();
        const matchingResult = $('#searchResults').children().filter(function () {
            return $(this).text() === searchString;
        });
        if (matchingResult.length === 1) {
            // 친구 요청 확인 메시지 표시
            if (confirm("친구 요청을 보내시겠습니까?")) {
                // 확인 버튼을 눌렀을 때의 동작
                const requestEmail = matchingResult.text();
                const requestData = {
                    request: "REQUEST",
                    requestEmail: requestEmail
                };

                try {
                    const response = await fetch('/dongnae/friendData/sendFriendRequest', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestData)
                    });

                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    alert("친구 요청이 성공적으로 전송되었습니다!");
                } catch (error) {
                    console.error('Error:', error);
                    alert("친구 요청 전송에 실패했습니다.");
                }
            } else {
                // 취소 버튼을 눌렀을 때의 동작
                event.preventDefault(); // 기본 동작 막기
            }
        } else {
            event.preventDefault(); // 기본 동작 막기
            alert("잘못 검색하였습니다"); // 알림 창 띄우기
        }
    });
}

// 검색한 유저에 대한 정보를 보여주는 함수
function displaySearchUsersResults(results, searchString) {
    // 검색 결과를 표시할 HTML 문자열을 생성합니다.
    var html = '';
    var matchFound = false;
    results.forEach(function (result) {
        // 각 결과를 리스트 아이템으로 표시합니다.
        html += '<li class="list-group-item searchResultsElement">';
        html += '<img src="' + result.image + '" alt="Profile Image" class="rounded-circle" style="width: 35px; height: 35px; margin-right: 10px;">';
        html += result.email;
        html += '</li>';
        if (result.email === searchString) {
           // 자기자신이거나 이미 친구에 있으면 제외하는 코드가 필요함! searchString외에!
            matchFound = true;
        }
    });

    // 생성된 HTML을 검색 결과 창에 넣어줍니다.
    $('#searchResults').html(html);

    // 검색 결과 창을 보이게 합니다.
    $('#searchResults').show();

    if (matchFound) {
        enableFriendRequestButton(); // 일치하는 검색 결과가 있을 때만 버튼 활성화
    } else {
        disableFriendRequestButton(); // 일치하는 검색 결과가 없으면 버튼 비활성화
    }
}

// 친구 요청 모달을 띄우는 코드
function friendRequestModal(){
    var friendRequestModal = new bootstrap.Modal($('#friendRequestModal')[0]);
    $('#nav__friend-request').on('click', function() {
        friendRequestModal.show();
    });
};

// 검색 결과 창을 숨기는 함수
function hideSearchResults() {
    // 검색 결과 창을 숨깁니다.
    $('#searchResults').hide();
}

// 친구 요청 버튼을 비활성화하는 함수
function disableFriendRequestButton() {
    $('#request-friend-button').prop('disabled', true);
    $('#request-friend-button').addClass('btn-secondary');
    $('#request-friend-button').removeClass('btn-outline-success');
}

// 친구 요청 버튼을 활성화하는 함수
function enableFriendRequestButton() {
    $('#request-friend-button').prop('disabled', false);
    $('#request-friend-button').removeClass('btn-secondary');
    $('#request-friend-button').addClass('btn-outline-success');
}


function deleteFriend() {
    $('.side-friendlist').on('contextmenu', function(event) {
        event.preventDefault(); // 기본 오른쪽 클릭 메뉴를 막음
        alert('오른쪽 마우스 버튼 클릭됨!');
    });
}