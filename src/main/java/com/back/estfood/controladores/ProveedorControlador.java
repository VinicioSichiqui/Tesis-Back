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

import com.back.estfood.modelos.Proveedor;
import com.back.estfood.servicios.ProveedorServicio;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProveedorControlador {
	
	@Autowired
	private ProveedorServicio proveedorServicio;
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	@GetMapping("proveedor")
	public ResponseEntity<?> listar(){
		
		List<Proveedor> listaProveedores= proveedorServicio.listar();
		
		if (listaProveedores.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe proveedores", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "proveedores", listaProveedores);
	}
	
	@GetMapping("proveedor/estado")
	public ResponseEntity<?> listarPorEstado(){
		
		List<Proveedor> listaProveedores= proveedorServicio.listarProveedoresPorEstado();
		
		if (listaProveedores.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe proveedores", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "proveedores", listaProveedores);
	}
	
	@GetMapping("/proveedor/{id}")
	public ResponseEntity<?> buscarProveedorPorId(@PathVariable Long id) {

		Proveedor proveedor = proveedorServicio.buscarPorId(id);
		
		if (proveedor == null) {
			return respuestaAccion.datoNulo(false, "No existe ese proveedor", "id invalido");
		}
		
		return respuestaAccion.accionCumplida(true, "Datos del proveedor", proveedor);
	}
	
	@GetMapping("proveedor/buscar/{termino}")
	public ResponseEntity<?> listar(@PathVariable String termino){
		
		List<Proveedor> listaProveedores= proveedorServicio.listarProveedoresPorTermino(termino);
		
		if (listaProveedores.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe proveedores", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "proveedores", listaProveedores);
	}
	
	@PostMapping("proveedor")
	public ResponseEntity<?> guardarProveedor(@RequestBody Proveedor proveedor) {
		Proveedor nuevoProveedor = null;
		
		// validamos que no exista el mismo codigo
		Proveedor provRuc = proveedorServicio.buscarPorRuc(proveedor.getRucProveedor());
		if (provRuc != null) {
			return respuestaAccion.datoDuplicado(false, "Hay un proveedor con ese ruc", "Código existente");
		}
		// creamos producto
		try {
			nuevoProveedor = proveedorServicio.guardar(proveedor);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar el proveedor",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		return respuestaAccion.accionCumplida(true, "Proveedor guardado", nuevoProveedor);
	}
	
	@PutMapping("proveedor/{id}")
	public ResponseEntity<?> actualizarProveedor(@RequestBody Proveedor proveedor, @PathVariable Long id) {
		
		Proveedor provActual = proveedorServicio.buscarPorId(id);
		Proveedor provActualizado = null;
		
		// si el prov no existe
		if (provActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}
		// si existe otro proveedor con el mismo codigo
		Proveedor provCodigo = proveedorServicio.buscarPorRuc(proveedor.getRucProveedor());
		if (provCodigo != null && provCodigo.getIdProveedor() != id) {
			return respuestaAccion.datoDuplicado(false, "Ya existe un proveedor con ese ruc", "Ruc existente");
		}
		
		try {
			
			provActual.setNombreProveedor(proveedor.getNombreProveedor());
			provActual.setRucProveedor(proveedor.getRucProveedor());
			provActual.setDireccionProveedor(proveedor.getDireccionProveedor());
			provActual.setEmailProveedor(proveedor.getEmailProveedor());
			provActual.setMovilProveedor(proveedor.getMovilProveedor());
			provActual.setTelefonoProveedor(proveedor.getTelefonoProveedor());
			provActual.setDetalleProveedor(proveedor.getDetalleProveedor());
			provActualizado = proveedorServicio.guardar(provActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el proveedor",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Proveedor actualizado", provActualizado);
	}
	
	@PutMapping("/proveedor/estado/{id}")
	public ResponseEntity<?> actualizarEstadoProveedor(@PathVariable Long id) {
		
		Proveedor provActual = proveedorServicio.buscarPorId(id);
		Proveedor provActualizado = null;
		
		// si el prov no existe
		if (provActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}

		try {
			provActual.setEstadoProveedor(!provActual.getEstadoProveedor());
			provActualizado = proveedorServicio.guardar(provActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el proveedor",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Estado proveedor", provActualizado);
	}
	
	@DeleteMapping("proveedor/{id}")
	public ResponseEntity<?> borrarProveedor(@PathVariable Long id){
		try {
			proveedorServicio.eliminar(id);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el proveedor",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Proveedor borrado", "borrado");
	}
}
