<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<style>
[data-vbg] {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    background-origin: border-box;
}
body, html {
    height: 100%;
    margin: 0;
    padding: 0;
}
</style>
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
<script src="https://unpkg.com/youtube-background@1.0.14/jquery.youtube-background.min.js"></script>
	<div data-vbg="https://www.youtube.com/watch?v=YPsuAeJuOiM"></div>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('[data-vbg]').youtube_background();
	});
</script>
</body>
<script>
</script>
</html>