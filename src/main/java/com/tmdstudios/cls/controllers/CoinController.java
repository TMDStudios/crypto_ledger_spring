package com.tmdstudios.cls.controllers;

import java.util.List;

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
		
//		Coin coin = coinService.findBySymbol(symbol);
		Coin coin = coinService.findByRank(coinRank);
		if(coin==null) {
			coinService.addCoin(newCoin);
//			System.out.println("ADDED - "+newCoin.getName());
		}else {
//			coin.setCoinRank(null);
//			coinService.updateCoin(coin);
			newCoin.setId(coin.getId());
			newCoin.setUsers(coin.getUsers());
			coinService.updateCoin(newCoin);
//			System.out.println("UPDATED - "+coin.getName());
		}
		
		return coin;
	}
	
	@RequestMapping("/api/coins/{id}")
	public Coin show(@PathVariable("id") Long id) {
		Coin coin = coinService.findById(id);
		return coin;
	}
	
	@RequestMapping(value="/api/coins/{id}", method=RequestMethod.PUT)
	public Coin update(
			@PathVariable("id") Long id,
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
		newCoin.setId(id);
		Coin coin = coinService.updateCoin(newCoin);
		return coin;
	}
	
	@RequestMapping(value="/api/coins/{id}", method=RequestMethod.DELETE)
	public void destroy(@PathVariable("id") Long id) {
		Coin coin = coinService.findById(id);
		coinService.deleteCoin(coin);
	}

}
