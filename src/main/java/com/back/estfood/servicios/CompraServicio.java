package com.back.estfood.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Compra;
import com.back.estfood.modelos.Proveedor;
import com.back.estfood.repositorio.ICompraRepo;

@Service
public class CompraServicio {
	@Autowired
	private ICompraRepo compraRepo;
	
	@Transactional(readOnly = true)
	public List<Compra> listar(){
		return compraRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Compra buscarPorId(Long id) {
		return compraRepo.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Compra> listarComprasPorEstado(){
		return compraRepo.findByEstadoCompra(true);
	}
	@Transactional(readOnly = true)
	public List<Compra> listarComprasPorTermino(String termino){
		return compraRepo.findAllComprasByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Compra> listarComprasProveedorPorTermino(String termino){
		return compraRepo.findAllComprasProveedorByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Compra> listarComprasPorFechas(Date fechaInicio, Date fechaFin){
		return compraRepo.findByEstadoCompraAndFechaCompraBetween(true, fechaInicio, fechaFin);
	}
	
	@Transactional(readOnly = true)
	public Compra buscarPorCodigoCompra(String cod, Proveedor proveedor) {
		return compraRepo.findByCodigoCompraAndProveedor(cod, proveedor);
	}
	
	@Transactional()
	public Compra guardar(Compra compra) {
		return compraRepo.save(compra);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		compraRepo.deleteById(id);
	}
}
