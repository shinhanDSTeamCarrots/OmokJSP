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
		            	<th>방 번호</th>
		            	<th>생성자</th>
		            	<th>방 이름</th>
		            	<th>방 생성일</th>
	            	</tr>
	            	<!--roomDAO받아와서 방 리스트 넣기-->
	            	
	            
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
			<h2>방 만들기</h2>
			
			<form id="createRoomForm">
	        <label for="roomName">방 이름:</label>
	        <input type="hidden" id="creatorId" name="creatorId">
	        <input type="text" id="roomName" name="roomName" required>
	        <br><br>
	        <input type="submit" value="생성">
	        <input type="button" id="cancelButton" value="취소">
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
		window.addEventListener("keyup", e => {
		    if(isModalOn() && e.key === "Escape") {
		        modalOff()
		    }
		})     
  
    </script>
</body>
</html>