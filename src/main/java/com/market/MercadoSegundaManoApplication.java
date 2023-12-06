package com.market;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.market.modelo.Producto;
import com.market.modelo.Usuario;
import com.market.servicios.ProductoServicio;
import com.market.servicios.UsuarioServicio;

@SpringBootApplication
public class MercadoSegundaManoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoSegundaManoApplication.class, args);
		
		
	}
	@Bean
	public CommandLineRunner initData(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
		return args -> {

			Usuario usuario = new Usuario("Luis Miguel", "López Magaña", null, "luismi.lopez@openwebinars.net", "luismi");
			usuario = usuarioServicio.registrar(usuario);

			Usuario usuario2 = new Usuario("Antonio", "García Martín", null, "antonio.garcia@openwebinars.net", "antonio");
			usuario2 = usuarioServicio.registrar(usuario2);

			
			List<Producto> listadoProductos = Arrays.asList(new Producto("Bicicleta de montaña", 100.0f,
					"https://www.radon-bikes.de/fileadmin/_processed_/csm_699040_275_7653bb0bbc.jpg", usuario),
					new Producto("Golf GTI Serie 2", 2500.0f,
							"https://www.minicar.es/large/Volkswagen-Golf-GTi-G60-Serie-II-%281990%29-Norev-1%3A18-i22889.jpg",
							usuario),
					new Producto("Raqueta de tenis", 10.5f, "https://m.media-amazon.com/images/S/aplus-media/vc/3aab386a-367a-4a72-b5fc-ab331d7cef84.__CR0,0,300,300_PT0_SX300_V1___.jpg", usuario),
					new Producto("Xbox Series X", 325.0f, "https://media.game.es/COVERV2/3D_L/P04/P04068.png", usuario2),
					new Producto("Trípode flexible", 10.0f, "https://swissgo.tech/content/files/swiss-pro_tripode-flexible-advance-10-300x300.jpg", usuario2),
					new Producto("Iphone 14 128 GB", 450.0f, "https://www.powerplanetonline.com/cdnassets/apple_iphone_14_pro_plata_01_m.jpg", usuario2));
			
			listadoProductos.forEach(productoServicio::insertar);
			

		};
	}

}
