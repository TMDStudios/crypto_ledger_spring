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
		<li class="nav_item"><a class="nav_link" href="/settings">Settings</a></li>
		<li class="nav_item"><a class="nav_link" href="/api/docs">API Docs</a></li>
	</c:if>
	<c:if test="${userId==null}">
		<li class="nav_item"><a class="nav_link" href="/login">Log In</a></li>
		<li class="nav_login"><a class="nav_link" href="/register">Register</a></li>
	</c:if>    
</ul>

<c:if test="${darkMode}">
<div style="height:62px; background-color: #1D2330; overflow:hidden; box-sizing: border-box; border: 1px solid #282E3B; text-align: right; line-height:14px; block-size:62px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #262B38;padding:1px;padding: 0px; margin: 0px; width: 100%;"><div style="height:40px; padding:0px; margin:0px; width: 100%;"><iframe src="https://widget.coinlib.io/widget?type=horizontal_v2&theme=dark&pref_coin_id=1505&invert_hover=no" width="100%" height="36px" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0" border="0" style="border:0;margin:0;padding:0;"></iframe></div><div style="color: #626B7F; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;"><a href="https://coinlib.io" target="_blank" style="font-weight: 500; color: #626B7F; text-decoration:none; font-size:11px">Cryptocurrency Prices</a>&nbsp;by Coinlib</div></div>
</c:if>
<c:if test="${!darkMode}">
<div style="height:62px; background-color: #FFFFFF; overflow:hidden; box-sizing: border-box; border: 1px solid #56667F; border-radius: 4px; text-align: right; line-height:14px; block-size:62px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #56667F;padding:1px;padding: 0px; margin: 0px; width: 100%;"><div style="height:40px; padding:0px; margin:0px; width: 100%;"><iframe src="https://widget.coinlib.io/widget?type=horizontal_v2&theme=light&pref_coin_id=1505&invert_hover=" width="100%" height="36px" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0" border="0" style="border:0;margin:0;padding:0;"></iframe></div><div style="color: #FFFFFF; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;"><a href="https://coinlib.io" target="_blank" style="font-weight: 500; color: #FFFFFF; text-decoration:none; font-size:11px">Cryptocurrency Prices</a>&nbsp;by Coinlib</div></div>
</c:if>

<c:if test="${userId!=null}">
	<c:if test="${showWatchlist}">
		<h4 class="center"><a class="link-light" href="/watchlist">All Coins</a></h4>
		<hr>
	</c:if>
	<c:if test="${!showWatchlist}">
		<h4 class="center"><a class="link-light" href="/watchlist">My Watchlist</a></h4>
		<hr>
	</c:if>
</c:if>

<div class="search-container">
    <form action="/watchlist/search" method="get">
		<span class="search-row">
		<input type="text" min="0" id="searchTerm" name="searchTerm" placeholder="Search"/>
		<input class="search-btn" type="submit" value="Search"/>
		</span>
	</form>	
</div>

<table>
	<thead>
    	<tr>
            <th></th>
            <th><a class="link-light" href="/sort/1">Rank</a></th>
            <th colspan=3><a class="link-light" href="/sort/2">Coin</a></th>
            <th><a class="link-light" href="/sort/3">Price</a></th>
            <th><a class="link-light" href="/sort/4">24h %</a></th>
            <th><a class="link-light" href="/sort/5">7d %</a></th>
            <th><a class="link-light" href="/sort/6">30d %</a></th>
        </tr>
    </thead>
	<tbody>
		<c:forEach var="coin" items="${coins}">
        	<tr class="card-item">
	        	<c:if test="${!myCoins.contains(coin.symbol)}">
					<td class="narrow"><img id="starImg${coin.symbol}" onclick="watchCoin('${coin.symbol}')" src="${starOutline}"/></td>
				</c:if>
				<c:if test="${myCoins.contains(coin.symbol)}">
					<td class="narrow"><img id="starImg${coin.symbol}" onclick="watchCoin('${coin.symbol}')" src="${starFull}"/></td>
				</c:if>
	            <c:if test="${coin.coinRank<=100}">
					<td class="narrow">${coin.coinRank}</td>
				</c:if>
				<c:if test="${coin.coinRank>100}">
					<td class="narrow">100+</td>
				</c:if>
	            <td class="narrow"><a href="/coins/${coin.symbol}"><img class="logo-img" src="${coin.logo}"/></a></td>
	            <td colspan=2 class="float-left"><a class="link-light" href="/coins/${coin.symbol}">${coin.name} - ${coin.symbol}</a></td>
	            <c:if test="${coin.price<0.001}">
					<td><p id="price${coin.symbol}">&lt; $<fmt:formatNumber pattern="0.000" value="${coin.price}"/></p></td>
				</c:if>
				<c:if test="${coin.price>=0.001}">
					<td><p id="price${coin.symbol}">$<fmt:formatNumber pattern="0.000" value="${coin.price}"/></p></td>
				</c:if>
	            <c:if test="${coin.priceChangePercentage1d>=0}">
					<td id="1dTd${coin.symbol}" class="green"><img id="1dImg${coin.symbol}" src="${upArrow}"/> <span id="1d${coin.symbol}"><fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage1d*100}"/>%</span></td>
				</c:if>
				<c:if test="${coin.priceChangePercentage1d<0}">
					<td id="1dTd${coin.symbol}" class="red"><img id="1dImg${coin.symbol}" src="${downArrow}"/> <span id="1d${coin.symbol}"><fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage1d*100}"/>%</span></td>
				</c:if>
				<c:if test="${coin.priceChangePercentage7d>=0}">
					<td id="7dTd${coin.symbol}" class="green"><img id="7dImg${coin.symbol}" src="${upArrow}"/> <span id="7d${coin.symbol}"><fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage7d*100}"/>%</span></td>
				</c:if>
				<c:if test="${coin.priceChangePercentage7d<0}">
					<td id="7dTd${coin.symbol}" class="red"><img id="7dImg${coin.symbol}" src="${downArrow}"/> <span id="7d${coin.symbol}"><fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage7d*100}"/>%</span></td>
				</c:if>
				<c:if test="${coin.priceChangePercentage30d>=0}">
					<td id="30dTd${coin.symbol}" class="green"><img id="30dImg${coin.symbol}" src="${upArrow}"/> <span id="30d${coin.symbol}"><fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage30d*100}"/>%</span></td>
				</c:if>
				<c:if test="${coin.priceChangePercentage30d<0}">
					<td id="30dTd${coin.symbol}" class="red"><img id="30dImg${coin.symbol}" src="${downArrow}"/> <span id="30d${coin.symbol}"><fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage30d*100}"/>%</span></td>
				</c:if>
            </tr>
		</c:forEach>
	</tbody>   
</table>

<br>
<div style="margin: auto; text-align: center;">Coin prices provided by <a href="https://nomics.com/" target="_blank">
https://nomics.com/</a></div>
<br>
<input type="hidden" id="nomicsApi" name="nomicsApi" value="${nomicsApi}">

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>