package com.anikdv.blog.app.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anikdv.blog.app.services.FileService;

/**
 * @apiNote This is Implementation of File Service
 * 
 * @author anikdv
 */

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String fileUpoad(String path, MultipartFile file) throws IOException {

		// Here is File Original Name
		String originalFilename = file.getOriginalFilename();

		// Generate Random File Name
		String randomFileId = UUID.randomUUID().toString();
		String randomFileName = randomFileId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

		// full path of file
		String filePath = path + File.separator + randomFileName;

		// if folder is not exists
		File file_1 = new File(path);
		if (!file_1.exists())
			file_1.mkdir();

		// copy file of existing path
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return randomFileName;
	}

	@Override
	public InputStream fileResource(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;
		FileInputStream stream = new FileInputStream(filePath);
		return stream;
	}

}
