<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<%@ page import ="Entities.User" %>
<head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<%String Subject = (String) session.getAttribute("loggedSubject"); %>
<% String Feedback=(String) session.getAttribute("Feedback");%>
<link rel="stylesheet" href="HomeV2.css">
<meta charset="windows-1256">
<title>Home</title>
</head>
<body>
 <%if (Feedback.equalsIgnoreCase("Success"))  {%>
<script>Swal.fire({
  icon: 'success',
  title: 'Updated Information Successfully.',
  showConfirmButton: false,
  timer: 1500
})
</script>
<%} Feedback="";%>   
    <div class="main-container">
        <section class="login" id="login">
            <header>     
                <h1>Office Hour Application</h1>
            </header>
            <ul>
                <li><a href="UpdateInformation.jsp">Update Information</a></li>
                <li><a href="Appointments.jsp">Appointments</a></li>
                <li><a href="ShowStaff?redirect=true">Show Staff Members</a></li>
                <li><a href="Inbox.jsp">Inbox</a></li>
                <li><a href="Notifications.jsp">Notifications</a></li>
                 <%if (currentUser.getRole().equalsIgnoreCase("Staff Member")) { %>
                <li><a href="ShowStaff?redirect=false">Show Subject Staff</a></li>
                <li><a href="Search.jsp">Search</a></li>
                <li><a href="ManageOfficeHours.jsp">Manage Office Hours</a></li>
                <li></li>
                <li><a href="HistoryOfRes.jsp">History Of Reservations</a></li>
                 <%} %>
                <li><a href="Login.html">Log out</a></li>
            </ul>
            <table class="info-container">
            <%if (currentUser.getRole().equalsIgnoreCase("Staff Member")) { %>
                <h2>Hello Staff Member</h2>
                <%} else { %>
                <h2>Hello Student</h2>
                <%} %>
                <tr>
                	<td class="info">Name</td>
                    <td class="info"><% if(currentUser.getRole().equalsIgnoreCase("Staff Member")){out.println(currentUser.getRank()+" ");}
                    out.println(currentUser.getName()); %></td>
                </tr>
                <tr>
                	<td class="info">Department</td>
                    <td class="info"><% out.println(currentUser.getDepartment()); %></td>
                </tr>
                <tr>
                	<td class="info">Email</td>
                    <td class="info"><% out.println(currentUser.getEmail()); %></td>
                </tr>
                <%if (currentUser.getRole().equalsIgnoreCase("Staff Member")) { %>
                <tr>
                	<td class="info">Subject</td>
               		<td class="info"><% out.println(Subject); %></th>
                </tr>                
                <%} %>
                
            </table>
        </section>
    </div>
</body>
</html>