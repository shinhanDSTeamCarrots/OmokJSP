<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="../img/favicon.ico" type="image/x-icon">
<title>회원가입</title>




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
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
    function check(){
    	let id = document.getElementById('id');
    	let pwd = document.getElementById('pwd');
    	let checkPwd = document.getElementById('checkPwd');
    	let name = document.getElementById('name');
    	let email = document.getElementById("email");
		let nicknm = document.getElementById("nicknm");
    	if(id.value.trim() == "" ){
    		alert("아이디를 입력해 주세요.");
    		return;
    	} else if(pwd.value.trim()==""){
    		alert("비밀번호를 입력해 주세요.");
    		return;
    	} else if(pwd.value!=checkPwd.value){
    		alert("비밀번호가 일치하지 않습니다.");
    		return;
    	}  else if(email.value.trim() ==""){
    		alert("이메일을 입력해 주세요.");
    		return;
    	} else if(name.value.trim() == ""){
    		alert("이름을 입력해 주세요.");
    		return;
		}
		
    	$.ajax({
    		type : "post",
    		async : "false",
    		url : "${contextPath}/member/joinMember.do",
    		dataType : "text",
    		data: {"signId": id,
					"signPw": pwd,
					"signName":name,
					"signEmail":email,
					"nicknm" : nicknm
					},
    		success : function(msg){
    			//회원가입 완료
				alert("회원가입이 성공하였습니다.");
    			<jsp:forward page="/omokStart.jsp"/>
    		},
    		error : function(msg){
    			alert("에러가 발생했습니다.")
    		},
    		complete: function(msg){
    			alert("작업을 완료했습니다.")
    		}
    	});
    } 
    
    function idCheck(){
    	let id = $("#id").val();
    	if(id.trim()==""){
    		alert ("ID를 입력하세요");
    		return;
    	}
    	$.ajax({
    		type : "post",
    		async : false,
    		url : "${contextPath}/member/duplicateMember.do",
    		dataType : "text",
    		data: {"id": id},
    		success : function(msg){
    			if(msg=="T"){
    				alert("사용할 수 있는 아이디입니다.");
    			} else{
    				alert("사용할 수 없는 아이디입니다.");
    			}
    		},
    		error : function(msg){
    			alert("에러가 발생했습니다.");
    		},
    		complete: function(msg){
    			alert("작업을 완료했습니다.");
    		}
    	});
    }
    </script>
</head>
<body>	
<div id = "wrap">
        <div class = "b1">
        	<form method = "post" action  = "${contextPath}/Member/joinMember.do">
           <h1>회원가입</h1>      
          <input type = "text" name = "signId" id= "id" placeholder="아이디">
          <input type = "button" id = "btn_duplicate" value = "중복확인" onClick = "idCheck()" /><br>
          <input type = "password" name = "signPw" id = "pwd" placeholder="비밀번호"><br>
          <input type ="password" name = "checkPwd" id = "checkPwd" placeholder="비밀번호 확인"><br>
          <input type = "text" name= "signName" id = "name" placeholder="이름"><br>
          <input type="text" name = "nicknm" id ="nicknm" placeholder="닉네임"><br>
          <input type = "email" name = "signEmail" id="email" placeholder="이메일"><br>
          <input type = "submit" value = "가입하기"  onClick = "check()">
          </form>                       
        </div>
    </div>
</body>
</html>
