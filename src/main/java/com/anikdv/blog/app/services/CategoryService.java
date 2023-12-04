package com.anikdv.blog.app.services;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.anikdv.blog.app.payloads.CategoryDto;

/**
 * This is a category Service
 *
 * @author AnikDV
 */
public interface CategoryService {

	/**
	 * create category
	 *
	 * @param categoryDto
	 * @return created category
	 */
	@CacheEvict(value = "category", allEntries = true)
	CategoryDto createCategory(final CategoryDto category);

	/**
	 * update category
	 *
	 * @param categoryDto
	 * @param categoryId
	 * @return updated category
	 */
	@CacheEvict(value = "category", key = "#categoryId", allEntries = true)
	CategoryDto updateCategory(final CategoryDto category, final Integer categoryId);

	/**
	 * delete category
	 *
	 * @param categoryId
	 * @return true/false
	 */
	@CacheEvict(cacheNames = "category", key = "#categoryId", allEntries = true)
	boolean deleteCategory(final Integer categoryId);

	/**
	 * get single category
	 *
	 * @param categoryId
	 * @return category
	 */
	@Cacheable(value = "category", key = "#categoryId")
	CategoryDto getSingleCategory(final Integer categoryId);

	/**
	 * get List of all category
	 *
	 * @return list of categories
	 */
	@Cacheable(cacheNames = "category")
	List<CategoryDto> getCategories();

}
