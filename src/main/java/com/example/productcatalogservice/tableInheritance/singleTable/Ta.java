package com.example.productcatalogservice.tableInheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_ta")
@DiscriminatorValue(value = "TA")
public class Ta extends User {

    private Long numberOfHelpRequests;

}
