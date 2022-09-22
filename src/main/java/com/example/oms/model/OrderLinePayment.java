package com.example.oms.model;

import com.example.oms.enums.PaymentInstrument;
import com.example.oms.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLinePayment {

//    @Id
    String linePaymentId;
    PaymentInstrument paymentInstrument;
    PaymentStatus status;
}
