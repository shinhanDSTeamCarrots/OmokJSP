<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목 게임</title>
<link rel="stylesheet" type="text/css" href="../Game/GameBoardStyle.css">
</head>
<body>
    <div class="game_container">
    	<div class="left_content">
	        <div class="player_info1">
	            <h2>플레이어 1</h2>
	            <p>흑돌</p>
	            <div class="score_box">
	                <p>승리: 0</p>
	                <p>패배: 0</p>
	                <p>승률: 0</p>
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
	                <p>승리: 0</p>
	                <p>패배: 0</p>
	                <p>승률: 0</p>
	            </div>
	        </div>
	        <div class="exitBtn">
	        	<button onclick="">X</button>
	        </div>
        </div>
    </div>
</body>
</html>
