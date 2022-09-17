package com.back.estfood.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.estfood.modelos.Usuario;
import com.back.estfood.repositorio.IUsuarioRepo;

@Service
public class UsuarioService implements UserDetailsService{
	@Autowired
	private IUsuarioRepo usuarioRepo;
	
	@Transactional(readOnly = true)
	public List<Usuario> listar(){
		return usuarioRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepo.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Usuario> listarUsuariosPorTermino(String termino){
		return usuarioRepo.findAllUsuarioByTermino(termino);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> listarUsuariosPorEstado(){
		return usuarioRepo.findByEstadoUsuario(true);
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorCedula(String cod) {
		return usuarioRepo.findByCedulaUsuario(cod);
	}
	
	@Transactional()
	public Usuario guardar(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}
	
	@Transactional()
	public void eliminar(Long id) {
		usuarioRepo.deleteById(id);
	}
	
	
	  @Override
	  
	  @Transactional(readOnly = true) public UserDetails loadUserByUsername(String cedula) throws UsernameNotFoundException { 
		  Usuario usuario = usuarioRepo.findByCedulaUsuario(cedula); 
		  if (usuario == null) {
			  System.out.println("Error en el login no existe el usuario " + cedula); 
			  throw new UsernameNotFoundException("Error en el login no existe el usuario " +cedula); 
		  } 
		  List<GrantedAuthority> authorities = 
				  usuario.getRoles().stream()
				  					.map(role -> new SimpleGrantedAuthority(
				  							role.getRol().getNombreRol()))
				  					.peek(authority ->
				  							System.out.println(authority.getAuthority()))
				  					.collect(Collectors.toList());
		  return new User(usuario.getPersona().getCedulaPersona(),usuario.getPasswordUsuario(), usuario.getEstadoUsuario(), true, true, true,authorities);
	  }
	 
}
