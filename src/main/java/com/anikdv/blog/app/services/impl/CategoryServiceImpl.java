package com.anikdv.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.entities.Category;
import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.CategoryDto;
import com.anikdv.blog.app.repositories.CategoryRepository;
import com.anikdv.blog.app.services.CategoryService;

/**
 * This is a implements of category service
 *
 * @author AnikDV
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapperConfiguration mapperConfiguration;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = null;
		try {
			category = this.DtoToEntity(categoryDto);
			Category savedCategory = this.categoryRepository.save(category);
			return this.EntityToDto(savedCategory);
		} catch (Exception e) {
			e.getMessage();
		}
		return this.EntityToDto(category);

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// get find old category and when not find any category then throws error
		Category oldCategory = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		// set new content
		oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		// save new category
		Category updatedCategory = this.categoryRepository
				.save(this.mapperConfiguration.modelMapper().map(oldCategory, Category.class));
		// and then return
		return this.mapperConfiguration.modelMapper().map(updatedCategory, CategoryDto.class);
	}

	@Override
	public boolean deleteCategory(Integer categoryId) {
		boolean flag = false;
		try {
			Category category = this.categoryRepository.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));

			this.categoryRepository.delete(category);
			flag = true;
			return flag;
		} catch (ResourceNotFoundException e) {
			return flag;
		}
	}

	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		return this.EntityToDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categorys = this.categoryRepository.findAll();
		List<CategoryDto> listOfCategoryDto = categorys.stream()
				.map(cat -> this.mapperConfiguration.modelMapper().map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return listOfCategoryDto;
	}

	/**
	 * DtoToEntity Method convert object an DTO To Entity
	 *
	 * @param categoryDto
	 * @return fully mapped instance of {@code destinationType}
	 */
	public Category DtoToEntity(final CategoryDto categoryDto) {
		return this.mapperConfiguration.modelMapper().map(categoryDto, Category.class);
	}

	/**
	 * EntityToDto Method {@code source} convert object an Entity To DTO
	 *
	 * @param category
	 *
	 * @return fully mapped instance of {@code destinationType}
	 */
	public CategoryDto EntityToDto(final Category category) {
		return this.mapperConfiguration.modelMapper().map(category, CategoryDto.class);
	}

}
