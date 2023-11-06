<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목 플레이 보드</title>
<style>
.board_line {
	display: grid;
	width: 504px;
    height: 504px;
	grid-template-columns: repeat(18, 1fr);
	grid-template-rows: repeat(18, 1fr);
	border: solid 1px black;
    margin : 25px;
    position: relative; /* 부모 요소를 기준으로 자식 요소를 배치 */
}
.board_dot{
	border: solid 1px black;
}
.dot{
	width:10px;
	height:10px;
	background-color: black;
	border-radius: 50%;
	position: absolute;
	transform: translate(-50%, -50%);
}
</style>
</head>
<body>
<div class="board_line">
<c:forEach var="x" begin="0" end="17"> <!-- for(int x=0; x<18; x++)와 동일 -->
	<c:forEach var="y" begin="0" end="17">
		<div class='board_dot'></div>
		<c:choose>
            <c:when test="${x%6 == 3 && y%6 == 3}">
                <c:set var="topValue" value="${x * 28}" />
                <c:set var="leftValue" value="${y * 28}" />
                <div class="dot" style="top: ${topValue}px; left: ${leftValue}px;"></div>
            </c:when>
        </c:choose>
	</c:forEach>
</c:forEach>
</div>
</body>
</body>
</html>