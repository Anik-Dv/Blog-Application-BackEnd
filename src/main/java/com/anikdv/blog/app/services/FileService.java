package com.anikdv.blog.app.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;

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
	String fileUpoad(final String path, final MultipartFile file) throws IOException;

	/**
	 * @param path
	 * @param fileName
	 * @return File InputStream | NOT NULL
	 * @throws FileNotFoundException
	 */
	InputStream getFileResource(final String path, final String fileName) throws FileNotFoundException;

	/**
	 * @param fileName
	 * @param postId
	 * @param path
	 * @return true/false | NOT NULL
	 * @throws FileNotFoundException
	 * @throws Exception
	 * @throws ResourceNotFoundException
	 */
	boolean deleteFileResource(final Integer postId, final String path, final String fileName) throws FileNotFoundException, ResourceNotFoundException, Exception;

}
