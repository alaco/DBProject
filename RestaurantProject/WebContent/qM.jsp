<!DOCTYPE HTML>
<!-- Allie LaCompte
DB Project W18 -->>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="dbbeans.*"%>
<% session = request.getSession(false);
	String key=(String) session.getAttribute("key");
	if(key !=null){%>	
<jsp:useBean id="qmbean"  class="dbbeans.QMBean" scope="session" />
<html lang="en">
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<%=request.getContextPath()%>/css/styling.css" rel="stylesheet" type="text/css">
		<title>QG</title>
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
				QM. Raters who gave a lower overall<br> 
				rating than the lowest rating(s) by rater(s)<br>
				with the username:
				<jsp:getProperty name="qmbean" property="userName"/>
			</h2>
			<table  class="table2" border=1>
				<tr>
					<th>Rater</th>
					<th>Email</th>
				</tr>
				<jsp:getProperty name="qmbean" property="qmList" />
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