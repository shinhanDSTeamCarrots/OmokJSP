<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>방 대기 화면</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/listRoomCss.css">
</head>
<body>
    <h1>게임 대기 방</h1>
    <div id="container">  
    	<!-- 방 만들기 버튼 -->
	    <button id="createRoomButton">방 만들기</button> 
	     
	    <!-- 방 목록 -->
	    <div class="room-list" style="background-color: #fff; padding-top: 20px;">
	        <div id="room_wrap" style="height: 3420px;">
	            
	            <!-- 여기에 동적으로 방이 추가 -->
	            <table>
	            	<tr>
		            	<th>생성자</th>
		            	<th>방 이름</th>
		            	<th>방 생성일</th>
		            	<th>
	            	</tr>
	            	<!--roomDAO받아와서 방 리스트 넣기-->
	            	<c:if test="${empty listRoom }">
					<tr>
						<td colspan="7">
							<b>생성된 방이 없습니다.</b>
						</td>
					</tr>
					</c:if>
					<c:forEach var="room" items="${listRoom }">
					<tr align="center">
						<td>${room.ROOM_ID }</td>
						<td>${room.OWNER_ID }</td>
						<td>${room.JOINED_NO }</td>
						<td>${room.ROOM_NM }</td>
						<td>${room.CREATED_DATE }</td>
						<td><a href="">입장</a></td>
						<!-- 이거 띄울 필요 없음 -->
					<!-- <% MemberVO memvo = (MemberVO)request.getSession().getAttribute("myvo"); %> -->
					</tr>
					</c:forEach>
					
	            
	            </table>
	        </div>
	    </div>	  
	    
	    <!-- 사용자 랭크 목록 -->
	    <div class="rank-list">
	    	<div id="rank_wrap" style="height: 3420px;">
		        <table>
		            <tr>
		                <th>사용자</th>
		                <th>랭크</th>
		            </tr>
		            <!-- RankDAO로 값 넣어주기 -->
		            <c:forEach items="${userRankList}" var="userRank">
		                <tr>
		                    <td>${userRank.username}</td>
		                    <td>${userRank.rank}</td>
		                </tr>
		            </c:forEach>
		        </table>
	        </div>
	    </div>
    </div>
    
	<!-- 방생성 폼 -->
	<div id="modal" class="modal-overlay">
		<div class="modal-window">
			
			
			<form id="createRoomForm">
	        <h2>방 만들기</h2>
	        
	        <table align="center">
	        <!-- 생성자 아이디 받아와서 넣어주기 -->
			<tr>
				<td width="200"><p align="right">생성자</p></td>
				<td width="400"><input type="" name="OWNER_ID"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">방 이름</p></td>
				<td width="400"><input type="text" name="ROOM_NM"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">방 비밀번호</p></td>
				<td width="400"><input type="password" name="ROOM_PW"></td>
			</tr>
			<tr>
				<td width="200"><p>&nbsp;</p></td>
				<td width="400">
					<input type="submit" value="생성">
					<input type="button" id="cancelButton" value="취소">
				</td>
			</tr>
			</table>
	  
        </div>
    </form>
		
	
	</div>
    

    <!-- JavaScript 코드 -->
    <script>
        // 방을 목록에 추가하는 함수
        function addRoomToList(creatorID, roomName) {
            var roomList = document.getElementById("room_wrap");
            var div = document.createElement("div");
            div.textContent = "방 생성자: " + creatorID + ", 방 이름: " + roomName;
            roomList.appendChild(div);
        }
        
        
        
        const loremIpsum = document.getElementById("lorem-ipsum")
        
        fetch("https://baconipsum.com/api/?type=all-meat&paras=200&format=html")
            .then(response => response.text())
            .then(result => loremIpsum.innerHTML = result)
            
       	const modal = document.getElementById("modal")
       	
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
		//생성버튼 클릭시 그냥 게임화면으로 넘어가게만 하면됨
		window.addEventListener("keyup", e => {
		    if(isModalOn() && e.key === "Escape") {
		        modalOff()
		    }
		})     
  
    </script>
</body>
</html>