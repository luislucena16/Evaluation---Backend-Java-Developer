package com.llucena.springboot.backend.apirestb.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String DIRECTORIO_UPLOAD = "uploads";
	
	@Override
	public Resource upload(String namePhoto) throws MalformedURLException {
		
		Path routeFile = getPath(namePhoto);
		log.info(routeFile.toString());
		
		Resource resource = new UrlResource(routeFile.toUri());
		
		if(!resource.exists() && !resource.isReadable()) {
			routeFile = Paths.get("src/main/resources/static/images").resolve("no-user.png").toAbsolutePath();
			
			resource = new UrlResource(routeFile.toUri());
			
			log.error("Error could not load image " + namePhoto);
		}
		
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {

		String nameFile = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ","");
		
		Path routeFile = getPath(nameFile);
		log.info(routeFile.toString());
		
		Files.copy(file.getInputStream(),routeFile);
		
		return nameFile;
	}

	@Override
	public boolean remove(String namePhoto) {
		
		if(namePhoto != null && namePhoto.length() >0) {
			
			Path routePreviousPhoto = Paths.get("uploads").resolve(namePhoto).toAbsolutePath();
			File filePreviousPhoto = routePreviousPhoto.toFile();
			
			if(filePreviousPhoto.exists() && filePreviousPhoto.canRead()) {
				filePreviousPhoto.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String namePhoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(namePhoto).toAbsolutePath();
	}

}
