function updateCoin(coin) {
  	//document.getElementById("demo").innerHTML = coin;
  	let xhttp = new XMLHttpRequest();
  	xhttp.open("POST", "/api/coins");
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("name="+coin[0]+
		"&symbol="+coin[1]+"&logo="+coin[2]+
		"&price="+coin[3]+
		"&coinRank="+coin[4]+
		"&priceChangePercentage1d="+coin[5]+
		"&priceChangePercentage7d="+coin[6]+
		"&priceChangePercentage30d="+coin[7]+"");
}

function getFile(myCallback, page) {
  	let req = new XMLHttpRequest();
	req.open('GET', "https://api.nomics.com/v1/currencies/ticker?key=eb3a7d733a1719cd0a2731a28b6c9bedcb052751&per-page=100&page="+page);
  	req.onload = function() {
    	if (req.status == 200) {
			// rename 1d, 7d, and 30d in order to use dot notation
			const rawData = this.responseText.replace(/1d/g, "oneDay");
			const rawData2 = rawData.replace(/7d/g, "sevenDay");
			const rawData3 = rawData2.replace(/30d/g, "thirtyDay");
			const jsonData = JSON.parse(rawData3);
			for (var i = 0; i < jsonData.length; i++) {
				const c = jsonData[i];
			    const coin = [c.name, c.symbol, c.logo_url, c.price, c.rank, c.oneDay.price_change_pct, c.sevenDay.price_change_pct, c.thirtyDay.price_change_pct];
			    myCallback(coin);
			}
    	} else {
      		myCallback("Error: " + req.status);
    	}
  	}
  	req.send();
}

function login(){
	window.open("/login","_self");
}

function register(){
	window.open("/register","_self");
}

function resetLedger(){
	if(confirm("Are you sure?\nThis cannot be undone!")==true){
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("DELETE", "/api/delete-all");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send();
		alert("Done");
	}else{
		alert("Cancelled");
	}
}

getFile(updateCoin, 1);
setTimeout(() => { getFile(updateCoin, 2); }, 1500);