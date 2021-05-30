<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<% ArrayList<OfficeHour> OfficeHours = (ArrayList<OfficeHour>) session.getAttribute("officehours"); %>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<% String Feedback=(String) session.getAttribute("Feedback");%>
<meta charset="windows-1256">
<link rel="stylesheet" href="Tables.css">
<title>Manage Office Hours</title>
</head>
<body>
 <%if (Feedback.equalsIgnoreCase("Success"))  {%>
<script>Swal.fire({
  icon: 'success',
  title: 'Office Hour is Updated/Added Successfully.',
  showConfirmButton: false,
  timer: 1500
})
</script>
<%} else if (Feedback.equalsIgnoreCase("Fail")) { %>   
<script>Swal.fire({
  icon: 'error',
  title: "Couldn't Add/Update Office Hour due to Wrong Entry.",
  showConfirmButton: false,
  timer: 1500
})
</script>
<%} Feedback=""; 
session.setAttribute("Feedback", Feedback);%>
	<div>
		<section class="login" id="login">
			<header>
				<h1>Office Hour Application</h1>
				<h2>Office Hours</h2>
				<%if (OfficeHours.isEmpty()) { %>
				<h2>You Don't Have Office Hours Registered.</h2>
				<%} else {%>	
			</header>
			<table>
				<tr>
					<th class=info>WeekDay</th>
					<th class=info>From Time</th>
					<th class=info>To Time</th>
					<th class=info>Location</th>
					<th class=info>Status This Week</th>
				</tr>
			<%for(int i=0;i<OfficeHours.size();i++){ %>
				<tr>
					<td class=info><%out.println(OfficeHours.get(i).getDay()); %></td>
					<td class=info><%out.println(OfficeHours.get(i).getFromTime().toString()); %></td>
					<td class=info><%out.println(OfficeHours.get(i).getToTime().toString()); %></td>
					<td class=info><%out.println(OfficeHours.get(i).getLocation()); %></td>
					<%if (OfficeHours.get(i).getStatus().equalsIgnoreCase("Reserved")) { %>
					<td><button  onclick="ViewReservation(<%=i%>)"><%out.println(OfficeHours.get(i).getStatus()); %></button></td>
					<% } else { %>
					<td class=info><%out.println(OfficeHours.get(i).getStatus()); %></td>
					<%} %>
					<td><a  href="OfficeHourInfo.jsp?crud=Update&index=<%=i%>" style="text-decoration: none;"  class=login-button>Update</a></td>
					<td><a  href="ManageOfficeHour?crud=Delete&index=<%=i%>" style="text-decoration: none;" class=login-button>Delete</a></td>
					<td class=demo><div id="<%=i%>"></div></td>
				</tr>
				</tr>
			<%} %>
			</table>
			<%} %>
			<div class=submit-container>
				<button onclick="window.location.href='OfficeHourInfo.jsp?crud=Add'" class=login-button >Add Office Hour</button>
				<a href="Home.jsp" style="text-decoration: none;" class=login-button>Back To Home</a>
			</div>

		</section>
	</div>
</body>
<script>
function ViewReservation(index) {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	  if (this.readyState == 4 && this.status == 200) {
		var html =this.responseText;
		 document.getElementById(index).innerHTML = html;
	    }
	  };
	  xhttp.open("POST", "GetReservation", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("index="+index);
	}
</script>
</html>