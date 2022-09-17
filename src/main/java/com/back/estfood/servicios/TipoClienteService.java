package com.back.estfood.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.TipoCliente;
import com.back.estfood.repositorio.ITipoClienteRepo;

@Service
public class TipoClienteService {
	@Autowired
	private ITipoClienteRepo tipoClienteRepo;
	
	@Transactional(readOnly = true)
	public List<TipoCliente> listar(){
		return tipoClienteRepo.findAll();
	}
}
