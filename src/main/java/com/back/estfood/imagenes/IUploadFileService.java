package com.back.estfood.imagenes;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;

public interface IUploadFileService {
	public Resource cargar(String nombreFoto, String ruta) throws MalformedURLException;

}
