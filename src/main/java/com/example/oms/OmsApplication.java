package com.example.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class OmsApplication {

	public static void main(String[] args) {

		SpringApplication.run(OmsApplication.class, args);
		System.out.println("Welcome to oms");

	}

}
