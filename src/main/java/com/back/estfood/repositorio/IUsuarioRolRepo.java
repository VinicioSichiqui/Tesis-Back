package com.back.estfood.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.UsuarioRol;

@Repository
public interface IUsuarioRolRepo extends JpaRepository<UsuarioRol, Long>{

}
