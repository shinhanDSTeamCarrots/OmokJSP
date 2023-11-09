<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>오목 게임</title>
	<link rel="stylesheet" type="text/css" href="../Game/CSS/omokPlayStyle.css">
	<style>
	
body {
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f0f0;
}
.game_container {
    display: flex;
}
.left_content {
	width: 260px;
	height: 554px;
	position:relative;
}
.right_content {
	width: 260px;
	height: 554px;
	flex-direction: column;
	position:relative;
}
.omok_board {
    width: 554px;
    height: 554px;
    border: 2px solid #000;
    background-color: burlywood;
    background-size: cover;
    cursor: pointer;
}
.player_info1 {
    border: 1px solid #000;
    background-color: burlywood;
    margin-right: 60px;
    border-radius: 10px;
    box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.3);
    height: 280px;
    width: 200px;
    bottom: 0;
    position:absolute;
    text-align: center;
}
.player_info2 {
    border: 1px solid #000;
    background-color: burlywood;
    margin-left: 60px;
    border-radius: 10px;
    box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.3);
    height: 280px;
    width: 200px;
    text-align: center;
}
.score_box {
    border: 1px solid #000;
    background-color: #eee;
    margin: 10px;
    padding: 10px;
}
.exitBtn {
	margin-left: 60px;
	right:0;
    bottom: 0;
    position:absolute;
}
.exitBtn > button {
	border: 1px solid #000;
	background-color:  burlywood;
	box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.3);
	margin: 5px 5px;
	border-radius: 50%;
	height: 50px;
    width: 50px;
    cursor: pointer;
}
.exitBtn > button:hover {
    background-color: #DEBACE;
}
	</style>
</head>
<body>
    <div class="game_container">
    	<div class="left_content">
	        <div class="player_info1">
	            <h2>플레이어 1</h2>
	            <p>흑돌</p>
	            <div class="score_box">
	                <p id="winCnt">승리: 0</p>
	                <p id="loseCnt">패배: 0</p>
	                <p id="winRate">승률: 0</p>
	            </div>
	        </div>
	    </div>
        <div class="omok_board">
            <%@ include file="/Game/omokBoard.jsp"  %>
        </div>
        <div class="right_content">
	        <div class="player_info2">
	            <h2>플레이어 2</h2>
	            <p>백돌</p>
	            <div class="score_box">
	                <p id="winCnt">승리: 0</p>
	                <p id="loseCnt">패배: 0</p>
	                <p id="winRate">승률: 0</p>
	            </div>
	        </div>
	        <div class="exitBtn">
	        	<button onclick="exit()">X</button>
	        </div>
        </div>
    </div>
    <script src="js/omokPlayEvent.js"></script>
    <script src="js/requSendFunction.js"></script>
    <script src="SocketControll.js"></script>
</body>
</html>
