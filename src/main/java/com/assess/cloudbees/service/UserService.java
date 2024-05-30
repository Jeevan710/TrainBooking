package com.assess.cloudbees.service;

import java.util.List;

import com.assess.cloudbees.entity.User;

public interface UserService {

	void addUser();

	List<User> getAllUsers();

	User getUserByMail(String mail);

}
