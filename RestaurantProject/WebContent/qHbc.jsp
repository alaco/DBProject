<!DOCTYPE HTML>
<!-- Allie LaCompte
DB Project W18 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="dbbeans.*"%>
<% session = request.getSession(false);
	String key=(String) session.getAttribute("key");
	if(key !=null){%>	
<jsp:useBean id="qhbean"  class="dbbeans.QHBean" scope="session" />
<html lang="en">
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<%=request.getContextPath()%>/css/styling.css" rel="stylesheet" type="text/css">
		<title>QHbc</title>
	</head>
	<body>
		<div class="wrapper">
			<table class="table3">
				<tr>
					<td class="back">
						<a href="menu.jsp"> Back </a>
					</td>
					<td class="logout">
						<a href="closesession.jsp">Log Out</a>
					</td>
				</tr>
			</table>
			<h2>
				QH<%if(qhbean.getVersion() == 1){%>b<%}else{%>c<%}%>. 
				Restaurant ratings with a staff rating<br>
				less than any <%if(qhbean.getVersion() == 1){%>staff<%}%>
				rating by <jsp:getProperty name="qhbean" property="userName"/>
			</h2>
			<table  class="table2" border=1>
				<tr>
					<th>Date</th>
					<th>Restaurant</th>
					<th>Opening Date(s)</th>
				</tr>
				<jsp:getProperty name="qhbean" property="qhList" />
			</table>
		</div>
		<div class="footer">
			<img src="<%=request.getContextPath()%>/images/dinner.png"/>    
		</div>
	</body>
</html>
<%}else{
	response.sendRedirect("error.jsp");
	}%>