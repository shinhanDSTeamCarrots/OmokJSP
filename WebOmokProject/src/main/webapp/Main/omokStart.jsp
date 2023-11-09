<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="icon" href="../img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="../css/omokStartStyle.css">
<title>로그인</title>
<script>
	function whenLoginClick() {
		let id = document.getElementById("loginId");
		let pwd = document.getElementById("loginPw");
		if (id.value.trim() == "") {
			alert("id를 입력해 주세요!");
			return;
		} else if (pwd.value.trim() == "") {
			alert("패스워드를 입력해 주세요!");
			return;
		}
		$.ajax({
			type : "post",
			async : "false",
			url : "${contextPath}/member/loginMember.do",
			dataType : "text",
			data : {
				"loginId" : id,
				"loginPw" : pwd
			},
			success : function(msg) {
				if (msg == "T") {
					//FLAGS "방으로"
					window.self.location = "/omokRegister.jsp";
				} else {
					alert("로그인에 실패했습니다 ㅠㅠ");
				}
			}
		});
	}

	function whenRegistClicked() {
		window.self.location = "/omokRegister.jsp";
	}
</script>
</head>

<body>
	<div class="container">
		<div class="b1">
			<h1>오목게임</h1>
			<br>
			<form method="post">
				<input type="text" name="loginId" id="loginId" placeholder="아이디" /><br>
				<input type="password" name="loginPw" id="loginPw"
					placeholder="비밀번호" /><br> <input type="button" value="로그인"
					onclick="whenLoginClick()" /><br> <input type="button"
					onclick="whenRegistClicked()" value="회원가입" />
			</form>
		</div>
	</div>
</body>
</body>

</html>