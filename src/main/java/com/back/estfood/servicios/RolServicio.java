package com.back.estfood.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Rol;
import com.back.estfood.repositorio.IRolRepo;

@Service
public class RolServicio {
	@Autowired
	private IRolRepo rolRepo;
	
	@Transactional(readOnly = true)
	public List<Rol> listar(){
		return rolRepo.findAll();
	}
}
