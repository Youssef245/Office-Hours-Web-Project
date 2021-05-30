<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<%@ page import ="Entities.User" %>
<head>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<%String Subject = (String) session.getAttribute("loggedSubject"); %>
<meta charset="windows-1256">
<link rel="stylesheet" href="Tables.css">
<title>Notifications</title>
</head>
<body>
	<div>
		<section class="login" id="login">
			<header>
				<h1>Office Hour Application</h1>
				<h2>Notification</ph2>
			</header>
				
			
		
		
			<table>
				
				<%if (currentUser.getNotifications().isEmpty()) { %>
				<p class=info>You Don't Have Notifications.<p>
				<%} else {%>
			<tr>
				<th class=info-header>Content</th>
			</tr>
			<%for (int i=0 ; i<currentUser.getNotifications().size();i++) { %>
			<tr class=info>
				<td><%out.println(currentUser.getNotifications().get(i)); %></td>
			</tr>
			<%} %>
			</table>
			<%} %>
			<div class=submit-container>
				<a href="Home.jsp" style="text-decoration: none;" class=login-button>Back To Home</a>
			</div>
		</section>
	</div>

</body>
</html>