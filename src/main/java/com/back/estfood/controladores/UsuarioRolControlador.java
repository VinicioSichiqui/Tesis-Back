package com.back.estfood.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.estfood.modelos.UsuarioRol;
import com.back.estfood.servicios.UsuarioRolServicio;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping ("api")
public class UsuarioRolControlador {
	@Autowired
	private UsuarioRolServicio usuarioRolServicio;
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	@GetMapping("usuarioRol")
	public ResponseEntity<?> listar(){
		
		List<UsuarioRol> listaUsuarioRol =  usuarioRolServicio.listar();
		
		if (listaUsuarioRol.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe UsuarioRoles", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "clientes", listaUsuarioRol);
	}
	
	@GetMapping("usuarioRol/{id}")
	public ResponseEntity<?> buscarUsuarioRolPorId(@PathVariable Long id) {

		UsuarioRol usuarioRol = usuarioRolServicio.buscarPorId(id);
		
		if (usuarioRol == null) {
			return respuestaAccion.datoNulo(false, "No existe ese Usuario Rol", "id invalido");
		}
		
		return respuestaAccion.accionCumplida(true, "Datos del usuarioRol", usuarioRol);
	}
	
	@PostMapping("usuarioRol")
	public ResponseEntity<?> guardarUsuarioRol(@RequestBody UsuarioRol usuarioRol) {
		UsuarioRol nuevoUsuarioRol = null;
		
		try {
			nuevoUsuarioRol = usuarioRolServicio.guardar(usuarioRol);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar el UsuarioRol",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		return respuestaAccion.accionCumplida(true, "UsuarioRol guardado", nuevoUsuarioRol);
	}
	
	@PutMapping("usuarioRol/{id}")
	public ResponseEntity<?> actualizarUsuarioRol(@RequestBody UsuarioRol usuarioRol, @PathVariable Long id) {
		
		UsuarioRol usuarioRolActual = usuarioRolServicio.buscarPorId(id);
		UsuarioRol usuarioRolActualizado = null;
		
		try {
			
			usuarioRolActual.setRol(usuarioRol.getRol());
			usuarioRolActual.setUsuario(usuarioRol.getUsuario());
			usuarioRolActualizado = usuarioRolServicio.guardar(usuarioRolActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el UsuarioRol",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "UsuarioRol actualizado", usuarioRolActualizado);
	}
	
	
	@DeleteMapping("usuarioRol/{id}")
	public ResponseEntity<?> borrarUsuarioRol(@PathVariable Long id){
		try {
			usuarioRolServicio.eliminar(id);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el UsuarioRol",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "UsuarioRol borrado", "borrado");
	}
}
