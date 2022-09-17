package com.back.estfood.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.estfood.modelos.Rol;
import com.back.estfood.servicios.RolServicio;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class RolControlador {
	@Autowired
	private RolServicio rolServicio;
	
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	@GetMapping("rol")
	public ResponseEntity<?> listar(){
		
		List<Rol> listaRoles = rolServicio.listar();
		
		if (listaRoles.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe roles", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "Roles", listaRoles);
	}
}
