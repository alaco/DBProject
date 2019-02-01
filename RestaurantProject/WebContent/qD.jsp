<!DOCTYPE HTML>
<!-- Created by Linh and modified by Allie LaCompte 
DB Project W18 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="dbbeans.*"%>
<% session = request.getSession(false);
	String key=(String) session.getAttribute("key");
	if(key !=null){%>	
<jsp:useBean id="qdbean"  class="dbbeans.ABDBean" scope="session" />
<html lang="en">
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<%=request.getContextPath()%>/css/styling.css" rel="stylesheet" type="text/css">
		<title>QD</title>
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
				QD. Most expensive menu item at<br>
				<jsp:getProperty name="qdbean" property="resName"/>
			</h2>
			<table  class="table2" border=1>
				<tr>
				    <th>Item</th>
				    <th>Price</th>
					<th>Manager</th>
					<th>Hours</th>
					<th>URL</th>
				</tr>
				<jsp:getProperty name="qdbean" property="qdList" />
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