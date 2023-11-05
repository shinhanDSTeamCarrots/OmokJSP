<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		function chatWinOpen() {
			let playerId = document.getElementById("playerId");
			let roomId = document.getElementById("roomId");
			if (playerId.value == "") {
				alert("아이디를 입력 후 채팅창을 열어주세요.");
				playerId.focus();
				return;
			}
			if (roomId.value == "") {
				alert("방 번호를 입력 후 채팅창을 열어주세요.");
				roomId.focus();
				return;
			}
			
			window.open("Test.jsp?playerId=" + playerId.value+"&roomId="+roomId.value, "");
			playerId.value = "";
		}
	</script>
	<h2>웹소켓 채팅 - 아이디 적용해서 채팅창 띄워주기</h2>
	아이디: <input type="text" id="playerId" />
	채팅방: <input type="text" id="roomId" value="1"/>
	<button onclick="chatWinOpen();">채팅 참여</button>
</body>
</html>