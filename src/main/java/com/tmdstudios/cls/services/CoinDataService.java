package com.tmdstudios.cls.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.tmdstudios.cls.models.Coin;

public class CoinDataService {

	private static String TOP_100 = "https://api.nomics.com/v1/currencies/ticker?key=eb3a7d733a1719cd0a2731a28b6c9bedcb052751&per-page=100".toString();
	
	public List<Coin> fetchCoinData() throws IOException {
		List<Coin> topCoins = new ArrayList<>();
		
		StringBuilder jsonData = new StringBuilder();
		
		URL url = new URL(TOP_100);
		
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		
		try {
			InputStream inputStream = urlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			
			InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String inputLine = bufferedReader.readLine();
			while(inputLine != null) {
				jsonData.append(inputLine);
				inputLine = bufferedReader.readLine();
			}

			JSONArray root = new JSONArray(jsonData.toString());
			for(int i = 0; i<root.length(); i++) {
				String name = root.getJSONObject(i).getString("name");
				String symbol = root.getJSONObject(i).getString("id");
				String logo = root.getJSONObject(i).getString("logo_url");
				Double price = root.getJSONObject(i).getDouble("price");
				Long rank = Long.parseLong(root.getJSONObject(i).getString("rank"));
				Double priceChange1d = root.getJSONObject(i).getJSONObject("1d").getDouble("price_change_pct");
				Double priceChange7d = root.getJSONObject(i).getJSONObject("7d").getDouble("price_change_pct");
				Double priceChange30d = root.getJSONObject(i).getJSONObject("30d").getDouble("price_change_pct");
				Coin coin = new Coin(name, symbol, logo, price, rank, priceChange1d, priceChange7d, priceChange30d);
				topCoins.add(coin);
			}
		} finally {
			urlConnection.disconnect();
		}
		
		return topCoins;
		
	}
	
}