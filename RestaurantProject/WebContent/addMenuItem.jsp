<!DOCTYPE HTML>
<!-- Allie LaCompte
DB Project W18 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="dbbeans.*"%>
<% session = request.getSession(false);
	String key=(String) session.getAttribute("key");
	if(key !=null){%>
<jsp:useBean id="raterbean"  class="dbbeans.RaterBean" scope="session" />
<html lang="en">
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<%=request.getContextPath()%>/css/styling.css" rel="stylesheet" type="text/css">
		<title>AddMenuItem</title>
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
			<h1>Add a Menu Item</h1>
			<form action="Control" method="Post">
				<div class="addRe">
					<p>
						<br><br><br><br>
						Restaurant:
						<select required name="restaurant">
							<option value="" disabled selected></option>
							<jsp:getProperty name="raterbean" property="reOptionList"/> 
						</select><br><br>				
						Name of item: 
						<input required type="text" size="20" name="item"> <br><br>
						Type:
						<select required name="type">
							<option value="" disabled selected></option>
							<option value="food">Food</option>
							<option value="beverage">Beverage</option>
						</select><br><br>
						Category:
						<select name="category">
							<option value="" disabled selected></option>
							<option value="starter">Starter</option>
							<option value="main">Main</option>
							<option value="dessert">Dessert</option>
						</select><br><br>
						Description:<br> 
						<textarea rows="4" cols="50" maxlength="280" name="description"></textarea>
						<br><br>
						Price:
						<select required name="dollars">
							<option value="" disabled selected>Dollars</option>
							<jsp:getProperty name="raterbean" property="dollarList" />
						</select>
						<select required name="cents">
							<option value="" disabled selected>Cents</option>
							<jsp:getProperty name="raterbean" property="centList" />
						</select><br><br><br><br>
					</p>
				</div>
				<p class="centre">
					<input type="submit" name="itemAddBttn" value="Add" class="bttn"/>
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