<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="Member.MemberVO"%>
<%@ page import="Room.RoomVO"%>
<%@ page import="Rank.RankDAO"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%!List<Map<String, Object>> memberWinRates = new ArrayList<>();%>
<%
	MemberVO myvo = (MemberVO) request.getSession().getAttribute("myvo");
	String member_id = myvo.getMember_id();
	int member_no = (int)request.getSession().getAttribute("memberno");
	RankDAO rankDao = new RankDAO();
	memberWinRates = rankDao.getAllMemberWinRates();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="icon" href="../img/favicon.ico" type="image/x-icon">
<title>방 대기 화면</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/listRoomCss.css">
</head>
<body>
	<h1>게임 대기 방</h1>
	<div id="container">
		<input type="button" id="refreshBtn" class="refreshBtn" src="../img/refresh.png" onclick="getRoomList()" width="10px"/>
		<!-- 방 만들기 버튼 -->
		<button id="createRoomButton">방 만들기</button>

		<!-- 방 목록 -->
		<div class="room-list"
			style="background-color: #fff; padding-top: 20px;">
			<div id="room_wrap" style="height: 3420px;">

				<!-- 여기에 동적으로 방이 추가 -->
				<table id="roomListTable">
					


					<!--roomDAO받아와서 방 리스트 넣기-->
					<!-- 방이름 클릭시 게임페이지로 이동 
					<c:forEach var="room" items="${roomList }">
						<tr align="center">
							<td>${room.OWNER_ID }</td>
							<td><a href="omokPlay.jsp">${room.ROOM_NM }</td>
							<td>${room.CREATED_DATE }</a></td>
							<td>${room.JOINED_NO }</td>
						</tr>
					</c:forEach>
					-->
				</table>
			</div>
		</div>

		<!-- 사용자 랭크 목록 -->
		<div class="rank-list">
			<div id="rank_wrap" style="height: 3420px;">

				<table>
					<tr>
						<th>사용자</th>
						<th>승률</th>
					</tr>
					<!-- RankDAO로 값 넣어주기 -->

					<c:forEach var="memberWinRate" items="${memberWinRates }">
						<tr>
							<td><c:out value="${memberWinRate['MEMBER_ID']}" /></td>
							<td><c:out value="${memberWinRate['WINRATE']}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

	<!-- 방생성 폼 -->
	<div id="modal" class="modal-overlay">
		<div class="modal-window">

			<form id="createRoomForm" method="post">
				<h2>방 만들기</h2>

				<table align="center">
					<!-- 생성자 아이디 받아와서 넣어주기 -->
					<tr>
						<td width="200"><p align="right">생성자</p></td>
						<td width="400"><input type="text" name="OWNER_ID"
							id="ownerIdField" value="<%= member_id %>" readonly /></td>

					</tr>
					<tr>
						<td width="200"><p align="right">방 이름</p></td>
						<td width="400"><input type="text" name="ROOM_NM"
							id="createroomName"  /></td>
					</tr>
					<tr>
						<td width="200"><p align="right">방 비밀번호</p></td>
						<td width="400"><input type="password" name="ROOM_PW"
							id="createroomPassword" /></td>
					</tr>
					<tr>
						<td width="200"><p>&nbsp;</p></td>
						<td width="400"><input type="button" id="createButton"
							value="생성"> <input type="button" id="cancelButton"
							value="취소"></td>
					</tr>
				</table>
		</div>
		</form>


	</div>


	<!-- JavaScript 코드 -->
	<script>
		console.log("member: ${member_id}");
       	const modal = document.getElementById("modal")
       	function getRoomList(){
			$.ajax({
		          url: "${pageContext.request.contextPath}/roomController/roomList.do",
		          type: "POST",
		          async: "false",
		          dataType: "text",
		          data: {
		          },
		          success: function (msg) {
					console.log(msg);
		            console.log("ajax 성공");
					var list = JSON.parse(msg);
					let out ="";
					out += "<tr>"
						+"<th>생성자</th>"
						+"<th>방 이름</th>"
						+"<th>방 생성일</th>"
						+"<th>참여자</th>"
						+"</tr>";
					
					if(msg == ""){
						document.getElementById("roomListTable").innerHTML= out;
						return;
					}
					for(let i in list.items){
						out	+=	"<tr>"
							+	"<td>"+list.items[i].owner_nm+"</td>"
							+	"<td><p onclick='whenRoomTryToJoin("+i+")'>"+list.items[i].room_nm+"</p></td>"
							+	"<td>"+list.items[i].created_date+"</td>"
							+	"<td>"+list.items[i].joined_nm+"</td>"
							+	"</tr>";
					}
					document.getElementById("roomListTable").innerHTML= out;
		          },
				  
			});
		}

		function whenRoomTryToJoin(i){
			//방이 joined 가 있는지 확인
			if(list.items[i].joined_no != -1){
				alert("사용자가 꽉 찬 방입니다.");
				return;
			}
			console.log("방 접속");
			$.ajax({
		          url: "${pageContext.request.contextPath}/roomController/playerJoined.do",
		          type: "POST",
		          async: "false",
		          dataType: "text",
		          data: {
					JOINED_NO: list.items[i].joined_no,
					ROOM_NO : list.items[i].room_no
		          },
		          success: function (msg) {
		            console.log("ajax 성공");
					if(msg == "T"){
		                window.self.location = "${pageContext.request.contextPath}/Game/omokPlay.jsp";
					}else{
						alert("방에 들어갈 수 없습니다!");
						getRoomList();
					}
		          },
				  
			});

		}
		function modalOn() {
		    modal.style.display = "flex"
		}
		function isModalOn() {
		    return modal.style.display === "flex"
		}
		function modalOff() {
		    modal.style.display = "none"
		}
		const btnModal = document.getElementById("createRoomButton")
		btnModal.addEventListener("click", e => {
		    modalOn()
		})
		const closeBtn = modal.querySelector("#cancelButton")
		closeBtn.addEventListener("click", e => {
		    modalOff()
		})
		modal.addEventListener("click", e => {
		    const evTarget = e.target
		    if(evTarget.classList.contains("modal-overlay")) {
		        modalOff()
		    }
		})
		//생성버튼 클릭시 그냥 게임화면으로 넘어가기
		const createBtn = modal.querySelector("#createButton")
		createBtn.addEventListener("click",e =>{
			console.log("생성버튼 시작!");
			let ownerIdField = document.getElementById("ownerIdField").value.trim();
			let createroomName = document.getElementById("createroomName").value.trim();
			let createroomPassword = document.getElementById("createroomPassword").value.trim();
			if (createroomName == "") {
				alert("방이름을 입력하세요");
				return;
			}
			console.log("ajax 시작!");
			$.ajax({
		          url: "${pageContext.request.contextPath}/roomController/addRoom.do",
		          type: "POST",
		          async: "false",
		          dataType: "text",
		          data: {
		            OWNER_NO: ownerIdField,
		            ROOM_NM: createroomName,
		            ROOM_PW: createroomPassword
		          },
		          success: function (msg) {
		            console.log("ajax 성공");
					console.log("msg"+msg);
		            if (msg == "T") {
		                window.self.location = "${pageContext.request.contextPath}/Game/omokPlay.jsp";
		            } else if(msg == "F") {
		              alert("방 생성에 실패했습니다 ㅠㅠ");
		            }
					else if(msg == "A"){
						alert("이미 방에 접속되어있는 아이디입니다.");
					}
		          }
			});
		});
		$(window).ready(function(){
			getRoomList();
		})
		
    </script>
</body>
</html>