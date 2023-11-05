<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        #wrap{
      display: flex;
      justify-content: center;
      align-items:center;
      min-height: 100vh;
      
    }
    .b1{
        width: 350px;
        height: 350px;
        border : 1px solid #000;
        box-sizing: border-box;
        text-align: center;
    }
    </style>
</head>
<body>
<div id="wrap">
        <div class="b1">
          <h1>오목게임</h1><br>
          <input type = "button" value = "방 만들기"><br>
          <input type = "button" value = "로그인"><br>
            <input type = "button" onclick = "window.open('omokRegister.jsp')" value = "회원가입">
        </div>
      </div>      
    </body>
</body>
</html>