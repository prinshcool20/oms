package com.example.oms.kafka;

import com.example.oms.dto.OrderStatusUpdate;
import com.example.oms.enums.OrderLineStatus;
import com.example.oms.model.Order;
import com.example.oms.model.OrderLine;
import com.example.oms.repo.OrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class KafkaListeners {


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepo orderRepo;

    @KafkaListener(topics = "SMS.OMS.order_status_update",groupId = "test" , containerFactory = "kafkaListenerContainerFactory")
    public void changeOrderStatus(String message) throws Exception {
        System.out.println(message);
        OrderStatusUpdate update = objectMapper.readValue(message, OrderStatusUpdate.class);


        // get from DB based on orderLineId
        Optional<Order> orderOP = orderRepo.findById(update.getOrderId());

        if(!orderOP.isPresent())
        {
            throw new Exception("invalid order id received\t" + update.getOrderId());
        }

        Order order = orderOP.get();


        for(OrderLine ol : order.getOrderline())
        {
            if(ol.getOrderLineId().equalsIgnoreCase(update.getOrderLineId()))
            {
                ol.setOrderLineStatus(update.getOrderLineStatus());

                if(update.getOrderLineStatus().equals(OrderLineStatus.SHIPPED)){


//                LocalDate myObj = LocalDate.now(); // Create a date object
//                System.out.println(myObj); // Display the current date
                    ol.setShippedDate(LocalDateTime.now());
                }

            }

        }
        // set the new status

        orderRepo.save(order);
        // save back in DB
    }
}
