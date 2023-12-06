package com.market.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.modelo.Compra;
import com.market.modelo.Producto;
import com.market.modelo.Usuario;
import com.market.repositorios.CompraRepositorio;

/**
 * Servicio para la entidad {@link Compra}.
 */
@Service
public class CompraServicio {
	
	@Autowired
	CompraRepositorio repositorio;
	
	@Autowired
	ProductoServicio productoServicio;
	
	public Compra insertar(Compra c, Usuario propietario) {
		c.setPropietarioDeLaCompra(propietario); // Setea el propietario de la compra
		return repositorio.save(c);
	}
	
	public Compra insertar(Compra c) {
		return repositorio.save(c);
	}
	
	// Establece un producto a una Compra, o lo que es lo mismo,
	// dada una compra añade nuevos productos a ella
	public Producto addProductoCompra(Producto p, Compra c) {
		p.setCompra(c);
		return productoServicio.editar(p);
	}
	
	public Compra buscarPorId(long id) {
		return repositorio.findById(id).orElse(null);
	}
	
	public List<Compra> todas() {
		return repositorio.findAll();
	}
	
	public List<Compra> porPropietario(Usuario u) {
		return repositorio.findByPropietarioDeLaCompra(u);
	}

}
