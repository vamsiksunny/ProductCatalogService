package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dto.CategoryDto;
import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.exceptions.ProductNotFoundException;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
//    @Qualifier("storageProductService")
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product>products = productService.getAllProducts();
        if (products != null) {
            for(Product product : products) {
                ProductDto productDto = from(product);
                productDtos.add(productDto);
            }
            return productDtos;
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        if (productId < 1) {
//            throw new IllegalArgumentException("Please pass product id > 0");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            throw new ProductNotFoundException("product not found");
        }
        ProductDto productDto = from(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createProduct(from(productDto));
        if (product != null) {
            return productDto;
        }
        throw new RuntimeException("creation failed as product existed already");
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable Long id) {
        boolean result = productService.deleteProduct(id);
        if (!result) {
            throw new ProductNotFoundException("product not found");
        }
    }

    @PutMapping("/{productId}")
    ProductDto replaceProduct(@PathVariable Long productId,
                              @RequestBody ProductDto productDto) {
        Product response = productService.replaceProduct(productId, from(productDto));

        if (response != null) {
            return from(response);
        }
        throw new ProductNotFoundException("product not found");
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        if (productDto.getCategoryDto() != null) {
            Category category = new Category();
            category.setId(productDto.getCategoryDto().getId());
            category.setDescription(productDto.getDescription());
            category.setName(productDto.getName());

            product.setCategory(category);
        }
        return product;
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());

            productDto.setCategoryDto(categoryDto);
        }
        return productDto;
    }

}
