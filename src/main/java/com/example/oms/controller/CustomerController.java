package com.example.oms.controller;


import com.example.oms.dto.OrderRequest;
import com.example.oms.model.Customer;
import com.example.oms.model.Order;
import com.example.oms.model.Product;
import com.example.oms.repo.CustomerRepo;
import com.example.oms.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerServiceImpl custService;

    @PostMapping("/oms/add_customer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return custService.addCustomer(customer);
    }

    @GetMapping("/oms/customer_details/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") String customerId) {
        return custService.getCustomerData(customerId);
    }

    @PostMapping("/oms/place_order")
    public ResponseEntity placeOrder(@RequestBody OrderRequest order) throws JsonProcessingException , Exception {
        try {
            return new ResponseEntity<>(custService.placeOrder(order) , HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/oms/add_product")
    public Product addProduct(@RequestBody Product product) throws JsonProcessingException {
        return custService.addProduct(product);
    }

    @GetMapping("/oms/order_detail/{orderId}")
    public Order getOrder(@PathVariable("orderId") String orderId ){

        return custService.getOrderDetails(orderId);
    }
}
