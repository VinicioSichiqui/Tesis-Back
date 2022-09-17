package com.back.estfood.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Venta;
import com.back.estfood.repositorio.IVentaRepo;

@Service
public class VentaServicio {
	@Autowired
	private IVentaRepo ventaRepo;
	
	@Transactional(readOnly = true)
	public List<Venta> listar(){
		return ventaRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Venta buscarPorId(Long id) {
		return ventaRepo.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public Venta ultimaVenta() {
		return ventaRepo.findLastVenta();
	}
	
	@Transactional(readOnly = true)
	public List<Venta> listarVentasPorTermino(String termino){
		return ventaRepo.findAllVentasByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Venta> listarVentasPorEstado(){
		return ventaRepo.findByEstadoVenta(true);
	}
	
	@Transactional(readOnly = true)
	public List<Venta> listarVentasClientePorTermino(String termino){
		return ventaRepo.findAllVentasClienteByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Venta> listarVentasPorFechas(Date fechaInicio, Date fechaFin){
		return ventaRepo.findByEstadoVentaAndFechaVentaBetween(true, fechaInicio, fechaFin);
	}
	
	@Transactional(readOnly = true)
	public Venta buscarPorCodigoVenta(String cod) {
		return ventaRepo.findByCodigoVenta(cod);
	}
	
	@Transactional()
	public Venta guardar(Venta venta) {
		return ventaRepo.save(venta);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		ventaRepo.deleteById(id);
	}
}
