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
<title>${coin.symbol} - $${coin.price}</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/home">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="/prices">View Prices</a></li>
    <c:if test="${userId!=null}">
    	<li class="nav_login">
	    	<c:if test="${darkMode}">
				<a class="nav_link" href="/mode">Light</a>
			</c:if>
			<c:if test="${!darkMode}">
				<a class="nav_link" href="/mode">Dark</a>
			</c:if>
	    </li>
		<li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
	</c:if>
	<c:if test="${userId==null}">
		<li class="nav_item"><a class="nav_link" href="/login">Log In</a></li>
		<li class="nav_login"><a class="nav_link" href="/register">Register</a></li>
	</c:if>    
</ul>

<h1>${coin.name}</h1>

<div align='center'>
    <div class="nomics-ticker-widget" data-name="${coin.name}" data-base="${coin.symbol}" data-quote="USD"></div>
    <script src="https://widget.nomics.com/embed.js"></script>
</div>
<br>
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

<c:if test="${userId!=null}">
<hr>
<form action="" method="post" id="form">
	<span>
	<input type="hidden" id="name" name="name" value="${coin.name}">
	<input type="hidden" id="symbol" name="symbol" value="${coin.symbol}">
	<input type="number" step="0.00000001" min="0" id="amount" name="amount" placeholder="Amount"/>
	<input type="hidden" id="purchasePrice" name="purchasePrice" value="${coin.price}">
	<input type="hidden" id="coinId" name="coinId" value="${coin.id}">
	<input class="button" type="submit" value="Buy"/>
	</span>
</form>	
</c:if>

<script type="text/javascript" src="../js/buy.js"></script>

</body>
</html>