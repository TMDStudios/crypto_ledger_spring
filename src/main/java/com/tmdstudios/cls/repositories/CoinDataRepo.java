package com.tmdstudios.cls.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.cls.models.CoinData;

@Repository
public interface CoinDataRepo extends CrudRepository<CoinData, Long> {
	List<CoinData> findAll();
}
