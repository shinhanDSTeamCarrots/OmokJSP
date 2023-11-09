<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Rank.RankDAO"%>
<%@ page import= "Member.MemberVO" %>
<%
	MemberVO myvo=(MemberVO)request.getSession().getAttribute("myvo");
	String member_id= myvo.getMember_id();
%>
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
	            	</tr>
	            	
	            	
	            	<!--roomDAO받아와서 방 리스트 넣기-->
	            	<!-- 방이름 클릭시 게임페이지로 이동 -->
					<c:forEach var="room" items="${listRoom }">
					<tr align="center">
						<td>${room.OWNER_ID }</td>
						<td>${room.JOINED_NO }</td>
						<td><a href="omokPlay.jsp">${room.ROOM_NM }</td>
						<td>${room.CREATED_DATE }</a></td>							
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
		                <th>승률</th>
		            </tr>
		            <!-- RankDAO로 값 넣어주기 -->
		            
		            <c:forEach items="${userRank}" var="userRank">
		                <tr>
		                    <td>${userRank.memberId}</td>
		                    <td>${userRank.winRate}</td>
		                </tr>
		            </c:forEach>
		        </table>
	        </div>
	    </div>
    </div>
    우
	<!-- 방생성 폼 -->
	<div id="modal" class="modal-overlay">
		<div class="modal-window">			
			
			<form id="createRoomForm">
	        <h2>방 만들기</h2>
	        
	        <table align="center">
	        <!-- 생성자 아이디 받아와서 넣어주기 -->
			<tr>
				<td width="200"><p align="right">생성자</p></td>
				<td width="400"><input type="hidden" name="OWNER_ID" id ="ownerIdField" value="<%= member_id %>"></td>
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
					<input type="submit" id ="createButton" value="생성">
					<input type="button" id="cancelButton" value="취소">
				</td>
			</tr>
			</table>
	  
        </div>
    </form>
		
	
	</div>
    

    <!-- JavaScript 코드 -->
    <script>  
	
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
		//생성버튼 클릭시 그냥 게임화면으로 넘어가기
		const createBtn = modal.querySelector("#createButton")
		createBtn.addEventListener("submit",e =>{
			window.location.href = "omokPlay.jsp";
		})
		
		
    </script>
</body>
</html>