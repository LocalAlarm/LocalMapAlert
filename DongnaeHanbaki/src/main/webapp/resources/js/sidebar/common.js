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
async function getNickname(token) {
    try {
        const response = await fetch('/dongnae/api/getNickname', { // 서버에서 토큰에 대한 닉네임을 가져오는 API 엔드포인트
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify({ token: token }) // 토큰을 서버로 전달
        });
        console.log(response);
        const data = await response.json();
        return data.nickname; // 서버에서 받은 닉네임 반환
    } catch (error) {
        console.error('Error fetching nickname:', error);
        return null; // 오류 발생 시 null 반환
    }
};

// 알람창을 띄워주는 함수
function showAlert(message) {
    alert(message);
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
            displaySearchResults(data, searchString); // 검색 결과를 화면에 표시
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

function initializeCollapseMenu() {
    $(document).on('click', '.collapse__nav', function(e) {
        // li 요소가 클릭된 경우는 제외
        if(!$(e.target).is('card, card *, li, li*')) {
            let $collapseMenu = $(this).closest('.collapse__nav').find('.collapse__menu');
            let $collapseLink = $(this).closest('.collapse__nav').find('.collapse__link');
            
            $collapseMenu.toggleClass('showCollapse');
            $collapseLink.toggleClass('rotate');
        }
    });
}

function initializeSidebarToggle() {
    // 사이드바를 확장하는 클릭 이벤트 리스너
    $('#nav-toggle').on('click', function(){
        $('#side-navbar').toggleClass('expander');
        $(this).toggleClass('rotate');
        console.log("회전회오리");
        $('#body-pd').toggleClass('body-pd');
        
        // 모든 collapse__menu에서 showCollapse 클래스 제거
        $('.collapse__menu').removeClass('showCollapse');
        $('.collapse__link').removeClass('rotate');
    });
}

function initializeMenuActivation() {
    $(document).on('click', '.nav__link', function(){
        $('.nav__link').removeClass('active');
        $(this).addClass('active');
    });
}