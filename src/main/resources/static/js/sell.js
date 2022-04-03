function sell(coinId){
	let amount = prompt("Amount: ");
	if (amount > 0) {
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("POST", "/api/sell");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("id="+coinId+"&amount="+amount);
		
		if (confirm("Sold "+amount)) {
			console.log("AMT:"+amount+" id: "+coinId);
		  	window.open("/home", "_self");
		} else {
		  	console.log("Clicked Cancel");
		}
	} else {
	  	prompt("Amount must be greater than 0");
	}
}