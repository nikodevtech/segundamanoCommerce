package com.market.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.modelo.Producto;
import com.market.modelo.Usuario;
import com.market.servicios.ProductoServicio;
import com.market.servicios.UsuarioServicio;

@Controller
@RequestMapping("/app")
public class ProductosController {

	@Autowired
	ProductoServicio productoServicio;
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	private Usuario usuario;
	
	
	@ModelAttribute("misproductos")
	public List<Producto> misProductos() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioServicio.buscarPorEmail(email);
		return productoServicio.productosDeUnPropietario(usuario);
	}
	
	@GetMapping("/misproductos")
	public String list(Model model, @RequestParam(name = "q", required = false) String query) {
		if(query != null) {
			model.addAttribute("misproductos", productoServicio.buscarMisProductos(query, usuario));
		}
		return "app/producto/lista";
		
	}
	
	@GetMapping("/misproductos/{id}/eliminar")
	public String borrar(@PathVariable Long id) {
		Producto p = productoServicio.findById(id);
		if(p.getCompra() == null) {
			productoServicio.borrar(id);
		}
		return "redirect:/app/misproductos";	
	}
	
	@GetMapping("/producto/nuevo")
	public String nuevoProductoForm(Model model) {
		model.addAttribute("producto", new Producto());
		return "app/producto/form";
	}
	
	@PostMapping("/producto/nuevo/submit")
	public String nuevoProductoSubmit(@ModelAttribute Producto p) {
		p.setPropietario(usuario);
		productoServicio.insertar(p);
		return "redirect:/app/misproductos";
		
	}
	
}
