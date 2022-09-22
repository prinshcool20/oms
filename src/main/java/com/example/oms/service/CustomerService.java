package com.example.oms.service;

import com.example.oms.dto.*;
import com.example.oms.model.Customer;
import com.example.oms.model.Order;
import com.example.oms.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer addCustomer(Customer customer);  // done
    Order placeOrder(OrderRequest orderRequest) throws Exception;
    String cancelOrderLine(OrderLineChangeReuqest orderCancelReuqest);
    String returnOrderLine(OrderLineChangeReuqest orderCancelReuqest);
    Order getOrderDetails(String orderId);
    Customer getCustomerData(String custId); // done
    void giveProductFeedback();
    Product addProduct(Product product) throws JsonProcessingException;
}
