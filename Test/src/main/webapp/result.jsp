<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<% request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String[] fruit=request.getParameterValues("fruit");
		String fruits = "";
		
		for(int i = 0; i < fruit.length; i++){
			fruits += fruit[i] + " ";
		}
	%>
	<h1>선호도 조사 결과</h1>
	<hr>
		<table border="1">
			<tr>
				<td>이름</td>
				<td><%=name  %></td>
			</tr>
			<tr>
				<td>좋아하는 과일</td>
				<td>
					<%=fruits %>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="전송"></td>
			</tr>
		</table>
</body>
</html>