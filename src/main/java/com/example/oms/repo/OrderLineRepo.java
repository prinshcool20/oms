package com.example.oms.repo;

import com.example.oms.model.OrderLine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderLineRepo extends MongoRepository<OrderLine, String> {
}
