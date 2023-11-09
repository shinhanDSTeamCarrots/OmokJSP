<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <link rel="icon" href="../img/favicon.ico" type="image/x-icon">
    <title>로그인</title>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <style>
      #wrap {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
      }

      .b1 {
        width: 350px;
        height: 350px;
        border: 1px solid #000;
        box-sizing: border-box;
        text-align: center;
      }
    </style>
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
        $.ajax({
          url: "${contextPath}/member/loginMember.do",
          type: "POST",
          async: "false",
          dataType: "text",
          data: {
            "loginId": id,
            "loginPw": pwd
          },
          success: function (msg) {
            console.log("ajax 성공");
            if (msg == "T") {
              //FLAGS "방으로"
              window.self.location="/omokRegister.jsp";
            } else {
              alert("로그인에 실패했습니다 ㅠㅠ");
            }
          }
        });
      }

      function whenRegistClicked() {
        window.self.location="/omokRegister.jsp";
      }
    </script>
  </head>

  <body>
    <div id="wrap">
      <div class="b1">
        <h1>오목게임</h1>
        <br>
        <form method="post">
          <input type="text" name="loginId" id="loginId" placeholder="아이디" /><br>
          <input type="password" name="loginPw" id="loginPw" placeholder="비밀번호" /><br>
          <input type="button" value="로그인" onclick="whenLoginClick()" /><br>
          <input type="button" onclick="whenRegistClicked()" value="회원가입"/>
        </form>
      </div>
    </div>
  </body>
  </body>

  </html>