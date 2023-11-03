<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script>
	var ip = "${pageContext.request.serverName}";
	var port = "${pageContext.request.serverPort}";
	var path = "${pageContext.request.contextPath}";
	var addr = "ws://"+ip+":"+port+"/"+path+"/"+"ChatingServer";
	var webSocket = new WebSocket(addr);
	//var chatWindow, chatMessage, chatId;
	var roomId, opponentId, myId;
	// 채팅창이 열리면 대화창, 메시지 입력창, 아이디 표시란으로 사용할 DOM 객체 저장
	// 윈도우가 로드되면 실행할 익명 함수
	window.onload = function() {
		/*
		chatWindow = document.getElementById("chatWindow");
		chatMessage = document.getElementById("chatMessage");
		chatId = document.getElementById("chatId").value;
		*/
	};
	/*
	// 메시지 전송
	function sendMessage() {
		// 대화창에 표시 
		chatWindow.innerHTML += "<div class='myMsg'>" + chatMessage.value
				+ "</div>";
		//webSocket.send(chatId + '|' + chatMessage.value); // 서버로 전송
		chatMessage.value = ""; //메시지 입력창 내용 지우기
		chatWindow.scrollTop = chatWindow.scrollHeight; // 대화창 스크롤
	}
	*/
	function omokPlay(stone, rowpos, colpos){
		/*
		var msg = "O"+":"+stone+"," +rowpos+","+colpos;
		webSocket.send("{"+roomId+"|"+myId+"|"+msg+"}");
		//{1|2|O:B,2,3}

		*/
		var msg  = new Object();
		msg.stone = stone;
		msg.row = rowpos;
		msg.col = colpos;
		var msgJson = new Object();
		msgJson.roomId = roomId;
		msgJson.senderId = myId;
		msgJson.type = "O";
		msgJson.content = msg;
		webSocket.send(msgJson);
		
	}
	function disconnect() { // 함수명 수정
		webSocket.close();
	}
	/*

	// 엔터 키 입력 처리
	function enterKey() {
		if (window.event.keyCode == 13) { // 13 = Enter 키의 코드값
			sendMessage();
		}
	}
	*/

	// 웹소켓 서버에 연결되었을 때 실행
	webSocket.onopen = function(event) {
		//chatWindow.innerHTML += "웹소켓 서버에 연결되었습니다.<br>";
	};

	// 웹소켓이 닫혔을 때 실행
	webSocket.onclose = function(event) {
		//chatWindow.innerHTML += "웹소켓 서버가 종료되었습니다.<br>";
	}

	webSocket.onerror = function(event) {
		alert(event.data);
		//chatWindow.innerHTML += "채팅 중 에러가 발생하였습니다.<br>";
	}
	
	// 메시지를 받았을 때 실행
	webSocket.onmessage = function(event) {
		/*
		var message = event.data.split("|"); // 대화명과 메시지 분리
		var sender = message[0];
		var content = message[1];
		if (content != "") {
			if (content.match("/")) { // 귓속말
				if (content.match(("/" + chatId))) { // 나에게 보낸 메시지만 출력
					var temp = content.replace(("/" + chatId), "[귓속말]: ");
					chatWindow.innerHTML += "<div>" + sender + "" + temp
							+ "</div>";
				}
			} else { // 일반 대화
				chatWindow.innerHTML += "<div>" + sender + ": " + content
						+ "</div>";
			}
		}
		chatWindow.scrollTop = chatWindow.srollHeight;
		*/

		//json으로 받을 예정
		let jsontext = event.data;
		if(jsontext.roomId != roomId)
			return;
		
		if(jsontext.type == "O"){
			//오목데이터
			//상대가 착수한 오목임
			//해당 값을 java로 송신시킴
			
		}
		else if (jsontext.type == "S"){
			//시스템데이터
			//상대의 서렌, 소켓 타임아웃, 탈주, 승리 데이터 확인
		}
		else if (jsontext.type == "C"){
			//확장형 채팅 데이터
		}
		else if (jsontext.type == "I"){
			//확장 데이터 2
		}

		
	};
</script>
<style>
#chatWindow {
	border: 1px solid black;
	width: 270px;
	height: 310px;
	overflow: scroll;
	padding: 5px;
}

#chatMessage {
	margin-top: 10px;
	width: 236px;
	height: 30px;
}

#sendBtn {
	margin-top: 10px;
	height: 30px;
	position: relative;
	top: 2px;
	left: -2px;
}

#closeBtn {
	margin-bottom: 3px;
	position: relative;
	top: 2px;
	left: -2px;
}

#chatId {
	width: 158px;
	height: 24px;
	border: 1px solid #AAA;
	background-color: #EEE;
}

.myMsg {
	text-align: right;
}
</style>
</head>
<body>
	아이디:
	<input type="text" id="chatId" value="${ param.chatId }" readonly />
	<button id="closeBtn" onclick="disconnect();">채팅 종료</button>
	<div id="chatWindow"></div>
	<div>
		<input type="text" id="chatMessage" onkeyup="enterKey();">
		<button id="sendBtn" onclick="sendMessage();">전송</button>
	</div>
</body>
</html>