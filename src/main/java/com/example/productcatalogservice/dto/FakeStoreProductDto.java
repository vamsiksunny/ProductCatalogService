package com.example.productcatalogservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FakeStoreProductDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String image;
    private double price;
    private String category;

}
