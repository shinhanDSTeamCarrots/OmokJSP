<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
	    function placeStone(row, col) {
	        $.ajax({
	            type: "POST",
	            url: "gamelogic",
	            data: {
	                action: "Stone",
	                row: row,
	                col: col,
	                player : player1
	            },
	            dataType: "ajax",
	            success: function(response) {
	                // 돌을 오목판에 배치하는 로직임
	            }
	        });
	    }
    </script>
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
        header {
            text-align: center;
        }
        .game-container {
            display: flex;
        }
        .omok-board {
            width: 400px;
            height: 400px;
            border: 2px solid #000;
            background-image: url('/images/Blank_Go_board.png'); 
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
            margin-right: 40px;
            box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.7);
            height: 320px;
            width: 150px;
            text-align: center;
        }
        .player-info2 {
            background-color: burlywood;    
            border: 1px solid #000;
            box-shadow: 12px 12px 7px rgba(0, 0, 0, 0.7);
            margin-left: 40px;
            height: 320px;
            width: 150px;
            text-align: center;
        }
        .score-box {
            border: 1px solid #000;
            margin: 10px;
            padding: 10px;
            background-color: #eee;
        }
    </style>
</head>
<body>
    <header>
        <h1>오목 게임</h1>
    </header>
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