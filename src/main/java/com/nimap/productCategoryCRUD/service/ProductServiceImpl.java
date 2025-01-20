package com.nimap.productCategoryCRUD.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nimap.productCategoryCRUD.dto.ProductDto;
import com.nimap.productCategoryCRUD.entity.CategoryEntity;
import com.nimap.productCategoryCRUD.entity.ProductEntity;
import com.nimap.productCategoryCRUD.repository.CategoryRepository;
import com.nimap.productCategoryCRUD.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Page<ProductDto> getProductPage(Pageable pageable) {
		
		return productRepository.findAll(pageable).map(ProductEntity::getProductDto);
	}
	
	@Override
	public ProductEntity saveProduct(Long categoryId, ProductDto productDto) {
		
		Optional<CategoryEntity> optionCategory = categoryRepository.findById(categoryId);
		
		if(optionCategory.isPresent()) {
			
			ProductEntity productEntity = new ProductEntity();
			productEntity.setName(productDto.getName());
			productEntity.setPrice(productDto.getPrice());
			productEntity.setDescription(productDto.getDescription());
			try {
				productEntity.setImage(productDto.getImage().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			productEntity.setCategoryEntity(optionCategory.get());
			
			return productRepository.save(productEntity);
		}
		
		return null;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		
		return productRepository.findAll().stream().map(ProductEntity::getProductDto).collect(Collectors.toList());
	}

	@Override
	public void deleteProduct(Long id) {
		Optional<ProductEntity> optionalProduct = productRepository.findById(id);
		
		if(optionalProduct.isEmpty())
			throw new IllegalArgumentException("Product with id:"+id+" not Found");
		
		productRepository.deleteById(id);
	}

	@Override
	public ProductDto getProductById(Long id) {
		
		Optional<ProductEntity> optionalProduct = productRepository.findById(id);
		
		if(optionalProduct.isPresent()) {
			ProductEntity productEntity = optionalProduct.get();
			
			return productEntity.getProductDto();
		}
			return null;
	}

	@Override
	public ProductDto updateProduct(Long categoryId, Long productId, ProductDto productDto) throws IOException {
		
		Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryId);
		
		Optional<ProductEntity>  optionalProduct = productRepository.findById(productId);
		
		if(optionalCategory.isPresent() && optionalProduct.isPresent()) {
			
			ProductEntity productEntity = optionalProduct.get();
			
			productEntity.setName(productDto.getName());
			productEntity.setDescription(productDto.getDescription());
			productEntity.setPrice(productDto.getPrice());
			
			productEntity.setCategoryEntity(optionalCategory.get());
			
			if(productDto.getImage() != null){
				productEntity.setImage(productDto.getImage().getBytes());
				
				ProductEntity updatedProduct = productRepository.save(productEntity);
				ProductDto updatedProductDto = new ProductDto();
				updatedProductDto.setId(updatedProduct.getId());
				
				return updatedProductDto;
			}

		} 
		
		return null;
	}

}


