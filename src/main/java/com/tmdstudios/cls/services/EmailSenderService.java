package com.tmdstudios.cls.services;

public interface EmailSenderService {
	void sendEmail(String to, String subject, String message);
}
