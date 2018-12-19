package com.dms;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dms.service.UserService;


@Component
public class DmsTestData {

	@Autowired
	private UserService userservice;
	
	@PostConstruct
	private void init() throws ParseException{
		
		if(userservice.findAll().size() == 0) {
		/*
			User user1 = new User("dusko@gmail.com","duka","Dusko","Vlacina",true,UserType.REGULAR);
			userservice.save(user1);
			
			User user2 = new User("admin@gmail.com","admin","admin","admin",false,UserType.ADMIN);
			userservice.save(user2);
		*/	
		}
	} 
}
