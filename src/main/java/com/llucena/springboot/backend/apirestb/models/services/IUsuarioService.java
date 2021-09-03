package com.llucena.springboot.backend.apirestb.models.services;

import com.llucena.springboot.backend.apirestb.models.entity.Usuario;

public interface IUsuarioService {
	public Usuario findByUsername(String username);
}
