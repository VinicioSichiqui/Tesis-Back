package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Zona;

@Repository
public interface IZonaRepo extends JpaRepository<Zona, Long>{

}
