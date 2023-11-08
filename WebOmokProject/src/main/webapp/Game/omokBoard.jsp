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