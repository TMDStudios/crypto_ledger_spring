function deleteCoin(coinId){
	if (confirm("Delete Entry?")) {
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("DELETE", "/api/delete/"+coinId);
		xhttp.send();
		
		xhttp.onload = function() {
		    console.log("Deleted Successfully!");
			window.open("/home", "_self");
		}	
	}
}