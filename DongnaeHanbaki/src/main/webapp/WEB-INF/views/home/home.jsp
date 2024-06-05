<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Chat</title>
<jsp:include page="../../patials/commonHead.jsp"></jsp:include>
<script>
var socket = null;

function connect() {
    socket = new WebSocket('http://localhost:8088/dongnae/chat');
    socket.onopen = function(event) {
        console.log('Connected to WebSocket');
    };
    socket.onmessage = function(event) {
        var chatBox = document.getElementById('chatBox');
        var message = document.createElement('div');
        message.textContent = event.data;
        chatBox.appendChild(message);
    };
    socket.onclose = function(event) {
        console.log('Disconnected from WebSocket');
    };
}

function sendMessage() {
    var messageInput = document.getElementById('message');
    var message = messageInput.value;
    socket.send(message);
    messageInput.value = '';
}

window.onload = function() {
    connect();
};
</script>
</head>
<body>
<jsp:include page="../../patials/commonBody.jsp"></jsp:include>
    <h1>WebSocket Chat</h1>
    
    <button type="button" class="btn btn-primary" id="chatToastBtn">Show live toast</button>
    <div class="toast-container bottom-0 end-0 p-3">
    	<div class="toast" id="chatToast" role="alert" aria-live="assertive" aria-atomic="true">
		<div class="toast-header">
			<img src="${pageContext.request.contextPath}/resources/img/chat-dots.svg" class="rounded me-2" alt="ChatIcon"> 
			<strong class="me-auto">Bootstrap</strong> 
			<small>11 mins ago</small>
			<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
		</div>
		<div class="toast-body">
		    <div id="chatBox" style="height: 300px; overflow-y: scroll; border: 1px solid black;"></div>
		    <input type="text" id="message" placeholder="Enter your message" />
		    <button onclick="sendMessage()">Send</button>
		</div>
	</div>
    </div>
</body>
<script>
const chatToastTrigger = document.getElementById('chatToastBtn')
const chatToast = document.getElementById('chatToast')

if (chatToastTrigger) {
  const toastBootstrap = bootstrap.Toast.getOrCreateInstance(chatToast)
  chatToastTrigger.addEventListener('click', () => {
	  toastBootstrap.show()
  })
}
</script>
</html>