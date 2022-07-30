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
<title>Reset Password</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/home">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="/prices">View Prices</a></li>
    <c:if test="${userId!=null}">
    	<li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
		<li class="nav_login"><a class="nav_link" href="/settings">Settings</a></li>
	</c:if>
	<c:if test="${userId==null}">
		<li class="nav_item"><a class="nav_link" href="/login">Log In</a></li>
		<li class="nav_login"><a class="nav_link" href="/register">Register</a></li>
	</c:if>    
</ul>

<h1>Reset Password</h1>

<c:if test="${message==null}">
    <form action="${token}" method="post">
		<div class="login-block">		
			<div>
				<label>New Password:</label>
			</div>
			<div>
				<input type=password id="pw" name="pw">
			</div>
			<div>
				<label>Confirm New Password:</label>
			</div>
			<div>
				<input type=password id="pwConfirm" name="pwConfirm">
			</div>
			
			<div>
				<input class="btn" type="submit" onclick="confirmReset('${token}', document.getElementById('pw').value, document.getElementById('pwConfirm').value)" value="Reset Password"/>
			</div>
	
		</div>
	</form>
</c:if>
<c:if test="${message!=null}">
    <p style="text-align: center;">${message}</p>
    <a class="nav_link" href="/login">Log In</a>
</c:if>

<script type="text/javascript" src="../js/passwordReset.js"></script>

</body>
</html>