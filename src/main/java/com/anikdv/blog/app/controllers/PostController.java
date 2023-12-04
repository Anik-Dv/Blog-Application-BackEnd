package com.anikdv.blog.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.multipart.MultipartFile;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.PostDto;
import com.anikdv.blog.app.payloads.PostResponse;
import com.anikdv.blog.app.services.FileService;
import com.anikdv.blog.app.services.PostService;
import com.anikdv.blog.app.utils.AppConstants;

import jakarta.servlet.http.HttpServletResponse;

/**
 * This is Post Rest Controller
 *
 * @apiNote This is Post API Controller
 * @author AnikDV
 */
@RestController
@RequestMapping("/api")
public class PostController {

	private Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${file.upload-dir}")
	private String path;

	/**
	 * This Method For Create Post
	 *
	 * @param postDto
	 * @param categoryId
	 * @param userId
	 * @param MultipartFile -> (optional)
	 * @return StatusCode
	 */
	@PostMapping(value = "/user/{userId}/category/{categoryId}/post/create")
	public ResponseEntity<?> createPost(final @RequestParam("postData") String postData,
			final @RequestParam(value = "file", required = false) MultipartFile file, final @PathVariable("userId") Integer userId,
			final @PathVariable("categoryId") Integer categoryId) {
		final String METHOD_NAME = "createPost";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if (postData.isEmpty()) {
				logger.error("Post Data Must Not be Empty!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("Post Content Must Not be Empty!", false));
			}
			PostDto createdPost = this.postService.createPost(postData, userId, categoryId, path, file);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Request!", false));
		}
	}

	/**
	 * This Method For Update Post
	 *
	 * @param postDto
	 * @param postId
	 * @return Status with update content
	 */
	@PutMapping(value = "/post/{postId}/update", consumes = "application/json")
	public ResponseEntity<?> updatePost(final @RequestBody PostDto postDto, final @PathVariable Integer postId) {
		final String METHOD_NAME = "updatePost";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if (postDto == null || postId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("Post ID " + postId + " does not exist.", false));
			}
			PostDto updatedPost = this.postService.updatePost(postDto, postId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
		} catch (InternalServerError e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Something Went Wrong!.", false));
		}
	}

	/**
	 * This Method For Delete Post
	 *
	 * @param postId
	 * @return Status with ApiResponse
	 */
	@DeleteMapping(value = "/post/{postId}")
	public ResponseEntity<?> deletePost(final @PathVariable Integer postId) {
		final String METHOD_NAME = "deletePost";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			boolean deletedPost = this.postService.deletePost(postId, path);
			if (deletedPost) {
				logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse("Post has been Deleted Successfully!", deletedPost));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("Not Found Any Post!", deletedPost));
			}
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Something Went Wrong!", false));
		}
	}

	/**
	 * This Method For Get All Posts Of User
	 *
	 * @param userId
	 * @return user all post | NOT NULL
	 */
	@GetMapping(value = "/user/{userId}/posts")
	public ResponseEntity<?> getPostByUserHandler(final @PathVariable Integer userId) {
		final String METHOD_NAME = "getPostByUserHandler";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if (userId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("User ID " + userId + " does not exist.", false));
			}
			Set<PostDto> posts = this.postService.getPostByUser(userId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(posts);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User Not Have Any Posts!", false));
		}
	}

	/**
	 * This Method For Get All Posts Of Category
	 *
	 * @param categoryId
	 * @return user all post | NOT NULL
	 */
	@GetMapping(value = "/category/{categoryId}/posts")
	public ResponseEntity<?> getPostByCategoryHandler(final @PathVariable Integer categoryId) {
		final String METHOD_NAME = "getPostByCategoryHandler";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if (categoryId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("Category ID " + categoryId + " does not exist.", false));
			}
			Set<PostDto> posts = this.postService.getPostByCategory(categoryId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(posts);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("Category Not Have Any Posts!", false));
		}
	}

	/**
	 * Getting All Posts
	 *
	 * @param pageNumber
	 * @param pageContentSize
	 * @param sortBy
	 * @param sortDir
	 * @return All Posts
	 */
	@GetMapping(value = "/posts/feed")
	public ResponseEntity<?> getPosts(
			final @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			final @RequestParam(value = "pageContentSize", defaultValue = AppConstants.PAGE_CONTENT_SIZE, required = false) Integer pageContentSize,
			final @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			final @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		final String METHOD_NAME = "getPosts";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			PostResponse posts = this.postService.getPosts(pageNumber, pageContentSize, sortBy, sortDir);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(posts);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found Any Posts!", false));
		}
	}

	/**
	 * This Method For Get Post of Given ID
	 *
	 * @param postId
	 * @return post of given post ID
	 */
	@GetMapping(value = "/post/{postId}")
	public ResponseEntity<?> getPostById(final @PathVariable Integer postId) {
		final String METHOD_NAME = "getPostById";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if (postId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("Post ID " + postId + " does not Empty!", false));
			}
			PostDto post = this.postService.getPostById(postId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(post);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("Not Found Any Post ID " + postId, false));
		}
	}

	/**
	 * Search The Posts
	 * 
	 * @param keyword
	 *
	 * @return all search posts | NOT NULL
	 */
	@GetMapping(value = "/search/post/{keyword}")
	public ResponseEntity<?> searchPostHandler(final @PathVariable String keyword) {
		final String METHOD_NAME = "searchPostHandler";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			List<PostDto> result = this.postService.searchPostByKeyword(keyword);
			if (result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found Any Posts!", false));
			}
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(result);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Something Went Wrong!", false));
		}
	}

	/**
	 * Upload Post Images
	 * 
	 * @param path
	 * @param file
	 * @param postId
	 * @return status with file
	 */
	@PostMapping(value = "/post/upload/{postId}")
	public ResponseEntity<?> uploadPostFile(final @PathVariable Integer postId,
			@RequestParam("file") MultipartFile file) {
		final String METHOD_NAME = "uploadPostFile";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		// find previous post
		PostDto post = this.postService.getPostById(postId);

		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("File Is Must Not Empty!", false));
		}

		// upload the file
		String fileName;
		try {
			fileName = this.fileService.fileUpoad(path, file);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Post Images Not Uploaded!", false));
		}

		// set new property
		post.setImageName(fileName);
		// then this update post
		PostDto postDto = this.postService.updatePost(post, postId);

		logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);

	}

	/**
	 * Fetch Post Images
	 * 
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/post/file/{fileName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getFileResource(final @PathVariable String fileName, HttpServletResponse response) {
		final String METHOD_NAME = "getFileResource";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			InputStream fileResource = this.fileService.getFileResource(path, fileName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(fileResource, response.getOutputStream());
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.contentType(MediaType.valueOf(response.getContentType())).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Is Not Found!");
		}
	}

	/**
	 * Delete Post Images
	 * 
	 * @param postId
	 * @param fileName
	 */
	@DeleteMapping("/post/{postId}/file/{fileName}")
	public ResponseEntity<?> deleteFileResourceFromPost(@PathVariable Integer postId, @PathVariable String fileName) {
		final String METHOD_NAME = "deleteFileResourceFromPost";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			boolean deleteFileResource = this.fileService.deleteFileResource(postId, path, fileName);
			if (!deleteFileResource) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse("File Not Found!", deleteFileResource));
			}

			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse("File Was Deleted Successfully!", deleteFileResource));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("File Was Not Deleted!", false));

		}
	}

}
