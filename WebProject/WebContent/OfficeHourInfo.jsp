 <%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
 <%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<html>
<head>
<% ArrayList<OfficeHour> OfficeHours = (ArrayList<OfficeHour>) session.getAttribute("officehours"); %>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<%String crud=request.getParameter("crud"); %>
<link rel="stylesheet" href="Input_info.css">
<meta charset="windows-1256">
<title>Add/Update Office Hour</title>
</head>
<body>
<%if (crud.equalsIgnoreCase("Add")) { %>
    <div class="login-container">
        <section class="login" id="login">
            <header>
                <h2>Office Hour Application</h2>
                <h4>Add Office Hour</h4>
            </header>
        
            <form class="login-form" action="ManageOfficeHour?crud=Add" method="POST">
                <input class="login-input" type="text" id="weekday" name="weekday" placeholder="Week Day" required>
                <input class="login-input" type="text" id="Location" name="Location" placeholder="Location" required>
                <label class=info for="From">From Time : </label>
                <input class="login-input" type="time" id="From" name="From" placeholder="Slot" required>
                <label class=info for="To">To Time : </label>
				<input class="login-input" type="time" id="To" name="To" placeholder="Slot" required>
                <input class="login-button" type="submit" value="Add">
            </form>
            <%} else { %>
            <%int i = Integer.parseInt(request.getParameter("index")); %>
            <p class="login-input">Update Office Hour</p>
            <form class="login-form" action="ManageOfficeHour?crud=Update&index=<%=i%>" method="POST">
                <input class="login-input" type="text" id="weekday" name="weekday" value="<%=OfficeHours.get(i).getDay()%>" required>
                <input class="login-input" type="text" id="Location" name="Location" value="<%=OfficeHours.get(i).getLocation()%>" required>
                <label class=info for="From">From Time : </label>
                <input class="login-input" type="time" id="From" name="From" placeholder="Slot" required>
                <label class=info for="To">To Time : </label>
				<input class="login-input" type="time" id="To" name="To" placeholder="Slot" required>
                <input class="login-button" type="submit" value="Update">
            </form>
            <%} %>
            <a href="ManageOfficeHours.jsp" style="text-decoration: none;" class=login-button>Back</a>
        </section>
    </div>

</body>
</html>