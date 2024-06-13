<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="WEB-INF/patials/commonHead.jsp"></jsp:include> <!-- 공통 헤더 파일 포함 -->
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap");
        /* VARIABLES CSS */
        :root {
            --nav--width: 92px;
            --first-color: #0c5df4;
            --bg-color: #12192c;
            --sub-color: #b6cefc;
            --white-color: #fff;
            --body-font: 'Poppins', sans-serif;
            --normal-font-size: 1rem;
            --small-font-size: .875rem;
            --z-fixed: 100;
        }

        /* BASE */
        *, ::before, ::after {
            box-sizing: border-box;
        }

        body {
            position: relative;
            margin: 0;
            padding: 2rem 6.75rem 0 0; /* 오른쪽에 패딩 추가 */
            font-family: var(--body-font);
            font-size: var(--normal-font-size);
            transition: .5s;
        }

        ul.collapse__menu {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        a.nav__link {
            text-decoration: none;
        }

        /* l NAV */
        .l-navbar {
            position: fixed;
            top: 0;
            right: 0; /* 오른쪽으로 위치 변경 */
            width: var(--nav--width);
            height: 100vh;
            background-color: var(--bg-color);
            color: var(--white-color);
            padding: 1.5rem 1.5rem 2rem;
            transition: .5s;
            z-index: var(--z-fixed);
            font-family: var(--body-font);
            font-size: var(--normal-font-size);
        }

        /* NAV */
        nav.sidebar {
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            overflow: hidden;
        }

        .nav__brand {
            display: grid;
            grid-template-columns: max-content max-content;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .nav__toggle {
            font-size: 1.25rem;
            padding: .75rem;
            cursor: pointer;
        }

        .nav__logo {
            color: var(--white-color);
            font-weight: 600;
        }

        .nav__link {
            display: grid;
            grid-template-columns: max-content max-content;
            align-items: center;
            column-gap: .75rem;
            padding: .75rem;
            color: var(--white-color);
            border-radius: .5rem;
            margin-bottom: 1rem;
            transition: .3s;
            cursor: pointer;
        }

        .nav__link:hover {
            background-color: var(--first-color);
        }

        .nav__icon {
            font-size: 1.25rem;
        }

        .nav_name {
            font-size: var(--small-font-size);
        }

        /* Expander menu */
        .expander {
            width: calc(var(--nav--width) + 9.25rem);
            right: 0; /* 확장시에도 오른쪽에 위치 */
        }

        /* Add padding body */
        .body-pd {
            padding: 2rem 16rem 0 0; /* 확장 시 오른쪽으로 패딩 추가 */
        }

        /* Active links menu */
        .active {
            background-color: var(--first-color);
        }

        /* COLLAPSE */
		.collapse__nav {
		    grid-template-columns: 20px max-content 1fr;
		}

        .collapse__link {
            justify-self: flex-end;
            transition: .5s;
        }

        .collapse__menu {
            display: none;
            padding: .75rem 2.25rem;
        }

        .collapse__sublink {
            color: var(--sub-color);
            font-size: var(--small-font-size);
        }

        .collapse__sublink:hover {
            color: var(--white-color);
        }

        /* Show collapse */
        .showCollapse {
            display: block;
        }

        /* Rotate icon */
        .rotate {
            transform: rotate(180deg);
            transition: .5s;
        }
    </style>
</head>
<body id="body-pd">
    <jsp:include page="WEB-INF/patials/commonBody.jsp"></jsp:include>
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
</body>
</html>

