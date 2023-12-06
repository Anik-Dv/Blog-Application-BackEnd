package com.anikdv.blog.app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.entities.Comments;
import com.anikdv.blog.app.entities.Post;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.CommentsDto;
import com.anikdv.blog.app.repositories.CommentsRepository;
import com.anikdv.blog.app.repositories.PostsRepository;
import com.anikdv.blog.app.repositories.UserRepository;
import com.anikdv.blog.app.services.CommentService;

/**
 * This Is Comments Service Implementations
 * 
 * @author anikdv
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentsRepository commentsRepository;

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapperConfiguration mapperConfiguration;

	@Override
	public CommentsDto createComment(CommentsDto comment, Integer user_id, Integer post_id) {
		// find user
		User user = this.userRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", user_id));
		// find post
		Post post = this.postsRepository.findById(post_id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", post_id));

		Comments comments = this.mapperConfiguration.modelMapper().map(comment, Comments.class);
		comments.setUser(user);
		comments.setPost(post);
		comments.setCreateDate(LocalDateTime.now());

		Comments createdComment = this.commentsRepository.save(comments);
		return this.mapperConfiguration.modelMapper().map(createdComment, CommentsDto.class);
	}

	@Override
	public CommentsDto updateComment(CommentsDto commentData, Integer comment_id, Integer user_id, Integer post_id) {
		// find post
		Post post = this.postsRepository.findById(post_id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", post_id));
		// find user
		User user = this.userRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", user_id));
		// find comment
		Comments comment = this.commentsRepository.findById(comment_id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", comment_id));

		Comments updatedComment = null;
		if (post.getPostId() == comment.getPost().getPostId() && user.getUserId() == comment.getUser().getUserId()) {
			// then set new data
			comment.setContent(commentData.getContent());
			// save the comment
			updatedComment = this.commentsRepository.save(comment);
		}
		return this.mapperConfiguration.modelMapper().map(updatedComment, CommentsDto.class);
	}

	@Override
	public boolean deleteComment(Integer comment_id, Integer user_id, Integer post_id) {
		boolean flag = false;
		// find user
		User user = this.userRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", user_id));
		// find post
		Post post = this.postsRepository.findById(post_id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", post_id));
		// find comments
		Comments comments = this.commentsRepository.findById(comment_id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", comment_id));

		if (comments.getUser().getUserId() == user.getUserId() && comments.getPost().getPostId() == post.getPostId()) {
			// delete comment
			this.commentsRepository.delete(comments);
			return flag = true;
		}
		return flag;
	}

	@Override
	public List<CommentsDto> getAllCommentsByPost(Integer post_id) {
		
		Post post = this.postsRepository.findById(post_id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", post_id));
		Set<Comments> postHasComments = post.getComments();

		List<CommentsDto> commentResult = postHasComments.stream()
				.map((comment) -> this.mapperConfiguration.modelMapper().map(comment, CommentsDto.class))
				.collect(Collectors.toList());
		return commentResult;
	}

	@Override
	public List<CommentsDto> getAllCommentsByUser(Integer user_id) {
		User user = this.userRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", user_id));
		Set<Comments> userHasComments = user.getComments();

		List<CommentsDto> commentResult = userHasComments.stream()
				.map((comment) -> this.mapperConfiguration.modelMapper().map(comment, CommentsDto.class))
				.collect(Collectors.toList());
		return commentResult;
	}

	@Override
	public List<CommentsDto> getAllComments() {
		List<Comments> comments = this.commentsRepository.findAll();
		List<CommentsDto> commentResult = comments.stream()
				.map((comment) -> this.mapperConfiguration.modelMapper().map(comment, CommentsDto.class))
				.collect(Collectors.toList());		
		return commentResult;
	}

	@Override
	public CommentsDto getComment(Integer comment_id) {
		Comments comment = this.commentsRepository.findById(comment_id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", comment_id));
		return this.mapperConfiguration.modelMapper().map(comment, CommentsDto.class);
	}

}
