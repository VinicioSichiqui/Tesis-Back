package com.back.estfood.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class DetalleProveedor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDetalleProveedor;
	
	private String cuidadDetalleProveedor;
	private String descripcionDetalleProveedor;
	
	@Temporal(TemporalType.DATE)
	private Date fechaIngresoDetalleProveedor;
	
}
