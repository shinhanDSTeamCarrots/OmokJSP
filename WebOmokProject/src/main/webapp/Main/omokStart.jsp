<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="icon" href="../img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="../css/omokStartStyle.css">
<title>로그인</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
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
        console.log("context: "+"${contextPath}");
        $.ajax({
          url: "http://localhost:8090/${contextPath}/member/loginMember.do",
          type: "POST",
          async: "false",
          dataType: "text",
          data: {
            loginId: id,
            loginPw: pwd
          },
          success: function (msg) {
            console.log("ajax 성공");
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
    <div class="form">
			<div id="title"></div>
			<div>
				<form method="post">
					<section class="what">
						<div id="Input">
							<input type="text" name="loginId" id="loginId" placeholder="아이디" /><br>
							<input type="password" name="loginPw" id="loginPw" placeholder="비밀번호" /><br>
						</div>
						<div id="button">
							<input type="button" value="로그인" onclick="whenLoginClick()" /><br>
							<input type="button" onclick="whenRegistClicked()" value="회원가입" />
						</div>
					</section>
				</form>
      </div>
		</div>
	</div> 
	
</body>
</body>

</html>