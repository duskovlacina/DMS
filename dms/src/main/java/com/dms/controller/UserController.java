package com.dms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dms.domain.User;
import com.dms.dto.UserDTO;
import com.dms.service.UserService;
import com.dms.utils.CryptoUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<UserDTO> registerUser(@RequestBody User user) throws Exception{
		
		byte[] salt = CryptoUtil.generateSalt();
		byte[] passwordHash = CryptoUtil.hashPassword(user.getPassword(), salt);
		user.setPasswordSalt(salt);
		user.setPasswordHash(passwordHash);
		
		user.setPassword("");
		
		User registerUser = userService.save(user);
		if(registerUser != null){
			User savedUser = userService.findByEmail(user.getEmail());
			userService.sendVerificationMail(savedUser);
			UserDTO userdto = new UserDTO(user);
			return new ResponseEntity<UserDTO>(userdto, HttpStatus.CREATED);		
		}
		UserDTO userDTO = null;
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/verify/{email}", method = RequestMethod.GET)
	public ResponseEntity<String> verifyUser(@PathVariable String email) throws Exception{
		userService.verifyEmail(email);
		return new ResponseEntity<String>("verified",HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<User> users = userService.findAll();
		List<UserDTO> usersdto = new ArrayList<UserDTO>();
		for(User user : users) {
			usersdto.add(new UserDTO(user));
		}
		return new ResponseEntity<List<UserDTO>>(usersdto,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{email}",method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> modifyUser(@RequestBody User user, @PathVariable String email){
		
		byte[] salt = CryptoUtil.generateSalt();
		byte[] passwordHash = CryptoUtil.hashPassword(user.getPassword(), salt);
		user.setPasswordSalt(salt);
		user.setPasswordHash(passwordHash);
		User modified = userService.modifyUser(user, email);
		return new ResponseEntity<UserDTO>(new UserDTO(modified),HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> signIn(@RequestBody User user,HttpSession session,HttpServletRequest request){	
		User loggedUser = userService.signIn(user);
		
			if(loggedUser != null ) {
			    HttpSession newSession = request.getSession();
			    newSession.setAttribute("loggedUser", loggedUser);
			    UserDTO logged = new UserDTO(loggedUser);
			    return new ResponseEntity<UserDTO>(logged,HttpStatus.OK);
			}
	
		UserDTO logged = null;
		return new ResponseEntity<UserDTO>(logged,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public ResponseEntity<String> signOut(HttpSession session, HttpServletRequest request){
		request.getSession().invalidate();
		return new ResponseEntity<String>("User logged out",HttpStatus.OK);
	}
	
	@RequestMapping(value="/isLoggedIn",method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getLoggedIn(HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if(logged!=null) {
			UserDTO loggedDTO = new UserDTO(logged);
			return new ResponseEntity<UserDTO>(loggedDTO,HttpStatus.OK);
		}
		UserDTO loggedDTO = null;
		return new ResponseEntity<UserDTO>(loggedDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/test/",
			method = RequestMethod.GET
			)
	public void Test() {
		System.out.println("testiraj");
	}
	

}
