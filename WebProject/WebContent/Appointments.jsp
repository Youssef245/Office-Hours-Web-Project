<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<%@ page import ="Entities.Appointment" %>
<head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<% String Feedback=(String) session.getAttribute("Feedback");%>
<meta charset="windows-1256">
<link rel="stylesheet" href="Tables.css">
<title>Appointments</title>
</head>
<body>
<%if (Feedback.equalsIgnoreCase("Success"))  {%>
<script>Swal.fire({
  icon: 'success',
  title: 'Appointment/s is Deleted Successfully',
  showConfirmButton: false,
  timer: 1500
})
</script>
<%} Feedback=""; 
session.setAttribute("Feedback", Feedback);%>
	<div class="main-container">
		<section class="login" id="login">
			<header>
				  	<h1>Office Hour Application</h1>
				  	<%if (currentUser.getAppointments().isEmpty()) { %>
					<h2> You Don't Have Appointments</h2>
					<%} else {%>
					<h2> Appointments </h2>
			</header>
			<div>

				<table>
					<tr>
						<th class=info><%if(currentUser.getRole().equalsIgnoreCase("Student")) %> Staff Member <%else %> Student</th>
						<th class=info>Day</th>
						<th class=info>From Time</th>
						<th class=info>To Time</th>
						<th class=info>Location</th>
						<th class=info>Date</th>
							
					</tr>
					<% for (int i=0;i<currentUser.getAppointments().size();i++) { %>
					<tr>
							<%if(currentUser.getRole().equalsIgnoreCase("Student")) { %>
							<td class=info><%out.println(currentUser.getAppointment(i).getOtherUser().getRank()+" "+currentUser.getAppointment(i).getOtherUser().getName()); %></td>
							<% } else { %>
							<td class=info><%out.println(currentUser.getAppointment(i).getOtherUser().getName()); %></td>
							<%} %>
							<td class=info><%out.println(currentUser.getAppointment(i).getOfficeHour().getDay()); %></td>
							<td class=info><%out.println(currentUser.getAppointment(i).getOfficeHour().getFromTime().toString()); %></td>
							<td class=info><%out.println(currentUser.getAppointment(i).getOfficeHour().getToTime().toString()); %></td>
							<td class=info><%out.println(currentUser.getAppointment(i).getOfficeHour().getLocation()); %></td>
							<td class=info><%out.println(currentUser.getAppointment(i).getDate().toString()); %></td>
							<td class=login-button> <a href="CrudAppointments?crud=Delete&val=<%=currentUser.getAppointment(i).getAppointmentID()%>">Cancel</a></td>
							<%if(currentUser.getRole().equalsIgnoreCase("staff member")) {%>
					        <td class=login-button> <a href="CancelAllAppointments?val=<%=currentUser.getAppointment(i).getAppointmentID()%>">Cancel All</a></td>
					        <%}%>
					</tr>
					<%} %>
				</table>
				<%} %>
			</div>
			<a href="Home.jsp" style="text-decoration: none;" >Back To Home</a>
		</section>
	</div>
		
		
</body>
</html>