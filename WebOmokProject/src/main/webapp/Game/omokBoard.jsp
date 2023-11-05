<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목 플레이 보드</title>
<style>
.flex-container {
	display: grid;
	grid-template-columns: repeat(19, 28px);
	grid-template-rows: repeat(19, 28px);
    margin : 9px;
	border: solid 1px black;
}
.flex-item{
	border: solid 1px black;
}
.dot{
	width:5px;
	height:5px;
	background-color: black;
	border-radius: 50%;
	position: absolute;
}
</style>
</head>
<body>
<div class="flex-container">
	<%
	for(int x=0; x<19; x++){
		for(int y=0; y<19; y++){
			out.println("<div class='flex-item'></div>");
			//out.println("<div class='flex-item'>"+(x+","+y)+"</div>");
		}
	}
	%>
</div>
</body>
</body>
</html>