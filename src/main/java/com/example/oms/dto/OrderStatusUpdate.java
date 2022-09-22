package com.example.oms.dto;

import com.example.oms.enums.OrderLineStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusUpdate {
    String orderId; // refactor
    String orderLineId;
    OrderLineStatus orderLineStatus;
    String productId;
    int quantity;
}
