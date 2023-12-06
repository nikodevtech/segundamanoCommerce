package com.market.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.modelo.Compra;
import com.market.modelo.Producto;
import com.market.modelo.Usuario;

/**
 * Interfaz que define un repositorio para la entidad {@link Producto}. Extiende
 * la interfaz JpaRepository de Spring Data para realizar operaciones CRUD y
 * otras consultas relacionadas con la entidad Producto en la base de datos.
 */
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

	/**
	 * Encuentra todos los productos pertenecientes a un propietario específico.
	 *
	 * @param propietario El usuario propietario de los productos.
	 * @return Lista de productos pertenecientes al propietario especificado.
	 */
	List<Producto> findByPropietario(Usuario propietario);

	/**
	 * Encuentra todos los productos asociados a una compra específica.
	 *
	 * @param compra La compra a la que están asociados los productos.
	 * @return Lista de productos asociados a la compra especificada.
	 */
	List<Producto> findByCompra(Compra compra);

	/**
	 * Encuentra todos los productos que no están asociados a ninguna compra, es decir,
	 * estan todavia en venta y no se han vendido a nadie .
	 *
	 * @return Lista de productos que no tienen una compra asociada.
	 */
	List<Producto> findByCompraIsNull();

	/**
	 * Encuentra todos los productos que contienen un nombre específico y pertenecen
	 * a un propietario.
	 *
	 * @param nombre      El nombre que deben contener los productos.
	 * @param propietario El propietario de los productos.
	 * @return Lista de productos que cumplen con los criterios de búsqueda.
	 */
	List<Producto> findByNombreContainsIgnoreCaseAndPropietario(String nombre, Usuario propietario);

	/**
	 * Encuentra todos los productos que contienen un nombre específico y no están
	 * asociados a ninguna compra, es decir, estan todavia en venta.
	 *
	 * @param nombre      El nombre que deben contener los productos.
	 * @return Lista de productos que cumplen con los criterios de búsqueda.
	 */
	List<Producto> findByNombreContainsIgnoreCaseAndCompraIsNull(String nombre);
}
