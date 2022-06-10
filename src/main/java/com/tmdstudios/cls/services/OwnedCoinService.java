package com.tmdstudios.cls.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tmdstudios.cls.models.OwnedCoin;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.repositories.OwnedCoinRepo;

@Service
public class OwnedCoinService {
	private final OwnedCoinRepo ownedCoinRepo;
	
	public OwnedCoinService(OwnedCoinRepo ownedCoinRepo) {
		this.ownedCoinRepo = ownedCoinRepo;
	}
	
	public List<OwnedCoin> allOwnedCoins(){
		return ownedCoinRepo.findAll();
	}
	
	public OwnedCoin addOwnedCoin(OwnedCoin ownedCoin) {
		return ownedCoinRepo.save(ownedCoin);
	}
	
	public OwnedCoin updateOwnedCoin(OwnedCoin ownedCoin) {
		return ownedCoinRepo.save(ownedCoin);
	}
	
	public void deleteOwnedCoin(OwnedCoin ownedCoin) {
		ownedCoinRepo.delete(ownedCoin);
	}
	
	public void deleteAllFromOwner(Long ownerId) {
		ownedCoinRepo.deleteAllFromOwner(ownerId);
	}
	
	public OwnedCoin findById(Long id) {
		Optional<OwnedCoin> optionalOwnedCoin = ownedCoinRepo.findById(id);
		if(optionalOwnedCoin.isPresent()) {
			return optionalOwnedCoin.get();
		}else {
			return null;
		}
	}
	
	public List<OwnedCoin> findBySymbol(String symbol, User owner) {
		Optional<List<OwnedCoin>> optionalOwnedCoin = ownedCoinRepo.findBySymbolIsAndOwnerIs(symbol, owner);
		if(optionalOwnedCoin.isPresent()) {
			return optionalOwnedCoin.get();
		}else {
			return null;
		}
	}
	
	public List<OwnedCoin> findByOwnerDesc(User owner) {
		Optional<List<OwnedCoin>> optionalOwnedCoin = ownedCoinRepo.findByOwnerIsOrderByIdDesc(owner);
		if(optionalOwnedCoin.isPresent()) {
			return optionalOwnedCoin.get();
		}else {
			return null;
		}
	}
	
	public List<OwnedCoin> customHistory(User owner, Integer amount) {
		return ownedCoinRepo.customHistory(owner.getId(), amount);
	}
	
}
