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

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.CategoryDto;
import com.anikdv.blog.app.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * @apiNote This is Category REST API Controller
 *
 * @author AnikDV
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

	private Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	/**
	 * This Method For Get All Category Resource
	 *
	 * @return All categories | NOT NULL
	 */
	@Tag(name = "Category API", description = "All Methods of Category APIs")
	@Operation(summary = "Get All Comments",
    description = "Get All Comments. The Response is Get All Comments object details.")
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		final String METHOD_NAME = "getAllCategory";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			List<CategoryDto> categories = this.categoryService.getCategories();
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(categories);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * This Method For Get Single Category Resource
	 *
	 * @param categoryId
	 * @return single category | NOT NULL
	 */
	@Tag(name = "Category API", description = "All Methods of Category APIs")
	@Operation(summary = "Get Category By Category ID",
    description = "Get Single Category By Category ID. The Response is Category object details.")
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getSingleCategory(final @PathVariable Integer categoryId) {
		final String METHOD_NAME = "getSingleCategory";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			CategoryDto singleCategory = this.categoryService.getSingleCategory(categoryId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(singleCategory);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category Not Found!", false));
		}
	}

	/**
	 * This Method For Create Category Resource
	 *
	 * @param categoryDto
	 * @return created category | NOT NULL
	 */
	@Tag(name = "Category API", description = "All Methods of Category APIs")
	@Operation(summary = "Get Comment By Comment ID",
    description = "Get Comment By Comment ID. The Response is Comment object with Post details.")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<?> createCategory(@Valid final @RequestBody CategoryDto categoryDto) {
		final String METHOD_NAME = "createCategory";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Something Went Wrong!", false));
		}
	}

	/**
	 * This Method For Update Category Resource
	 *
	 * @param categoryDto
	 * @param categoryId
	 * @return updated category | NOT NULL
	 */
	@Tag(name = "Category API", description = "All Methods of Category APIs")
	@Operation(summary = "Update An Existing Category.",
    description = "Update an Existing Category. The Response is Updated Category object details.")
	@PutMapping(value = "/update/{categoryId}", consumes = "application/json")
	public ResponseEntity<?> updateCategory(@Valid final @RequestBody CategoryDto categoryDto,
			final @PathVariable Integer categoryId) {
		final String METHOD_NAME = "updateCategory";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Category Not Found!", false));
		}
	}

	/**
	 * This Method For Delete Category Resource
	 *
	 * @param categoryId
	 * @return status with If Deleted True/False | NOT NULL
	 */
	@Tag(name = "Category API", description = "All Methods of Category APIs")
	@Operation(summary = "Delete An Existing Category.",
    description = "Delete an Existing Category. The Response is Updated Categroy object details.")
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(final @PathVariable Integer categoryId) {
		final String METHOD_NAME = "deleteCategory";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			boolean deleteCategory = this.categoryService.deleteCategory(categoryId);
			if (deleteCategory) {
				logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse("Category Has Been Deleted Successfully!", true));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category Not Found!", false));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage() + " | Something Went Wrong!", false));
		}
	}

}
