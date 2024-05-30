package com.assess.cloudbees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assess.cloudbees.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User,Long>{

	User findByMail(String mail);

}
