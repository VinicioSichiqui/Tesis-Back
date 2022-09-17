package com.back.estfood.auth;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/*
 * Para dar accesos a las del back  a los usuarios segun su rol
 * Configuracion de cors
 * 
 * 
 * Si desea agregar mas paths puedo hacerlo segun su módulo por favor mantener el orden 
 * 
 * 
 * Si desea agregar otro rol de hacerlo asi:  "nombre del rol de la bd" = 'estudiante'
 * Revisar el import.sql del back la tabla roles
 *
 * 
 * */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	// lado del oauth
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/uploads/img/**", "/images/**", "/api/producto/menu/**").permitAll()
		// Acceso a solo los gets todos los roles
		.antMatchers(HttpMethod.GET, "/**")	.hasAnyRole("ADMINISTRADOR", "VENDEDOR", "INVENTARIO")		
		.antMatchers(HttpMethod.PUT, "/api/usuario/**")	.hasAnyRole("ADMINISTRADOR", "VENDEDOR", "INVENTARIO")	
		.antMatchers(HttpMethod.PUT, "/api/producto/menu/**")	.hasAnyRole("ADMINISTRADOR", "VENDEDOR", "INVENTARIO")
		.antMatchers(HttpMethod.PUT, "/api/producto/destacar/**")	.hasAnyRole("ADMINISTRADOR", "VENDEDOR", "INVENTARIO")
		.antMatchers("/api/cliente/**")		.hasAnyRole("ADMINISTRADOR", "VENDEDOR")
		.antMatchers("/api/compra/**")		.hasAnyRole("ADMINISTRADOR", "INVENTARIO")
		.antMatchers("/api/formaPago/**")	.hasAnyRole("ADMINISTRADOR", "VENDEDOR", "INVENTARIO")
		.antMatchers("/api/producto/**")	.hasAnyRole("ADMINISTRADOR", "INVENTARIO")
		.antMatchers("/api/proveedor/**")	.hasAnyRole("ADMINISTRADOR", "VENDEDOR", "INVENTARIO")
		.antMatchers("/api/rol/**")			.hasAnyRole("ADMINISTRADOR")
		.antMatchers("/api/tipoCliente/**")	.hasAnyRole("ADMINISTRADOR")
		.antMatchers("/api/usuario/**")		.hasAnyRole("ADMINISTRADOR")
		.antMatchers("/api/usuarioRol/**")	.hasAnyRole("ADMINISTRADOR")
		.antMatchers("/api/venta/**")		.hasAnyRole("ADMINISTRADOR", "VENDEDOR")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	// Configuracion Cors para informacion cruzada
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("")); // permitir el dominio del cliente "angular"
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		config.setAllowedOriginPatterns(Collections.singletonList("*"));
		config.setAllowCredentials(true); //permitimos credenciales
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "access-control-allow-origin")); // permitimos las cabeceras
		
		// registramos configuracion del cors para todas las rutas del back
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
	// filtro de cors y pasamos toda la configuracion anterior
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter( corsConfigurationSource() ));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
}
