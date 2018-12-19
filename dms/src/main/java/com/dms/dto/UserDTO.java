package com.dms.dto;

import com.dms.domain.User;
import com.dms.domain.UserType;

public class UserDTO {
	private Long id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private UserType usertype;
	private byte[] passwordHash;
	private byte[] passwordSalt;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.usertype = user.getUsertype();
		this.passwordHash = user.getPasswordHash();
		this.passwordSalt = user.getPasswordSalt();
	}

	
	
	
	public byte[] getPasswordHash() {
		return passwordHash;
	}




	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}




	public byte[] getPasswordSalt() {
		return passwordSalt;
	}




	public void setPasswordSalt(byte[] passwordSalt) {
		this.passwordSalt = passwordSalt;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}
	
	
	

}
