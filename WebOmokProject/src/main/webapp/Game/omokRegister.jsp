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
    <script>
    function check(){
    	id = document.getElementById('id');
    	pwd = document.getElementById('pwd');
    	checkPwd = document.getElementById('checkPwd');
    	
    	email = document.getElementById("email");
    	if(id.value.trim() == "" ){
    		alert("아이디를 입력해 주세요.");
    	} else if(pwd.value.trim()==""){
    		alert("비밀번호를 입력해 주세요.");
    	} else if(pwd.value!=checkPwd.value){
    		alert("비밀번호가 일치하지 않습니다.");
    	}  else if(email.value.trim() ==""){
    		alert("이메일을 입력해 주세요.");
    	}
    } 
    function idCheck(){
    	var _id = $("#id");
    	$.ajax({
    		type : "post",
    		async : true,
    		url : "${contextPath}",
    		dataType : "text",
    		data : {id : _id},
    		/*success: function(data, textStatus){
    			
    		}*/
    		
    		
    	})
    }
  	//아이디 중복확인
    </script>
</head>
<body>
<div id = "wrap">
        <div class = "b1">
           <h1>회원가입</h1>
          <input type = "text" name = "id" id= "id" placeholder="아이디">
          <input type = "button" id = "btn_duplicate" value = "중복확인" onClick = "idCheck()" /><br>
          <input type = "password" name = "pwd" id = "pwd" placeholder="비밀번호"><br>
          <input type ="password" name = "checkPwd" id = "checkPwd" placeholder="비밀번호 확인"><br>
          <input type = "text" name= "name" id = "name" placeholder="이름"><br>
          <input type="text" name = "nicknm" id ="nicknm" placeholder="닉네임"><br>
          <input type = "email" name = "email" id="email" placeholder="이메일"><br>
          <input type = "submit" value = "가입하기" onclick=check()>             
        </div>
    </div>
</body>
</html>
