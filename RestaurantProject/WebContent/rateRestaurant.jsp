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
		<title>RateRestaurant</title>
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
			<h1>Rate a Restaurant</h1>
			<form action="Control" method="Post">
				<div class="addRe">
					<p>
						<br><br><br><br>
						Restaurant:
						<select required name="restaurant">
							<option value="" disabled selected></option>
							<jsp:getProperty name="raterbean" property="reOptionList"/> 
						</select><br><br>
						Price rating:
						<select required name="price">
							<option value="" disabled selected></option>
							<jsp:getProperty name="raterbean" property="ratingValueList"/> 
						</select><br><br>
						Food rating:
						<select required name="food">
							<option value="" disabled selected></option>
							<jsp:getProperty name="raterbean" property="ratingValueList"/> 
						</select><br><br>
						Mood rating:
						<select required name="mood">
							<option value="" disabled selected></option>
							<jsp:getProperty name="raterbean" property="ratingValueList"/> 
						</select><br><br>
						Staff rating:
						<select required name="staff">
							<option value="" disabled selected></option>
							<jsp:getProperty name="raterbean" property="ratingValueList"/> 
						</select><br><br>
						Comments:<br> 
						<textarea rows="4" cols="50" maxlength="280" name="comments"></textarea>
						<br><br>
					</p>
				</div>
				<p class="centre">
					<input type="submit" name="rateRestBttn" value="Add" class="bttn"/>
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