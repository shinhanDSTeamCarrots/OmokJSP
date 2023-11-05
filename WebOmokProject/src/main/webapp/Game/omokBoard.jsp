<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목 플레이 보드</title>
<style>
.board_line {
	display: grid;
	grid-template-columns: repeat(18, 28px);
	grid-template-rows: repeat(18, 28px);
	border: solid 1px black;
    margin : 24px;
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
	<%
	for(int x=0; x<18; x++){
		for(int y=0; y<18; y++){
	%>
			<div class='board_dot'></div>
			<%
			if(x%6 == 3 && y%6 == 3){
                int topValue = 102 + (x * 28);
                int leftValue = 468 + (y * 28);
            %>
				<div class="dot" style="top: <%=topValue%>px; left: <%=leftValue%>px;"></div>
	<%
			}
		}
	}
	%>
</div>
</body>
</body>
</html>