<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/dark.css">
<title>Crypto Ledger</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/">Home</a></li>
    <li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
</ul>

<c:if test="${user!=null}">
	<c:if test="${showWatchlist}">
	<h4 class="center"><a class="link-light" href="/watchlist">All Coins</a></h4>
	</c:if>
	<c:if test="${!showWatchlist}">
		<h4 class="center"><a class="link-light" href="/watchlist">My Watchlist</a></h4>
	</c:if>
	<hr>
</c:if>

<br>

<table>
	<thead>
    	<tr>
            <th></th>
            <th>Rank</th>
            <th colspan=3>Coin</th>
            <th>Price</th>
            <th>24h %</th>
            <th>7d %</th>
            <th>30d %</th>
            <th>Sentiment</th>
        </tr>
    </thead>
	<tbody>
		<c:forEach var="coin" items="${coins}">
        	<tr class="card-item">
	        	<c:if test="${!myCoins.contains(coin)}">
					<td class="narrow"><a href="/coins/watch/${coin.id}"><img src="${starOutline}"/></a></td>
				</c:if>
				<c:if test="${myCoins.contains(coin)}">
					<td class="narrow"><a href="/coins/watch/${coin.id}"><img src="${starFull}"/></a></td>
				</c:if>
	            <c:if test="${coin.coinRank<=100}">
					<td class="narrow">${coin.coinRank}</td>
				</c:if>
				<c:if test="${coin.coinRank>100}">
					<td class="narrow">100+</td>
				</c:if>
	            <td class="narrow"><a href="/coins/${coin.id}"><img class="logo-img" src="${coin.logo}"/></a></td>
	            <td colspan=2 class="float-left"><a class="link-light" href="/coins/${coin.id}">${coin.name} - ${coin.symbol}</a></td>
	            <td>$<fmt:formatNumber pattern="0.000" value="${coin.price}"/></td>
	            <c:if test="${coin.priceChangePercentage1d>=0}">
					<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage1d}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage1d<0}">
					<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage1d}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage7d>=0}">
					<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage7d}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage7d<0}">
					<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage7d}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage30d>=0}">
					<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage30d}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage30d<0}">
					<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage30d}"/>%</td>
				</c:if>
				<td><a class="link-light" href="/coins/${coin.id}">${coin.users.size()}</a></td>
            </tr>
		</c:forEach>
	</tbody>   
</table>

<br>
<div style="margin: auto; text-align: center;">Coin prices provided by <a href="https://nomics.com/" target="_blank">
https://nomics.com/</a></div>
<br>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>