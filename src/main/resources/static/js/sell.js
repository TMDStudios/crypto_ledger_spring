function sell(coinId, coinTotal){
	let amount = prompt("Enter Amount:");
	if (amount > 0) {
			
		if (amount <= coinTotal){
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
		}else {
			alert("The sale amount cannot be larger than your coin total");
		}
		
		
	}else {
	  	alert("Amount must be greater than 0");
	}
}