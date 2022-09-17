package com.back.estfood.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Cliente;
import com.back.estfood.repositorio.IClienteRepo;

@Service
public class ClienteServicio {
	@Autowired
	private IClienteRepo clienteRepo;
	
	@Transactional(readOnly = true)
	public List<Cliente> listar(){
		return clienteRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return clienteRepo.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Cliente> listarClientesPorTermino(String termino){
		return clienteRepo.findAllClientesByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> listarClientesPorEstado(){
		return clienteRepo.findByEstadoCliente(true);
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarPorCedula(String cod) {
		return clienteRepo.findByCedulaCliente(cod);
	}
	
	@Transactional()
	public Cliente guardar(Cliente cliente) {
		return clienteRepo.save(cliente);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		clienteRepo.deleteById(id);
	}
}
