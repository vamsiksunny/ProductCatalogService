package com.example.productcatalogservice.dto;

import lombok.Data;

@Data
public class FakeStoreProductDto {

    private Long id;
    private String title;
    private String description;
    private String image;
    private double price;
    private String category;

}
