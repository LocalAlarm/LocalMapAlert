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
        // moimId를 모임 모달의 data 속성에 저장
        $('#moim-modal').attr('data-moim-id', moimId);
        
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
                loadBoardList(moimId, 0, 10); // 첫 번째 페이지를 로드
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
        
        // FormData 객체 생성
        var formData = new FormData(this);
        var moimId = $('#moim-modal').attr('data-moim-id'); // moimId 가져오기
        formData.append('moimId', moimId); // moimId 추가

        // 작성된 게시물 데이터를 서버에 전송 (예: AJAX 사용)
        $.ajax({
            url: `/dongnae/moim/${moimId}/board`,
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log('게시물 작성 성공:', response);
                // 작성 완료 후 모달 닫기
                createPostModal.hide();
            },
            error: function(err) {
                console.error('게시물 작성 실패:', err);
            }
        });
    });
}

function loadBoardList(moimId, page, size) {
    $.ajax({
        url: `/dongnae/moim/${moimId}/boards`,
        method: 'GET',
        dataType: 'json',
        data: {
            page: page,
            size: size
        },
        success: function(data) {
            const boardList = $('#moim-board-list');
            boardList.empty();

            if (data.content.length > 0) {
                data.content.forEach(function(board) {
                    boardList.append(`
                        <tr class="moim-board-item" data-id="${board.id}">
                            <td>${board.title}</td>
                            <td>${board.author}</td>
                        </tr>
                    `);
                });

                // 게시글 클릭 이벤트 추가
                $('.moim-board-item').on('click', function() {
                    const boardId = $(this).data('id');
                    showMoimBoardDetail(boardId);
                });
            } else {
                boardList.append('<li>게시물이 없습니다.</li>');
            }
        },
        error: function(err) {
            console.error('Error fetching boards:', err);
        }
    });
}

function showMoimBoardDetail(boardId) {
    $.ajax({
        url: `/dongnae/moim/board/${boardId}`,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            // 게시글 상세 정보를 모달에 채워넣기
            $('#post-detail-title').text(data.title);
            $('#post-detail-author').text(data.author);
            $('#post-detail-content').text(data.content);

            // Carousel 처리
            const postDetailCarouselContainer = $('#post-detail-carousel-container');
            const postDetailCarouselInner = $('#post-detail-carousel-inner');
            postDetailCarouselInner.empty();

            if (data.images && data.images.length > 0) {
                data.images.forEach(function(image, index) {
                    postDetailCarouselInner.append(`
                        <div class="carousel-item ${index === 0 ? 'active' : ''}">
                            <img src="${image.url}" alt="Post Image ${index + 1}" class="d-block w-100">
                        </div>
                    `);
                });
                postDetailCarouselContainer.show();
            } else {
                postDetailCarouselContainer.hide();
            }

            // 모달을 보여주기
            var postDetailModal = new bootstrap.Modal($('#moim-post-detail-modal')[0]);
            postDetailModal.show();
        },
        error: function(err) {
            console.error('Error fetching post details:', err);
        }
    });
}



    // 모달창이 닫혔을대 폼을 리셋한다.
    $('#createMoimModal').on('hidden.bs.modal', function () {
        //$(this).find('form')[0].reset();
    });
