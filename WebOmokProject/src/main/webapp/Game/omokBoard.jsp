<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="../Game/CSS/omokBoardStyle.css">
</head>
<body>
	<div class="board_line">
	<c:forEach var="x" begin="0" end="17"> <!-- for(int x=0; x<18; x++)와 동일 -->
		<c:forEach var="y" begin="0" end="17">
			<div class='board_dot'></div>
			<c:choose>
	            <c:when test="${x%6 == 3 && y%6 == 3}">
	                <c:set var="topValue" value="${x * 28}" />
	                <c:set var="leftValue" value="${y * 28}" />
	                <div class="dot" style="top: ${topValue}px; left: ${leftValue}px;"></div>
	            </c:when>
	        </c:choose>
		</c:forEach>
	</c:forEach>
	</div>
	<!-- 좌표 표기를 위한 틀 생성 -->
	<div id="board" onclick="stoneInfo(event)"></div>
	<script type="text/javascript">
	//room_id, member_no 불러오기
	
	
	var stonInfoCallBack = function(stone, row, col){
		var x = row;
		var y = col;
		
		console.log("착수 콜백 함수");
		sendYouInfo();
	};
	var sysCallback = function(type,content){
	    console.log("시스템 콜백 함수");
	    
	    if(type == "TIME"){
	    	alert("상대 접속이 끊겼습니다.");
	    	sendMemStatus(type);
	    	location.href="/WebOmokProject/room/listRoom.jsp"; //대기실로 이동
	    }else if(type == "SURRENDER"){
	    	alert("상대가 항복하였습니다.");
	    	sendMemStatus(type);
	    	location.href="/WebOmokProject/room/listRoom.jsp";
	    }else if(type == "VICTORY"){
	    	alert("상대가 승리하였습니다.");
	    	location.href="/WebOmokProject/room/listRoom.jsp";
	    }else if(type == "JOIN"){
	    	alert("상대가 참가하였습니다.");
	    	requUserInfo(content);
	    }
	};
	
	
	//유저 정보 요청
	function requUserInfo(content){
		$.ajax({
			type: "post",
			async: "false", //동기 방식으로 동작
			url:"/WebOmokProject/omok/requUserInfo.do", //전달할 주소
			dataType:"json", //전달할 데이터 타입
			data:{ //전달할 데이터
				member_id : content
			},
			success: function (){ //유저id, 승리, 패배, 승률
				console.log(data);
				//프론트에 표시
			}
		})
	}
	
	//내가 둔 돌 좌표 전달
	function sendMyInfo(){
		$.ajax({
			type: "post",
			async: "false", 
			url:"/WebOmokProject/omok/sendMyInfo.do",
			dataType:"json",
			data:{
				x_row: x,
				y_col: y,
				stone: stone
			},
			success: function (data){ //기웅이가 줄 값
				console.log(data);
				
				if(data == ""){
					
				}
			}
		})
	}
	
	//상대 둔 돌 좌표 전달 >>>서버 통신 받으면 바로 이미자 출력
	function sendYouInfo(){
		$.ajax({
			type: "post",
			async: "true", 
			url:"/WebOmokProject/omok/sendYouInfo.do",
			dataType:"stone", 
			data:{
				x_row: x,
				y_col: y,
				stone: stone
			}
		})
	}
	
	//유저 상태 전달 > 패배, 상대 항복
	function sendMemStatus(type){
		$.ajax({
			type: "post",
			async: "true",
			url:"/WebOmokProject/omok/sendMemStatus.do",
			dataType:"",
			data:{
				type: type
			}
		})
	}
	
	//통신 콜백 함수
	function callBack(){
	   //제공할 매개변수들 값
	   let param = {};
	   
	   //현재 방 NO
	   param.roomNo = ${roomNo}
	   //자기 NO
	   param.myNo = ${myNo}
	   
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
	
	</script>

</body>
</html>