package com.back.estfood.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.estfood.modelos.FormaPago;
import com.back.estfood.servicios.FormaPagoServicio;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class FormaPagoControlador {
	@Autowired
	private FormaPagoServicio formaPagoServicio;
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	@GetMapping("formaPago")
	public ResponseEntity<?> listar(){
		
		List<FormaPago> listaFormasPago= formaPagoServicio.listar();
		
		if (listaFormasPago.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe formas de pago", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "Formas de Pago", listaFormasPago);
	}
}
