package com.anikdv.blog.app.services;

import java.util.List;

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
	CategoryDto createCategory(final CategoryDto categoryDto);

	/**
	 * update category
	 *
	 * @param categoryDto
	 * @param categoryId
	 * @return updated category
	 */
	CategoryDto updateCategory(final CategoryDto categoryDto, final Integer categoryId);

	/**
	 * delete category
	 *
	 * @param categoryId
	 * @return true/false
	 */
	boolean deleteCategory(final Integer categoryId);

	/**
	 * get single category
	 *
	 * @param categoryId
	 * @return category
	 */
	CategoryDto getSingleCategory(final Integer categoryId);

	/**
	 * get List of all category
	 *
	 * @return list of categories
	 */
	List<CategoryDto> getCategories();

}
