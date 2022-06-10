<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
		<li class="nav_login"><a class="nav_link" href="/settings">Settings</a></li>
	</c:if>
	<c:if test="${userId==null}">
		<li class="nav_item"><a class="nav_link" href="/login">Log In</a></li>
		<li class="nav_login"><a class="nav_link" href="/register">Register</a></li>
	</c:if>    
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
    	<c:forEach var="coin" items="${activeCoins}">
		<tr>
			<td scope="col" class="center-cell"><a class="link-light" href="/coins/${coin.coinRef}">${coin.name}</a></td>
			<td scope="col" class="center-cell"><fmt:formatNumber pattern="0.00000000" value="${coin.totalAmount}"/> ${coin.symbol}</td>
			<td scope="col" class="center-cell">$<fmt:formatNumber pattern="0.000" value="${coin.totalValue}"/></td>	
			<c:if test="${coin.purchasePrice<0.001}">
				<td scope="col" class="center-cell">&lt; $<fmt:formatNumber pattern="0.000" value="${coin.purchasePrice}"/></td>
			</c:if>
			<c:if test="${coin.purchasePrice>=0.001}">
				<td scope="col" class="center-cell">$<fmt:formatNumber pattern="0.000" value="${coin.purchasePrice}"/></td>
			</c:if>		
			<c:if test="${coin.currentPrice<0.001}">
				<td scope="col" class="center-cell">&lt; $<fmt:formatNumber pattern="0.000" value="${coin.currentPrice}"/></td>
			</c:if>
			<c:if test="${coin.currentPrice>=0.001}">
				<td scope="col" class="center-cell">$<fmt:formatNumber pattern="0.000" value="${coin.currentPrice}"/></td>
			</c:if>		
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
			<td scope="col" class="center-cell">
			<button type="button" class="sell-btn" onclick="sell(document.getElementById('${coin.id}').value, ${coin.totalAmount})" id="${coin.id}" value="${coin.id}">Sell</button>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="8"><a>Portfolio Value: $<fmt:formatNumber pattern="0.000" value="${overallTotal}"/> | Portfolio Profit:</a> 
			<c:if test="${currentProfit>0}">
				<a id="gain">$<fmt:formatNumber pattern="0.00" value="${currentProfit}"/></a>
			</c:if>
			<c:if test="${currentProfit==0}">
				<a>$<fmt:formatNumber pattern="0.00" value="${currentProfit}"/></a>
			</c:if>
			<c:if test="${currentProfit<0}">
				<a id="loss">$<fmt:formatNumber pattern="0.00" value="${currentProfit}"/></a>
			</c:if>
			</td>
		</tr>
    </tbody>
</table>

<h2 class="header">Order History</h2>

<table>
	<thead>
    	<tr class="blue-bg">
            <th>Coin Name</th>
            <th colspan=6>Status</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="coin" items="${inactiveCoins}">
		<tr>
			<td scope="col" class="center-cell"><a class="link-light" href="/coins/${coin.coinRef}">${coin.name}</a></td>
			<c:if test="${coin.merged}">
				<c:if test="${coin.purchasePrice<0.001}">
					<td colspan=6>Coin Merged | Original Purchase <fmt:formatNumber pattern="0.00000000" value="${coin.amount}"/> at &lt; $<fmt:formatNumber pattern="0.000" value="${coin.purchasePrice}"/></td>
				</c:if>
				<c:if test="${coin.purchasePrice>=0.001}">
					<td colspan=6>Coin Merged | Original Purchase <fmt:formatNumber pattern="0.00000000" value="${coin.amount}"/> at $<fmt:formatNumber pattern="0.000" value="${coin.purchasePrice}"/></td>
				</c:if>
			</c:if>
			<c:if test="${coin.sold}">
				<c:if test="${coin.gain >= 0}">
					<c:if test="${coin.sellPrice<0.01}">
						<td colspan=6 class="green">Sold <fmt:formatNumber pattern="0.00000000" value="${coin.sellAmount}"/> at &lt; $<fmt:formatNumber pattern="0.00" value="${coin.sellPrice}"/> - Profit $<fmt:formatNumber pattern="0.000" value="${coin.gain}"/></td>
					</c:if>
					<c:if test="${coin.sellPrice>=0.01}">
						<td colspan=6 class="green">Sold <fmt:formatNumber pattern="0.00000000" value="${coin.sellAmount}"/> at $<fmt:formatNumber pattern="0.00" value="${coin.sellPrice}"/> - Profit $<fmt:formatNumber pattern="0.000" value="${coin.gain}"/></td>
					</c:if>
				</c:if>
				<c:if test="${coin.gain < 0}">
					<c:if test="${coin.sellPrice<0.01}">
						<td colspan=6 class="red">Sold <fmt:formatNumber pattern="0.00000000" value="${coin.sellAmount}"/> at &lt; $<fmt:formatNumber pattern="0.00" value="${coin.sellPrice}"/> - Loss $<fmt:formatNumber pattern="0.000" value="${coin.gain*-1}"/></td>
					</c:if>
					<c:if test="${coin.sellPrice>=0.01}">
						<td colspan=6 class="red">Sold <fmt:formatNumber pattern="0.00000000" value="${coin.sellAmount}"/> at $<fmt:formatNumber pattern="0.00" value="${coin.sellPrice}"/> - Loss $<fmt:formatNumber pattern="0.000" value="${coin.gain*-1}"/></td>
					</c:if>
				</c:if>
			</c:if>
			<td scope="col" class="center-cell">
			<button type="button" class="del-btn" onclick="deleteCoin(document.getElementById('${coin.id}').value)" id="${coin.id}" value="${coin.id}">Delete</button>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="8">Total Profit:  
			<c:if test="${overallProfit>0}">
				<a id="gain">$<fmt:formatNumber pattern="0.00" value="${overallProfit}"/></a>
			</c:if>
			<c:if test="${overallProfit==0}">
				<a>$<fmt:formatNumber pattern="0.00" value="${overallProfit}"/></a>
			</c:if>
			<c:if test="${overallProfit<0}">
				<a id="loss">$<fmt:formatNumber pattern="0.00" value="${overallProfit}"/></a>
			</c:if>
			</td>
		</tr>
    </tbody>
</table>

<br>

<script type="text/javascript" src="../js/app.js"></script>
<script type="text/javascript" src="../js/sell.js"></script>
<script type="text/javascript" src="../js/delete.js"></script>

</body>
</html>