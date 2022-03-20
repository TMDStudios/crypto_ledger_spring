<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/light.css">
<title>${coin.symbol} - $${coin.price}</title>
</head>
<body>

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

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>