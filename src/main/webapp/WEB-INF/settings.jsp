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
<title>Settings</title>
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

<h1>Settings</h1>

<form:form action="/settings" method="post">
	<div class="switch-container">
		<p>Dark Mode</p>
		<label class="switch">
	  	<input type="checkbox" name="darkMode" checked="${user.settings.darkMode}">
	  	<span class="slider round"></span>
		</label>
	</div>
	<div class="login-block">
		<div>
			<p>Number of old transactions displayed on home page:</p>
			<input type="number" name="historyLength" value="${user.settings.historyLength}"/>
		</div>
	</div>
	<div class="login-block">
		<div>
			<button class="red-btn" onclick="resetLedger()">Reset Ledger</button>
		</div>
	</div>
	<hr>
	<div class="login-block">
		<div>
			<input class="btn" type="submit" value="Submit"/>
		</div>
	</div>
</form:form>

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

<input type="hidden" id="nomicsApi" name="nomicsApi" value="${nomicsApi}">
<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>