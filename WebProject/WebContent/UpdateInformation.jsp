<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<%@ page import ="Entities.User" %>
<head>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<%String Subject = (String) session.getAttribute("loggedSubject"); %>
<link rel="stylesheet" href="Input_info.css">
<meta charset="windows-1256">
<title>Update Information</title>
</head>
<body>
<div class="login-container">
    <section class="login" id="login">
        <header>
          <h2>Office Hour Application</h2>
          <h4>Update Information</h4>
        </header>
        <form class="login-form" action="UpdateProfile" method="POST">
            <input class="login-input" type="Text" id="name" name="name" value="<%=currentUser.getName()%>" required ><br>
            <input class="login-input" type="email" id="email" name="email" value="<%=currentUser.getEmail()%>" required><br>
            <input class="login-input" type="Text" id="Department" name="Department" value="<%=currentUser.getDepartment()%>" required><br>
            <input class="login-input" type="Text" id="Password" name="Password" value="<%=currentUser.getPassword()%>" required><br>
            <%if(currentUser.getRole().equalsIgnoreCase("Staff Member")) { %>
            <input class="login-input" type="Text" id="Subject" name="Subject" value="<%=Subject%>" required><br>
            <%} %>
            <br><br>
            <div class="submit-container">
                <input class="login-button" type="submit" value ="Save Information">
            </div>
            </form>
</div>
        <a href="Home.jsp">Back To Home</a>
        </section>
</body>
</html>