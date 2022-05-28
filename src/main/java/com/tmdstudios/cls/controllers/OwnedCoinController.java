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
	
	@RequestMapping(value="/api/buy", method=RequestMethod.POST)
	public OwnedCoin newOwnedCoin(
			HttpSession session,
			@RequestParam(value="name") String name, 
			@RequestParam(value="symbol") String symbol,  
			@RequestParam(value="amount") Double amount,
			@RequestParam(value="purchasePrice") Double purchasePrice,
			@RequestParam(value="coinRef") Long coinRef
			) {
		OwnedCoin newOwnedCoin = new OwnedCoin(name, symbol, amount, purchasePrice, coinRef);
		
		Long userId = (Long) session.getAttribute("userId");		
		User owner = userService.findById(userId);
		List<OwnedCoin> ownedCoins = ownedCoinService.findBySymbol(symbol, owner);
		
		Double totalAmount = 0.0;
		Double totalSpent = 0.0;
		Double currentPrice = coinService.findBySymbol(symbol).getPrice();
		
		if(purchasePrice<=0) {purchasePrice=currentPrice;}
		
		for(OwnedCoin coin:ownedCoins) {
			// only keep track of totals for last coin
			totalAmount+=coin.getAmount();
			totalSpent+=coin.getAmount()*coin.getPurchasePrice();
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
		newOwnedCoin.setPriceDifference(currentPrice-purchasePrice*100-100);
		ownedCoinService.addOwnedCoin(newOwnedCoin);
		
		if(ownedCoins.size()>0) {
			OwnedCoin oldOwnedCoin = ownedCoins.get(ownedCoins.size()-1);
			oldOwnedCoin.setMerged(true);
			oldOwnedCoin.setPriceDifference(currentPrice/purchasePrice*100-100);
			ownedCoinService.updateOwnedCoin(oldOwnedCoin);
		}
		
		return newOwnedCoin;
	}
	
	@RequestMapping(value="/api/sell", method=RequestMethod.POST)
	public OwnedCoin sellOwnedCoin(
			HttpSession session,
			@RequestParam(value="id") Long id,  
			@RequestParam(value="amount") Double amount
			) {
		
		OwnedCoin ownedCoin = ownedCoinService.findById(id);
		
		if(ownedCoin.getTotalAmount() >= amount && amount > 0) {
			ownedCoin.setSold(true);
			ownedCoin.setDateSold(new Date());
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
			remainingCoin.setCoinRef(ownedCoin.getCoinRef());
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
	
	@RequestMapping("/api/coins/watch/{id}")
	public Boolean watchCoin(HttpSession session, @PathVariable("id") Long id) {
		
		if(session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			
			Coin coin = coinService.findById(id);
			boolean coinFound = false;
			for(Coin userCoin:user.getCoins()) {
				if(userCoin.getSymbol()==coin.getSymbol()) {
					coinFound=true;
					user.getCoins().remove(user.getCoins().indexOf(userCoin));
					break;
				}
			}
			if(!coinFound) {
				user.getCoins().add(coin);
			}
			userService.updateUser(user);
			return true;
		}
		return false;
		
	}
}
