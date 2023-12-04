package com.anikdv.blog.app.services.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.configurations.ObjectMapperConfig;
import com.anikdv.blog.app.entities.Category;
import com.anikdv.blog.app.entities.Post;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.PostDto;
import com.anikdv.blog.app.payloads.PostResponse;
import com.anikdv.blog.app.repositories.CategoryRepository;
import com.anikdv.blog.app.repositories.PostsRepository;
import com.anikdv.blog.app.repositories.UserRepository;
import com.anikdv.blog.app.services.FileService;
import com.anikdv.blog.app.services.PostService;
import com.anikdv.blog.app.utils.GetAllDataFromCache;

/**
 * This is Implementation of Post Service
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

	@Autowired
	private ObjectMapperConfig objectMapperConfig;

	@Lazy
	@Autowired
	private FileService fileService;

	@Override
	public PostDto createPost(String postData, Integer userId, Integer categoryId, String path, MultipartFile file)
			throws Exception {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID", userId));
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

		// convert string into json
		PostDto postDto = objectMapperConfig.getObjectMapper().readValue(postData, PostDto.class);

		Post postEntity = this.mapperConfiguration.modelMapper().map(postDto, Post.class);

		// upload file in post
		// FileService fileService = new FileServiceImpl();
		String uploadedFileName = this.fileService.fileUpoad(path, file);
		if (!uploadedFileName.isEmpty() && uploadedFileName != null) {
			postEntity.setImageName(uploadedFileName);
		}
		// setup default properties
		postEntity.setCreateDate(LocalDateTime.now());
		postEntity.setUser(user);
		postEntity.setCategory(category);
		Post createdPost = this.postsRepository.save(postEntity);
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
	public boolean deletePost(Integer postId, String path) {
		boolean flag = false;
		try {
			Post post = this.postsRepository.findById(postId)
					.orElseThrow(() -> new ResourceNotFoundException("POST", "ID", postId));

			String filePath = path + File.separator + post.getImageName();
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			this.postsRepository.delete(post);
			return flag = true;
		} catch (ResourceNotFoundException e) {
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
	public PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir)
			throws ResourceNotFoundException {

		// for get all data from cache
		GetAllDataFromCache cache = new GetAllDataFromCache();
		cache.getAllDataCache(pageNumber, pageSize);

		Sort sortDiraction = null;
		if (sortDir.equals("asc"))
			sortDiraction = Sort.by(sortBy).ascending();
		else if (sortDir.equals("desc"))
			sortDiraction = Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDiraction);
		Page<Post> postOfPage = this.postsRepository.findAll(pageable);
		List<Post> allPosts = postOfPage.getContent();
		System.out.println("get all data from database");
		List<PostDto> listOfPostDto = allPosts.stream()
				.map((post) -> this.mapperConfiguration.modelMapper().map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(listOfPostDto);
		postResponse.setPageNumber(postOfPage.getNumber());
		postResponse.setPageContentSize(postOfPage.getSize());
		postResponse.setTotalPages(postOfPage.getTotalPages());
		postResponse.setTotalContent(postOfPage.getTotalElements());
		postResponse.setLastPage(postOfPage.isLast());
		return postResponse;
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
	public List<PostDto> searchPostByKeyword(String keyword) {
		List<PostDto> resultOfPostDto = null;
		try {
			List<Post> result = this.postsRepository.searchPosts("%" + keyword + "%");
			resultOfPostDto = result.stream()
					.map((post) -> this.mapperConfiguration.modelMapper().map(post, PostDto.class))
					.collect(Collectors.toList());
			return resultOfPostDto;
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		return resultOfPostDto;
	}

}
