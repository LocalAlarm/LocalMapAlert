function connectMoim() {
    moimSocket = new WebSocket('ws://localhost:8088/dongnae/moim');

    moimSocket.onopen = function (event) {
        console.log('Connected to MoimWebSocket');
    }

    moimSocket.onmessage = function (event) {
        try {
            var moimJsonData = JSON.parse(event.data);
            if (isMessage(moimJsonData)) {
                handleMoimMessage(moimJsonData);
            } else if (isUserRooms(moimJsonData)) {
                handleMoimUserRooms(moimJsonData);
            } else {
                console.error("Unknown data type received:", moimJsonData);
            }

        } catch (e) {
            console.error("Error processing WebSocket message: ", e);
        }
    };

    moimSocket.onclose = function (event) {
        console.log('Disconnected from MoimWebSocket');
    };
    moimSocket.onerror = function (error) {
        console.log("WebSocket error: " + error);
    };
}

async function handleMoimUserRooms(userRooms) {
    var buttonHtml = '';
    if (Array.isArray(userRooms.masterMoims)) {
        for (const element of userRooms.masterMoims) {
            buttonHtml += '<li class="mb-1 mt-1 moim-list collapse__sublink" id="' + element.id + '">' + element.name + '</li>';
        }
    } else {
        console.error("userRooms.masterMoims is not an array");
    }

    if (Array.isArray(userRooms.moims)) { // 메시지가 배열인지 확인
        for (const element of userRooms.moims) {
            buttonHtml += '<li class="mb-1 mt-1 moim-list collapse__sublink" id="' + element.id + '">' + element + '</li>';
        }
    } else {
        console.error("userRooms.moims is not an array");
    }
    $('#moimList').html(buttonHtml);
}

function createMoimModalFunction(){
    var createMoimModal = new bootstrap.Modal($('#createMoimModal')[0]);
    $('#nav__create-moim').on('click', function() {
        createMoimModal.show();
    });
    
    // 모달창이 닫혔을대 폼을 리셋한다.
    $('#createMoimModal').on('hidden.bs.modal', function () {
        $(this).find('form')[0].reset();
    });
};

async function submitCreateMoimForm() {
    function validateTitle(title) {
        const titleRegex = /^[a-zA-Z0-9가-힣\s]{3,}$/; // 3글자 이상, 특수문자 제외
        return titleRegex.test(title);
    }

    function validateIntroduce(introduce) {
        return introduce.length >= 10; // 10글자 이상
    }
    
    const title = $('#createMoimModal-title').val();
    const introduce = $('#createMoimModal-introduce').val();
    const profilePic = $('#createMoimModal-profilePic')[0].files[0];

    if (!validateTitle(title)) {
        showDangerAlert('이름을 다시 지어주세요!', '모임 이름은 3글자 이상이어야 하며, 특수문자는 포함할 수 없습니다.', '수정을 하고 생성해주세요.');
        return;
    }

    if (!validateIntroduce(introduce)) {
        showDangerAlert('소개글을 다시 써주세요!', '모임 소개는 10글자 이상이어야 합니다.', '수정을 하고 생성해주세요.');
        return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('introduce', introduce);
    if (profilePic) {
        formData.append('profilePic', profilePic);
    }

    try {
        $('#createMoimForm').find('input, textarea, button').prop('disabled', true);

        const response = await fetch('/dongnae/moim/createMoim', {
            method: 'POST',
            body: formData,
        })

        if (!response.ok) {
            throw new Error('네트워크가 응답하지 않습니다!!');
        }

        showSuccessAlert('모임이 생성되었습니다.', '모임창을 확인해주세요!', '친구들과 활동해보세요!');
        $('#createMoimModal').modal('hide'); // Hide the modal
    } catch (error) {
        showDangerAlert('모임 생성에 실패했습니다!', `${error.message}`, '나중에 다시 시도해주세요.')
    }
}

function initializeMoimModal() {
    var createMoimModal = new bootstrap.Modal($('#moim-modal')[0]);
    var createPostModal = new bootstrap.Modal($('#moim-post-modal')[0]);
    
    $('#moimList').on('click', '.moim-list', function() {
        // 클릭된 li 요소의 id 가져오기
        const moimId = this.id;
        
        $.ajax({
            url: `/dongnae/moim/${moimId}`,
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                const modalTitle = $('#moim-modal-title');
                modalTitle.empty();
                modalTitle.append(`
                    <img src="${data.profilePic}" alt="Moim logo" style="width: 35px; height: 35px; border-radius: 50%; margin-right: 10px;">
                    ${data.name}
                `);
                createMoimModal.show();
            },
            error: function(err) {
                console.error('Error fetching moim data:', err);
            }
        });
    });

    // 작성하기 버튼 클릭 이벤트
    $('#openMoimPostModal').on('click', function() {
        createPostModal.show();
    });

    // 게시물 작성 폼 제출 이벤트
    $('#postForm').on('submit', function(e) {
        e.preventDefault();
        // 작성된 게시물 데이터를 서버에 전송 (예: AJAX 사용)
        const postTitle = $('#postTitle').val();
        const postContent = $('#postContent').val();
        
        // 여기에 AJAX 요청을 추가하여 서버에 데이터 전송 가능
        console.log('게시물 작성:', postTitle, postContent);

        // 작성 완료 후 모달 닫기
        createPostModal.hide();
    });
}


    // 모달창이 닫혔을대 폼을 리셋한다.
    $('#createMoimModal').on('hidden.bs.modal', function () {
        //$(this).find('form')[0].reset();
    });
