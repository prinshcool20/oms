package com.example.oms.service.impl;


import com.example.oms.dto.*;
import com.example.oms.enums.*;
import com.example.oms.model.*;
import com.example.oms.repo.CustomerRepo;
import com.example.oms.repo.OrderRepo;
import com.example.oms.repo.ProductRepo;
import com.example.oms.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepo custRepo;

    @Autowired
    ProductRepo prodRepo;
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Customer addCustomer(Customer customer) {
        return custRepo.save(customer);
    }

    @Override
    public Order placeOrder(OrderRequest orderRequest) throws Exception {

        Order o = new Order();

        OrderPayment op = new OrderPayment();
        op.setPaymentMode(orderRequest.getPaymentMode());

        // if online then order id paid
        if (orderRequest.getPaymentMode().equals(PaymentMode.POSTPAID))
            op.setPaymentorderStatus(PaymentOrderStatus.PENDING);
        else {
            op.setPaymentorderStatus(PaymentOrderStatus.SUCCESS);
        }
        op.setPaymentID(String.valueOf(UUID.randomUUID()));
        o.setOrderPayment(op);
        // order total amount
        double totalOrderAmount = 0;
        double orderLineAmount = 0;

        List<OrderLine> lines = new ArrayList<>();
        Map<String, Integer> pMap = orderRequest.getProducts();
        for (String pid : pMap.keySet()) {
            Optional<Product> p = prodRepo.findById(pid);// find by product from product table () prodRepo.findById(pid);
            if (!p.isPresent()) {
                throw new Exception("Invalid Product Id: "+ pid);
            }
            Product pObj = p.get();

            OrderLine ol = new OrderLine();
            ol.setOrderLineId(String.valueOf(UUID.randomUUID()));
            ol.setOrderLineStatus(OrderLineStatus.PLACED);
            int qty = pMap.get(pid);
            ol.setQuantity(qty);
            OrderLinePayment olp = new OrderLinePayment();
            olp.setPaymentInstrument(orderRequest.getPaymentInstrument());
            if (orderRequest.getPaymentMode().equals(PaymentMode.POSTPAID)) {
                olp.setStatus(PaymentStatus.COD);
            } else {
                olp.setStatus(PaymentStatus.PAID);
            }
            olp.setLinePaymentId(String.valueOf(UUID.randomUUID()));
            ol.setOrderLinePayment(olp);
            ol.setProduct(pObj);
            double disc = pObj.getDiscount(); // null
            ol.setDiscount(disc);
            double mrp = pObj.getMrp(); // null
            ol.setMrp(mrp);
            double discountAmount = (mrp * (disc / 100));
            orderLineAmount = qty * (mrp - discountAmount);
            ol.setSellingPrice(orderLineAmount);
            //ol.setProduct(orderRequest.getProductIds());
            totalOrderAmount = totalOrderAmount + orderLineAmount;
            //ol.setOrderLineId(ol.getOrderLineId());
            lines.add(ol);

        }
        o.setTotalOrderAmount(totalOrderAmount);
        o.setOrderline(lines);
        String c = orderRequest.getCustomerId();
        Optional<Customer> cop = custRepo.findById(c);
        if (!cop.isPresent()) {
            throw new Exception("invalid customer id:\t" + c);
        }
        o.setCustomerId(cop.get().getCustId());
        //o.setCustomerId(custRepo.findById(c));
        o.setState(OrderState.PLACED);
        Order saved = orderRepo.save(o);
        // if oder if placed then send update to sms/kafka

        // try to make this object mapper run
//        OrderStatusUpdate orderStatusUpdate = objectMapper.convertValue(saved.getOrderline().get(0),OrderStatusUpdate.class);
        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate();
        OrderLine saveOl = saved.getOrderline().get(0);
        orderStatusUpdate.setOrderLineId(saveOl.getOrderLineId());
        orderStatusUpdate.setQuantity(saveOl.getQuantity());
        orderStatusUpdate.setProductId(saveOl.getProduct().getProductId());
        orderStatusUpdate.setOrderLineStatus(saveOl.getOrderLineStatus());

        String payload = objectMapper.writeValueAsString(orderStatusUpdate);

        kafkaTemplate.send("OMS.SMS.order_status_update", payload);

        return saved;
    }

    @Override
    public String cancelOrderLine(OrderLineChangeReuqest orderCancelReuqest) {
        return null;
    }

    @Override
    public String returnOrderLine(OrderLineChangeReuqest orderCancelReuqest) {
        return null;
    }

    @Override
    public Order getOrderDetails(String orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        return null;
    }

    @Override
    public Customer getCustomerData(String customerId) {
        Optional<Customer> cop = custRepo.findById(customerId);
        if (cop.isPresent()) {
            return cop.get();
        }
        return null;
    }

    @Override
    public void giveProductFeedback() {

    }

    @Override
    public Product addProduct(Product product) throws JsonProcessingException {
        Product p = prodRepo.save(product);

//        String payload = objectMapper.writeValueAsString(p);
//
//        kafkaTemplate.send("OMS.SMS.order_status_update" , payload);
        return p;
    }
}
