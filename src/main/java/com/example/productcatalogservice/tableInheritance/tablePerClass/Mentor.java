package com.example.productcatalogservice.tableInheritance.tablePerClass;

import jakarta.persistence.Entity;

@Entity(name="tpc_mentor")
public class Mentor extends User {
    private Double ratings;
}
