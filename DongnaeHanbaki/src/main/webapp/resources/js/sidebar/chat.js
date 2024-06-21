// 채팅창에 관련된 js 코드.

function connectChat() {
	chatSocket = new WebSocket('ws://localhost:8088/dongnae/chatList'); // WebSocket 서버에 연결

	chatSocket.onopen = function(event) {
        console.log('Connected to ChatWebSocket'); // 연결 성공 시 콘솔에 메시지 출력
    };

    chatSocket.onmessage = function(event) {
        try {
            var chatJsonData = JSON.parse(event.data);

            if (isMessage(chatJsonData)) {
                handleMessage(chatJsonData);
            } else if (isUserRooms(chatJsonData)) {
                handleUserRooms(chatJsonData);
            } else {
                console.error("Unknown data type received:", chatJsonData);
            }
        } catch (e) {
            console.error("Error processing WebSocket message: ", e);
        }
    };
    
    chatSocket.onclose = function(event) {
        console.log('Disconnected from WebSocket'); // 연결 종료 시 콘솔에 메시지 출력
    };
    chatSocket.onerror = function(error) {
        console.log("WebSocket error: " + error);
    };
}

// Message인지 판별하는 함수
function isMessage(data) {
    return data.senderToken !== undefined && data.content !== undefined;
}

function isUserRooms(data) {
    return data.requestIds !== undefined && data.friendIds !== undefined;
}

async function handleUserRooms(userRooms) {
    if (Array.isArray(userRooms.chatRoomIds)) { // 메시지가 배열인지 확인
        for (const element of userRooms.chatRoomIds) {
            var buttonHtml = '';
            buttonHtml += '<li class="mb-1 mt-1 chatToastBtn collapse__sublink" id="' + element + '">' + element + '</li>';
            $('#chatList').html(buttonHtml);
        }
    } else {
        console.error("userRooms.chatRoomIds is not an array");
    }
}

// Message 데이터를 처리하는 함수
async function handleMessage(message) {
    // 닉네임 가져오기
    const nickname = await getNickname(message.senderToken);
    // 가져온 닉네임을 사용하여 메시지 표시
    if (token === message.senderToken) {
        displayMessage(nickname, message.content, 'sent');
    } else {
        displayMessage(nickname, message.content, 'received');
    }
}

function sendMessage() {
    var messageInput = document.getElementById('message');
    var content = messageInput.value;
    if (content.trim() !== "") { // 메시지가 비어있지 않을 때만 전송
        var jsonMsg = {
            "roomId": getIdByClass('chat-toast-container'),
            "content": content,
            "timestamp": Date.now()
        };
     	chatSocket.send(JSON.stringify(jsonMsg)); // JSON.stringify() 함수를 사용하여 JSON 객체를 문자열로 변환하여 전송
        messageInput.value = ''; // 입력창 비우기
    }
}

function displayMessage(nickname, message, type) {
    var chatBox = document.getElementById('chatBox');
    var messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type); // 메시지 스타일 지정
    messageDiv.textContent = nickname + ': ' + message; // 메시지 내용 설정
    chatBox.appendChild(messageDiv); // 메시지를 채팅 박스에 추가
    chatBox.scrollTop = chatBox.scrollHeight; // 새 메시지가 추가될 때 스크롤을 맨 아래로 이동
}

function initResize() {
    const chatBox = document.getElementById('chatBox');
    const handle = document.getElementById('resizeHandle');
    let startY;
    let startHeight;

    handle.addEventListener('mousedown', (e) => {
        startY = e.clientY;
        startHeight = parseInt(document.defaultView.getComputedStyle(chatBox).height, 10);
        chatBox.style.overflowY = 'hidden'; // 드래그 중 스크롤바 숨기기
        document.documentElement.addEventListener('mousemove', doDrag, false);
        document.documentElement.addEventListener('mouseup', stopDrag, false);
    });

    function doDrag(e) {
        const newHeight = startHeight - (e.clientY - startY);
        chatBox.style.height = newHeight + 'px';
    }

    function stopDrag() {
        document.documentElement.removeEventListener('mousemove', doDrag, false);
        document.documentElement.removeEventListener('mouseup', stopDrag, false);
        chatBox.style.overflowY = 'auto'; // 드래그 끝난 후 스크롤바 자동 표시
    }
}

function scrollToBottom() {
    var chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
}

function initializeChatToast() {
    $('#friendList').on('click', '.chatToastBtn', function () {
        const chatToast = document.getElementById('chatToast');
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(chatToast);
        toastBootstrap.hide();
        var buttonId = $(this).attr('id');
        $.ajax({
            type: 'POST',
            url: '/dongnae/api/getChatHistory',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ id: buttonId }), // 버튼 ID 전송
            success: async function(response) {
                if (Array.isArray(response.messages)) { // 메시지가 배열인지 확인
                    for (const element of response.messages) {
                        getNickname(element.senderToken)
                            .then(nickname => {
                            console.log(nickname);
                                if (token === element.senderToken) {
                                    displayMessage(nickname, element.content, 'sent');
                                } else {
                                    displayMessage(nickname, element.content, 'received');
                                }
                            })
                            .catch(error => {
                                console.error('Error occurred:', error);
                            });
                    }
                } else {
                    console.error("chatRoom.messages is not an array");
                }
                $('.toast-container').attr('id', buttonId);
                toastBootstrap.show(); // Toast 버튼 클릭 시 Toast 표시
                scrollToBottom(); // 채팅창을 열었을 때 스크롤을 맨 아래로 이동
            },
            error: function(xhr, status, error) {
                // 에러 시 처리
                console.error('An error occurred while fetching chat info:', error);
            }
        });
    });
}

function handleMessageEnterPress() {
    document.getElementById('message').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            sendMessage(); // 엔터 키 누를 시 메시지 전송
            event.preventDefault(); // 엔터 키의 기본 동작 막기
        }
    });
}