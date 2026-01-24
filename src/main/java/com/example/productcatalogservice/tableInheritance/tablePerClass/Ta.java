package com.example.productcatalogservice.tableInheritance.tablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_ta")
public class Ta extends User {

    private Long numberOfHelpRequests;

}
