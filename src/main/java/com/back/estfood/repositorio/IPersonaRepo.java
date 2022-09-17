package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Persona;

@Repository
public interface IPersonaRepo extends JpaRepository<Persona, Long>{

}
