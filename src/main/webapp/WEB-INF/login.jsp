<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/light.css">
<title>Log In</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/home">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="/prices">View Prices</a></li>
    <li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
</ul>

<h1>Log In</h1>

<form:form action="/login" method="post" modelAttribute="newLogin">

	<table>
		<thead>
	    	<tr class="blue-bg">
	            <td colspan="2">Log In</td>
	        </tr>
	    </thead>
	    <thead>
	        <tr class="blue-bg">
	            <td class="label">Username:</td>
	            <td class="float-left">
	            	<form:errors path="username" class="text-danger"/>
					<form:input class="input" path="username"/>
	            </td>
	        </tr>
	        <tr class="blue-bg">
	            <td class="label">Password:</td>
	            <td class="float-left">
	            	<form:errors path="password" class="text-danger"/>
					<form:input class="input" path="password" type="password"/>
	            </td>
	        </tr>
	        <tr class="blue-bg">
	        	<td colspan=2><input class="input" class="button" type="submit" value="Log In"/></td>
	        </tr>
	    </thead>
	</table>
</form:form>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>