package com.tmdstudios.cls.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tmdstudios.cls.models.LoginUser;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.repositories.UserRepo;

@Service
public class UserService {
	
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	@Autowired
	UserRepo userRepo;
	
	public User register(User newUser, BindingResult result) {
		// Reject if username is taken (present in database)
    	Optional<User> potentialUser = userRepo.findByUsername(newUser.getUsername());

    	if(potentialUser.isPresent()) {
    		result.rejectValue("username", "UsernameTaken", "An account with that username already exists!");
    	}
        // Reject if password doesn't match confirmation
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}
    	
    	if(result.hasErrors()) {
	        return null;
	    }
    
        // Hash and set password, save user to database
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	return userRepo.save(newUser);
	}
	
	public User login(LoginUser newLogin, BindingResult result) {
    	// TO-DO - Reject values:
    	Optional<User> potentialUser = userRepo.findByUsername(newLogin.getUsername());
        
    	// Find user in the DB by username
        // Reject if NOT present
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("username", "Matches", "User not found!");
    		return null;
    	}
        
        // Reject if BCrypt password match fails
    	if(!BCrypt.checkpw(newLogin.getPassword(), potentialUser.get().getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	    return null;
    	}
    	
        // Otherwise, return the user object
        return potentialUser.get();
    }
	
	public String forgotPassword(String email) {
		Optional<User> optionalUser = userRepo.findByEmail(email);
		if(!optionalUser.isPresent()) {
			return "Invalid email";
		}
		
		User user = optionalUser.get();
		user.setToken(generateToken());
		user.setTokenCreationDate(LocalDateTime.now());
		user.setConfirm(user.getPassword());
		user = userRepo.save(user);
		
		return user.getToken();
	}
	
	public String resetPassword(String token, String password) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		if(!optionalUser.isPresent()) {
			return "Invalid token";
		}
		
		LocalDateTime tokenCreationDate = optionalUser.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";
		}
		
		User user = optionalUser.get();

		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(hashed);
		user.setConfirm(password);
		user.setToken(null);
		user.setTokenCreationDate(null);

		userRepo.save(user);

		return "Your password has been updated.";
	}
	
	public String generateApiKey(User user) {
		String apiKey = UUID.randomUUID().toString();
		user.setConfirm(user.getPassword());
		user.setApiKey(apiKey);
		userRepo.save(user);
		return apiKey;
	}
	
	private String generateToken() {
		StringBuilder token = new StringBuilder();
		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationTime) {
		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationTime, now);
		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
	
	public List<User> allUsers(){
		return userRepo.findAll();
	}
	
	public User updateUser(User user) {
		return userRepo.save(user);
	}
	
	public User findById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
	
	public User findByToken(String token) {
		Optional<User> optionalUser = userRepo.findByToken(token);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
	
	public User findByApiKey(String apiKey) {
		Optional<User> optionalUser = userRepo.findByApiKey(apiKey);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
}