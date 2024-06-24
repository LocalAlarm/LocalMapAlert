function moimFunction() {
    connectMoim();
    resetMoimModal();
    createMoimModalFunction();
    initializeMoimModal();
    registerMoimModal();
	processRegistMoim();
    initializeSearchMoimsEvents();
}

function connectMoim() {
    moimSocket = new WebSocket('ws://localhost:8088/dongnae/moim');

    moimSocket.onopen = function (event) {
        console.log('Connected to MoimWebSocket');
    }

    moimSocket.onmessage = function (event) {
        try {
            var moimJsonData = JSON.parse(event.data);
            handleMoimUserRooms(moimJsonData);
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
            buttonHtml += '<li class="mb-1 mt-1 moim-list collapse__sublink" id="' + element.id + '">';
            buttonHtml += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-shield-fill-plus" viewBox="0 0 16 16">';
            buttonHtml += '<path fill-rule="evenodd" d="M8 0c-.69 0-1.843.265-2.928.56-1.11.3-2.229.655-2.887.87a1.54 1.54 0 0 0-1.044 1.262c-.596 4.477.787 7.795 2.465 9.99a11.8 11.8 0 0 0 2.517 2.453c.386.273.744.482 1.048.625.28.132.581.24.829.24s.548-.108.829-.24a7 7 0 0 0 1.048-.625 11.8 11.8 0 0 0 2.517-2.453c1.678-2.195 3.061-5.513 2.465-9.99a1.54 1.54 0 0 0-1.044-1.263 63 63 0 0 0-2.887-.87C9.843.266 8.69 0 8 0m-.5 5a.5.5 0 0 1 1 0v1.5H10a.5.5 0 0 1 0 1H8.5V9a.5.5 0 0 1-1 0V7.5H6a.5.5 0 0 1 0-1h1.5z"/>';
            buttonHtml += '</svg>';
            buttonHtml += element.name + '</li>';
        }
    } else {
        console.error("userRooms.masterMoims is not an array");
    }

    if (Array.isArray(userRooms.moims)) { // 메시지가 배열인지 확인
        for (const element of userRooms.moims) {
            buttonHtml += '<li class="mb-1 mt-1 moim-list collapse__sublink" id="' + element.id + '">' + element.name + '</li>';
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
            if (response.status === 409) {
                // 중복된 모임 이름인 경우
                const errorMessage = await response.text();
                showDangerAlert('중복된 모임입니다!', '이미 생성된 모임의 이름이에요.', '다른 이름을 사용해주세요.');
            } else {
                throw new Error('네트워크가 응답하지 않습니다!!');
            }
            return;
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
                $('#moim-modal').attr('chat-token', data.chatId);
                loadBoardList(1); // 첫 번째 페이지를 로드
                createMoimModal.show();

                $.ajax({
                    type:'POST',
                    url: '/dongnae/chat/getChatHistory',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify({ id: data.chatId }),
                    success: async function(response) {
                        if (isEmpty(response)) {
                        } else if (Array.isArray(response)) { // 메시지가 배열인지 확인
                            for (const element of response) {
                                displayMessage(element, 'chat-histroy-div');
                            }
                        } else {
                            console.error("chatRoom.messages is not an array");
                        }
                    }
                })
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

    // 게시물 작성 이벤트
    $('#postMoimForm').on('submit', function(e) {
        e.preventDefault();
        var page = $('#moim-modal').attr('data-moim-page');
        // FormData 객체 생성
        var formData = new FormData(this);
        var moimId = $('#moim-modal').attr('data-moim-id'); // moimId 가져오기
        formData.append('moimId', moimId); // moimId 추가

        // 작성된 게시물 데이터를 서버에 전송
        $.ajax({
            url: `/dongnae/moim/${moimId}/board`,
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log('게시물 작성 성공:', response);
                createPostModal.hide();
                loadBoardList(page);
            },
            error: function(err) {
                console.error('게시물 작성 실패:', err);
            }
        });
    });
}

async function moimPagenate(moimId, page, pageSize) {
    // 모임 페이지에 관한 것을 모달에 저장.
    page = Number(page); // page 변수를 숫자로 변환
    $('#moim-modal').attr('data-moim-page', page);
    const moimPagenation = $('#moim-pagination');
    moimPagenation.empty();
    const totalCount = await $.ajax({
        url: `/dongnae/moim/${moimId}/boards/count`,
        method: 'GET'
    });
    if (page >= 3) {
        moimPagenation.append(`<li class="page-item"><a class="page-link" onclick="loadBoardList(${page - 2})">${page - 2}</a></li>`);
    }
    if (page >= 2) {
        moimPagenation.append(`<li class="page-item"><a class="page-link" onclick="loadBoardList(${page - 1})">${page - 1}</a></li>`);
    }
    moimPagenation.append(`<li class="page-item"><a class="page-link disabled active">${page}</a></li>`);
    if (totalCount > (page * pageSize)) {
        moimPagenation.append(`<li class="page-item"><a class="page-link" onclick="loadBoardList(${page + 1})">${page + 1}</a></li>`);
    }
    if (totalCount > ((page + 1) * pageSize)) {
        moimPagenation.append(`<li class="page-item"><a class="page-link" onclick="loadBoardList(${page + 2})">${page + 2}</a></li>`);
    }
}

function loadBoardList(page) {
    var moimId = $('#moim-modal').attr('data-moim-id'); // moimId 가져오기
    const moimPageSize = 10;
    moimPagenate(moimId, page, moimPageSize);

    $.ajax({
        url: `/dongnae/moim/${moimId}/boards`,
        method: 'GET',
        dataType: 'json',
        data: {
            page: page-1,
            size: moimPageSize
        },
        success: function(data) {
            console.log(data);
            const boardList = $('#moim-board-list');
            boardList.empty();

            if (data.content.length > 0) {
                data.content.forEach(function(board) {
                    // Add the board item with a placeholder for the author
                    const boardItem = $(`
                    <tr class="moim-board-item" data-id="${board.id}">
                        <td>${board.title}</td>
                        <td id="author-${board.id}">Loading...</td>
                        <td>
                            <button class="btn btn-primary btn-sm moim-board-edit" onclick="editMoimBoard('${board.id}')">수정</button>
                            <button class="btn btn-danger btn-sm moim-board-delete" onclick="deleteMoimBoard('${board.id}')">삭제</button>
                        </td>
                    </tr>
                    `);
                    boardList.append(boardItem);

                    // Fetch the author's name and image
                    searchUserByToken(board.author, function(err, userData) {
                        if (err) {
                            $(`#author-${board.id}`).html('Unknown Author');
                        } else {
                            $(`#author-${board.id}`).html(`
                                <img src="${userData.image}" alt="${userData.nickname}" style="width: 35px; height: 35px; border-radius: 50%; margin-right: 10px;">
                                ${userData.nickname}
                            `);
                        }
                    });
                });

                // 게시글 클릭 이벤트 추가
                $(document).on('click', '.moim-board-item', function(event) {
                    // 클릭한 요소가 삭제 또는 수정 버튼일 경우 이벤트를 중지합니다.
                    if ($(event.target).closest('.moim-board-edit, .moim-board-delete').length > 0) {
                        return;
                    }

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

function deleteMoimBoard(boardId) {
    // Send the DELETE request using fetch API
    fetch(`/dongnae/moim/${boardId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        var page = $('#moim-modal').attr('data-moim-page');
        loadBoardList(page);
        return response.json(); // Assuming the response is JSON
    })
    .then(data => {
        console.log('Board deleted successfully:', data);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

function editMoimBoard(boardId) {
    $.ajax({
        url: `/dongnae/moim/board/${boardId}`,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            // 게시글 상세 정보를 모달에 채워넣기
            $('#edit-moim-board-title').val(data.title);
            $('#edit-moim-board-content').val(data.content);

            // 모달을 보여주기
            var postDetailModal = new bootstrap.Modal($('#moim-edit-detail-modal')[0]);
            postDetailModal.show();
        },
        error: function(err) {
            console.error('Error fetching post details:', err);
        }
    });
}


function showMoimBoardDetail(boardId) {
    resetMoimDetailModal();
    $.ajax({
        url: `/dongnae/moim/board/${boardId}`,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            // 게시글 상세 정보를 모달에 채워넣기
            $('#post-detail-title').text(data.title);
            $('#post-detail-content').text(data.content);
            const commentsList = $('#post-detail-comments-list');
            commentsList.empty(); // 댓글 목록 초기화

            // 작성자 토큰 값을 사용하여 사용자 정보를 가져오기
            searchUserByToken(data.author, function(err, userData) {
                if (err) {
                    $('#post-detail-author').text('Unknown Author');
                } else {
                    $('#post-detail-author').html(`
                        <img src="${userData.image}" alt="${userData.nickname}" style="width: 35px; height: 35px; border-radius: 50%; margin-right: 10px;">
                        ${userData.nickname}
                    `); // 변환된 사용자 이름을 표시
                }
            });

            // Carousel 처리
            const postDetailCarouselContainer = $('#post-detail-carousel-container');
            const postDetailCarouselInner = $('#post-detail-carousel-inner');
            postDetailCarouselInner.empty();
            console.log(data);
            if (data.images && data.images.length > 0) {
                data.images.forEach(function(image, index) {
                    postDetailCarouselInner.append(`
                        <div class="carousel-item ${index === 0 ? 'active' : ''}" style="background-color: white;">
                            <img src="${image.image}" alt="Post Image ${index + 1}" class="d-block" style="height: 450px;">
                        </div>
                    `);
                });
                postDetailCarouselContainer.show();
            } else {
                postDetailCarouselContainer.hide();
            }
            MoimCommentList(data.comments, commentsList);

            // 댓글 작성 이벤트 설정
            $('#post-detail-comment-submit').off('click').on('click', function() {
                const commentContent = $('#post-detail-comment-input').val();
                if (commentContent.length < 5) {
                    showDangerAlert('너무 적어요.', '댓글은 최소 5글자 이상이어야 합니다.', '조금 더 작성해보세요!')
                } else {
                    submitMoimComment(boardId, commentContent);
                }
            });

            // 모달을 보여주기
            var postDetailModal = new bootstrap.Modal($('#moim-post-detail-modal')[0]);
            postDetailModal.show();
        },
        error: function(err) {
            console.error('Error fetching post details:', err);
        }
    });
}


function submitMoimComment(boardId, content) {
    $.ajax({
        url: `/dongnae/moim/${boardId}/add-comments`,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ content: content }),
        success: function(data) {
            // 댓글 작성 후 댓글 리스트를 다시 로드
            const commentsList = $('#post-detail-comments-list');
            commentsList.empty(); // 댓글 목록 초기화
            fetchComments(boardId, commentsList);
        },
        error: function(err) {
            console.error('Error submitting comment:', err);
        }
    });
}

async function fetchComments(boardId, commentsList) {
    try {
        const response = await fetch(`/dongnae/moim/get-comments/${boardId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        const comments = await response.json();
        // 함수
        MoimCommentList(comments, commentsList)
    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

async function MoimCommentList(moimCommentList, commentsList) {
    if (moimCommentList.length > 0) {
        // 모든 댓글에 대해 searchUserByToken을 호출하고 결과를 Promise 배열로 반환
        const commentPromises = moimCommentList.map(comment => 
            new Promise((resolve, reject) => {
                searchUserByToken(comment.author, (err, userData) => {
                    if (err) {
                        resolve(`
                            <div class="comment">
                                <p><strong>Unknown Author</strong>: ${comment.content}</p>
                            </div>
                        `);
                    } else {
                        resolve(`
                            <div class="comment">
                                <p><img src="${userData.image}" alt="${userData.nickname}" style="width: 35px; height: 35px; border-radius: 50%; margin-right: 10px;">
                                <strong>${userData.nickname}</strong>: ${comment.content}</p>
                            </div>
                        `);
                    }
                });
            })
        );

        // 모든 Promise가 완료될 때까지 기다림
        const commentElements = await Promise.all(commentPromises);

        // 결과를 순서대로 commentsList에 추가
        commentElements.forEach(commentHtml => {
            commentsList.append(commentHtml);
        });
    } else {
        commentsList.append('<p>댓글이 없습니다.</p>');
    }
}


function resetMoimDetailModal() {
    // 입력된 값을 초기화
    $('#post-detail-title').text('');
    $('#post-detail-content').text('');
    $('#post-detail-author').html('');
    $('#post-detail-carousel-inner').empty();
    $('#post-detail-comments-list').empty();
    $('#post-detail-comment-input').val('');
    $('#collapseComments').collapse('hide');
}

function offDarkBackgroundOfMoimDetailModal() {
	$('#moim-post-detail-modal').on('show.bs.modal', function (e) {
        var modal = new bootstrap.Modal(document.getElementById('moim-post-detail-modal'), {
            backdrop: false
        });
	});
}

function initializeSearchMoimsEvents() {
    // searchMoimResultsElement 클래스를 가진 요소에 대한 마우스 오버 이벤트 처리
    $(document).on('mouseenter', '.searchMoimResultsElement', function () {
        $(this).addClass('active');
    });

    $(document).on('mouseleave', '.searchMoimResultsElement', function () {
        $(this).removeClass('active');
    });

    $(document).on('click', '.searchMoimResultsElement', function () {
        const clickText = $(this).text();
        $('#search-moim').val(clickText);
        hideMoimSearchResults();
        enableMoimRegisterButton(); // 클릭된 값과 일치하므로 버튼 활성화
    });

    // 검색창 입력 시 이벤트 처리
    $('#search-moim').on('input', function () {
        var searchString = $(this).val(); // 검색어 가져오기
        if (searchString.length >= 1) { // 검색어가 1글자 이상일 때만 검색 요청
            searchMoimByName(searchString);
        } else {
            hideMoimSearchResults(); // 검색어가 없을 때는 검색 결과 창을 숨깁니다.
            $('#moim-search-results').empty(); // 검색 결과 비우기
            disableMoimRegisterButton(); // 버튼 비활성화
        }
    });
}

async function processRegistMoim() {
    $('#register-moim-button').on('click', async function (event) {
        const searchString = $('#search-moim').val();
        // 친구 요청 확인 메시지 표시
        if (confirm("모임에 가입을 하시겠습니까?")) {
            try {
                const response = await fetch(`/dongnae/moim/${encodeURIComponent(searchString)}/add-participants`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to register for the moim');
                }
                const data = await response.json();
                showSuccessAlert("가입 성공", "모임에 가입되었습니다.", "");
            } catch (error) {
                console.error(error);
                // 에러 처리
            }
        } else {
            event.preventDefault(); // 기본 동작 막기
        }
    });
}

function hideMoimSearchResults() {
    // 검색 결과 창을 숨깁니다.
    $('#moim-search-results').hide();
}

function displaySearchMoimsResults(data, searchString) {
        // 검색 결과를 표시할 HTML 문자열을 생성합니다.
        var html = '';
        var matchFound = false;
        data.forEach(function (result) {
            // 각 결과를 리스트 아이템으로 표시합니다.
            html += '<li class="list-group-item searchMoimResultsElement">';
            html += '<img src="' + result.profilePic + '" alt="Profile Image" class="rounded-circle" style="width: 35px; height: 35px; margin-right: 10px;">';
            html += result.name;
            html += '</li>';
            if (result.name === searchString) {
                matchFound = true;
            }
        });
    
        // 생성된 HTML을 검색 결과 창에 넣어줍니다.
        $('#moim-search-results').html(html);
    
        // 검색 결과 창을 보이게 합니다.
        $('#moim-search-results').show();
    
        if (matchFound) {
            enableFriendRequestButton(); // 일치하는 검색 결과가 있을 때만 버튼 활성화
        } else {
            disableMoimRegisterButton(); // 일치하는 검색 결과가 없으면 버튼 비활성화
        }

}

function enableMoimRegisterButton() {
    $('#register-moim-button').prop('disabled', false);
    $('#register-moim-button').removeClass('btn-secondary');
    $('#register-moim-button').addClass('btn-outline-success');
}

function disableMoimRegisterButton() {
    $('#register-moim-button').prop('disabled', true);
    $('#register-moim-button').addClass('btn-secondary');
    $('#register-moim-button').removeClass('btn-outline-success');
}

// 모임 가입을 하는 모달창
function registerMoimModal(){
    var registerMoimModal = new bootstrap.Modal($('#register-moim-modal')[0]);
    $('#nav__register-moim').on('click', function() {
        registerMoimModal.show();
    });
};

function resetMoimModal() {
    $('#moim-modal').on('hidden.bs.modal', function () {
        // 제목 초기화
        $('#moim-modal-title').text('');
        
        // 게시물 목록 초기화
        $('#moim-board-list').empty();
        
        // 페이지네이션 초기화
        $('#moim-pagination').empty();
        
        // 채팅 내역 초기화
        $('#moim-modal .chat-histroy-div').empty();
        $('#moim-modal .chat-new-div').empty();
        
        // 메시지 입력 필드 초기화
        $('#moim-message-input').val('');
        
        // chat-token 초기화 (필요 시)
        $('#moim-modal').removeAttr('chat-token');
    });
}