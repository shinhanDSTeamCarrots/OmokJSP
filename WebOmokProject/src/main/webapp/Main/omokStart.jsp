<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="../img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="../css/omokStartStyle.css">
<title>로그인</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	//id와 비밀번호를 입력한 후 Enter키를 눌렀을 때, "Sign In"버튼과 같은 동작 실행
	window.onload = function() {
		document.getElementById("loginPw").addEventListener("keyup", function(event) {
		    if (event.key === "Enter") {
		        whenLoginClick();
		    }
		});
	}

	function whenLoginClick() {
		console.log("로그인 시도");
		let id = document.getElementById("loginId");
		let pwd = document.getElementById("loginPw");
		if (id.value.trim() == "") {
			console.log("아이디 입력 오류");
			alert("id를 입력해 주세요!");
			return;
		} else if (pwd.value.trim() == "") {
			console.log("패스워드 입력 오류");
			alert("패스워드를 입력해 주세요!");
			return;
		}
		console.log("ajax 시작");
		console.log("context: " + "${contextPath}");
		$.ajax({
			url : "${contextPath}/member/loginMember.do",
			type : "POST",
			async : "false",
			dataType : "text",
			data : {
				loginId : id.value.trim(),
				loginPw : pwd.value.trim()
			},
			success : function(msg) {
				console.log("ajax 성공");
				if (msg == "T") {
					//FLAGS "방으로"
					window.self.location = "${contextPath}/room/listRoom.jsp";
				} else {
					alert("로그인에 실패했습니다");
				}
			}
		});
	}

	function whenRegistClicked() {
		window.self.location = "${contextPath}/Main/omokRegister.jsp";
	}
</script>
</head>
<body>
	<div class="container">
		<div class="title">
			<div id="miniTitle">
				<img src="../img/O.png" alt="Image 1" class="image">
				<img src="../img/M.png" alt="Image 2" class="image">
				<img src="../img/O.png" alt="Image 3" class="image">
				<img src="../img/K.png" alt="Image 3" class="image">
			</div>
			<div class="login-container">
				<div class="input-group">
					<label for="loginId" value="Username">Username
						<input type="text" id="loginId" name="loginId" required>
					</label>
				</div>
				<div class="input-group">
					<label for="loginPw">Password
						<input type="password" id="loginPw" name="loginPw" required>
					</label>
				</div>
				<div id="button">
					<button type="submit" class="btn" onclick="whenLoginClick()">Sign In</button>
					<div class="login-footer">
						<p>Don't have an Account? <a href="#" onclick="whenRegistClicked()">SignUp</a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>