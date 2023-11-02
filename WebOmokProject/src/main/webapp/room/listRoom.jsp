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
    
    <!-- 방 목록 -->
    <div class="room-list">
        <h2>방 목록</h2>
        <ul>
            <c:forEach items="${roomList}" var="room">
                <li>${room.roomName}</li>
            </c:forEach>
        </ul>
    </div>

    <!-- 사용자 랭크 목록 -->
    <div class="rank-list">
        <h2>사용자 랭크</h2>
        <table>
            <tr>
                <th>사용자</th>
                <th>랭크</th>
            </tr>
            <c:forEach items="${userRankList}" var="userRank">
                <tr>
                    <td>${userRank.username}</td>
                    <td>${userRank.rank}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>