<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% int memberno = (int)request.getSession().getAttribute("memberno");
		int roomid = (int) request.getSession().getAttribute("roomid");%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>오목 게임</title>
	<link rel="stylesheet" type="text/css" href="../css/omokPlayStyle.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="game_container">
    	<div class="left_content">
	        <div class="player_info1" id = "player_info1">
	            <h2>플레이어 1</h2>
	            <p>흑돌</p>
	            <div class="score_box">
	                <p id="winCnt1">승리: 0</p>
	                <p id="loseCnt1">패배: 0</p>
	                <p id="winRate1">승률: 0</p>
	            </div>
	        </div>
	    </div>
        <div class="omok_board">
            <%@ include file="/Game/omokBoard.jsp"  %>
        </div>
        <div class="right_content">
	        <div class="player_info2" id = "player_info2">
	            <h2>플레이어 2</h2>
	            <p>백돌</p>
	            <div class="score_box">
	                <p id="winCnt2">승리: 0</p>
	                <p id="loseCnt2">패배: 0</p>
	                <p id="winRate2">승률: 0</p>
	            </div>
	        </div>
	        <div class="exitBtn">
	        	<button onclick="exit()">X</button>
	        </div>
        </div>
    </div>
    <script src="js/omokPlayEvent.js"></script>
    <script src="js/SocketControll.js"></script>
    <script src="js/requSendFunction.js"></script>
    <script src="js/socketCallBack.js"></script>
	<script>
		
		//통신 콜백 함수
		function callBack(){
			//제공할 매개변수들 값
			let param = {};
			
			//현재 방 NO
			param.roomId = <%=roomid%>;
			//자기 NO
			param.myId = <%=memberno%>;
			
			var ip = "${pageContext.request.serverName}";
			var port = "${pageContext.request.serverPort}";
			var path = "${pageContext.request.contextPath}";
			var addr = "ws://"+ip+":"+port+path+"/"+"OmokGameSocket/" +"<%=roomid%>" +"/" + "<%=memberno%>" ;
			param.addr = addr;
			/* 
				Def: 오목 콜백 함수. 오목 착수한 위치를 받는다
				Param:   
					Stone: 상대가 착수한 돌. "B", "W" 로 전달
					row: 상대가 착수한 돌의 행 위치
					col: 상대가 착수한 돌 열 위치
			*/
			param.omokCallback = stonInfoCallBack;
			
			/* 
				Def: 시스템 콜백 함수
				Param:   
					type: 상대가 보낸 시스템 메시지 타입
			*/
			param.systemCallback = sysCallback;
			
			//함수 실행
			RoomStart(param);
		}

		//콜백 함수 호출!
		callBack();
		
		/*유저 정보 요청 및 좌표 전달 ajax 제이쿼리 때문에 같이 합침*/


		//유저 정보 요청
		function requUserInfo(){
			console.log("정보 탐색 시작!");
			$.ajax({
				type: "post",
				async: "false", //동기 방식으로 동작
				url:"/WebOmokProject/omok/requUserInfo.do", //전달할 주소
				dataType:"text", //전달할 데이터 타입
				data:{ //전달할 데이터
					playerno : myId,
					oppno: opponentId
				},
				success: function(msg){ //유저id, 승리, 패배, 승률
					console.log("정보 탐색 성공!!");
					//유저 id 값 받아서 리턴
					let jsonInfo = JSON.parse(msg);
					jsonInfo = jsonInfo.arr;
					//0번은 플레이어
					//1번은 상대
					//내 돌이 1(흑돌) 이면 내가 왼쪽
					//아니면 오른쪽
					console.log(jsonInfo);
					let infoname1 = $("#player_info1 > h2");
					let infowin1 = $("#winCnt1")
					let infolose1 = $("#loseCnt1");
					let inforate1 = $("#winRate1");
					let infoname2 = $("#player_info2 > h2");
					let infowin2 = $("#winCnt2");
					let infolose2 = $("#loseCnt2");
					let inforate2 = $("#winRate2");
					if(mystone == 1){
						infoname1.text(""+jsonInfo[0].NAME+"님");
						infowin1.text("승리: "+jsonInfo[0].winCnt);
						infolose1.text("패배: "+jsonInfo[0].loseCnt);
						inforate1.text("승률: "+jsonInfo[0].winRate+"%");
						infoname2.text(""+jsonInfo[1].NAME+"님");
						infowin2.text("승리: "+jsonInfo[1].winCnt);
						infolose2.text("패배: "+jsonInfo[1].loseCnt);
						inforate2.text("승률: "+jsonInfo[1].winRate+"%");
					}else{
						infoname1.text(""+jsonInfo[1].NAME+"님");
						infowin1.text("승리: "+jsonInfo[1].winCnt);
						infolose1.text("패배: "+jsonInfo[1].loseCnt);
						inforate1.text("승률: "+jsonInfo[1].winRate+"%");
						infoname2.text(""+jsonInfo[0].NAME+"님");
						infowin2.text("승리: "+jsonInfo[0].winCnt);
						infolose2.text("패배: "+jsonInfo[0].loseCnt);
						inforate2.text("승률: "+jsonInfo[0].winRate+"%");
					}

				},
				error:function(){
					alert("유저 정보 요청 실패");
				}
			})
		}

		//내가 둔 돌 좌표 전달
		function sendMyInfo(x, y){
			$.ajax({
				type: "post",
				async: "false", 
				url:"/WebOmokProject/omok/sendMyInfo.do",
				dataType:"text",
				data:{
					x_row: x,
					y_col: y,
					stone: mystone,
					player: myId,
					opponent: opponentId
				},
				success: function (success){
					console.log("서버 연산 결과"+ success);
					if(success == "T"){
						stone(x, y, mystone);
						console.log("오목 보내기 전 x, y   "+x+","+y);
						omokPlay(x,y);
						isPlay = false;
					}
					if(success == "W"){
						stone(x, y, mystone);
						isPlay = false;
						alert("승리");
						sendVictoryMessage();
					}
					if(success == "6"){
						alert("6목 이상입니다.\n다시놓아주세요!");
					}
					if(success == "3"){
						alert("쌍삼입니다.\n다시놓아주세요!");
					}
					if(success == "F"){
						alert("놓을 수 없는 자리 입니다\n다시놓아주세요!");
					}
				},
				error:function(){
					alert("Myinfo좌표 전달 실패");
				}
			})
		}

		//상대 둔 돌 좌표 전달 >>>서버 통신 받으면 바로 이미지 출력
		function sendYouInfo(x,y){
			$.ajax({
				type: "post",
				async: "true", 
				url:"/WebOmokProject/omok/sendYouInfo.do",
				dataType:"stone", 
				data:{
					x_row: x,
					y_col: y,
					stone: oppstone
				},
				success: function(){
					isPlay = true;
				}
			})
		}

		//유저 상태 전달 > 패배, 상대 항복
		function sendMemStatus(type){
			$.ajax({
				type: "post",
				async: "true",
				url:"/WebOmokProject/omok/sendMemStatus.do",
				dataType:"text",
				data:{
					type: type
				}
			})
		}
	</script>
</body>
</html>
