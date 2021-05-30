<!DOCTYPE html>
<html>
<head>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<link rel="stylesheet" href="Input_info.css">
<meta charset="windows-1256">
<title>Sign Up</title>
</head>
<body>
	<div class="login-container">
		<section class="login" id="login">
		  <header>
			<h2>Office Hour Application</h2>
			<h4>SignUp Page</h4>
		  </header>


		<form class="login-form" onsubmit="return false;" method="POST">
			<input class="login-input" type="Text" id="name" name="name" placeholder="Name" required><br>
			<input class="login-input" type="email" id="email" name="email" placeholder="Email" required><br>
			<input class="login-input" type="Text" id="Department" name="Department" placeholder="Department" required><br>

			<input type="radio"  name="Role" id="Student" onclick="remove()">
			<label for="Student"> Student</label>
			<br>
			<input type="radio"  name="Role" id="Staff Member" onclick="add()">
			<label for="StaffMember">Staff Member</label>
			<br><br>
			<div id='SMSubject'></div>
				<div class="g-recaptcha" id="rcaptcha"  data-sitekey="6LersxsaAAAAAP8y9u3n_EhL_z48eNEgagiLHx2R"></div>
			<span id="captcha" style="color:red" /></span>

			<div id="demo"></div>
			<div class="submit-container">
            	<button class="login-button" id="submit" onclick="Validate()">Sign Up</button>
        	</div>
		</form>	
		</section>
	</div>
<script>
function Validate() {
	
	 if(get_action(myForm)&&(document.getElementById("Student").checked||document.getElementById("Staff Member").checked)) {
	  var xhttp = new XMLHttpRequest();
	  
	  var Subject="";
	  var Rank="";
	  var email= document.getElementById("email").value;
	  var Department = document.getElementById("Department").value;
	  var name = document.getElementById("name").value;
	  if(document.getElementById("Student").checked)
	  	var Role = "Student";
	  else if(document.getElementById("Staff Member").checked){
		  	var Role = "Staff Member";
		  	Subject=document.getElementById("Subject").value;
		  	Rank=document.getElementById("Rank").value;}
			  
	  xhttp.onreadystatechange = function() {
	  if (this.readyState == 4 && this.status == 200) {
		  if(this.responseText=="Student"||this.responseText=="Staff Member")
		  { window.location.href = 'Login.html';
			  Swal.fire({
				  icon: 'success',
				  title: 'Signed Up Successfully. Check Your Email For Password.',
				  showConfirmButton: false,
				  timer: 1500
				})
			  window.location.href = 'Login.html';
			  }
		  else
		 document.getElementById("demo").innerHTML = this.responseText
	    }
	  };
	  xhttp.open("POST", "addaccount", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("email="+email+"&Department="+Department+"&name="+name+"&Role="+Role+"&Subject="+Subject+"&Rank="+Rank);
	}
}

function get_action(form) 
{
    var v = grecaptcha.getResponse();
    if(v.length == 0)
    {
        document.getElementById('captcha').innerHTML="You can't leave Captcha Code empty";
        return false;
    }
    else
    {
         document.getElementById('captcha').innerHTML="Captcha completed";
        return true; 
    }
}

var myForm = document.getElementById('my-form');
var num=0;

myForm.addEventListener('submit', function (e) {
 if(!get_action(myForm)) {
      e.preventDefault(); // The browser will not make the HTTP POST request
      return;
 }
});

function add(){
	if(num==0)
	{
    	var new_input="<input type='text' id='Subject' name='Subject' placeholder='Subject' required>"
    	+"<br>"
    	+"<label for='Rank'>Choose Your Academic Rank:</label>"
    	+"<select name='Rank' id='Rank'>"
    	+"<option name='TA' value='TA'>TA</option>"
    	+"<option name='Dr.' value='Dr.'>Dr.</option>"
    	+"</select>";
    	document.getElementById("SMSubject").innerHTML += new_input;
    	num++;
    }
  }
function remove()
{
	var new_input="<input class='login-input' type='text' id='Subject' name='Subject' placeholder='Subject' required>";
	document.getElementById("SMSubject").innerHTML = "";
	num=0;
}
</script>
</form>
</body>
</html>