<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../Game/CSS/omokBoardStyle.css">
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
<!-- 좌표 표기를 위한 틀 생성 -->
<div id="board">
</div>
<script type="text/javascript">
//클릭 시 콘솔 로그에 좌표값 출력
document.getElementById('board').addEventListener('click', (e) => {
	//좌표값 보정
	let x = Math.round(e.offsetX/28);
	let y = Math.round(e.offsetY/28);
	console.log(`X: \${x}px, Y: \${y}px`);
});
</script>
</body>
</html>