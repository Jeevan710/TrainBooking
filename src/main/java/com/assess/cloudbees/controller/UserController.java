package com.assess.cloudbees.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assess.cloudbees.entity.User;
import com.assess.cloudbees.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/addUser")
	public void addUser()
	{
		userService.addUser();
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<?> getAllUsers()
	{
		List<User> users=userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
