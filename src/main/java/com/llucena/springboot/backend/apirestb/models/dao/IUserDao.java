package com.llucena.springboot.backend.apirestb.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.llucena.springboot.backend.apirestb.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>{
	
	public User findByUsername(String username);
	
	@Query("select u from User u where u.username=?1")
	public User findByUsername2(String username);
}
