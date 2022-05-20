package com.tmdstudios.cls.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.cls.models.Settings;
import com.tmdstudios.cls.models.User;

@Repository
public interface SettingsRepo extends CrudRepository<Settings, Long> {
	Settings findByUserIs(User user);
}
