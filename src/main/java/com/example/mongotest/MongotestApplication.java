package com.example.mongotest;

import com.example.mongotest.model.Customer;
import com.example.mongotest.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MongotestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongotestApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.deleteAll();
            customerRepository.save(new Customer("John", "Doe"));
            customerRepository.save(new Customer("Jane", "Doe"));

            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
