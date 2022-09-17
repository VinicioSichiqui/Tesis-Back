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

import com.back.estfood.modelos.Cliente;
import com.back.estfood.servicios.ClienteServicio;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ClienteControlador {
	
	@Autowired
	private ClienteServicio clienteServicio;
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	@GetMapping("cliente")
	public ResponseEntity<?> listar(){
		
		List<Cliente> listaClientes= clienteServicio.listar();
		
		if (listaClientes.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe clientes", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "clientes", listaClientes);
	}
	
	@GetMapping("cliente/estado")
	public ResponseEntity<?> listarPorEstado(){
		
		List<Cliente> listaClintes= clienteServicio.listarClientesPorEstado();
		
		if (listaClintes.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe clientes", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "clientes", listaClintes);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {

		Cliente cliente = clienteServicio.buscarPorId(id);
		
		if (cliente == null) {
			return respuestaAccion.datoNulo(false, "No existe ese cliente", "id invalido");
		}
		
		return respuestaAccion.accionCumplida(true, "Datos del cliente", cliente);
	}
	
	@GetMapping("cliente/buscar/{termino}")
	public ResponseEntity<?> listar(@PathVariable String termino){
		
		List<Cliente> listaClientes= clienteServicio.listarClientesPorTermino(termino);
		
		if (listaClientes.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe clientes", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "clientes", listaClientes);
	}
	
	@PostMapping("cliente")
	public ResponseEntity<?> guardarCliente(@RequestBody Cliente cliente) {
		Cliente nuevoCliente = null;
		
		// validamos que no exista el misma cedula
		Cliente clienteCedula = clienteServicio.buscarPorCedula(cliente.getPersona().getCedulaPersona());
		if (clienteCedula != null) {
			return respuestaAccion.datoDuplicado(false, "Hay un cliente con esa cédula", "Cédula existente");
		}
		// creamos cliente
		try {
			nuevoCliente = clienteServicio.guardar(cliente);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar el cliente",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		return respuestaAccion.accionCumplida(true, "Cliente guardado", nuevoCliente);
	}
	
	@PutMapping("cliente/{id}")
	public ResponseEntity<?> actualizarCliente(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		Cliente clienteActual = clienteServicio.buscarPorId(id);
		Cliente clienteActualizado = null;
		
		// si el cliente no existe
		if (clienteActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}
		// si existe otro cliente con la misma cedula
		Cliente clienteCodigo = clienteServicio.buscarPorCedula(cliente.getPersona().getCedulaPersona());
		if (clienteCodigo != null && clienteCodigo.getIdCliente() != id) {
			return respuestaAccion.datoDuplicado(false, "Ya existe un cliente con esa cédula", "Cédula existente");
		}
		
		try {
			
			clienteActual.setTelefonoCliente(cliente.getTelefonoCliente());
			clienteActual.setTipoCliente(cliente.getTipoCliente());
			clienteActual.setPersona(cliente.getPersona());
			clienteActualizado = clienteServicio.guardar(clienteActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el cliente",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Cliente actualizado", clienteActualizado);
	}
	
	@PutMapping("/cliente/estado/{id}")
	public ResponseEntity<?> actualizarEstadoCliente(@PathVariable Long id) {
		
		Cliente clienteActual = clienteServicio.buscarPorId(id);
		Cliente clienteActualizado = null;
		
		// si el cliente no existe
		if (clienteActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}

		try {
			clienteActual.setEstadoCliente(!clienteActual.getEstadoCliente());
			clienteActualizado = clienteServicio.guardar(clienteActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el cliente",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Estado cliente", clienteActualizado);
	}
	
	@DeleteMapping("cliente/{id}")
	public ResponseEntity<?> borrarCliente(@PathVariable Long id){
		try {
			clienteServicio.eliminar(id);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el cliente",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Cliente borrado", "borrado");
	}
}
