package com.llucena.springboot.backend.apirestb.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	public Resource upload(String name) throws MalformedURLException;
	public String copy(MultipartFile file) throws IOException;
	public boolean remove(String namePhoto);
	public Path getPath(String namePhoto);
	
}
