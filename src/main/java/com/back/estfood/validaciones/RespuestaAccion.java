package com.back.estfood.validaciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
/*
 * Regresan un ResponseEntity con un HttpStatus segun la accion realizada
 * 
 */
@Service
public class RespuestaAccion {
	
	public ResponseEntity<?> errorBD(Boolean ok, String mensaje, String error ){
		Map<String, Object> response = new HashMap<>();
		response.put("ok", ok);
		response.put("mensaje", mensaje);
		response.put("error", error);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<?> listaDatosVacia(Boolean ok, String mensaje, Object datos ){
		Map<String, Object> response = new HashMap<>();
		response.put("ok", ok);
		response.put("mensaje", mensaje);
		response.put("respuesta", datos);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
	}
	public ResponseEntity<?> accionCumplida(Boolean ok, String mensaje, Object datos ){
		Map<String, Object> response = new HashMap<>();
		response.put("ok", ok);
		response.put("mensaje", mensaje);
		response.put("respuesta", datos);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	public ResponseEntity<?> accionIncumplida(Boolean ok, String mensaje, Object error ){
		Map<String, Object> response = new HashMap<>();
		response.put("ok", ok);
		response.put("mensaje", mensaje);
		response.put("error", error);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<?> datoNulo(Boolean ok, String mensaje, String error ){
		Map<String, Object> response = new HashMap<>();
		response.put("ok", ok);
		response.put("mensaje", mensaje);
		response.put("error", error);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
	}
	public ResponseEntity<?> datoDuplicado(Boolean ok, String mensaje, String error ){
		Map<String, Object> response = new HashMap<>();
		response.put("ok", ok);
		response.put("mensaje", mensaje);
		response.put("error", error);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CONFLICT);
	}
}
