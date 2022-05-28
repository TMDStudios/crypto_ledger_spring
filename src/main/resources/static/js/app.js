let allCoins = [];

function updateCoin(coin) {
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

function fetchData(myCallback, page) {
	allCoins = []; // Keep track of updated coins
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
				try{
					const c = jsonData[i];
				    const coin = [c.name, c.symbol, c.logo_url, c.price, c.rank, c.oneDay.price_change_pct, c.sevenDay.price_change_pct, c.thirtyDay.price_change_pct];
				    allCoins.push(c.symbol);
				    myCallback(coin);
				}catch(e){
					console.log(e);
				}
			}
			handleLeftovers();
    	} else {
      		myCallback("Error: " + req.status);
    	}
  	}
  	req.send();
}

function handleLeftovers() {
  	let req = new XMLHttpRequest();
	req.open('GET', "/api/coins");
  	req.onload = function() {
    	if (req.status == 200) {
			let leftoverCoins = [] // Filter out coins that have not been updated
			const jsonData = JSON.parse(this.responseText);
			for (var i = 0; i < jsonData.length; i++) {
				if(allCoins.indexOf(jsonData[i].symbol)<0){leftoverCoins.push(jsonData[i])}
			}
			// Set leftover coin ranks to 500+
			// Fetch updated prices?
			for (var i = 0; i < leftoverCoins.length; i++){
				console.log("updating: "+leftoverCoins[i].symbol);
				const coin = [
					leftoverCoins[i].name, 
					leftoverCoins[i].symbol, 
					leftoverCoins[i].logo, 
					leftoverCoins[i].price, 
					500+i, 
					leftoverCoins[i].priceChangePercentage1d, 
					leftoverCoins[i].priceChangePercentage7d, 
					leftoverCoins[i].priceChangePercentage30d];
				updateCoin(coin);
			}
			console.log("LEFTOVERS: "+leftoverCoins);
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

function watchCoin(coinId){
	let xhttp = new XMLHttpRequest();
  	xhttp.open("GET", "/api/coins/watch/"+coinId);
	xhttp.onload = ( function() {
			if(this.responseText==="false"){
				alert("Please log in to use this feature");
			}else{
				if(document.getElementById("starImg"+coinId).src == "https://tmdstudios.files.wordpress.com/2022/03/goldstaroutline-1.png"){
					document.getElementById("starImg"+coinId).src = "https://tmdstudios.files.wordpress.com/2022/03/goldstar.png";
				}else{
					document.getElementById("starImg"+coinId).src = "https://tmdstudios.files.wordpress.com/2022/03/goldstaroutline-1.png";
				}
			}
		});
	xhttp.send();
}

fetchData(updateCoin, 1);