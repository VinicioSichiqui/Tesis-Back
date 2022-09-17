package com.back.estfood.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.estfood.modelos.TipoCliente;
import com.back.estfood.servicios.TipoClienteService;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TipoClienteControlador {
	@Autowired
	private TipoClienteService tipoClienteServicio;
	
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	@GetMapping("tipoCliente")
	public ResponseEntity<?> listar(){
		
		List<TipoCliente> listaTiposCliente= tipoClienteServicio.listar();
		
		if (listaTiposCliente.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe tipos de Clientes", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "Tipos de Clientes", listaTiposCliente);
	}
}
