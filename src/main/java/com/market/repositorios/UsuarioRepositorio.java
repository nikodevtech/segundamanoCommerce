package com.market.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.modelo.Usuario;

/**
 * Interfaz que define un repositorio para la entidad {@link Usuario}. Extiende
 * la interfaz JpaRepository de Spring Data para realizar operaciones CRUD y
 * otras consultas relacionadas con la entidad Usuario en la base de datos.
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	/**
	 * Encuentra al primer usuario que tiene la dirección de correo electrónico
	 * especificada.
	 *
	 * @param email La dirección de correo electrónico del usuario a buscar.
	 * @return El primer usuario encontrado con la dirección de correo electrónico
	 *         especificada.
	 */
	Usuario findFirstByEmail(String email);
}
