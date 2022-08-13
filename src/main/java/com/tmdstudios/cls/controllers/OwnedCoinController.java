package com.tmdstudios.cls.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.OwnedCoin;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.services.CoinService;
import com.tmdstudios.cls.services.OwnedCoinService;
import com.tmdstudios.cls.services.UserService;

@RestController
public class OwnedCoinController {
	
	@Autowired
	private OwnedCoinService ownedCoinService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@RequestMapping("/api/ownedCoins")
	public List<OwnedCoin> allOwnedCoins() {
		return ownedCoinService.allOwnedCoins();
	}
	
	@RequestMapping("/api/user-ledger/{apiKey}")
	public List<OwnedCoin> allOwnedCoins(@PathVariable("apiKey") String apiKey) {		
		User user = userService.findByApiKey(apiKey);
		return ownedCoinService.findByOwnerDesc(user);
	}
	
	@RequestMapping(value="/api/buy/{apiKey}", method=RequestMethod.POST)
	public OwnedCoin newOwnedCoin(
			HttpSession session,
			@PathVariable("apiKey") String apiKey,
			@RequestParam(value="name") String name, 
			@RequestParam(value="symbol") String symbol,  
			@RequestParam(value="amount") Double amount,
			@RequestParam(value="purchasePrice") Double purchasePrice
			) {
		OwnedCoin newOwnedCoin = new OwnedCoin(name, symbol, amount, purchasePrice);
				
		User owner = userService.findByApiKey(apiKey);
		List<OwnedCoin> ownedCoins = ownedCoinService.findBySymbol(symbol, owner);
		
		Double totalAmount = 0.0;
		Double totalSpent = 0.0;
		
		Coin coin = coinService.findBySymbol(symbol);
		Double currentPrice = coin.getPrice();
		
		if(purchasePrice<=0) {purchasePrice=currentPrice;}
		
		for(OwnedCoin ownedCoin:ownedCoins) {
			// only keep track of totals for last coin
			totalAmount+=ownedCoin.getAmount();
			totalSpent+=ownedCoin.getAmount()*ownedCoin.getPurchasePrice();
		}
		
//		System.out.println("Found: "+ownedCoins.size());
		totalSpent += purchasePrice*amount;
		totalAmount += amount;
		
		newOwnedCoin.setOwner(userService.findById(owner.getId()));
		newOwnedCoin.setValue(currentPrice*amount);
		newOwnedCoin.setTotalAmount(totalAmount);
		newOwnedCoin.setTotalValue(totalAmount*currentPrice);
		newOwnedCoin.setTotalSpent(totalSpent);
		newOwnedCoin.setPurchasePrice(totalSpent/totalAmount);
		newOwnedCoin.setCurrentPrice(currentPrice);
		newOwnedCoin.setPriceDifference(currentPrice-purchasePrice*100-100);
		newOwnedCoin.setTotalProfit(currentPrice*totalAmount-totalSpent/totalAmount*totalAmount);
		ownedCoinService.addOwnedCoin(newOwnedCoin);
		
		if(ownedCoins.size()>0) {
			OwnedCoin oldOwnedCoin = ownedCoins.get(ownedCoins.size()-1);
			oldOwnedCoin.setMerged(true);
			oldOwnedCoin.setUpdatedAt(new Date());
			oldOwnedCoin.setPriceDifference(currentPrice/purchasePrice*100-100);
			ownedCoinService.updateOwnedCoin(oldOwnedCoin);
		}
		
		return newOwnedCoin;
	}
	
	@RequestMapping(value="/api/sell/{apiKey}", method=RequestMethod.POST)
	public OwnedCoin sellOwnedCoin(
			HttpSession session,
			@PathVariable("apiKey") String apiKey,
			@RequestParam(value="id") Long id,  
			@RequestParam(value="amount") Double amount
			) {
		
		if(userService.findByApiKey(apiKey)==null) {
			return null;
		}
		OwnedCoin ownedCoin = ownedCoinService.findById(id);
		Coin coin = coinService.findBySymbol(ownedCoin.getSymbol());
		
		if(ownedCoin.getTotalAmount() >= amount && amount > 0) {
			ownedCoin.setSold(true);
			ownedCoin.setDateSold(new Date());
			ownedCoin.setUpdatedAt(new Date());
			ownedCoin.setCurrentPrice(coin.getPrice());
			ownedCoin.setPriceDifference(coin.getPrice()/ownedCoin.getPurchasePrice()*100-100);
			ownedCoin.setSellPrice(ownedCoin.getCurrentPrice());
			ownedCoin.setSellAmount(amount);
			ownedCoin.setGain(ownedCoin.getSellPrice() * amount - ownedCoin.getPurchasePrice() * amount);
			ownedCoin.setTotalAmount(ownedCoin.getTotalAmount() - amount);
			ownedCoin.setTotalSpent(ownedCoin.getPurchasePrice() * amount);
			ownedCoin.setTotalValue(ownedCoin.getTotalAmount()*ownedCoin.getCurrentPrice());
			ownedCoin.setPriceDifference(ownedCoin.getCurrentPrice()/ownedCoin.getPurchasePrice()*100-100);
			ownedCoinService.updateOwnedCoin(ownedCoin);
		}
		
		if(ownedCoin.getTotalAmount() > 0) {
			OwnedCoin remainingCoin = new OwnedCoin();

			remainingCoin.setAmount(ownedCoin.getTotalAmount());
			remainingCoin.setCurrentPrice(ownedCoin.getCurrentPrice());
			remainingCoin.setName(ownedCoin.getName());
			remainingCoin.setPurchasePrice(ownedCoin.getPurchasePrice());
			remainingCoin.setSymbol(ownedCoin.getSymbol());
			remainingCoin.setOwner(ownedCoin.getOwner());
			remainingCoin.setValue(ownedCoin.getValue());
			remainingCoin.setTotalAmount(ownedCoin.getTotalAmount());
			remainingCoin.setTotalValue(ownedCoin.getTotalValue());
			remainingCoin.setTotalSpent(ownedCoin.getTotalSpent());
			remainingCoin.setPriceDifference(ownedCoin.getPriceDifference());
			ownedCoinService.addOwnedCoin(remainingCoin);

		}
		
		return ownedCoin;
	}
	
	@RequestMapping(value="/api/delete/{id}", method=RequestMethod.DELETE)
	public void destroy(@PathVariable("id") Long id) {
		OwnedCoin ownedCoin = ownedCoinService.findById(id);
		ownedCoinService.deleteOwnedCoin(ownedCoin);
	}
	
	@RequestMapping(value="/api/delete-all", method=RequestMethod.DELETE)
	public void deleteAll(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		ownedCoinService.deleteAllFromOwner(userId);
	}
	
	@RequestMapping("/api/coins/watch/{symbol}")
	public Boolean watchCoin(HttpSession session, @PathVariable("symbol") String symbol) {
		if(session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			
			boolean coinFound = false;
			for(String userCoin:user.getCoins()) {
				if(userCoin.equals(symbol)) {
					coinFound=true;
					user.getCoins().remove(user.getCoins().indexOf(userCoin));
					break;
				}
			}
			if(!coinFound) {
				user.getCoins().add(symbol);
			}
			userService.updateUser(user);
			return true;
		}
		return false;
		
	}
}
