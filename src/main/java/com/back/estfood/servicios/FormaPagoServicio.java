package com.back.estfood.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.FormaPago;
import com.back.estfood.repositorio.IFormaPagoRepo;

@Service
public class FormaPagoServicio {
	@Autowired
	private IFormaPagoRepo formaPagoRepo;
	
	@Transactional(readOnly = true)
	public List<FormaPago> listar(){
		return formaPagoRepo.findAll();
	}
}
