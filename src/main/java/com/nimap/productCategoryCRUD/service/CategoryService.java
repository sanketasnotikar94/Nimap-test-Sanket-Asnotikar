package com.nimap.productCategoryCRUD.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nimap.productCategoryCRUD.dto.CategoryDto;
import com.nimap.productCategoryCRUD.entity.CategoryEntity;

public interface CategoryService {

	public CategoryEntity createCategory(CategoryDto categoryDto);

	public List<CategoryDto> getAllCategories();
	
	public CategoryDto getCategoryById(Long id);
	
	public CategoryDto updateCategory(Long categoryId, CategoryDto CategoryDto);
	
	void deleteCategory(Long id);

	public Page<CategoryDto> getCategoryPage(Pageable pageable);
}
