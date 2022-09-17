package com.back.estfood.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.estfood.modelos.Cliente;

@Repository
public interface IClienteRepo extends JpaRepository<Cliente, Long>{
	
	@Query (value = "select c from Cliente c where c.persona.cedulaPersona like %?1%")
	public List<Cliente> findAllClientesByTermino(String termino);
	
	public List<Cliente> findByEstadoCliente(Boolean estadoCliente);
	
	@Query (value = "select c from Cliente c where c.persona.cedulaPersona = ?1")
	public Cliente findByCedulaCliente(String cedulaCliente);
}
