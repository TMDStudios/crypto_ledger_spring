function validate(){
	let xhttp = new XMLHttpRequest();
  	xhttp.open("POST", "/api/buy");
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	let purchasePrice = document.getElementById("purchasePrice").value;
	console.log(document.getElementById("purchasePrice").value);
	if(purchasePrice===null||purchasePrice<0){purchasePrice=0;}
	console.log("PURCHASE PRICE - "+purchasePrice);
	xhttp.send("name="+document.getElementById("name").getAttribute("value")+
		"&symbol="+document.getElementById("symbol").getAttribute("value")+
		"&amount="+document.getElementById("amount").value+
		"&purchasePrice="+purchasePrice+
		"&coinRef="+document.getElementById("coinId").value);
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