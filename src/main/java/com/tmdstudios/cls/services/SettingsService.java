package com.tmdstudios.cls.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.cls.models.Settings;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.repositories.SettingsRepo;

@Service
public class SettingsService {
	@Autowired
	private SettingsRepo settingsRepo;
	
	public Settings findByUser(User user) {
		return settingsRepo.findByUserIs(user);
	}
	
	public Settings updateSettings(Settings settings) {
		return settingsRepo.save(settings);
	}
}
