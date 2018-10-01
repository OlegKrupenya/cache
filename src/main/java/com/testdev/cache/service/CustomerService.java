package com.testdev.cache.service;

import com.testdev.cache.dao.CustomerRepository;
import com.testdev.cache.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Cacheable(value = "company.byId", key = "#id", unless = "#result != null and #result.lastName.toUpperCase().startsWith('TEST')")
    public Optional<Customer> findById(long id) {
        return customerRepository.findById(id);
    }

//    @Cacheable(value = "company.ByLastName", key = "#lastName", unless = "#lastName != null and #lastName.toUpperCase().startsWith('TEST')")
    public List<Customer> findByLastName(String name) {
        return customerRepository.findByLastName(name);
    }

    @Caching(evict = {@CacheEvict(value = "company.ByLastName", allEntries = true)},
            put = {@CachePut(value = "company.byId", key = "#result.id", unless = "#result != null and #result.lastName.toUpperCase().startsWith('TEST')")})
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }
}
