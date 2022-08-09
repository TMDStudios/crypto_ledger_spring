package com.tmdstudios.cls.services;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.tmdstudios.cls.models.Coin;

@Service
public class CoinService {

	private final CoinDataService coinDataService;
	
	public CoinService(CoinDataService coinDataService) {
		this.coinDataService = coinDataService;
	}
	
	public List<Coin> getCoinData() {
		ArrayList<Coin> coinData = new ArrayList<Coin>();
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonCoinData = (JSONArray) parser.parse(coinDataService.getCoinData().get(0).getJsonData());
			
			for(int i = 0; i<jsonCoinData.size(); i++) {
				JSONObject coinObject = (JSONObject) jsonCoinData.get(i);
				Coin coin = new Coin(
						coinObject.get("name").toString(), 
						coinObject.get("symbol").toString(),
						coinObject.get("logo").toString(),
						Double.parseDouble(coinObject.get("price").toString()),
						Long.parseLong(coinObject.get("coinRank").toString()),
						Double.parseDouble(coinObject.get("priceChangePercentage1d").toString()),
						Double.parseDouble(coinObject.get("priceChangePercentage7d").toString()),
						Double.parseDouble(coinObject.get("priceChangePercentage30d").toString())
						);
				coinData.add(coin);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return coinData;
	}
	
	public Coin findBySymbol(String symbol) {
		List<Coin> coinData = getCoinData();
		for(Coin coin : coinData) {
			if(coin.getSymbol().toLowerCase().equals(symbol.toLowerCase())) {
				return coin;
			}
		}
		return null;
	}
}
