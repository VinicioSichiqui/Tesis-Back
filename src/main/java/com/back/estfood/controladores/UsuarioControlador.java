package com.back.estfood.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.estfood.modelos.Usuario;
import com.back.estfood.servicios.UsuarioService;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioService usuarioServicio;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("usuario")
	public ResponseEntity<?> listar(){
		
		List<Usuario> listaUsuarios = usuarioServicio.listar();
		listaUsuarios = listaUsuarios.stream().map(usuario -> {
			usuario.setPasswordUsuario("");
			return usuario;
		}).collect(Collectors.toList());
		
		if (listaUsuarios.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe usuarios", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "usuarios", listaUsuarios);
	}
	
	@GetMapping("usuario/estado")
	public ResponseEntity<?> listarPorEstado(){
		
		List<Usuario> listaUsuarios = usuarioServicio.listarUsuariosPorEstado();
		listaUsuarios = listaUsuarios.stream().map(usuario -> {
			usuario.setPasswordUsuario("");
			return usuario;
		}).collect(Collectors.toList());
		if (listaUsuarios.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe usuarios", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "usuarios", listaUsuarios);
	}
	
	@GetMapping("usuario/{id}")
	public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {

		Usuario usuario = usuarioServicio.buscarPorId(id);
		if (usuario == null) {
			return respuestaAccion.datoNulo(false, "No existe ese usuario", "id invalido");
		}
		usuario.setPasswordUsuario("");
		
		return respuestaAccion.accionCumplida(true, "Datos del usuario", usuario);
	}
	
	@GetMapping("usuario/buscar/{termino}")
	public ResponseEntity<?> listar(@PathVariable String termino){
		
		List<Usuario> listaUsuarios = usuarioServicio.listarUsuariosPorTermino(termino);
		if (listaUsuarios.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe usuarios", "tabla vacía");
		}
		listaUsuarios = listaUsuarios.stream().map(usuario -> {
			usuario.setPasswordUsuario("");
			return usuario;
		}).collect(Collectors.toList());
		
		return respuestaAccion.accionCumplida(true, "usuarios", listaUsuarios);
	}
	
	@PostMapping("usuario")
	public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = null;
		
		// validamos que no exista la misma cedula
		Usuario usuarioCedula = usuarioServicio.buscarPorCedula(usuario.getPersona().getCedulaPersona());
		if (usuarioCedula != null) {
			return respuestaAccion.datoDuplicado(false, "Hay un usuario con esa cédula", "Cédula existente");
		}
		// creamos usuario
		try {
			usuario.setPasswordUsuario(passwordEncoder.encode(usuario.getPasswordUsuario()));
			nuevoUsuario = usuarioServicio.guardar(usuario);
			nuevoUsuario.setPasswordUsuario("");
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar el usuario",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		return respuestaAccion.accionCumplida(true, "Usuario guardado", nuevoUsuario);
	}
	
	@PutMapping("usuario/{id}")
	public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		
		Usuario usuarioActual = usuarioServicio.buscarPorId(id);
		Usuario usuarioActualizado = null;
		
		// si el usuario no existe
		if (usuarioActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}
		// si existe otro usuario con la misma cedula
		Usuario usuarioCodigo = usuarioServicio.buscarPorCedula(usuario.getPersona().getCedulaPersona());
		if (usuarioCodigo != null && usuarioCodigo.getIdUsuario() != id) {
			return respuestaAccion.datoDuplicado(false, "Ya existe un usuario con esa cédula", "Cédula existente");
		}
		
		try {
			if(usuario.getPasswordUsuario() != null && usuario.getAnteriorPassword() != null) {
				if(usuario.getPasswordUsuario().length()>0 && usuario.getAnteriorPassword().length() > 0) {
					if(passwordEncoder.matches(usuario.getAnteriorPassword(), usuarioActual.getPasswordUsuario())) {
						// codificación de la password
						usuarioActual.setPasswordUsuario(passwordEncoder.encode(usuario.getPasswordUsuario()));
					} else {
						return respuestaAccion.accionIncumplida(false, "Contraseña anterior no coincide", "Datos erróneos");
					}
				}
			}
			usuarioActual.setPersona(usuario.getPersona());
			usuarioActualizado = usuarioServicio.guardar(usuarioActual);
			usuarioActualizado.setPasswordUsuario("");
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el usuario",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Usuario actualizado", usuarioActualizado);
	}
	
	@PutMapping("/usuario/estado/{id}")
	public ResponseEntity<?> actualizarEstadoUsuario(@PathVariable Long id) {
		
		Usuario usuarioActual = usuarioServicio.buscarPorId(id);
		Usuario usuarioActualizado = null;
		
		// si el usuario no existe
		if (usuarioActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}

		try {
			usuarioActual.setEstadoUsuario(!usuarioActual.getEstadoUsuario());
			usuarioActualizado = usuarioServicio.guardar(usuarioActual);
			usuarioActualizado.setPasswordUsuario("");
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el usuario",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Estado usuario", usuarioActualizado);
	}
	
	@DeleteMapping("usuario/{id}")
	public ResponseEntity<?> borrarUsuario(@PathVariable Long id){
		try {
			usuarioServicio.eliminar(id);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el usuario",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Usuario borrado", "borrado");
	}
}
