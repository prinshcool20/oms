package com.example.oms.model;


import com.example.oms.enums.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Document(collection = "customer")
public class Customer extends BaseCollection {

    @Id
    String custId;
    @NonNull
    String custName;
    String dob;
    Gender gender;
    String address;
    String email;
    @NonNull
    String phn;
//    List<Order> order;
}
