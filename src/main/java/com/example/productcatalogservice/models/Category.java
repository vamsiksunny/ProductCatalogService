package com.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Category extends BaseModel {

    private String name;

    private String description;

    @OneToMany(mappedBy = "category") // for JPA
    @JsonBackReference // for jackson while de-serializing
    private List<Product> products;

}
