package com.example.oms.model;


import com.example.oms.enums.OrderState;
import com.example.oms.enums.PaymentMode;
import com.example.oms.enums.PaymentOrderStatus;
//import com.example.oms.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
//@Document
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Order extends BaseCollection{

    @Id
    String orderId;
    String customerId;
    OrderState state;
    OrderPayment orderPayment;
    double totalOrderAmount;
    LocalDateTime orderCompletedDate;
    List<OrderLine> orderline;
}
