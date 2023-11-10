<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="../img/favicon.ico" type="image/x-icon">
<title>회원 가입</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="../css/omokRegisterStyle.css">
<script type="text/javascript">
	
	function check() {
		let id = document.getElementById('id');
		let pwd = document.getElementById('pwd');
		let checkPwd = document.getElementById('checkPwd');
		let name = document.getElementById('name');
		let email = document.getElementById("email");
		let nicknm = document.getElementById("nicknm");
		if (id.value.trim() == "") {
			alert("아이디를 입력해 주세요.");
			return;
		} else if (id.length < 6 || id.length > 20) {
			alert("아이디는 6-20자 사이로 입력해주세요.");
			return; 
		} else if (pwd.value.trim() == "") {
			alert("비밀번호를 입력해 주세요.");
			return;
		} else if (pwd.value != checkPwd.value) {
			alert("비밀번호가 일치하지 않습니다.");
			return; 
		} else if (pwd.length < 6 || pwd.length > 20)) {
			alert("비밀번호는 6-20자 사이로 입력해주세요.");
			return;
		} else if (email.value.trim() == "") {
			alert("이메일을 입력해 주세요.");
			return;
		} else if (name.value.trim() == "") {
			alert("이름을 입력해 주세요.");
			return;
		}
		console.log("회원가입 통신 시작");
	
		$.ajax({
			type : "post",
			async : "false",
			url : "${contextPath}/member/joinMember.do",
			dataType : "text",
			data : {
				"signId" : id.value.trim(),
				"signPw" : pwd.value.trim(),
				"signName" : name.value.trim(),
				"signEmail" : email.value.trim(),
				"nicknm" : nicknm.value.trim()
			},
			success : function(msg) {
				console.log("회원가입 성공");
				//회원가입 완료
				alert("회원가입이 성공하였습니다.");
              	window.self.location = "${contextPath}/Main/omokStart.jsp";
			}
		});
	}
	function idCheck() {
		let id = $("#id").val();
		if (id.trim() == "") {
			alert("ID를 입력하세요");
			return;
		}
		console.log("중복확인 체크");
		$.ajax({
			type : "post",
			async : false,
			url : "${contextPath}/member/duplicateMember.do",
			dataType : "text",
			data : {
				"id" : id.trim()
			},
			success : function(msg) {
				console.log("중복확인 체크 성공");
				if (msg == "T") {
					alert("사용할 수 있는 아이디입니다.");
				} else {
					alert("사용할 수 없는 아이디입니다.");
				}
			}
		});
	}	
</script>
</head>
<body>
	<div class="container">
		<div class="content">
			<form method="post">
				<h2>회원가입</h2>
				<div class="input-group">
					<label for="id">아이디 <input type="text" name="signId"
						id="id" placeholder="아이디"> <input type="button"
						id="btn_duplicate" value="중복확인" onClick="idCheck()" />						
					</label> <label for="pwd">비밀번호 <input type="password" name="signPw"
						id="pwd" placeholder="비밀번호">
					</label> <label for="checkPwd">비밀번호 확인 <input type="password"
						name="checkPwd" id="checkPwd" placeholder="비밀번호 확인">
					</label> <label for="name">이름 <input type="text" name="signName"
						id="name" placeholder="이름">
					</label> <label for="nicknm">닉네임 <input type="text" name="nicknm"
						id="nicknm" placeholder="닉네임">
					</label> <label for="email">이메일 <input type="email"
						name="signEmail" id="email" placeholder="이메일">
					</label> <input type="button" value="가입하기" onClick="check()">
				</div>
			</form>
			<div class="bearImg">
				<img src="../img/bear.png">
			</div>
		</div>
	</div>
</body>
</html>