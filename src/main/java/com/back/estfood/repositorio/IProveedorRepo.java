package com.back.estfood.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Proveedor;

@Repository
public interface IProveedorRepo extends JpaRepository<Proveedor, Long>{
	
	public Proveedor findByRucProveedor(String rucProveedor);
	public List<Proveedor> findByEstadoProveedor(Boolean estadoProveedor);
	
	@Query (value = "select p from Proveedor p where lower(p.nombreProveedor)   like %?1%")
	public List<Proveedor> findAllProveedoresByTermino(String termino);
	
}
