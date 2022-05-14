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
    	<li class="nav_login">
	    	<c:if test="${darkMode}">
				<a class="nav_link" href="/mode">Light</a>
			</c:if>
			<c:if test="${!darkMode}">
				<a class="nav_link" href="/mode">Dark</a>
			</c:if>
	    </li>
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
	            <c:if test="${coin.price<0.001}">
					<td>&lt; $<fmt:formatNumber pattern="0.000" value="${coin.price}"/></td>
				</c:if>
				<c:if test="${coin.price>=0.001}">
					<td>$<fmt:formatNumber pattern="0.000" value="${coin.price}"/></td>
				</c:if>
	            <c:if test="${coin.priceChangePercentage1d>=0}">
					<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage1d*100}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage1d<0}">
					<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage1d*100}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage7d>=0}">
					<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage7d*100}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage7d<0}">
					<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage7d*100}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage30d>=0}">
					<td class="green"><img src="${upArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage30d*100}"/>%</td>
				</c:if>
				<c:if test="${coin.priceChangePercentage30d<0}">
					<td class="red"><img src="${downArrow}"/> <fmt:formatNumber pattern="0.000" value="${coin.priceChangePercentage30d*100}"/>%</td>
				</c:if>
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