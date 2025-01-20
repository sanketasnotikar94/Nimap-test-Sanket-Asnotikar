package com.nimap.productCategoryCRUD.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nimap.productCategoryCRUD.dto.ProductDto;
import com.nimap.productCategoryCRUD.entity.ProductEntity;
import com.nimap.productCategoryCRUD.service.ProductService;


@RestController
@RequestMapping("api/product")
public class ProductController{
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getPage")
	public ResponseEntity<Page<ProductDto>> getCategoryPage(@PageableDefault(size=5, sort="id") Pageable pageable){
		
		Page<ProductDto> productPage  = productService.getProductPage(pageable);
		
		return ResponseEntity.ok(productPage);
	}
	
	@PostMapping("/create/{categoryId}")
	public ResponseEntity<ProductEntity> saveProduct(@PathVariable Long categoryId, @ModelAttribute ProductDto productDto){
		
		ProductEntity productEntity =  productService.saveProduct(categoryId, productDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productEntity);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		List<ProductDto> productDtoList = productService.getAllProducts();
		
		return ResponseEntity.ok(productDtoList);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		 productService.deleteProduct(id);
		 
		 return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
		ProductDto productDto = productService.getProductById(id);
		
		if(productDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(productDto);
	}
	
	@PutMapping("{categoryId}/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long categoryId, 
			@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException{
		
		ProductDto updatedProduct = productService.updateProduct(categoryId,productId,productDto);
		
		if(updatedProduct == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		
		return ResponseEntity.ok(updatedProduct);
		
	}

}
