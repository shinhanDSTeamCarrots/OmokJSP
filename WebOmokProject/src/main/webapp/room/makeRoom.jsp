<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>방 생성</title>
</head>
<body>
    <h1>방 생성</h1>
    
    <!-- 방 생성 폼 -->
    <form id="createRoomForm">
        <label for="roomName">방 이름:</label>
        <input type="text" id="roomName" name="roomName" required>
        <br><br>
        <input type="submit" value="방 생성하기">
    </form>

    <!-- JavaScript 코드 -->
    <script>
        // 팝업 페이지 초기화
        window.addEventListener("message", function(event) {
            if (event.origin === window.location.origin) {
                var creatorID = event.data;
                document.getElementById("creatorID").textContent = creatorID;
            }
        });

        // 방 생성 폼 제출 이벤트 핸들러
        document.getElementById("createRoomForm").addEventListener("submit", function(event) {
            event.preventDefault(); // 폼 제출 기본 동작 방지

            var roomName = document.getElementById("roomName").value;
            var creatorID = document.getElementById("creatorID").textContent;

            // 새로운 방을 생성하고 방 목록에 추가합니다
            window.opener.addRoomToList(creatorID, roomName);

            // 팝업 창 닫기
            window.close();
            
            window.opener.location.reload();
        });
    </script>
</body>
</html>