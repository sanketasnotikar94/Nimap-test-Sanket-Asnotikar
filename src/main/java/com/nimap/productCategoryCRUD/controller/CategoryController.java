package com.nimap.productCategoryCRUD.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.nimap.productCategoryCRUD.dto.CategoryDto;
import com.nimap.productCategoryCRUD.entity.CategoryEntity;
import com.nimap.productCategoryCRUD.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/getPage")
	public ResponseEntity<Page<CategoryDto>> getCategoryPage(@PageableDefault(size=5, sort="id") Pageable pageable){
		
		Page<CategoryDto> categoryPage  = categoryService.getCategoryPage(pageable);
		
		return ResponseEntity.ok(categoryPage);
	}

	@PostMapping("/create")
	public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryDto categoryDto){
		
		CategoryEntity categoryEntity = categoryService.createCategory(categoryDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryEntity);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		
		List<CategoryDto> allCategories = categoryService.getAllCategories();
		
		return ResponseEntity.ok(allCategories);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
		
		CategoryDto CategoryDto = categoryService.getCategoryById(id);
		
		if(CategoryDto == null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(CategoryDto);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
		
		CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
		
		if(updatedCategory == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		
		return ResponseEntity.ok(updatedCategory);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
		categoryService.deleteCategory(id);
		 
		 return ResponseEntity.noContent().build();
	}
}
