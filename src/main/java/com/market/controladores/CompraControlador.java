package com.market.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.modelo.Compra;
import com.market.modelo.Producto;
import com.market.modelo.Usuario;
import com.market.servicios.CompraServicio;
import com.market.servicios.ProductoServicio;
import com.market.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class CompraControlador {
	
	@Autowired
	CompraServicio compraServicio;
	
	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	ProductoServicio productoServicio;

	@Autowired
	HttpSession session;
	
	private Usuario usuario;
	
	
	@ModelAttribute("carrito") //Adjunta al modelo el resultado de este metodo.
	public List<Producto> productosCarrito() {
		
		List<Long> contenidoCarrito = (List<Long>) session.getAttribute("carrito");
		
		if(contenidoCarrito == null) {
			return null;
		} else {
			return productoServicio.variosPorId(contenidoCarrito);
		}	
		
	}
	
	@ModelAttribute("total_carrito")
	public Double totalCarrito() {
		List<Producto> productosCarrito = productosCarrito();
		if (productosCarrito != null) {
			
			Double total = 0.0;
			for (Producto p : productosCarrito) {
				total += p.getPrecio();				
			}
			return total;
		}		
			
		return 0.0;
	}
	

	
	@GetMapping("/carrito")
	public String verCarrito(Model model) {
		return "app/compra/carrito";
	}
	
	@GetMapping("/carrito/add/{id}")
	public String addCarrito(Model model, @PathVariable Long id) {
		List<Long> contenidoCarrito = (List<Long>) session.getAttribute("carrito");
		if (contenidoCarrito == null)
			contenidoCarrito = new ArrayList<>();
		if (!contenidoCarrito.contains(id))
			contenidoCarrito.add(id);
		session.setAttribute("carrito", contenidoCarrito);
		return "redirect:/app/carrito";
	}
	
	@GetMapping("/carrito/eliminar/{id}")
	public String borrarDeCarrito(Model model, @PathVariable Long id) {
		List<Long> contenidoCarrito = (List<Long>) session.getAttribute("carrito");
		if (contenidoCarrito == null)
			return "redirect:/public";
		contenidoCarrito.remove(id);
		if (contenidoCarrito.isEmpty())
			session.removeAttribute("carrito");
		else
			session.setAttribute("carrito", contenidoCarrito);
		return "redirect:/app/carrito";
		
	}
	

	@GetMapping("/carrito/finalizar")
	public String checkout() {
		List<Long> contenidoCarrito = (List<Long>) session.getAttribute("carrito");
		if (contenidoCarrito == null)
			return "redirect:/public";
		
		List<Producto> productos = productosCarrito();
		
		Compra c = compraServicio.insertar(new Compra(), usuario);
		
		productos.forEach(p -> compraServicio.addProductoCompra(p, c));
		session.removeAttribute("carrito");
		
		return "redirect:/app/compra/factura/"+c.getId();
		
	}
	
	@GetMapping("/compra/factura/{id}")
	public String factura(Model model, @PathVariable Long id) {
		Compra c = compraServicio.buscarPorId(id);
		List<Producto> productosCompra = productoServicio.productosDeUnaCompra(c);
		model.addAttribute("productos", productosCompra);
		model.addAttribute("compra", c);
		Double total = 0.0;
		for (Producto p : productosCompra) {
			total += p.getPrecio();				
		}
		model.addAttribute("total_compra", total);
		return "/app/compra/factura";
	}
	
	@ModelAttribute("mis_compras")
	public List<Compra> misCompras() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioServicio.buscarPorEmail(email);
		return compraServicio.porEmailPropietario(email);
	}
	
	@GetMapping("/miscompras")
	public String verMisCompras(Model model) {
		return "/app/compra/listado";
	}
	
	
}
