package com.tmdstudios.cls.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tmdstudios.cls.models.LoginUser;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.repositories.UserRepo;

@Service
public class UserService {

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
}