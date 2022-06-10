package com.tmdstudios.cls.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="settings")
public class Settings {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Boolean darkMode = true;
	private Integer historyLength = 5;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public Settings() {}
	
	public Settings(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getDarkMode() {
		return darkMode;
	}

	public void setDarkMode(Boolean darkMode) {
		this.darkMode = darkMode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getHistoryLength() {
		return historyLength;
	}

	public void setHistoryLength(Integer historyLength) {
		this.historyLength = historyLength;
	}
	
}
