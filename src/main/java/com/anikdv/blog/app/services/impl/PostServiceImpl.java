package com.anikdv.blog.app.services.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.entities.Category;
import com.anikdv.blog.app.entities.Post;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.PostDto;
import com.anikdv.blog.app.repositories.CategoryRepository;
import com.anikdv.blog.app.repositories.PostsRepository;
import com.anikdv.blog.app.repositories.UserRepository;
import com.anikdv.blog.app.services.PostService;

/**
 * This is Implementations of Post Service
 *
 * @author AnikDV
 */
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapperConfiguration mapperConfiguration;

	@Override
	public PostDto createPost(PostDto postdto, Integer userId, Integer categoryId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID", userId));
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

		Post post = this.mapperConfiguration.modelMapper().map(postdto, Post.class);

		// setup default properties
		post.setImageName("default.png");
		post.setCreateDate(ZonedDateTime.now());
		post.setUser(user);
		post.setCategory(category);

		Post createdPost = this.postsRepository.save(post);
		return this.mapperConfiguration.modelMapper().map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) throws ResourceNotFoundException {

		Post post = this.postsRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("POST", "ID", postId));

		// set new value to old post
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTag(postDto.getTag());

		Post updatedPost = this.postsRepository.save(post);
		return this.mapperConfiguration.modelMapper().map(updatedPost, PostDto.class);
	}

	@Override
	public boolean deletePost(Integer postId) {
		boolean flag = false;
		try {
			Post post = this.postsRepository.findById(postId)
					.orElseThrow(() -> new ResourceNotFoundException("POST", "ID", postId));
			this.postsRepository.delete(post);
			return flag = true;
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			return flag;
		}
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postsRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		return this.mapperConfiguration.modelMapper().map(post, PostDto.class);

	}

	@Override
	public List<PostDto> getPosts() throws ResourceNotFoundException {
		List<Post> posts = this.postsRepository.findAll();
		return posts.stream().map((post) -> this.mapperConfiguration.modelMapper().map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Set<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID", userId));
		Set<Post> userAllPosts = this.postsRepository.findByUser(user);
		Set<PostDto> posts = userAllPosts.stream()
				.map((p) -> this.mapperConfiguration.modelMapper().map(p, PostDto.class)).collect(Collectors.toSet());
		return posts;
	}

	@Override
	public Set<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		Set<Post> categoryAllPosts = this.postsRepository.findByCategory(category);
		Set<PostDto> posts = categoryAllPosts.stream()
				.map((p) -> this.mapperConfiguration.modelMapper().map(p, PostDto.class)).collect(Collectors.toSet());
		return posts;
	}

	@Override
	public PostDto searchPostByKeyword(String keyword) {
		return null;
	}

}
