package com.tmdstudios.cls.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.services.CoinService;

@RestController
public class CoinController {
	
	private final CoinService coinService;
	
	public CoinController(CoinService coinService) {
		this.coinService = coinService;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/api/coins")
	public List<Coin> allCoins() {
		return coinService.allCoins();
	}
	
	@RequestMapping(value="/api/coins", method=RequestMethod.POST)
	public Coin newCoin(
			@RequestParam(value="name") String name, 
			@RequestParam(value="symbol") String symbol, 
			@RequestParam(value="logo") String logo, 
			@RequestParam(value="price") Double price,
			@RequestParam(value="coinRank") Long coinRank,
			@RequestParam(value="priceChangePercentage1d") Double priceChangePercentage1d,
			@RequestParam(value="priceChangePercentage7d") Double priceChangePercentage7d,
			@RequestParam(value="priceChangePercentage30d") Double priceChangePercentage30d
			) {
		Coin newCoin = new Coin(
				name, 
				symbol, 
				logo, 
				price, 
				coinRank, 
				priceChangePercentage1d, 
				priceChangePercentage7d, 
				priceChangePercentage30d
				);
		
		Coin coin = coinService.findBySymbol(symbol);
		if(coin==null) {
			coinService.addCoin(newCoin);
		}else {
			newCoin.setId(coin.getId());
			newCoin.setUsers(coin.getUsers());
			coinService.updateCoin(newCoin);
		}
		
		return coin;
	}
	
	@RequestMapping("/api/coins/{id}")
	public Coin show(@PathVariable("id") Long id) {
		Coin coin = coinService.findById(id);
		return coin;
	}

}
