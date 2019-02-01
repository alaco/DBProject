<!DOCTYPE HTML>
<!-- Allie LaCompte
DB Project W18 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<%=request.getContextPath()%>/css/styling.css" rel="stylesheet" type="text/css">
		<title>LoginPage</title>
	</head>
	<body >
		<div class="wrapper">
			<br><br><br>
			<h1> Ottawa Restaurants </h1>
				<form action="Control" method="Post">
					<table class="table1">
						<tr class="trindex">
							<td class="tdindex">
								Returning user:
								<br><br>
								Username: 
								<input required type="text" size="20" name="userName">
								<br><br><br><br><br><br>
							</td>
						</tr>
						<tr class="trindex">
							<td class="tdindex">
								<input type="submit" name="loginBttn" value="Login" class="bttn"/>
							</td>
						</tr>
					</table>
				</form>
				<form action="Control" method="Post">
					<table class="table1">
						<tr class="trindex">
							<td class="tdindex">
							New user:
							<br><br>
							Username: 
							<input required type="text" size="20" name="newName"> <br><br>
							email:
							<input required type="email" size="20" name="email"> <br><br>
							User type: 
							<select required name="userType">
								<option value="" disabled selected>Type</option>
								<option value="blog">Blog</option>
								<option value="online">Online</option>
								<option value="food critic">Food Critic</option>
							</select>
						</td>
						</tr>
						<tr class="trindex">
							<td class="tdindex">
							<input type="submit" name="signupBttn" value="Sign up" class="bttn"/>
							</td>
						</tr>
					</table>
				</form>
		</div>
		<div class="footer">
		<p id = "error">
			<%=request.getAttribute("error") != null ? request.getAttribute("error") : "" %>     
		</p>  
			<img src="<%=request.getContextPath()%>/images/dinner.png"/>    
		</div>
	</body>
</html>