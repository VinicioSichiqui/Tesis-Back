package com.back.estfood.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Usuario;

@Repository
public interface IUsuarioRepo extends JpaRepository<Usuario, Long>{
	
	@Query (value = "select u from Usuario u where u.persona.cedulaPersona like %?1%")
	public List<Usuario> findAllUsuarioByTermino(String termino);
	
	public List<Usuario> findByEstadoUsuario(Boolean estadoUsuario);
	
	@Query (value = "select u from Usuario u where u.persona.cedulaPersona = ?1")
	public Usuario findByCedulaUsuario(String cedulaUsuario);
	
}
