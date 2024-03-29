function validate(){
	let xhttp = new XMLHttpRequest();
	let apiKey = document.getElementById("apiKey").value;
	if(apiKey.length==0){
		alert("Invalid API Key!");
		return false;
	}
  	xhttp.open("POST", "/api/buy/"+apiKey);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	if(document.getElementById("amount").value){
		let purchasePrice = document.getElementById("purchasePrice").value;
		console.log(document.getElementById("purchasePrice").value);
		if(purchasePrice<0){
			alert("Purchase price must be greater than zero!");
			return false;
		}
		if(!purchasePrice){purchasePrice=0;}
		xhttp.send("name="+document.getElementById("name").getAttribute("value")+
			"&symbol="+document.getElementById("symbol").getAttribute("value")+
			"&amount="+document.getElementById("amount").value+
			"&purchasePrice="+purchasePrice);
		alert("Bought "+document.getElementById("amount").value+" " + document.getElementById("symbol").getAttribute("value")+"!")
		window.open("/home", "_self");
		return false;
	}else{
		alert("Amount must be greater than 0");
	}	
}
function init(){
	if(document.getElementById('form')!==null){
		document.getElementById('form').onsubmit = validate;
	}
}
window.onload = init;