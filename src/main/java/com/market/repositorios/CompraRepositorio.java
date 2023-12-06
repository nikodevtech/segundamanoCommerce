package com.market.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.modelo.Compra;
import com.market.modelo.Usuario;

/**
 * Interfaz que define un repositorio para la entidad {@link Compra}. Extiende
 * la interfaz JpaRepository de Spring Data para realizar operaciones CRUD y
 * otras consultas relacionadas con la entidad Compra en la base de datos.
 */
public interface CompraRepositorio extends JpaRepository<Compra, Long> {

	/**
	 * Encuentra todas las compras pertenecientes a un propietario específico.
	 *
	 * @param propietario El usuario propietario de las compras.
	 * @return Lista de compras pertenecientes al propietario especificado.
	 */
	List<Compra> findByPropietarioDeLaCompra(Usuario propietario);
}
