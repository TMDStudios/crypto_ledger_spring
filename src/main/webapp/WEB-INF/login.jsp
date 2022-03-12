<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Crypto Ledger</title>
</head>
<body>

<h1>Crypto Ledger</h1>

<form:form action="/register" method="post" modelAttribute="newUser">

	<table>
		<thead>
	    	<tr class="blue-bg">
	            <td colspan="2">New User</td>
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
	            <td class="label">Email (optional):</td>
	            <td class="float-left">
	            	<form:errors path="email" class="text-danger"/>
					<form:input class="input" path="email" type="email"/>
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
	            <td class="label">Confirm PW:</td>
	            <td class="float-left">
	            	<form:errors path="confirm" class="text-danger"/>
					<form:input class="input" path="confirm" type="password"/>
	            </td>
	        </tr>
	        <tr class="blue-bg">
	        	<td colspan=2><input class="input" class="button" type="submit" value="Register"/></td>
	        </tr>
	    </thead>
	</table>
</form:form>
<hr>
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
</body>
</html>