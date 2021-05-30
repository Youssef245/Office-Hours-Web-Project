<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<html>
<head>
<% ArrayList<String> Subjects = (ArrayList<String>) session.getAttribute("SubjectsList"); %>
<% ArrayList<ArrayList<User>> Names = (ArrayList<ArrayList<User>>) session.getAttribute("NamesList"); %>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<%String Subject = (String) session.getAttribute("loggedSubject"); %>
<meta charset="windows-1256">
<link rel="stylesheet" href="Tables.css">
<title>Subject Staff</title>
</head>
<body>
	<div class="main-container">
		<section class="login" id="login">
			<header>
			  <h1>Office Hour Application</h1>
			  <h2>Staff's Subjects</h2>
			</header>
		
			<div>
				<table>
				<tr>
					<th class=info-header>Name</th>
					<th class=info-header>Email</th>
				</tr>
				<% for (int i=0;i<Subjects.size();i++) { %>
					<%if(Subjects.get(i).equalsIgnoreCase(Subject)) { %>
						<%for (int z=0;z<Names.get(i).size();z++) { %>
							<%if (!Names.get(i).get(z).getEmail().equalsIgnoreCase(currentUser.getEmail())) { %>
								<tr>
									<td class=info><% out.println(Names.get(i).get(z).getName()); %></td>
									<td class=info><% out.println(Names.get(i).get(z).getEmail()); %></td>	
									<td><button onclick="SendMessage(<%=Names.get(i).get(z).getID()%>)" class="login-button">Send Message</button></td>
								</tr>
								<tr>
									<td class=demo ><div id="<%=Names.get(i).get(z).getID()%>sm"></div><td>
								</tr>
							<%} %>
						<%} %>
						</table>
					<%} %> 
				<%} %>
			</div>		
				<a class=login-button href="Home.jsp">Back To Home</a>								
		</section>
		</div>

<script>
function SendMessage(id) {
	
	var sendMessage = "<Table>"; 
	sendMessage+="<tr><td><textarea name='content' id='content'  rows='4' cols='50'></textarea></td>";
	sendMessage+="<td><button onclick='Send("+id+")'>Send</button></td>";
	sendMessage+="<td><div id='Success'></div></td></tr>";
	sendMessage+="</Table>";
	document.getElementById(id+"sm").innerHTML = sendMessage;
	
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