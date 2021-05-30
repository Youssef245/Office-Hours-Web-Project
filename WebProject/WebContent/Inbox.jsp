<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<head>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<link rel="stylesheet" href="Tables.css">
<meta charset="windows-1256">
<title>Inbox</title>
</head>
<body>
	<div class="main-container">
		<section class="login" id="login">
			<header>
				<h1>Office Hour Application</h1>
				<h2>Inbox</h2>
				<%if (currentUser.getMessages().isEmpty()) { %>
				<h2>You Don't Have Messages.</h2>
				<%} else {%>
			</header>
	
			<table>
				<tr>
					<th class=info-header>Message</th>
					<th class=info-header>Sender</th>
				</tr>
			<%for (int i=0;i<currentUser.getMessages().size();i++) { %>
				<tr>
					<td class=info><%out.println(currentUser.getMessage(i).getContent()); %></td>
					<td class=info><%out.println(currentUser.getMessage(i).getSender().getName()); %></td>
					<%if(currentUser.getRole().equalsIgnoreCase("Staff Member")) { %>
					<td ><button class=login-button onclick='SendMessage(<%=currentUser.getMessage(i).getSender().getID()%>,<%=currentUser.getMessage(i).getID()%>)'>
					Reply</button></td>
					</tr>
							<tr>
								<td class=demo><div id="<%=currentUser.getMessage(i).getID()%>"></div><td>
							</tr>
					<%} else {%>
					</tr>
					<%} %>
	
			<%} %>
			</table>
			<%} %>
			<div class=submit-container>
				<a href="Home.jsp" style="text-decoration: none;" class=login-button>Back To Home</a>
			</div>
			
		</section>
	</div>	

<script>
function SendMessage(id,messageid) {
	
	var sendMessage = "<Table>"; 
	sendMessage+="<tr><td><textarea name='content' id='content'  rows='4' cols='50'></textarea></td>";
	sendMessage+="<td><button onclick='Send("+id+")'>Send</button></td>";
	sendMessage+="<td><div id='Success'></div></td></tr>";
	sendMessage+="</Table>";
	document.getElementById(messageid).innerHTML = sendMessage;
	
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