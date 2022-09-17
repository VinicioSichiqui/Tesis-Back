package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.DetalleProveedor;

@Repository
public interface ICiudadRepo extends JpaRepository<DetalleProveedor, Long> {

}
