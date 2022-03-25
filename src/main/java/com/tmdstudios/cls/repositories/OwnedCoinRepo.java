package com.tmdstudios.cls.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.cls.models.OwnedCoin;
import com.tmdstudios.cls.models.User;

@Repository
public interface OwnedCoinRepo extends CrudRepository<OwnedCoin, Long> {
	// make this find all by owner
	List<OwnedCoin> findAll();
	Optional<List<OwnedCoin>> findBySymbolIsAndOwnerIs(String symbol, User owner);
	OwnedCoin findByIdIs(Long id);
}
