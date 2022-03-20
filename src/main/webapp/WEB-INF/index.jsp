<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/light.css">
<title>Crypto Ledger</title>
</head>
<body>

<h1>Crypto Ledger</h1>

<c:if test="${user!=null}">
	<p>Hello ${user.username}</p>
	<h4><a href="/logout">Log Out</a></h4>
</c:if>
<c:if test="${user==null}">
	<h4><a href="/login">Log In</a></h4>
	<h4><a href="/register">Register</a></h4>
</c:if>
<hr>
<h4><a href="/prices">View Prices</a></h4>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>