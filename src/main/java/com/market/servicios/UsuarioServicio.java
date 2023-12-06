package com.market.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.market.modelo.Usuario;
import com.market.repositorios.UsuarioRepositorio;

/**
 * Servicio para la entidad {@link Usuario}.
 */
@Service
public class UsuarioServicio {

	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//Registra el usuario en la base de datos con el password encriptado
	public Usuario registrar(Usuario u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return repositorio.save(u); 
	}
	
	public Usuario findById(long id) {
		return repositorio.findById(id).orElse(null);
	}
	
	public Usuario buscarPorEmail(String email) {
		return repositorio.findFirstByEmail(email);
	}
}
