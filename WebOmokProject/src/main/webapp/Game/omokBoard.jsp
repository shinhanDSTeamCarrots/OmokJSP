<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목 플레이 보드</title>
</head>
<body>
<%
for(int x=0; x<19; x++){
	for(int y=0; y<19; y++){
		out.println(x+","+y);
		out.println(" ");
	}
	out.println("<br>");
}
%>
</body>
</html>