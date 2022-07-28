var userEmail = "";

function resetPassword(){
	userEmail = prompt("Enter your email:");
	
	if(confirm("Reset Password?")==true){
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("POST", "/api/reset-request");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.onload = function(){
				alert("Password request email sent.\nPlease check your email.");
				//confirmReset(this.responseText.split("token=")[1])
			}
		xhttp.send("email="+userEmail);
	}else{
		alert("Cancelled");
	}
}

function confirmReset(token, newPassword){
	let xhttp = new XMLHttpRequest();
  	xhttp.open("PUT", "/api/reset-password?token="+token);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.onload = function(){
			console.log(this.responseText)
		}
	xhttp.send("email="+userEmail+"&password="+newPassword);
	alert("Done");
}