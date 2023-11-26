/**
 * 
 */
package com.anikdv.blog.app.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * @apiNote This is File Service Declaration
 * @author anikdv
 * 
 */
public interface FileService {

	/**
	 * @param path
	 * @param file
	 * @return FileName | NOT NULL
	 * @throws IOException
	 */
	String fileUpoad(String path, MultipartFile file) throws IOException;

	/**
	 * @param path
	 * @param fileName
	 * @return File InputStream | NOT NULL
	 * @throws FileNotFoundException
	 */
	InputStream fileResource(String path, String fileName) throws FileNotFoundException;

}
