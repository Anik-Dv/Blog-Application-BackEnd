package com.anikdv.blog.app.payloads;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is a category DTO of category entity
 *
 * @author AnikDV
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto implements Serializable {

	private static final long serialVersionUID = -7893607740507662277L;

	private Integer categoryId;
	@NotBlank
	@Size(min = 4, max = 30, message = "Category has must be minimum 4 chars and maximum 30 chars!")
	private String categoryTitle;
}
