package com.tmdstudios.cls.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.CoinData;
import com.tmdstudios.cls.services.CoinDataService;
import com.tmdstudios.cls.services.CoinService;

@RestController
public class CoinController {
	
	@Autowired
	private CoinDataService coinDataService;
	
	@Autowired
	private CoinService coinService;
	
	@CrossOrigin(origins = "https://crypto-ledger.herokuapp.com")
	@RequestMapping("/api/coins")
	public List<Coin> allCoins() {
		return coinService.getCoinData();
	}
	
	@CrossOrigin(origins = "https://crypto-ledger.herokuapp.com")
	@RequestMapping(value="/api/coins", method=RequestMethod.POST)
	public CoinData newCoin(@RequestParam(value="jsonData") String jsonData) {
		CoinData newCoinData = new CoinData(jsonData);	
		if(coinDataService.getCoinData().isEmpty()) {
			coinDataService.saveCoinData(newCoinData);
		}else {
			CoinData existingCoinData = coinDataService.getCoinData().get(0);
			existingCoinData.setJsonData(jsonData);
			coinDataService.saveCoinData(existingCoinData);
		}
		
		return newCoinData;
	}
	
	@RequestMapping("/api/coins/{symbol}")
	public Coin show(@PathVariable("symbol") String symbol) {
		
		return null;
	}

}
