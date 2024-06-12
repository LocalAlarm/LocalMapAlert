<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--   제이쿼리  -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<!--  팝업창 오픈소스  -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
 
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