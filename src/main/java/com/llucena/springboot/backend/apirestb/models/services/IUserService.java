package com.llucena.springboot.backend.apirestb.models.services;

import com.llucena.springboot.backend.apirestb.models.entity.User;

public interface IUserService {
	public User findByUsername(String username);
}
