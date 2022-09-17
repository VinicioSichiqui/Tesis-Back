package com.back.estfood.servicios;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Producto;
import com.back.estfood.repositorio.IProductoRepo;

@Service
public class ProductoServicio {
	
	@Autowired
	private IProductoRepo productoRepo;
	
	@Transactional(readOnly = true)
	public List<Producto> listar(){
		return productoRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Producto buscarPorId(Long id) {
		return productoRepo.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<Producto> listarProductoPorTermino(String termino){
		return productoRepo.findAllProductosByTermino(termino);
	}

	@Transactional(readOnly = true)
	public List<Producto> listarProductoEstadoPorTermino(String termino){
		return productoRepo.findAllProductosByTerminoAndEstado(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Producto> listarProductoPorEstado(){
		return productoRepo.findByEstadoProducto(true);
	}
	@Transactional(readOnly = true)
	public List<Producto> listarProductoMenuCliente(){
		return productoRepo.findByMenuClienteProducto(true);
	}
	@Transactional(readOnly = true)
	public List<Producto> listarProductoDestacado(){
		return productoRepo.findByDestacarProductoAndMenuClienteProducto(true, true);
	}
	
	@Transactional(readOnly = true)
	public List<Producto> listarProductoPorFechas(Date fechaInicio, Date fechaFin){
		return productoRepo.findByEstadoProductoAndFechaIngresoBetween(true, fechaInicio, fechaFin);
	}
	@Transactional(readOnly = true)
	public List<Producto> listarProductoStock(){
		return productoRepo.findByStockProductoLessThanEqual(5);
	}
	
	@Transactional(readOnly = true)
	public Producto buscarPorCodigo(String cod) {
		return productoRepo.findByCodigoProducto(cod);
	}
	
	@Transactional()
	public Producto guardar(Producto producto) {
		return productoRepo.save(producto);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		productoRepo.deleteById(id);
	}
	
	
	 
	
}
