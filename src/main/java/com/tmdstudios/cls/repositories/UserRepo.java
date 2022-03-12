package com.tmdstudios.cls.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	List<User> findAll();
	Optional<User> findByUsername(String username);
	User findByIdIs(Long id);
	List<User> findAllByCoins(Coin coin);
	List<User> findByCoinsNotContains(Coin coin);
}