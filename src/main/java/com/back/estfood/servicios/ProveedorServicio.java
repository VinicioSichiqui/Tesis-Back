package com.back.estfood.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Proveedor;
import com.back.estfood.repositorio.IProveedorRepo;


@Service
public class ProveedorServicio {
	
	@Autowired
	private IProveedorRepo proveedorRepo;
	
	@Transactional(readOnly = true)
	public List<Proveedor> listar(){
		return proveedorRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Proveedor buscarPorId(Long id) {
		return proveedorRepo.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Proveedor> listarProveedoresPorTermino(String termino){
		return proveedorRepo.findAllProveedoresByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Proveedor> listarProveedoresPorEstado(){
		return proveedorRepo.findByEstadoProveedor(true);
	}
	
	@Transactional(readOnly = true)
	public Proveedor buscarPorRuc(String cod) {
		return proveedorRepo.findByRucProveedor(cod);
	}
	
	@Transactional()
	public Proveedor guardar(Proveedor proveedor) {
		return proveedorRepo.save(proveedor);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		proveedorRepo.deleteById(id);
	}
	
}
