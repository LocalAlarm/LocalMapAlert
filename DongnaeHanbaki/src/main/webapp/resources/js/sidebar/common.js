// 공통적으로 사용하는 요소에 대한 js

function getIdByClass(className) {
    // 특정 클래스명을 가진 모든 요소를 가져옵니다.
    var elements = document.getElementsByClassName(className);
    // 요소가 존재할 경우, 첫 번째 요소의 아이디를 반환합니다.
    if (elements.length > 0) {
        return elements[0].id;
    }
    return null;
}

// 비동기로 토큰에 대한 닉네임을 가져오는 함수
function getNickname(token) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/dongnae/api/getNickname',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ token: token }),
            success: function (response) {
                console.log('Received data:', response);
                resolve(response.nickname);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error fetching nickname:', errorThrown);
                reject(null);
            }
        });
    });
}

function isUserRooms(data) {
    return data.requestIds !== undefined && data.friendIds !== undefined;
}

// 유저 메일로 유저를 검색하는 함수
async function searchUserByEmail(email) {
    try {
        const response = await fetch('/dongnae/api/searchUserByEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email })
        });
        if (!response.ok) {
            throw new Error('Failed to fetch user data');
        }
        const data = await response.json();
        const searchString = $('#searchFriend').val();
        if (data.length > 0) { // 검색 결과가 있을 경우에만 표시
            displaySearchUsersResults(data, searchString); // 검색 결과를 화면에 표시
        } else {
            hideSearchResults(); // 검색 결과가 없을 때는 결과 창을 숨깁니다.
            disableFriendRequestButton(); // 버튼 비활성화
        }
    } catch (error) {
        console.error(error);
        // 에러 처리
        disableFriendRequestButton(); // 버튼 비활성화
    }
}

// 모임을 모임 이름으로 검색하는 함수
async function searchMoimByName(searchData) {
    console.log(searchData);
    try {
        const response = await fetch('/dongnae/moim-search/name', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain'
            },
            body:searchData
        });
        if (!response.ok) {
            throw new Error('Failed to fetch user data');
        }
        const data = await response.json();
        console.log(data);
        console.log(data.content && data.content.length > 0)
        const searchString = $('#search-moim').val();
        if (data.content && data.content.length > 0) { // 검색 결과가 있을 경우에만 표시
            displaySearchMoimsResults(data.content, searchString); // 검색 결과를 화면에 표시
        } else {
            hideSearchResults(); // 검색 결과가 없을 때는 결과 창을 숨깁니다.
            disableMoimRegisterButton(); // 버튼 비활성화
        }
    } catch (error) {
        console.error(error);
        // 에러 처리
        disableFriendRequestButton(); // 버튼 비활성화
    }
}

function initializeCollapseMenu() {
    $(document).on('click', '.collapse__nav', function (e) {
        // li 요소가 클릭된 경우는 제외
        if (!$(e.target).is('card, card *, li, li*, ion-icon, ion-icon*')) {
            let $collapseMenu = $(this).closest('.collapse__nav').find('.collapse__menu');
            let $collapseLink = $(this).closest('.collapse__nav').find('.collapse__link');

            $collapseMenu.toggleClass('showCollapse');
            $collapseLink.toggleClass('rotate');
        }
    });
}

function initializeSidebarToggle() {
    // 사이드바를 확장하는 클릭 이벤트 리스너
    $('#nav-toggle').on('click', function () {
        $('#side-navbar').toggleClass('expander');
        $(this).toggleClass('rotate');
        $('#body-pd').toggleClass('body-pd');

        // 모든 collapse__menu에서 showCollapse 클래스 제거
        $('.collapse__menu').removeClass('showCollapse');
        $('.collapse__link').removeClass('rotate');
    });
}

function searchUserByToken(token, callback) {
    $.ajax({
        url: '/dongnae/api/getUserVOByToken',
        method: 'POST',
        contentType: 'text/plain',
        data: token, // 단순 문자열 토큰 값
        success: function(userData) {
            callback(null, userData);
        },
        error: function(err) {
            console.error('Error fetching user data:', err);
            callback(err);
        }
    });
}

function getDataTokenByClass(className) {
    var element = document.querySelector(`.${className}`);
    if (element) {
        return element.getAttribute('data-token');
    } else {
        console.error(`Element with class ${className} not found.`);
        return null;
    }
}

function getChatTokenById(Id) {
    var element = document.getElementById(Id); // #를 제거합니다
    if (element) { 
        return element.getAttribute('chat-token');
    } else {
        return null;
    }
}

function initializeMenuActivation() {
    $(document).on('click', '.nav__link', function () {
        $('.nav__link').removeClass('side__active');
        $(this).addClass('side__active');
    });
}

var isEmpty = function (value) {
    if (value == "" || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
        return true;
    } else {
        return false;
    }
};

function showDangerAlert(title, text, footerText) {
	Swal.fire({
		icon: "error",
		title: title,
		text: text,
        footer: '<span style="color:indianred">' + footerText + '</span>'
	});
}

function showSuccessAlert(title, text, footerText) {
	Swal.fire({
		icon: "success",
		title: title,
		text: text,
		footer: '<span style="color:indianred">' + footerText + '</span>'
	});
}