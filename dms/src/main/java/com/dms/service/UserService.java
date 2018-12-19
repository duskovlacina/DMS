package com.dms.service;

import java.util.List;

import com.dms.domain.User;

public interface UserService {
	
	User save(User user);
	
	User signIn(User user);
	
	void sendVerificationMail(User user);

	boolean verifyEmail(String email);
	
	User modifyUser(User user, String email);
	
	List<User> findAll();
	
	User findByEmail(String email);
	
}
