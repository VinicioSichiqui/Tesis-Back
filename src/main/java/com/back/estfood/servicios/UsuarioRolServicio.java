package com.back.estfood.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.UsuarioRol;
import com.back.estfood.repositorio.IUsuarioRolRepo;

@Service
public class UsuarioRolServicio {
	@Autowired
	private IUsuarioRolRepo usuarioRolRepo;
	
	@Transactional(readOnly = true)
	public List<UsuarioRol> listar(){
		return usuarioRolRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public UsuarioRol buscarPorId(Long id) {
		return usuarioRolRepo.findById(id).orElse(null);
	}
	
	@Transactional()
	public UsuarioRol guardar(UsuarioRol usuarioRol) {
		return usuarioRolRepo.save(usuarioRol);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		usuarioRolRepo.deleteById(id);
	}
}
