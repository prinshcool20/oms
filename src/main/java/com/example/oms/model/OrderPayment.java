package com.example.oms.model;

import com.example.oms.enums.PaymentMode;
import com.example.oms.enums.PaymentOrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class OrderPayment {

    @Id
    String paymentID;
    PaymentOrderStatus paymentorderStatus;
    PaymentMode paymentMode;
}
