package com.anikdv.blog.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.CommentsDto;
import com.anikdv.blog.app.services.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @apiNote This is Comments REST API Controller
 *
 * @author anikdv
 */
@RestController
@RequestMapping("/api")
public class CommentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	/**
	 * This is Create Comment Controller
	 *
	 * @param comments
	 * @param user_id
	 * @param post_id
	 * @return Status with created Comment | NOT NULL
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Create New Comment of Existing Post",
    description = "Create an New Comment. The response is Create New Comment object With Post details.")
	@PostMapping(value = "/post/{post_id}/user/{user_id}/comment", consumes = "application/json")
	public ResponseEntity<?> createComment(@RequestBody CommentsDto comments, @PathVariable Integer user_id,
			@PathVariable Integer post_id) {
		final String METHOD_NAME = "createComment";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			CommentsDto createdComment = this.commentService.createComment(comments, user_id, post_id);
			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

	/**
	 * This is Update Comments Controller
	 *
	 * @param commentData
	 * @param post_id
	 * @param user_id
	 * @param comment_id
	 * @return Updated Comment | NOT NULL
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Update an Existing Comment",
    description = "Update An Existing Comment. The response is updated Comment object With Post details..")
	@PutMapping(value = "/post/{post_id}/user/{user_id}/comment/{comment_id}", consumes = "application/json")
	public ResponseEntity<?> updateComment(@RequestBody CommentsDto commentData, @PathVariable Integer post_id,
			@PathVariable Integer user_id, @PathVariable Integer comment_id) {
		final String METHOD_NAME = "updateComment";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if (post_id == null || user_id == null || comment_id == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse(post_id + user_id + comment_id + " does not exist.", false));
			}
			CommentsDto updateComment = this.commentService.updateComment(commentData, comment_id, user_id, post_id);

			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(updateComment);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

	/**
	 * This is Delete Comments Controller
	 *
	 * @param comment_id
	 * @param user_id
	 * @param post_id
	 * @return Status Code | NOT NULL
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Delete an Comment",
    description = "Delete an existing Comment. The response is Message if Comment is Deleted Success or Not.")
	@DeleteMapping("/post/{post_id}/user/{user_id}/comment/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable Integer comment_id, @PathVariable Integer user_id,
			@PathVariable Integer post_id) {
		final String METHOD_NAME = "deleteComment";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			boolean deleteComment = this.commentService.deleteComment(comment_id, user_id, post_id);
			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			if (deleteComment) {
				LOGGER.info("Comment Id :" + comment_id + " Was Deleted Successfully");
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse("Comment Was Deleted Successfully.", deleteComment));
			} else {
				LOGGER.info("Comment Id :" + comment_id + " Not Deleted!");
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse("Comment Has Not Deleted!", deleteComment));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

	/**
	 * This Method For Get Specific Post Fetch All Comments
	 *
	 * @param post_id
	 * @return All Comments of Post
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Get All Comments By Post",
    description = "Get All Comments For Specific Post. The response is List of All Comments object with Post details.")
	@GetMapping("/post/{post_id}/comments")
	public ResponseEntity<?> getAllCommentsByPost(@PathVariable Integer post_id) {
		final String METHOD_NAME = "getAllCommentsByPost";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			List<CommentsDto> allCommentsByPost = this.commentService.getAllCommentsByPost(post_id);
			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(allCommentsByPost);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

	/**
	 * This Method For Only This User Fetch All Comments
	 *
	 * @param user_id
	 * @return All Comments of User
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Get All Comments By User",
    description = "Get All Comments By existing Users. The response is Get All Comments of Existing User.")
	@GetMapping("/user/{user_id}/comments")
	public ResponseEntity<?> getAllCommentsByUser(@PathVariable Integer user_id) {
		final String METHOD_NAME = "getAllCommentsByPost";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			List<CommentsDto> allCommentsByUser = this.commentService.getAllCommentsByUser(user_id);
			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(allCommentsByUser);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

	/**
	 * This Method For Fetch All Comments
	 *
	 * @return All Comments | NOT NULL
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Get All Comments",
    description = "Get All Existing Comments. The response is Get All Comments.")
	@GetMapping("/comments")
	public ResponseEntity<?> getAllComments() {
		final String METHOD_NAME = "getAllComments";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			List<CommentsDto> allCommentsByPost = this.commentService.getAllComments();
			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(allCommentsByPost);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

	/**
	 * This Method For Fetch Single Comments
	 *
	 * @param comment_id
	 * @return Single Comment | NOT NULL
	 */
	@Tag(name = "Comment API", description = "All Methods of Comment APIs")
	@Operation(summary = "Get Comment By Comment ID",
    description = "Get Comment By Comment ID. The Response is Comment object with Post details.")
	@GetMapping("/comment/{comment_id}")
	public ResponseEntity<?> getComment(@PathVariable Integer comment_id) {
		final String METHOD_NAME = "getComment";
		LOGGER.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			CommentsDto comment = this.commentService.getComment(comment_id);
			LOGGER.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(comment);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), false));
		}
	}

}
