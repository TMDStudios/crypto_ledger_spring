function validate(){
	let xhttp = new XMLHttpRequest();
  	xhttp.open("POST", "/api/buy");
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("name="+document.getElementById("name").getAttribute("value")+
		"&symbol="+document.getElementById("symbol").getAttribute("value")+
		"&amount="+document.getElementById("amount").value+
		"&purchasePrice="+document.getElementById("purchasePrice").getAttribute("value")+"");
	if (confirm("Bought "+document.getElementById("amount").value+" " + document.getElementById("symbol").getAttribute("value")+"!")) {
		console.log("Clicked OK");
	  	window.open("/home", "_self");
	} else {
	  	console.log("Clicked Cancel");
	}
	return false;
}
function init(){
    document.getElementById('form').onsubmit = validate;
}
window.onload = init;