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
		<title>Home</title>
	</head>
	<body>
		<div class="wrapper">
			<p class="logout">
				<a href="closesession.jsp">Log Out</a>
			</p>
			<h1> Choose an Option </h1>
			<table class= "table3">
				<tr class = "trmenu">
					<td class= "tdmenu">
						<p>
							<a href="rateRestaurant.jsp"> Rate a restaurant. </a>
						</p>
						<p>
							<a href="allRaters.jsp"> See all raters. </a>
						</p>
						<p>
							<a href="userRatings.jsp"> See all your ratings. </a>
						</p>
						<p>
							<a href="addRestaurant.jsp"> Add a restaurant. </a>
						</p>
						<form action="Control" method="POST">
							<p>
								Delete:  
								<select required name=restaurant>
									<option value="" disabled selected>Restaurant</option>	
									<jsp:getProperty name="raterbean" property="reOptionList"/> 				
								</select>
								<input type="submit" name="reDelBttn" value="Delete" class="bttn"/>
							</p>
						</form>
						<p>
							<a href="addMenuItem.jsp"> Add a menu item. </a>
						</p>
						<form action="Control" method="POST">
							<p>
								Delete menu item from:   
								<select required name=restaurant>
									<option value="" disabled selected>Restaurant</option>	
									<jsp:getProperty name="raterbean" property="reOptionList"/> 				
								</select>
								<input type="submit" name="miDelBttn" value="Select" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QA: See restaurant and location details for: 
								<select required name=restaurant>
									<option value="" disabled selected>Restaurant</option>	
									<jsp:getProperty name="raterbean" property="reOptionList"/> 				
								</select>
								<input type="submit" name="qABttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QB: Display the full menu for:
								<select required name="restaurant">
									<option value="" disabled selected>Restaurant</option>
									<jsp:getProperty name="raterbean" property="reOptionList" />
								</select>
								<input type="submit" name="qBBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QC: Find the managers' names and opening dates for all
								<select required name=type>
									<option value="" disabled selected>Type</option>	
									<jsp:getProperty name="raterbean" property="typeOptionList"/> 				
								</select>
								restaurants.
								<input type="submit" name="qCBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QD: Display the most expensive menu item at:
								<select required name="restaurant">
									<option value="" disabled selected>Restaurant</option>
									<jsp:getProperty name="raterbean" property="reOptionList" />
								</select>
								<input type="submit" name="qDBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								<a href="qE.jsp"> QE: See average price by menu item category for each type of restaurant. </a>
							</p>
						</form>
						<p>
							<a href="qF.jsp"> QF: See number of ratings per restaurant per user. </a>
						</p>
					</td>
					<td>
						<form action="Control" method="POST">
							<p>
								Delete your account:   
								<input type="submit" name="userDelBttn" value="Delete" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QG: Find restaurants that did not have any ratings in: 
								<select required name="month">
									<option value="" disabled selected>Month</option>
									<jsp:getProperty name="raterbean" property="monthOptionList" />
								</select>
								<select required name="year">
									<option value="" disabled selected>Year</option>
									<jsp:getProperty name="raterbean" property="yearIndexList" />
								</select>
								<input type="submit" name="qGBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QHa: Find restaurants with at least one staff rating less than staff ratings by:
								<select required name="userh">
									<option value="" disabled selected>User</option>
									<jsp:getProperty name="raterbean" property="raterOptionList" />
								</select>
								<input type="submit" name="qHaBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QHb: Find restaurant ratings with a staff rating less than staff ratings by:
								<select required name="userh">
									<option value="" disabled selected>User</option>
									<jsp:getProperty name="raterbean" property="raterOptionList" />
								</select>
								<input type="submit" name="qHbBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QHc: Find restaurant ratings with a staff rating less than any ratings by:
								<select required name="userh">
									<option value="" disabled selected>User</option>
									<jsp:getProperty name="raterbean" property="raterOptionList" />
								</select>
								<input type="submit" name="qHcBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QI: Find the best
								<select required name=type>
									<option value="" disabled selected>Type</option>
									<jsp:getProperty name="raterbean" property="typeOptionList"/> 
								</select>
								restaurants in terms of food ratings.
								<input type="submit" name="qIBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QJa: Find types of restaurants that are more popular than
								<select required name=type>
									<option value="" disabled selected>Type</option>
									<jsp:getProperty name="raterbean" property="typeOptionList"/> 
								</select>
								restaurants in terms of average rating.
								<input type="submit" name="qJaBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<form action="Control" method="POST">
							<p>
								QJb: Find types of restaurants that are more popular than
								<select required name=type>
									<option value="" disabled selected>Type</option>
									<jsp:getProperty name="raterbean" property="typeOptionList"/> 
								</select>
								restaurants in terms of number of ratings.
								<input type="submit" name="qJbBttn" value="Search" class="bttn"/>
							</p>
						</form>
						<p>
							<a href="qK.jsp"> QK: See the top overall ratings in terms of food and mood. </a>
						</p>
						<p>
							<a href="qL.jsp"> QL: See the top overall ratings in terms of food and mood (without rater join date). </a>
						</p>
						<form action="Control" method="POST">
							<p>
								QM: Find raters who gave a lower overall rating than the lowest rating by:
								<select required name=user>
									<option value="" disabled selected>User</option>
									<jsp:getProperty name="raterbean" property="raterOptionList2"/> 
								</select>
								<input type="submit" name="qMBttn" value="Search" class="bttn"/>
							</p>
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div class=footer>
			<img src="<%=request.getContextPath()%>/images/dinner.png"/>    
		</div>
	</body>
</html>
<%}else{
	response.sendRedirect("error.jsp");
	}%>