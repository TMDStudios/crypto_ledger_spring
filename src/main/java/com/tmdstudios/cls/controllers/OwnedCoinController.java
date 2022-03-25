package com.tmdstudios.cls.controllers;

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
	
//	public OwnedCoinController(OwnedCoinService ownedCoinService) {
//		this.ownedCoinService = ownedCoinService;
//	}
	
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
			@RequestParam(value="purchasePrice") Double purchasePrice
			) {
		OwnedCoin newOwnedCoin = new OwnedCoin(name, symbol, amount, purchasePrice);
		
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
			newOwnedCoin.setPriceDifference(currentPrice-purchasePrice);
			ownedCoinService.updateOwnedCoin(oldOwnedCoin);
		}
		
		return newOwnedCoin;
	}
	
	@RequestMapping("/api/ownedCoins/{id}")
	public OwnedCoin show(@PathVariable("id") Long id) {
		OwnedCoin ownedCoin = ownedCoinService.findById(id);
		return ownedCoin;
	}
	
//	@RequestMapping(value="/api/ownedCoins/{id}", method=RequestMethod.PUT)
//	public OwnedCoin update(
//			@PathVariable("id") Long id,
//			@RequestParam(value="name") String name, 
//			@RequestParam(value="symbol") String symbol, 
//			@RequestParam(value="logo") String logo, 
//			@RequestParam(value="price") Double price,
//			@RequestParam(value="ownedCoinRank") Long ownedCoinRank,
//			@RequestParam(value="priceChangePercentage1d") Double priceChangePercentage1d,
//			@RequestParam(value="priceChangePercentage7d") Double priceChangePercentage7d,
//			@RequestParam(value="priceChangePercentage30d") Double priceChangePercentage30d
//			) {
//		OwnedCoin newOwnedCoin = new OwnedCoin(
//				name, 
//				symbol, 
//				logo, 
//				price, 
//				ownedCoinRank, 
//				priceChangePercentage1d, 
//				priceChangePercentage7d, 
//				priceChangePercentage30d
//				);
//		newOwnedCoin.setId(id);
//		OwnedCoin ownedCoin = ownedCoinService.updateOwnedCoin(newOwnedCoin);
//		return ownedCoin;
//	}
	
	@RequestMapping(value="/api/ownedCoins/{id}", method=RequestMethod.DELETE)
	public void destroy(@PathVariable("id") Long id) {
		OwnedCoin ownedCoin = ownedCoinService.findById(id);
		ownedCoinService.deleteOwnedCoin(ownedCoin);
	}
}
