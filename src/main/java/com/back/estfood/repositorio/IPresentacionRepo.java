package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Presentacion;

@Repository
public interface IPresentacionRepo extends JpaRepository<Presentacion, Long>{

}
