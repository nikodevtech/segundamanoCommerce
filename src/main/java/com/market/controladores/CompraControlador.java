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
			for (int i = 0; i < productosCarrito.size(); i++) {
				total += productosCarrito.get(i).getPrecio();				
			}
			return total;
		}		
			
		return 0.0;
	}
	
	@ModelAttribute("mis_compras")
	public List<Compra> misCompras() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioServicio.buscarPorEmail(email);
		return compraServicio.porEmailPropietario(email);
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
	
	@GetMapping("misproductos")
	public String verMisProductos(Model model) {
		List<Producto> mis_productos = productoServicio.productosDeUnPropietario(usuario);
		model.addAttribute("misproductos", mis_productos);
		return "app/producto/lista";
	}
	
	
	
	
}
