package com.dms.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dms.domain.User;
import com.dms.repository.UserRepository;
import com.dms.utils.CryptoUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Override
	public User save(User user) {
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing == null){
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}


	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User signIn(User user) {
		
		User user2 = userRepository.findByEmail(user.getEmail());
		if(user2 != null) {
			byte[] pwdHash = CryptoUtil.hashPassword(user.getPassword(), user2.getPasswordSalt());
			if(Arrays.equals(pwdHash, user2.getPasswordHash()) && user2.isVerified() == true) {
				return user2;
			}
		}
		return null;
	}

	@Override
	@Async
	public void sendVerificationMail(User user) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Link to account verification");
		mail.setText("Hello " + user.getName() + ",\n\n http://localhost:8085/api/users/verify/"+user.getEmail()+"");
		javaMailSender.send(mail);
	}


	@Override
	public boolean verifyEmail(String email) {
		User user = userRepository.findByEmail(email);
		user.setVerified(true);
		userRepository.save(user);
		return true;
	}

	@Override
	public User modifyUser(User user, String email) {
		
		User user2 = userRepository.findByEmail(user.getEmail());
			if(user.getEmail() != null){
				user2.setEmail(user2.getEmail());
			}
			if(user.getName() != null){
				user2.setName(user.getName());
			}
			if(user.getSurname()!=null){
				user2.setSurname(user.getSurname());
			}
			if(user.getPassword() != null){
				byte[] pwdHash = CryptoUtil.hashPassword(user.getPassword(), user2.getPasswordSalt());
				user2.setPasswordHash(pwdHash);
				user2.setPassword("");
			}
		
		return userRepository.save(user2);
	}


	

	
}
