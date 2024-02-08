package com.anikdv.blog.app.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.PostDto;
import com.anikdv.blog.app.services.FileService;
import com.anikdv.blog.app.services.PostService;

/**
 * @apiNote This is Implementation of File Service
 *
 * @author anikdv
 */

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private PostService postService;

	@Override
	public String fileUpoad(String path, MultipartFile file) throws IOException {
		String randomFileName = "";
		// Check if the file is present before processing it
        if (file != null && !file.isEmpty()) {
        	// Here is File Original Name
    		String originalFilename = file.getOriginalFilename();

    		// Generate Random File Name
    		String randomFileId = UUID.randomUUID().toString();
    		randomFileName = randomFileId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

    		// full path of file
    		String filePath = path + File.separator + randomFileName;

    		// if folder is not exists
    		File file_1 = new File(path);
    		if (!file_1.exists())
    			file_1.mkdir();

    		// copy file of existing path
    		Files.copy(file.getInputStream(), Paths.get(filePath));
        }
        return randomFileName;
	}

	@Override
	public InputStream getFileResource(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;
		FileInputStream stream = new FileInputStream(filePath);
		return stream;
	}

	@Override
	public boolean deleteFileResource(Integer postId, String path, String fileName) throws ResourceNotFoundException, Exception {
		PostDto post = this.postService.getPostById(postId);
		if (post.getImageName().equals(fileName)) {
			String filePath = path + File.separator + fileName;
			File postFile = new File(filePath);
			if (postFile.exists()) {
				post.setImageName("");
				postFile.delete();
			}
			// update that post again
			this.postService.updatePost(post, postId, post.getUser().getUserid());
			return true;
		}
		return false;
	}

}
