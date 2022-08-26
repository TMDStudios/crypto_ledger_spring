package com.tmdstudios.cls.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.CoinData;
import com.tmdstudios.cls.repositories.CoinDataRepo;

@Service
public class CoinDataService {
	@Value("${NOMICS_API}")
	private String nomicsApi;
	
	@Autowired
	CoinDataRepo coinDataRepo;
	
	public List<CoinData> getCoinData() {
		return coinDataRepo.findAll();
	}
	
	public CoinData saveCoinData(CoinData coinData) {
		return coinDataRepo.save(coinData);
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	@Scheduled(cron = "*/30 * * * * *")
	public List<Coin> fetchData() throws IOException, InterruptedException {
		
		HttpURLConnection connection = null;
		URL url = new URL("https://api.nomics.com/v1/currencies/ticker?key="+nomicsApi+"&per-page=100");
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-length", "0");
        connection.connect();
        int status = connection.getResponseCode();
        
        System.out.println("STATUS: "+status);
        
        StringBuilder sb = new StringBuilder();
        
        if(status==200) {
        	BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String line;
            while ((line = br.readLine()) != null) {
            	String cleanLine = line.replace("1d", "oneDay");
            	String cleanLine2 = cleanLine.replace("7d", "sevenDay");
            	String cleanLine3 = cleanLine2.replace("30d", "thirtyDay");
                sb.append(cleanLine3+"\n");
            }
            br.close();
        }
		
		JSONParser parser = new JSONParser();
		ArrayList<Coin> coinData = new ArrayList<Coin>();
		JSONArray jsonArray = new JSONArray();
		try {
			JSONArray jsonCoinData = (JSONArray) parser.parse(sb.toString());
			
			for(int i = 0; i<jsonCoinData.size(); i++) {
				JSONObject coinObject = (JSONObject) jsonCoinData.get(i);
				
				JSONObject customCoinJson = new JSONObject();
				
				try {
					JSONObject priceChange1d = (JSONObject) coinObject.get("oneDay");
					JSONObject priceChange7d = (JSONObject) coinObject.get("sevenDay");
					JSONObject priceChange30d = (JSONObject) coinObject.get("thirtyDay");
					customCoinJson.put("name", coinObject.get("name").toString());
					customCoinJson.put("symbol", coinObject.get("symbol").toString());
					customCoinJson.put("logo", coinObject.get("logo_url").toString());
					customCoinJson.put("price", coinObject.get("price").toString());
					customCoinJson.put("coinRank", coinObject.get("rank").toString());
					customCoinJson.put("priceChangePercentage1d", priceChange1d.get("price_change_pct").toString());
					customCoinJson.put("priceChangePercentage7d", priceChange7d.get("price_change_pct").toString());
					customCoinJson.put("priceChangePercentage30d", priceChange30d.get("price_change_pct").toString());
				}catch(NullPointerException e) {
					customCoinJson.put("name", coinObject.get("name").toString());
					customCoinJson.put("symbol", coinObject.get("symbol").toString());
					customCoinJson.put("logo", coinObject.get("logo_url").toString());
					customCoinJson.put("price", coinObject.get("price").toString());
					customCoinJson.put("coinRank", coinObject.get("rank").toString());
					customCoinJson.put("priceChangePercentage1d", "0");
					customCoinJson.put("priceChangePercentage7d", "0");
					customCoinJson.put("priceChangePercentage30d", "0");
				}
				
				jsonArray.add(customCoinJson);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(getCoinData().isEmpty()) {
			saveCoinData(new CoinData(jsonArray.toString()));
		}else {
			CoinData existingCoinData = getCoinData().get(0);
			existingCoinData.setJsonData(jsonArray.toString());
			saveCoinData(existingCoinData);
		}
		
		return coinData;
	}
}
