<!DOCTYPE HTML>
<!-- Allie LaCompte
DB Project W18 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="dbbeans.*"%>
<% session = request.getSession(false);
	String key=(String) session.getAttribute("key");
	if(key !=null){%>
<jsp:useBean id="restbean" class="dbbeans.RestaurantBean" scope="session" />
<html lang="en">
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<%=request.getContextPath()%>/css/styling.css" rel="stylesheet" type="text/css">
		<title>DeleteItem</title>
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
			<h1>
				Delete a Menu Item from<br>
				<jsp:getProperty name="restbean" property="restName" />
			</h1>
			<form action="Control" method="Post">
				<div class="centre">
					<p>
						<br><br><br><br>
						Select menu item to delete:  
						<select required name="item">
							<option value="" disabled selected>Item</option>
							<jsp:getProperty name="restbean" property="menuItemList" />
						</select>
					</p>
				</div>
				<p class="centre">
					<input type="submit" name="delItemBttn" value="Delete" class="bttn"/>
				</p>
			</form>
		</div>
		<div class="footer">
			<img src="<%=request.getContextPath()%>/images/dinner.png"/>    
		</div>
	</body>
</html>
<%}else{
	response.sendRedirect("error.jsp");
	}%>