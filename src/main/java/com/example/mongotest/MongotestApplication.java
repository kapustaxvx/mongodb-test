package com.example.mongotest;

import com.example.mongotest.model.Customer;
import com.example.mongotest.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootApplication
public class MongotestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongotestApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CustomerRepository customerRepository,
                          MongoTemplate mongoTemplate) {
        return args -> {
//            customerRepository.deleteAll();
            Customer customer = new Customer("John", "Doe", "jd@gmail.com");
//            customerRepository.save(new Customer("John", "Doe", "jd@gmail.com"));
//            customerRepository.save(new Customer("Jane", "Doe","jd2023@gmail.com"));

            customerRepository.findAll().forEach(System.out::println);

            usingMongoTemplateAndQuery(customerRepository, mongoTemplate, customer);
            customerRepository.findCustomerByEmail("jd@gmail.com").ifPresentOrElse(
                    s -> {
                        System.out.println("Already exists");
                    }, () -> {
                        System.out.println("Inserting customer");
                        customerRepository.insert(customer);}
            );
        };
    }

    private static void usingMongoTemplateAndQuery(CustomerRepository customerRepository, MongoTemplate mongoTemplate, Customer customer) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is("jd@gmail.com"));
        List<Customer> customerList = mongoTemplate.find(query, Customer.class);
        if (customerList.size() > 1){
            throw new IllegalStateException("Multiple customers");
        }
        if (customerList.isEmpty()){
            System.out.println("Inserting customer");
            customerRepository.insert(customer);
        } else {
            System.out.println("Already exists");
        }
    }
}
