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
		<title>AddRestaurant</title>
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
			<h1>Add a Restaurant</h1>
			<form action="Control" method="Post">
				<div class="addRe">
					<p>
						<br><br><br><br>
						Name: 
						<input required type="text" size="20" name="reName"> <br><br>
						Type:
						<input required type="text" size="20" name="type"> <br><br>
						URL:
						<input type="text" size="20" name="url"> <br><br>
						Opening date: 
						<select required name="opendM">
							<option value="" disabled selected>Month</option>
							<option value="">Unknown</option>
							<jsp:getProperty name="raterbean" property="monthOptionList" />
						</select>
						<select required name="opendY">
							<option value="" disabled selected>Year</option>
							<option value="">Unknown</option>
							<jsp:getProperty name="raterbean" property="yearOptionList" />
						</select><br><br>
						Manager:
						<input type="text" size="20" name="manager"> <br><br>
						Phone number:
						<input required type="text" size="20" name="phone"> <br><br>
						Address:
						<input required type="text" size="20" name="addrs"> <br><br>
						Hours:
						<select required name="openh">
							<option value="" disabled selected>Opening time</option>
							<jsp:getProperty name="raterbean" property="hourOptionList" />
						</select>
						 to 
						 <select required name="closeh">
							 <option value="" disabled selected>Closing time</option>
							 <jsp:getProperty name="raterbean" property="hourOptionList" />
						</select><br><br><br><br>
					</p>
				</div>
				<p class="centre">
					<input type="submit" name="reAddBttn" value="Add" class="bttn"/>
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