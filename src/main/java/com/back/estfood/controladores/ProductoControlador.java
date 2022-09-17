package com.back.estfood.controladores;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.back.estfood.modelos.Producto;
import com.back.estfood.servicios.CloudinaryServicio;
import com.back.estfood.servicios.ProductoServicio;
import com.back.estfood.validaciones.RespuestaAccion;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductoControlador {
	
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private CloudinaryServicio cloudinaryServicio;
	
	@Autowired
	private RespuestaAccion respuestaAccion;
	
	
	@GetMapping("producto")
	public ResponseEntity<?> listar(){
		
		List<Producto> listaProductos= productoServicio.listar();
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	
	@GetMapping("producto/stock")
	public ResponseEntity<?> listarProdcutosOutStock(){
		
		List<Producto> listaProductos= productoServicio.listarProductoStock();
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	
	@GetMapping("producto/estado")
	public ResponseEntity<?> listarPorEstado(){
		
		List<Producto> listaProductos= productoServicio.listarProductoPorEstado();
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	@GetMapping("producto/menu/cliente")
	public ResponseEntity<?> listarPorMenuCliente(){
		
		List<Producto> listaProductos= productoServicio.listarProductoMenuCliente();
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	@GetMapping("producto/menu/destacado")
	public ResponseEntity<?> listarPorProdDestacado(){
		
		List<Producto> listaProductos= productoServicio.listarProductoDestacado();
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> buscarProductoPorId(@PathVariable Long id) {

		Producto producto = productoServicio.buscarPorId(id);
		
		if (producto == null) {
			return respuestaAccion.datoNulo(false, "No existe ese producto", "id invalido");
		}
		
		return respuestaAccion.accionCumplida(true, "Datos del producto", producto);
	}
	
	@GetMapping("/producto/fechas/{fechaInicio}/{fechaFin}")
	public ResponseEntity<?> buscarProductoPorFechas(@PathVariable Date fechaInicio, 
			@PathVariable Date fechaFin) {

		List<Producto> listaProductos= productoServicio.listarProductoPorFechas(fechaInicio, fechaFin);
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	
	@GetMapping("producto/buscar/{termino}")
	public ResponseEntity<?> listar(@PathVariable String termino){
		
		List<Producto> listaProductos= productoServicio.listarProductoPorTermino(termino);
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	
	@GetMapping("producto/buscar/estado/{termino}")
	public ResponseEntity<?> listarEstadoPorTermino(@PathVariable String termino){
		
		List<Producto> listaProductos= productoServicio.listarProductoEstadoPorTermino(termino);
		
		if (listaProductos.size() == 0) {
			return respuestaAccion.listaDatosVacia(false, "No existe productos", "tabla vacía");
		}
		
		return respuestaAccion.accionCumplida(true, "productos", listaProductos);
	}
	

	@PostMapping("/producto")
	public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
		Producto nuevoProdcto = null;
		
		// validamos que no exista el mismo codigo
		Producto prodCodigo = productoServicio.buscarPorCodigo(producto.getCodigoProducto());
		if (prodCodigo != null) {
			return respuestaAccion.datoDuplicado(false, "Hay un producto con ese código", "Código existente");
		}
		// creamos producto
		try {
			
			nuevoProdcto = productoServicio.guardar(producto);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar el producto",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		return respuestaAccion.accionCumplida(true, "Producto guardado", nuevoProdcto);
	}
	
	@PostMapping("/producto/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) throws IOException{
		Map<String, Object> response = new HashMap<>();
		Producto producto = productoServicio.buscarPorId(id);
		if(!archivo.isEmpty()) {
			String idFotoAnterior = "";
			if(producto.getImgIdProducto() != null) {
				idFotoAnterior = producto.getImgIdProducto();
				Map result = cloudinaryServicio.delete(idFotoAnterior);
			}
			try {
				Map result = cloudinaryServicio.upload(archivo);
				producto.setImgProducto((String)result.get("secure_url"));
				producto.setImgIdProducto((String)result.get("public_id"));
				
			} catch (Exception e) {
				response.put("mensaje", "Error al subir la imagen");
				response.put("errror", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return respuestaAccion.errorBD(false, "Error al subir la img", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			}
			
			productoServicio.guardar(producto);
		}
		return respuestaAccion.accionCumplida(true, "Imagen guardada", producto);
	}
	
	@PutMapping("/producto/{id}")
	public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto, @PathVariable Long id) {
		
		Producto prodActual = productoServicio.buscarPorId(id);
		Producto prodActualizado = null;
		
		// si el prod no existe
		if (prodActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}
		// si existe otro producto con el mismo codigo
		Producto prodCodigo = productoServicio.buscarPorCodigo(producto.getCodigoProducto());
		if (prodCodigo != null && prodCodigo.getIdProducto() != id) {
			return respuestaAccion.datoDuplicado(false, "Ya existe un producto con ese código", "Código existente");
		}
		
		try {
			
			prodActual.setCodigoProducto(producto.getCodigoProducto());
			prodActual.setImgProducto(producto.getImgProducto());
			prodActual.setDescripcionProducto(producto.getDescripcionProducto());
			prodActual.setEstadoProducto(producto.getEstadoProducto());
			prodActual.setFechaIngreso(producto.getFechaIngreso());
			prodActual.setPrecioVentaProducto(producto.getPrecioVentaProducto());
			prodActual.setStockProducto(producto.getStockProducto());
			prodActual.setMarca(producto.getMarca());
			prodActual.setPresentacion(producto.getPresentacion());
			prodActual.setZona(producto.getZona());
			prodActualizado = productoServicio.guardar(prodActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el producto",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Producto actualizado", prodActualizado);
	}
	
	@PutMapping("/producto/estado/{id}")
	public ResponseEntity<?> actualizarEstadoProducto(@PathVariable Long id) {
		
		Producto prodActual = productoServicio.buscarPorId(id);
		Producto prodActualizado = null;
		
		// si el prod no existe
		if (prodActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}

		try {
			prodActual.setEstadoProducto(!prodActual.getEstadoProducto());
			prodActualizado = productoServicio.guardar(prodActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el producto",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Estado actualizado", prodActualizado);
	}
	
	@PutMapping("/producto/menu/{id}")
	public ResponseEntity<?> actualizarProductoMenu(@PathVariable Long id) {
		
		Producto prodActual = productoServicio.buscarPorId(id);
		Producto prodActualizado = null;
		
		// si el prod no existe
		if (prodActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}

		try {
			
			prodActual.setMenuClienteProducto(!prodActual.getMenuClienteProducto());
			prodActualizado = productoServicio.guardar(prodActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el producto",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Estado actualizado", prodActualizado);
	}
	@PutMapping("/producto/destacar/{id}")
	public ResponseEntity<?> actualizarProductoDestacar(@PathVariable Long id) {
		
		Producto prodActual = productoServicio.buscarPorId(id);
		Producto prodActualizado = null;
		
		// si el prod no existe
		if (prodActual == null) {
			return respuestaAccion.datoNulo(false, "No existe en la Base de Datos", "id inválido");
		}
		
		try {
			
			prodActual.setDestacarProducto(!prodActual.getDestacarProducto());
			prodActualizado = productoServicio.guardar(prodActual);
			
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el producto",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Estado actualizado", prodActualizado);
	}
	
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable Long id) throws IOException{
		try {
			Producto producto = productoServicio.buscarPorId(id);
			productoServicio.eliminar(id);
			if(producto.getImgIdProducto() != null) {
				Map result = cloudinaryServicio.delete(producto.getImgIdProducto());
			}
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el producto",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Producto borrado", "borrado");
	}
	
	
	
}
