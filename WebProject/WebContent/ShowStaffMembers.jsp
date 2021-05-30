<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="Entities.User" %>
<%@ page import ="Entities.OfficeHour" %>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<% ArrayList<String> Subjects = (ArrayList<String>) session.getAttribute("SubjectsList"); %>
<% ArrayList<ArrayList<User>> Names = (ArrayList<ArrayList<User>>) session.getAttribute("NamesList"); %>
<% User currentUser = (User) session.getAttribute("SessionUser"); %>
<% String Feedback=(String) session.getAttribute("Feedback");%>
<meta charset="windows-1256">
<link rel="stylesheet" href="ShowStaffMembers.css">
<title>Show Staff Members</title>
</head>
<body>
<%if (Feedback.equalsIgnoreCase("Success"))  {%>
<script>Swal.fire({
  icon: 'success',
  title: 'Appointment is Creatd Successfully',
  showConfirmButton: false,
  timer: 1500
})
</script>
<%} Feedback=""; 
session.setAttribute("Feedback", Feedback);%>
	<div>
		<section class="login" id="login">
			<h1>Subjects and Staff Members</h1>
			<% for (int i=0;i<Subjects.size();i++) { %>
				<button type="button" class="collapsible" ><% out.println(Subjects.get(i)); %></button>
				<div class="content">
				<Table>
						<%for (int z=0;z<Names.get(i).size();z++) { %>
						<%if (!Names.get(i).get(z).getEmail().equalsIgnoreCase(currentUser.getEmail())) { %>
						<tr>
							<td><p class=info><% out.println(Names.get(i).get(z).getName()); %></p></td>
							<td><p class=info><% out.println(Names.get(i).get(z).getEmail()); %></p></td>	
							<td><button class=login-button onclick="getOfficeHours(<%=Names.get(i).get(z).getID()%>)">View Office Hours</button></td>
							<td><button class=login-button onclick="SendMessage(<%=Names.get(i).get(z).getID()%>)">Send Message</button></td>
						</tr>
						<tr>
							<td class=demo><div id="<%=Names.get(i).get(z).getID()%>"></div><td>							
						</tr>
						<tr>
							<td class=demo><div id="<%=Names.get(i).get(z).getID()%>sm"></div><td>
						</tr>
						<%} %>
						<%} %>
				</Table>
				</div>
			<%} %> 
			<div class=submit-container>
				<a href="Home.jsp" style="text-decoration: none;" class=login-button>Back To Home</a>
			</div>
			
		</section>
	</div>

<script>
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
    } else {
      content.style.display = "block";
    }
  });
}

function getOfficeHours(val) {
	
	  var xhttp = new XMLHttpRequest();
	  var role = "<%=currentUser.getRole()%>";
	  xhttp.onreadystatechange = function() {
	  if (this.readyState == 4 && this.status == 200) {
		  
		 var Container = this.responseText.split("%%");		 
		 var htmlInput="<Table>";
		 if(Container=="No Hours to Show Yet.")
		 {
		 	document.getElementById(val).innerHTML = Container;
		 }
		 else 
		 {
			 var hours= Container[0].split("|");
			 console.log(hours);
			 var Locations=Container[1].split("|");
			 for (var i=0;i<hours.length;i++)
			 {
				 htmlInput+="<tr><td class=demo>"+hours[i].substring(0,35)+"</td>";
				 htmlInput+="<td class=demo>"+Locations[i]+"</td>";
				 if(role=="Student")
				 {
					 if(hours[i].substring(36)=="Available")
					 {
					 	htmlInput+="<td class=login-button><a href='CrudAppointments?crud=Create&val="+val+"&num="+i.toString()+"'>Reserve</a></td></tr>";
					 }
					 else
					 {
						htmlInput+="<td class=demo>Reserved</td></tr>";
					 }
				 }
				 		
			 }
			 htmlInput+="</Table>";
			 document.getElementById(val).innerHTML = htmlInput;
		 }
	 }
};
	  xhttp.open("POST", "GetOfficeHours", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("val="+val);

}
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