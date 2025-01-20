package com.nimap.productCategoryCRUD.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDto {
	
    private Long id;
    
    private String name;
    
    private String description;
    
    private Integer price;
    
    private MultipartFile image;
    
    private byte[] returnedImage;
    
    private Long categoryId;
    
    private String categoryName;
    
    
}
