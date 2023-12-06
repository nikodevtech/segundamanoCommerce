package com.market.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.modelo.Producto;
import com.market.servicios.ProductoServicio;

/**
 * Controlador para manejar las solicitudes relacionadas con la zona pública del sistema.
 * La ruta base para este controlador es "/public/".
 */
@Controller
@RequestMapping("/public/")
public class ZonaPublicaControlador {

    /**
     * Servicio de productos que se inyecta automáticamente.
     */
    @Autowired
    ProductoServicio productoServicio;

    /**
     * Método que se ejecuta antes de cada solicitud manejada por este controlador para agregar
     * la lista de productos no vendidos al modelo y tenerlos disponibles siempre en la vista.
     * @return Lista de productos no vendidos.
     */
    @ModelAttribute("productos")
    public List<Producto> productosNoVendidos() {		
        return productoServicio.productosSinVender();
    }

    /**
     * Maneja las solicitudes GET a las rutas "/" y "/index".
     * Muestra la página principal y, si se proporciona un parámetro de consulta 'q',
     * realiza una búsqueda de productos y los agrega al modelo.
     * @param model Modelo utilizado para enviar datos a la vista.
     * @param query Parámetro de consulta opcional para la búsqueda de productos.
     * @return Nombre de la vista que se mostrará al usuario ("index").
     */
    @GetMapping({"/","/index"})
    public String index(Model model, @RequestParam(name = "q", required = false) String query) {
        
        // Realiza una búsqueda si se proporciona un parámetro de consulta 'q'.
        if(query != null) {
            model.addAttribute("productos",productoServicio.buscar(query));
        } 
        
        return "index";	
    }

    /**
     * Maneja las solicitudes GET a la ruta "/producto/{id}".
     * Muestra detalles sobre un producto específico según el ID proporcionado.
     * Si el producto no se encuentra, redirige a la página principal.
     * @param id ID del producto a mostrar.
     * @param model Modelo utilizado para enviar datos a la vista.
     * @return Redirección a la página principal o nombre de la vista ("redirect:/public" o "index").
     */
    @GetMapping("/producto/{id}")
    public String showProduct(@PathVariable("id") Long id, Model model) {
        Producto p = productoServicio.findById(id);
        // Si el producto se encuentra, lo agrega al modelo, de lo contrario, redirige a la página principal.
        if(p != null) {
            model.addAttribute("producto",p);
        } 
        return "redirect:/public";
    }
}
