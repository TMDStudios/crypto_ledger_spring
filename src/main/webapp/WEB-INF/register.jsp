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
<title>Register</title>
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

<h1>Register</h1>

<form:form action="/register" method="post" modelAttribute="newUser">
	<div class="login-block">
		<div>
			<label>Username:</label>
		</div>
		<div>
			<form:errors path="username" class="text-danger"/>
		</div>
		<div>
			<form:input path="username"/>
		</div>
		
		<div>
			<label>Email (optional):</label>
		</div>
		<div>
			<form:errors path="email" class="text-danger"/>
		</div>
		<div>
			<form:input path="email"/>
		</div>

		<div>
			<label>Password:</label>
		</div>
		<div>
			<form:errors path="password" class="text-danger"/>
		</div>
		<div>
			<form:input path="password" type="password"/>
		</div>
		
		<div>
			<label>Confirm PW:</label>
		</div>
		<div>
			<form:errors path="confirm" class="text-danger"/>
		</div>
		<div>
			<form:input path="confirm" type="password"/>
		</div>

		<div>
			<input class="btn" type="submit" value="Register"/>
		</div>
		
		<div class="banner-div">
			<a href="${link}">
				<img
				  class="banner"
				  style="max-width: 100%"
				  src="${banner}?w=600"
				/>
			</a>
		</div>

	</div>
</form:form>

<input type="hidden" id="nomicsApi" name="nomicsApi" value="${nomicsApi}">
<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>