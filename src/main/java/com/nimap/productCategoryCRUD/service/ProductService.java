package com.nimap.productCategoryCRUD.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nimap.productCategoryCRUD.dto.ProductDto;
import com.nimap.productCategoryCRUD.entity.ProductEntity;

public interface ProductService {

	ProductEntity saveProduct(Long categoryId, ProductDto productDto);
	
	List<ProductDto> getAllProducts();
	
	void deleteProduct(Long id);
	
	ProductDto getProductById(Long id);
	
	ProductDto updateProduct(Long categoryId, Long productId, ProductDto productDto) throws IOException;

	Page<ProductDto> getProductPage(Pageable pageable);
}
