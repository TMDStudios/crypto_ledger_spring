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
<title>Crypto Ledger</title>
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
<div class="index-container">
	<p>LOG IN TO START</p>
	<button onclick="login()">Log In</button>
	<p>Don't have an account?</p>
	<button onclick="register()">Create Account</button>
</div>

<div style="width: 60%; margin: auto; text-align: center">
	<a href="https://play.google.com/store/apps/details?id=com.tmdstudios.cryptoledgerkotlin">
		<img
		  class="banner"
		  style="max-width: 100%"
		  src="https://tmdstudios.files.wordpress.com/2021/11/clbanner-1.png?w=600"
		/>
	</a>
</div>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>