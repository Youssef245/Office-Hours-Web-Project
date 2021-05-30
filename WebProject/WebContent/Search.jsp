<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<head>
<% ArrayList<User> Results = (ArrayList<User>) session.getAttribute("SearchResults"); %>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<%String Subject = (String) session.getAttribute("loggedSubject"); %>
<% String Feedback=(String) session.getAttribute("Feedback");%>
<meta charset="windows-1256">
<link rel="stylesheet" href="Searchh.css">
<title>Search</title>
</head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<body>
<% if (Feedback.equalsIgnoreCase("Fail")) { %>   
<script>Swal.fire({
  icon: 'error',
  title: "Student Not Found.",
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
				<h2>Search for Students</h2>
			</header>
		
			<form action="Search" method="POST">
				<input class="info-input" type="search" name="search" id="search" placeholder="Search by Name" required>
				<input class=login-button type="submit">
			</form>
			<%if (Results.isEmpty()) %> <p class=info>No Results.</p><%else { %>
				<table>
					<tr>
						<th class="info-header">Name</th>
						<th class="info-header">Email</th>
						<th class="info-header">Department</th>
					</tr>
				<% for (int i=0;i<Results.size();i++) { %>
					<tr>
						<td class=info><% out.println(Results.get(i).getName()); %></td>
						<td class=info><% out.println(Results.get(i).getEmail()); %></td>
						<td class=info><% out.println(Results.get(i).getDepartment()); %></td>
						<td><button class=login-button onclick='SendMessage(<%=Results.get(i).getID()%>)'>Message</button></td>
					</tr>
					<tr>
									<td class=demo><div id="<%=Results.get(i).getID()%>"></div><td>
					</tr>
				</table>
				<%} %>
				<%} %>
				<%Results.clear(); %>
				<div class=submit-container>
					<a href="Home.jsp" style="text-decoration: none;" class=login-button >Back To Home</a>
				</div>
				
		</section>
	</div>

<script>
function SendMessage(id) {
	
	var sendMessage = "<Table>"; 
	sendMessage+="<tr><td><textarea name='content' id='content'  rows='4' cols='50'></textarea></td>";
	sendMessage+="<td><button onclick='Send("+id+")'>Send</button></td>";
	sendMessage+="<td><div id='Success'></div></td></tr>";
	sendMessage+="</Table>";
	document.getElementById(id).innerHTML = sendMessage;
	
}

function Send(val) {
	
	  var xhttp = new XMLHttpRequest();
	  var message = document.getElementById("content").value;
	  xhttp.onreadystatechange = function() {
	  if (this.readyState == 4 && this.status == 200) {
		 var htmlInput=this.responseText;	 
		 document.getElementById("Success").innerHTML = htmlInput;
	  }
	  
	  };
	  xhttp.open("POST", "SendMessage", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("val="+val+"&message="+message);

}
</script>
</body>
</html>