var userEmail = "";

function validateEmail(email){
    var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function resetPassword(){
	userEmail = prompt("Enter your email:");
	if(validateEmail(userEmail)){
		if(confirm("Reset Password?")==true){
			let xhttp = new XMLHttpRequest();
		  	xhttp.open("POST", "/api/reset-request");
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.onload = function(){
					if(this.responseText.startsWith("Invalid")){
						
					}
					console.log(this.responseText)
					alert("Password request email sent.\nPlease check your email.");
					//confirmReset(this.responseText.split("token=")[1])
				}
			xhttp.send("email="+userEmail);
		}else{
			alert("Cancelled");
		}
	}else{
		alert("Please enter a valid email")
	}
}

function confirmReset(token, newPassword, confirmPassword){
	if(validatePassword(newPassword, confirmPassword)=="OK"){
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("PUT", "/api/reset-password?token="+token);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.onload = function(){
				alert(this.responseText)
				//window.open("/reset-password/"+token, "_self");
			}
		xhttp.send("email="+userEmail+"&password="+newPassword);
	}else{
		alert(validatePassword(newPassword, confirmPassword))
	}
}

function validatePassword(pw, newPw){
	if(pw!=newPw){
		return "Passwords must match"
	}else if(pw.length<8){
		return "Password must be 8 or more characters"
	}else{
		return "OK"
	}
}