<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<!--  팝업창 오픈소스  -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
<!-- Kakao 지도 API 스크립트 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6ba5718e3a47f0f8291a79529aae8d8e&libraries=services,clusterer,drawing"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- IONICONS -->
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

    <div class="l-navbar" id="side-navbar">
        <nav class="nav sidebar">
            <div>
                <div class="nav__brand">
                    <ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
                    <a href="#" class="nav__logo">유저이름</a>
                </div>
                <div class="nav__list">
                    <a href="#" class="nav__link active">
                        <ion-icon name="home-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">홈페이지</span>
                    </a>
                    <div class="nav__link collapse__nav">
                        <ion-icon name="person-add-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">친구요청</span>
                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
						<ul class="collapse__menu">
						    <li><a href="#" class="collapse__sublink" onclick="showAlert('Data')">Data</a></li>
						    <li><a href="#" class="collapse__sublink" onclick="showAlert('Group')">Group</a></li>
						    <li><a href="#" class="collapse__sublink" onclick="showAlert('Members')">Members</a></li>
						</ul>
                    </div>

                    <div class="nav__link collapse__nav">
                        <ion-icon name="people-circle-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">친구목록</span>
                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
                        <ul class="collapse__menu">
                            <li><a href="#" class="collapse__sublink">Data</a></li>
                            <li><a href="#" class="collapse__sublink">Group</a></li>
                            <li><a href="#" class="collapse__sublink">Members</a></li>
                        </ul>
                    </div>
                    <div class="nav__link collapse__nav">
                        <ion-icon name="chatbubbles-outline" class="nav__icon"></ion-icon>
                        <span class="nav_name">채팅방</span>
                        <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>
                        <ul class="collapse__menu">
                            <li><a href="#" class="collapse__sublink">Data</a></li>
                            <li><a href="#" class="collapse__sublink">Group</a></li>
                            <li><a href="#" class="collapse__sublink">Members</a></li>
                        </ul>
                    </div>
                </div>
                <a href="#" class="nav__link">
                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
                    <span class="nav_name">Log out</span>
                </a>
            </div>
        </nav>
    </div>
<script>
function showAlert(message) {
    alert(message);
}

$(document).ready(function(){
    // COLLAPSE MENU
    $(document).on('click', '.collapse__nav, .collapse__link', function(e) {
        // li 요소가 클릭된 경우는 제외
        if(!$(e.target).is('li, li *')) {
            let $collapseMenu = $(this).closest('.collapse__nav').find('.collapse__menu');
            let $collapseLink = $(this).closest('.collapse__nav').find('.collapse__link');
            
            $collapseMenu.toggleClass('showCollapse');
            $collapseLink.toggleClass('rotate');
        }
    });
    // 사이드바를 확장하는 클릭 이벤트 리스너
    $('#nav-toggle').on('click', function(){
        $('#side-navbar').toggleClass('expander');
        $('#body-pd').toggleClass('body-pd');
        
        // 모든 collapse__menu에서 showCollapse 클래스 제거
        $('.collapse__menu').removeClass('showCollapse');
        $('.collapse__link').removeClass('rotate');
    });

    // 클릭된 메뉴를 active로 활성화 시키고, 기존의 active를 제거하는 코드
    $(document).on('click', '.nav__link', function(){
        $('.nav__link').removeClass('active');
        $(this).addClass('active');
    });
});
</script>