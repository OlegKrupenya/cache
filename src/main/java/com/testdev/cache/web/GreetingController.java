package com.testdev.cache.web;

import com.testdev.cache.dao.CustomerRepository;
import com.testdev.cache.domain.Customer;
import com.testdev.cache.domain.Greeting;
import com.testdev.cache.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private CustomerService customerService;

    @PersistenceContext
    private EntityManager em;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Optional<Customer> optionalCustomer = customerService.findById(1L);
        optionalCustomer.ifPresent(customer -> log.info(customer.toString()));

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}