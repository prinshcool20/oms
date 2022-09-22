package com.example.oms.repo;

import com.example.oms.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepo extends MongoRepository<Customer,String> {


}
