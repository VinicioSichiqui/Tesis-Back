package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.FormaPago;

@Repository
public interface IFormaPagoRepo extends JpaRepository<FormaPago, Long>{

}
