package com.example.oms.dto;

import com.example.oms.enums.OrderState;
import com.example.oms.enums.PaymentInstrument;
import com.example.oms.enums.PaymentMode;
import com.example.oms.model.OrderLine;
import com.example.oms.model.OrderPayment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderRequest {

    String customerId;
    Map<String, Integer> products;  // key - product id, value  = qty
    PaymentInstrument paymentInstrument;
    PaymentMode paymentMode;

}
