package com.assess.cloudbees.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assess.cloudbees.DAO.UserDAO;
import com.assess.cloudbees.entity.User;
import com.assess.cloudbees.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;

	@Override
	public void addUser() {
		User user1=new User("Jee","R","j@gmail.com");
		User user2=new User("Jeevan","R","jeevan@gmail.com");
		User user3=new User("Arun","Ice","test@gmail.com");
		userDAO.save(user1);
		userDAO.save(user2);
		userDAO.save(user3);
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.findAll();
	}

	@Override
	public User getUserByMail(String mail) {
		User user=userDAO.findByMail(mail);
		if(user==null)
		{
			throw new ResourceNotFoundException("User not found");
		}
		return user;
	}

}
