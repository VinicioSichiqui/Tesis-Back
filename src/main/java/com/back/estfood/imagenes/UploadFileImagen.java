package com.back.estfood.imagenes;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class UploadFileImagen implements IUploadFileService {

	@Override
	public Resource cargar(String nombreFoto, String ruta) throws MalformedURLException {
		Path rutaArchivo = getPath(nombreFoto, ruta);
		Resource recurso = new UrlResource(rutaArchivo.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-image.jpg").toAbsolutePath();
			recurso = new UrlResource(rutaArchivo.toUri());

		}
		return recurso;
	}


	public Path getPath(String nombreFoto, String ruta) {
		return Paths.get(ruta).resolve(nombreFoto).toAbsolutePath();
	}

}
