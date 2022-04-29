package com.tmdstudios.cls.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.User;

@Repository
public interface CoinRepo extends CrudRepository<Coin, Long> {
	List<Coin> findAllByOrderByCoinRankAsc();
	Optional<Coin> findBySymbol(String symbol);
	Coin findByIdIs(Long id);
	List<Coin> findAllByUsers(User user);
	List<Coin> findByUsersNotContains(User user);
	
	Optional<Coin> findByCoinRank(Long coinRank);
	
	@Query(value = "SELECT * FROM coins ORDER BY coin_rank ASC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop100();
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=1 ORDER BY coins.coin_rank", nativeQuery = true) 
	List<Coin> getUserCoins(User user);
}