package com.tmdstudios.cls.repositories;

import java.util.List;
import java.util.Optional;

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
}