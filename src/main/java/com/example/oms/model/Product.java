package com.example.oms.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
//@Document
public class Product {

    @Id
    String productId;
    String productDesc;
    String productBrand;
    String productCategory;
    Integer inventoryAvaliable;
    double discount;
    double mrp;
}
