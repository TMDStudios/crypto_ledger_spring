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
	
	public List<Coin> topCoinsDesc(){
		return coinRepo.getTop100desc();
	}
	
	public List<Coin> topCoinsName(){
		return coinRepo.getTop100Name();
	}
	
	public List<Coin> topCoinsNameDesc(){
		return coinRepo.getTop100NameDesc();
	}
	
	public List<Coin> topCoinsPrice(){
		return coinRepo.getTop100Price();
	}
	
	public List<Coin> topCoinsPriceDesc(){
		return coinRepo.getTop100PriceDesc();
	}
	
	public List<Coin> topCoins1d(){
		return coinRepo.getTop1001d();
	}
	
	public List<Coin> topCoins1dDesc(){
		return coinRepo.getTop1001dDesc();
	}
	
	public List<Coin> topCoins7d(){
		return coinRepo.getTop1007d();
	}
	
	public List<Coin> topCoins7dDesc(){
		return coinRepo.getTop1007dDesc();
	}
	
	public List<Coin> topCoins30d(){
		return coinRepo.getTop10030d();
	}
	
	public List<Coin> topCoins30dDesc(){
		return coinRepo.getTop10030dDesc();
	}
	
	public List<Coin> userCoins(User user){
		return coinRepo.getUserCoins(user.getId());
	}
	
	public List<Coin> userCoinsDesc(User user){
		return coinRepo.getUserCoinsDesc(user.getId());
	}
	
	public List<Coin> userCoinsName(User user){
		return coinRepo.getUserCoinsName(user.getId());
	}
	
	public List<Coin> userCoinsNameDesc(User user){
		return coinRepo.getUserCoinsNameDesc(user.getId());
	}
	
	public List<Coin> userCoinsPrice(User user){
		return coinRepo.getUserCoinsPrice(user.getId());
	}
	
	public List<Coin> userCoinsPriceDesc(User user){
		return coinRepo.getUserCoinsPriceDesc(user.getId());
	}
	
	public List<Coin> userCoins1d(User user){
		return coinRepo.getUserCoins1d(user.getId());
	}
	
	public List<Coin> userCoins1dDesc(User user){
		return coinRepo.getUserCoins1dDesc(user.getId());
	}
	
	public List<Coin> userCoins7d(User user){
		return coinRepo.getUserCoins7d(user.getId());
	}
	
	public List<Coin> userCoins7dDesc(User user){
		return coinRepo.getUserCoins7dDesc(user.getId());
	}
	
	public List<Coin> userCoins30d(User user){
		return coinRepo.getUserCoins30d(user.getId());
	}
	
	public List<Coin> userCoins30dDesc(User user){
		return coinRepo.getUserCoins30dDesc(user.getId());
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