<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function test(){
		window.self.location = "${pageContext.request.contextPath}/Game/omokPlay.jsp";
	}
</script>
</head>
<body>
<%	
	int memberno = -1;
	memberno = Integer.parseInt(request. getParameter("memberno"));
	request.getSession().setAttribute("memberno", memberno);
	int roomid = -1;
	roomid = Integer.parseInt(request. getParameter("roomid"));
	request.getSession().setAttribute("roomid", roomid);
%>
	<input type="button" onclick="test()"/>
</body>
</html>