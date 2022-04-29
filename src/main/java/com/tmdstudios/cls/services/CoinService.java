package com.tmdstudios.cls.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.repositories.CoinRepo;

@Service
public class CoinService {
	private final CoinRepo coinRepo;
	
	public CoinService(CoinRepo coinRepo) {
		this.coinRepo = coinRepo;
	}
	
	public List<Coin> allCoins(){
		return coinRepo.findAllByOrderByCoinRankAsc();
	}
	
	public List<Coin> topCoins(){
		return coinRepo.getTop100();
	}
	
	public List<Coin> userCoins(User user){
		return coinRepo.getUserCoins(user);
	}
	
	public List<Coin> getAssignedUsers(User user){
		return coinRepo.findAllByUsers(user);
	}
	
	public List<Coin> getUnassignedUsers(User user){
		return coinRepo.findByUsersNotContains(user);
	}
	
	public Coin addCoin(Coin coin) {
		return coinRepo.save(coin);
	}
	
	public Coin updateCoin(Coin coin) {
		return coinRepo.save(coin);
	}
	
	public void deleteCoin(Coin coin) {
		coinRepo.delete(coin);
	}
	
	public Coin findById(Long id) {
		Optional<Coin> optionalCoin = coinRepo.findById(id);
		if(optionalCoin.isPresent()) {
			return optionalCoin.get();
		}else {
			return null;
		}
	}
	
	public Coin findBySymbol(String symbol) {
		Optional<Coin> optionalCoin = coinRepo.findBySymbol(symbol);
		if(optionalCoin.isPresent()) {
			return optionalCoin.get();
		}else {
			return null;
		}
	}
	
	public Coin findByRank(Long coinRank) {
		Optional<Coin> optionalCoin = coinRepo.findByCoinRank(coinRank);
		if(optionalCoin.isPresent()) {
			return optionalCoin.get();
		}else {
			return null;
		}
	}
	
}