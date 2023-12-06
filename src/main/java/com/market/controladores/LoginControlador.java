package com.market.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.market.modelo.Usuario;
import com.market.servicios.UsuarioServicio;
/**
 * Clase controladora de la vista de login/registro para gestionar las 
 * solicitudes relacionadas con la autenticación y registro de usuarios.
 */
@Controller
public class LoginControlador {
	
	@Autowired
	UsuarioServicio usuarioService;
	
	/**
     * Redirecciona a la página principal del sistema.
     *
     * @return La vista de la página principal.
     */
	@GetMapping("/")
	public String welcome() {
		return "redirect:/public/";
	}
	
	/**
     * Muestra la página de inicio de sesión (login).
     *
     * @param model El modelo que se utiliza para enviar datos a la vista.
     * @return La vista de inicio de sesión.
     */
    @GetMapping("/auth/login")
    public String login(Model model) {
        // Se agrega un nuevo objeto Usuario al modelo para el formulario de inicio de sesión.
        model.addAttribute("usuario", new Usuario());
        return "login";
    }
	
    /**
     * Maneja la solicitud de registro de un nuevo usuario.
     *
     * @param usuario El objeto Usuario que contiene los datos del nuevo usuario.
     * @return Redirige a la página de inicio de sesión después de registrar al nuevo usuario.
     */
    @PostMapping("/auth/register")
    public String register(@ModelAttribute Usuario usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/auth/login";
    }

}
