package com.tmdstudios.cls.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
	
	@Query(value = "SELECT * FROM coins WHERE coin_rank <= 100 ORDER BY coin_rank DESC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop100desc();
	
	@Query(value = "SELECT * FROM coins ORDER BY name ASC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop100Name();
	
	@Query(value = "SELECT * FROM coins WHERE coin_rank <= 100 ORDER BY name DESC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop100NameDesc();
	
	@Query(value = "SELECT * FROM coins ORDER BY price ASC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop100Price();
	
	@Query(value = "SELECT * FROM coins WHERE coin_rank <= 100 ORDER BY price DESC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop100PriceDesc();
	
	@Query(value = "SELECT * FROM coins ORDER BY price_change_percentage1d ASC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop1001d();
	
	@Query(value = "SELECT * FROM coins WHERE coin_rank <= 100 ORDER BY price_change_percentage1d DESC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop1001dDesc();
	
	@Query(value = "SELECT * FROM coins ORDER BY price_change_percentage7d ASC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop1007d();
	
	@Query(value = "SELECT * FROM coins WHERE coin_rank <= 100 ORDER BY price_change_percentage7d DESC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop1007dDesc();
	
	@Query(value = "SELECT * FROM coins ORDER BY price_change_percentage30d ASC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop10030d();
	
	@Query(value = "SELECT * FROM coins WHERE coin_rank <= 100 ORDER BY price_change_percentage30d DESC LIMIT 100", nativeQuery = true) 
	List<Coin> getTop10030dDesc();
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.coin_rank", nativeQuery = true) 
	List<Coin> getUserCoins(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.coin_rank DESC", nativeQuery = true) 
	List<Coin> getUserCoinsDesc(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.name", nativeQuery = true) 
	List<Coin> getUserCoinsName(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.name DESC", nativeQuery = true) 
	List<Coin> getUserCoinsNameDesc(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price", nativeQuery = true) 
	List<Coin> getUserCoinsPrice(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price DESC", nativeQuery = true) 
	List<Coin> getUserCoinsPriceDesc(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price_change_percentage1d", nativeQuery = true) 
	List<Coin> getUserCoins1d(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price_change_percentage1d DESC", nativeQuery = true) 
	List<Coin> getUserCoins1dDesc(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price_change_percentage7d", nativeQuery = true) 
	List<Coin> getUserCoins7d(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price_change_percentage7d DESC", nativeQuery = true) 
	List<Coin> getUserCoins7dDesc(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price_change_percentage30d", nativeQuery = true) 
	List<Coin> getUserCoins30d(@Param("owner_id") Long ownerId);
	
	@Query(value = "SELECT * FROM coins INNER JOIN users_coins ON users_coins.coin_id = coins.id WHERE users_coins.user_id=:owner_id ORDER BY coins.price_change_percentage30d DESC", nativeQuery = true) 
	List<Coin> getUserCoins30dDesc(@Param("owner_id") Long ownerId);
}