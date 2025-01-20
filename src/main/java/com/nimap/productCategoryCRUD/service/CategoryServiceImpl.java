package com.nimap.productCategoryCRUD.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nimap.productCategoryCRUD.dto.CategoryDto;
import com.nimap.productCategoryCRUD.entity.CategoryEntity;
import com.nimap.productCategoryCRUD.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Page<CategoryDto> getCategoryPage(Pageable pageable) {
		
		return categoryRepository.findAll(pageable).map(CategoryEntity::getCategoryDto);
	}

	@Override
	public CategoryEntity createCategory(CategoryDto categoryDto) {
		
		CategoryEntity categoryEntity = new CategoryEntity() ;
		categoryEntity.setName(categoryDto.getName());
		categoryEntity.setDescription(categoryDto.getDescription());
		
		return categoryRepository.save(categoryEntity);
	}
	
	@Override
	public List<CategoryDto> getAllCategories(){
		
		return categoryRepository.findAll().stream().map(CategoryEntity::getCategoryDto).collect(Collectors.toList());		
	}

	@Override
	public CategoryDto getCategoryById(Long id) {
		
		Optional<CategoryEntity> optionalGetById = categoryRepository.findById(id);
		
		if(optionalGetById.isPresent()) {
			
			CategoryEntity categoryEntity = optionalGetById.get();
			
			return categoryEntity.getCategoryDto();
		}
		
		return null;
	}

	@Override
	public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
		
		Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryId);
		
		if(optionalCategory.isPresent()) {
			CategoryEntity categoryEntity = optionalCategory.get();
			
			categoryEntity.setName(categoryDto.getName());
			categoryEntity.setDescription(categoryDto.getDescription());

			CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
			CategoryDto updatedCategoryDto = new CategoryDto();
			
			updatedCategoryDto.setId(updatedCategory.getId());
			
			return updatedCategoryDto;
		}
		return null;
	}

	@Override
	public void deleteCategory(Long id) {
		Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
		
		if(optionalCategory.isEmpty())
			throw new IllegalArgumentException("Product with id:"+id+" not Found");
		
		categoryRepository.deleteById(id);
	}

}
