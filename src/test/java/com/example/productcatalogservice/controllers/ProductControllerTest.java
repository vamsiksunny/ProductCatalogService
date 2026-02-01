package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repo.ProductRepo;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    //@Autowired
    @MockBean
    private IProductService productService;
    @Autowired
    private ProductRepo productRepo;

    @Test
    public void TestGetProductById_WithValidId_ReturnsProductSuccessfully() {
        //Arrange
        Long productId = 2L;

        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");
        product.setPrice(100000D);
        when(productService.getProductById(productId))
                .thenReturn(product);

        //Act
        ResponseEntity<ProductDto> productDtoResponseEntity =
                productController.getProductById(productId);

        //Assert
        assertNotNull(productDtoResponseEntity);
        assertNotNull(productDtoResponseEntity.getBody());
        assertEquals(HttpStatus.OK,
                productDtoResponseEntity.getStatusCode());

        assertEquals(productId,productDtoResponseEntity.getBody().getId());
        assertEquals(100000D, productDtoResponseEntity.getBody().getPrice());
        assertEquals("Iphone", productDtoResponseEntity.getBody().getName());
        assertNull(productDtoResponseEntity.getBody().getCategoryDto());
        assertNull(productDtoResponseEntity.getBody().getDescription());
        verify(productService,times(1)).getProductById(productId);
    }

    @Test
    public void TestGetProductById_WithNegativeId_ThrowsIllegalArgumentException() {
        //Arrange
//        Long productId = -1L;
//
//        //Act and Assert
//        Exception exception = assertThrows(IllegalArgumentException.class,
//                ()-> productController.getProductById(productId));
//
//        assertEquals("Invalid Id passed",exception.getMessage());
//        verify(productService,times(0)).getProductById(productId);
    }

    @Test
    public void addRecordsToRDSInstance() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("vamsik");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("sunny");

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("thota");

        Category category = new Category();
        category.setId(1l);
        category.setName("thop");

        product1.setCategory(category);
        product2.setCategory(category);
        product3.setCategory(category);

        productRepo.save(product1);
        productRepo.save(product2);
        productRepo.save(product3);
    }

}