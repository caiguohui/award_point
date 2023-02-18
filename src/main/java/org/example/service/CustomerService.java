package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.Customer;
import org.example.exception.BizException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Customer Service
 */
@Slf4j
@Service
public class CustomerService {

    private Map<Long, Customer> customerMap;

    public CustomerService() {
        //TODO init customer test data
        log.warn("init customer test data!!!");
        customerMap = new HashMap<>();
        for (long customerId = 1; customerId <= 100; customerId++) {
            Customer customer = new Customer();
            customer.setCustomerId(customerId);
            customer.setCustomerName("name" + customerId);
            customer.setCreateTime(LocalDateTime.now());
            customerMap.put(customerId, customer);
        }
    }

    /**
     * get customer list
     *
     * @return
     */
    public Collection<Customer> getList() {
        return customerMap.values();
    }

    /**
     * get customer by customer id, when not found, throw BizException, code=500
     *
     * @param customerId
     * @return
     */
    public Customer getById(long customerId) {
        log.debug("getById --> customerId: {}", customerId);
        Customer customer = customerMap.get(customerId);
        if (customer == null) throw new BizException(500, "customer[" + customerId + "] information not found");
        log.debug("getById --> customerId: {}, result: {}", customerId, customer);
        return customer;
    }
}
