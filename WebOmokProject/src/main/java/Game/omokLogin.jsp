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
	<div id = "wrap">
	<div class = "b1" align = "center">
	<h1>로그인</h1>
	<input type = "text" name = "id" id= "id" placeholder="아이디"><br>
	<input type = "password" name = "pw" id= "pw" placeholder="비밀번호"><br>
	<input type = "submit" onclick = login() value = "로그인">
	</div>
	</div>
	//로그인 함수 통해서 데이터베이스랑 어떻게 연동할지?
	//로그인 완성되면 대기실로 보내는 방법 생각해 보기
	
</body>
</html>
