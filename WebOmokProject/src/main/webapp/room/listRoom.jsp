<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>방 대기 화면</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/listRoomCss.css">
</head>
<body>
    <h1>게임 대기 방</h1>
    
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
        <h2>사용자 랭크</h2>
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

    <!-- JavaScript 코드 -->
    <script>
        // 방을 목록에 추가하는 함수
        function addRoomToList(creatorID, roomName) {
            var roomList = document.getElementById("room_wrap");
            var div = document.createElement("div");
            div.textContent = "방 생성자: " + creatorID + ", 방 이름: " + roomName;
            roomList.appendChild(div);
        }

        // 방 생성 버튼 클릭 이벤트 핸들러
        document.getElementById("createRoomButton").addEventListener("click", function() {
            // 팝업 창을 엽니다
            var popupWindow = window.open("makeRoom.jsp", "MakeRoomPopup", "width=400, height=300");

            // 팝업 창이 열린 후 작업을 수행
            popupWindow.onload = function() {
                // 팝업 창이 로드되면 부모 페이지의 데이터를 팝업 창에 전달(생성자 ID 등)
                popupWindow.postMessage("부모페이지에서 전달한 데이터", window.location.origin);
            };
        });
    </script>
</body>
</html>