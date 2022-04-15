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
		
		User owner = (User) session.getAttribute("user");
		List<OwnedCoin> ownedCoins = ownedCoinService.findBySymbol(symbol, owner);
		
		Double totalAmount = 0.0;
		Double totalSpent = 0.0;
		Double currentPrice = coinService.findBySymbol(symbol).getPrice();
		
		for(OwnedCoin coin:ownedCoins) {
			// only keep track of totals for last coin
			totalAmount+=coin.getAmount();
			totalSpent+=coin.getAmount()*coin.getPurchasePrice();
		}
		
		System.out.println("Found: "+ownedCoins.size());
		
		newOwnedCoin.setOwner(userService.findById(owner.getId()));
		newOwnedCoin.setValue(currentPrice*amount);
		newOwnedCoin.setTotalAmount(totalAmount+amount);
		newOwnedCoin.setTotalValue((totalAmount+amount)*currentPrice);
		newOwnedCoin.setTotalSpent(totalSpent+currentPrice*amount);
		newOwnedCoin.setPriceDifference(currentPrice-purchasePrice);
		ownedCoinService.addOwnedCoin(newOwnedCoin);
		
		if(ownedCoins.size()>0) {
			OwnedCoin oldOwnedCoin = ownedCoins.get(ownedCoins.size()-1);
			oldOwnedCoin.setMerged(true);
			newOwnedCoin.setPriceDifference(currentPrice/purchasePrice*100-100);
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
}
