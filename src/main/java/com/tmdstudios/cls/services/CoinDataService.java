package com.tmdstudios.cls.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.cls.models.CoinData;
import com.tmdstudios.cls.repositories.CoinDataRepo;

@Service
public class CoinDataService {
	@Autowired
	CoinDataRepo coinDataRepo;
	
	public List<CoinData> getCoinData() {
		return coinDataRepo.findAll();
	}
	
	public CoinData saveCoinData(CoinData coinData) {
		return coinDataRepo.save(coinData);
	}
}
