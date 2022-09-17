package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.DetalleVentaProducto;

@Repository
public interface IDetalleVentaProductoRepo extends JpaRepository<DetalleVentaProducto, Long>{

}
