package com.tmdstudios.cls.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tmdstudios.cls.models.OwnedCoin;
import com.tmdstudios.cls.models.User;

@Repository
public interface OwnedCoinRepo extends CrudRepository<OwnedCoin, Long> {
	// make this find all by owner
	List<OwnedCoin> findAll();
	Optional<List<OwnedCoin>> findBySymbolIsAndOwnerIs(String symbol, User owner);
	Optional<List<OwnedCoin>> findByOwnerIsOrderByIdDesc(User owner);
	OwnedCoin findByIdIs(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM owned_coins WHERE user_owned_coin_id = :owner_id", nativeQuery = true) 
	void deleteAllFromOwner(@Param("owner_id") Long ownerId);
}
