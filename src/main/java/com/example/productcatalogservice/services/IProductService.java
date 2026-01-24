package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product createProduct(Product input);

    Product replaceProduct(Long id, Product input);

    boolean deleteProduct(Long id);

}
