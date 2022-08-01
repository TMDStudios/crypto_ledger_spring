<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<c:if test="${darkMode}">
	<link rel="stylesheet" type="text/css" href="/css/dark.css">
</c:if>
<c:if test="${!darkMode}">
	<link rel="stylesheet" type="text/css" href="/css/light.css">
</c:if>
<title>API Docs</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/home">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="/prices">View Prices</a></li>
    <c:if test="${userId!=null}">
    	<li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
		<li class="nav_item"><a class="nav_link" href="/settings">Settings</a></li>
		<li class="nav_item"><a class="nav_link" href="/api/docs">API Docs</a></li>
	</c:if>
	<c:if test="${userId==null}">
		<li class="nav_item"><a class="nav_link" href="/login">Log In</a></li>
		<li class="nav_login"><a class="nav_link" href="/register">Register</a></li>
	</c:if>    
</ul>

<h1>Crypto Ledger API</h1>

<div>
	<p>The Crypto Ledger API currently supports four different functions. You can either fetch live prices or use your API key to access your 
	personal ledger. You can also buy or sell coins.</p>
	<p><strong style="color: red;"><u>NOTE:</u></strong> These links are not yet active.</p>
	<p>
		No API key is required to fetch coin prices. Simply use:
    	<br>
    	<mark>https://crypto-ledger.herokuapp.com/api/coins</mark>
	</p>
	<p>You will need an API key to see and/or modify your ledger. Click on the button below to generate your key.</p>
	<p><strong style="color: red;"><u>Important:</u></strong> <b>Treat your API key like you would a password. Do not share it with
    	anyone or display it publicly.</b>
    </p>
	<p>You can fetch your full ledger and buy/sell coins with the following URLs:</p>
	<p>View full ledger:
    	<br>
    	<mark>https://crypto-ledger.herokuapp.com/api/get-user-ledger/YOUR_API_KEY</mark>
    </p>
	<p>Buy coins:
    	<br>
    	<mark>https://crypto-ledger.herokuapp.com/api/buy-coin-api/YOUR_API_KEY</mark>
    </p>
	<p>Sell coins:
    	<br>
    	<mark>https://crypto-ledger.herokuapp.com/api/sell-coin-api/YOUR_API_KEY</mark>
    </p>
</div>

<c:if test="${api_key!=null}">
	<div id="center-content">Your API Key: ${api_key}</div>
</c:if>
<c:if test="${api_key==null}">
	<div>
		<form action="#" method="post">
			<input type="submit" value="Get Token"/>
		</form>
	</div>
</c:if>

<br>
<div class="banner-div">
  <a href="https://tmdstudios.wordpress.com/py-learning-companion/">
    <img
      class="banner"
      style="max-width: 100%"
      src="https://tmdstudios.files.wordpress.com/2020/10/plclogo.png?w=600"
    />
  </a>
</div>

<script type="text/javascript" src="../js/buy.js"></script>

</body>
</html>