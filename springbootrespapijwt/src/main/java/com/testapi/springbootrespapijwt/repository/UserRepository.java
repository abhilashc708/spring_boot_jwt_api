package com.testapi.springbootrespapijwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testapi.springbootrespapijwt.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findOneByUsername(String username);

}
