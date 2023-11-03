<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목 게임</title>
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
        .game-container {
            display: flex;
        }
        .omok-board {
            width: 550px;
            height: 550px;
            border: 2px solid #000;
            background-image: url('/images/Blank_Go_board.png');
            background-color: burlywood;
            background-size: cover;
        }
        .omok-cell {
            border: 1px solid #000;
            background-color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .player-info1 {
            border: 1px solid #000;
            background-color: burlywood;
            margin-top: 270px;
            margin-right: 60px;
            border-radius: 10px;
            box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.7);
            height: 280px;
            width: 200px;
            text-align: center;
        }
        .player-info2 {
            border: 1px solid #000;
            background-color: burlywood;
            margin-left: 60px;
            border-radius: 10px;
            box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.7);
            height: 280px;
            width: 200px;
            text-align: center;
        }
        .score-box {
            border: 1px solid #000;
            background-color: #eee;
            margin: 10px;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div class="game-container">
        <div class="player-info1">
            <h2>플레이어 1</h2>
            <p>흑돌</p>
            <div class="score-box">
                <p>승리: 0</p>
                <p>패배: 0</p>
                <p>승률: 0</p>
            </div>
        </div>
        <div class="omok-board">
            <%@ include file="/Game/omokBoard.jsp"  %>
        </div>
        <div class="player-info2">
            <h2>플레이어 2</h2>
            <p>백돌</p>
            <div class="score-box">
                <p>승리: 0</p>
                <p>패배: 0</p>
                <p>승률: 0</p>
            </div>
        </div>
    </div>
</body>
</html>
