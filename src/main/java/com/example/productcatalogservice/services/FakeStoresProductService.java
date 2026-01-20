package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoresProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",
                FakeStoreProductDto.class,
                id);

        if (fakeStoreProductDtoResponseEntity.hasBody() &&
            fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return from(fakeStoreProductDtoResponseEntity.getBody());
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtosResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class);

        if (fakeStoreProductDtosResponseEntity.hasBody() &&
                fakeStoreProductDtosResponseEntity.getStatusCode().
                        equals(HttpStatusCode.valueOf(200))) {

            FakeStoreProductDto[] fakeStoreProductDtos =
                    fakeStoreProductDtosResponseEntity.getBody();
            for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
                Product product = from(fakeStoreProductDto);
                products.add(product);
            }
            return products;
        }

        return null;
    }

    @Override
    public Product createProduct(Product input) {
        return null;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());

        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());

        product.setCategory(category);
        return product;
    }
}
