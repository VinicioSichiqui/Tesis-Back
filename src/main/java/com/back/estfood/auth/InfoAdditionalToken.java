package com.back.estfood.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.back.estfood.modelos.Usuario;
import com.back.estfood.servicios.UsuarioService;



/*
 * 
 * Agregar informacion adicional al token
 * 
 * */

@Component
public class InfoAdditionalToken implements TokenEnhancer{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		// buscar por usuario "cedula" en este caso
		Usuario usuario = usuarioService.buscarPorCedula(authentication.getName());
		
		// Hasmap para pasar informacion adicional al token
		Map<String, Object> info = new HashMap<>();
		info.put("id", usuario.getIdUsuario());
		info.put("nombres", usuario.getPersona().getNombresPersona());
		info.put("apellidos", usuario.getPersona().getApellidosPersona());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
	
}
