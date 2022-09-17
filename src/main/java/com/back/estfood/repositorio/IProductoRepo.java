package com.back.estfood.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Producto;

@Repository
public interface IProductoRepo extends JpaRepository<Producto, Long>{
	
	public Producto findByCodigoProducto(String codigoProducto);
	public List<Producto> findByEstadoProducto(Boolean estadoProducto);
	
	public List<Producto> findByStockProductoLessThanEqual(Integer stockProducto);
	
	@Query (value = "select p from Producto p where lower(p.descripcionProducto)   like %?1%")
	public List<Producto> findAllProductosByTermino(String termino);
	
	@Query (value = "select p from Producto p where lower(p.descripcionProducto) like %?1% and p.estadoProducto=true")
	public List<Producto> findAllProductosByTerminoAndEstado(String termino);
	
	public List<Producto> findByDestacarProductoAndMenuClienteProducto(Boolean destacarProducto, Boolean menuCliente);
	
	public List<Producto> findByMenuClienteProducto(Boolean menuClienteProducto);
	
	public List<Producto> findByEstadoProductoAndFechaIngresoBetween( Boolean estado, Date fechaInicio, Date fechaFin);
}
