<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/light.css">
<title>Crypto Ledger</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/home">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="/prices">View Prices</a></li>
    <li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
</ul>

<h1>Crypto Ledger</h1>

<table>
	<thead>
    	<tr class="blue-bg">
            <th>Coin Name</th>
            <th>Total Amount</th>
            <th>Total Value</th>
            <th>Purchase Price</th>
            <th>Current Price</th>
            <th>Profit</th>
            <th>Trend</th>
            <th>Trade</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="coin" items="${ownedCoins}">
		<tr>
		<c:if test = "${!coin.sold&&!coin.merged}">
			<td scope="col" class="center-cell"><a class="link-light" href="/coins/${coin.id}">${coin.name}</a></td>
			<td scope="col" class="center-cell"><fmt:formatNumber pattern="0.00000000" value="${coin.totalAmount}"/> ${coin.symbol}</td>
			<td scope="col" class="center-cell">$<fmt:formatNumber pattern="0.000" value="${coin.totalValue}"/></td>
			<td scope="col" class="center-cell">$<fmt:formatNumber pattern="0.000" value="${coin.purchasePrice}"/></td>
			<td scope="col" class="center-cell">$<fmt:formatNumber pattern="0.000" value="${coin.currentPrice}"/></td>
			<c:if test="${coin.totalProfit>=0}">
				<td class="green"><img src="${upArrow}"/> $<fmt:formatNumber pattern="0.000" value="${coin.totalProfit}"/></td>
			</c:if>
			<c:if test="${coin.totalProfit<0}">
				<td class="red"><img src="${downArrow}"/> $<fmt:formatNumber pattern="0.000" value="${coin.totalProfit}"/></td>
			</c:if>
			<c:if test="${coin.priceDifference>=0}">
				<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceDifference}"/>%</td>
			</c:if>
			<c:if test="${coin.priceDifference<0}">
				<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceDifference}"/>%</td>
			</c:if>
			<td scope="col" class="center-cell">Buy/Sell</td>
		</c:if>
		</tr>
		</c:forEach>
    </tbody>
</table>

<hr>

<table>
	<thead>
    	<tr class="blue-bg">
            <th>Coin Name</th>
            <th>Total Amount</th>
            <th>Total Value</th>
            <th>Purchase Price</th>
            <th>Current Price</th>
            <th>Profit</th>
            <th>Trend</th>
            <th>Trade</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="coin" items="${ownedCoins}">
		<tr>
		<c:if test = "${coin.sold||coin.merged}">
			<td scope="col" class="center-cell"><a class="link-light" href="/coins/${coin.id}">${coin.name}</a></td>
			<td scope="col" class="center-cell"><fmt:formatNumber pattern="0.00000000" value="${coin.totalAmount}"/> ${coin.symbol}</td>
			<td scope="col" class="center-cell"><fmt:formatNumber pattern="0.000" value="${coin.totalValue}"/></td>
			<td scope="col" class="center-cell"><fmt:formatNumber pattern="0.000" value="${coin.purchasePrice}"/></td>
			<td scope="col" class="center-cell"><fmt:formatNumber pattern="0.000" value="${coin.currentPrice}"/></td>
			<c:if test="${coin.totalProfit>=0}">
				<td class="green"><img src="${upArrow}"/> $<fmt:formatNumber pattern="0.000" value="${coin.totalProfit}"/></td>
			</c:if>
			<c:if test="${coin.totalProfit<0}">
				<td class="red"><img src="${downArrow}"/> $<fmt:formatNumber pattern="0.000" value="${coin.totalProfit}"/></td>
			</c:if>
			<c:if test="${coin.priceDifference>=0}">
				<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceDifference}"/>%</td>
			</c:if>
			<c:if test="${coin.priceDifference<0}">
				<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceDifference}"/>%</td>
			</c:if>
			<td scope="col" class="center-cell">Buy/Sell</td>
		</c:if>
		</tr>
		</c:forEach>
    </tbody>
</table>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>