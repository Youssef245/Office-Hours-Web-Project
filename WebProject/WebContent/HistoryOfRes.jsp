<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<%@ page import ="Entities.Appointment" %>
<head>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Tables.css">
<title>History of Reservations</title>
</head>
<body>
<div class="main-container">
        <section class="login" id="login">
                <header>
                        <h1>Office Hour Application</h1>
                        <h2>History of Reservations</h2>
                        <%if (currentUser.getHistory().isEmpty()) { %>
                        <h2>You Don't Have Past Reservations.</h2>
                        <%} else {%>
                </header>
        
        
                <table>
                <tr>
                        <th class=info-header ><%if(currentUser.getRole().equalsIgnoreCase("Student")) %> Staff Member <%else %> Student</th>
                        <th class=info-header>Day</th>
 						<th class=info-header>From Time</th>
 						<th class=info-header>To Time</th>
 						<th class=info-header>Location</th>
                        <th class=info-header>Date</th>

                </tr>
                <% for (int i=0;i<currentUser.getHistory().size();i++) { %>
                <tr>
                        <td class=info ><%out.println(currentUser.getHistory().get(i).getOtherUser().getName()); %></td>
                        <td class=info> <%out.println(currentUser.getHistory().get(i).getOfficeHour().getDay()); %></td>
						<td class=info><%out.println(currentUser.getHistory().get(i).getOfficeHour().getFromTime().toString()); %></td>
      					<td class=info><%out.println(currentUser.getHistory().get(i).getOfficeHour().getToTime().toString()); %></td>
      					<td class=info> <%out.println(currentUser.getHistory().get(i).getOfficeHour().getLocation()); %></td>
                        <td class=info> <%out.println(currentUser.getHistory().get(i).getDate().toString()); %></td>
                </tr>
                <%}%>
                </table>
                <%} %>
                <a href="Home.jsp" style="text-decoration: none;">Back To Home</a>
        </section>
</div>

</body>
</html>