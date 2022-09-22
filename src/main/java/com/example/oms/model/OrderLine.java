package com.example.oms.model;

import com.example.oms.enums.OrderLineStatus;
import com.example.oms.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Document
//@Data
public class OrderLine {

//    @Id
    String orderLineId;
    OrderLineStatus orderLineStatus;
    Product product;
    int quantity;
    double sellingPrice;
    double mrp;
    double discount;
    PaymentStatus paymentstatus;
    LocalDateTime deliveredDate;
    LocalDateTime shippedDate;
    OrderLinePayment orderLinePayment;
}
