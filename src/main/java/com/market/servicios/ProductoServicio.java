package com.market.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.modelo.Compra;
import com.market.modelo.Producto;
import com.market.modelo.Usuario;
import com.market.repositorios.ProductoRepositorio;

/**
 * Servicio para la entidad {@link Producto}.
 */
@Service
public class ProductoServicio {
	
	@Autowired
	private ProductoRepositorio repositorio;


	//CRUD SIMPLE
	public Producto insertar(Producto p) {
		return repositorio.save(p);
	}
	
	public void borrar(Producto p) {
		repositorio.delete(p);
	}
	
	public void borrar(long id) {
		repositorio.deleteById(id);
	}	
	
	public Producto editar(Producto p) {
		return repositorio.save(p);
	}
	
	public Producto findById(long id) {
		return repositorio.findById(id).orElse(null);
	}
	
	public List<Producto> findAll() {
		return repositorio.findAll();
	}
	
	public List<Producto> variosPorId(List<Long> ids) {
		return repositorio.findAllById(ids);
	}
		
	
	//CONSULTAS DE PRODUCTOS POR FILTROS PERSONALIZADOS
	public List<Producto> productosDeUnPropietario(Usuario u) {
		return repositorio.findByPropietario(u);
	}
	
	public List<Producto> productosDeUnaCompra(Compra c) {
		return repositorio.findByCompra(c);
	}
	
	public List<Producto> productosSinVender() {
		return repositorio.findByCompraIsNull();
	}
	
	public List<Producto> buscar(String query) {
		return repositorio.findByNombreContainsIgnoreCaseAndCompraIsNull(query);
	}
	
	public List<Producto> buscarMisProductos(String query, Usuario u) {
		return repositorio.findByNombreContainsIgnoreCaseAndPropietario(query,u);
	}
	

}
