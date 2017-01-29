package com.timePlanner.service.impl;


import com.timePlanner.dao.CustomerDao;
import com.timePlanner.dto.Customer;
import com.timePlanner.dto.User;
import com.timePlanner.service.CustomerService;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UserService userService;

    public List<Customer> getCustomersWithDetailsByCompanyId(int companyId) {
        return customerDao.getCustomersWithDetailsByCompanyId(companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCustomer(Customer customer) {
        userService.saveUser(customer.getUser());
        User user = null;
        try {
            user = userService.getUserByEmail(customer.getUser().getEmail());
        } catch (EmptyResultException e) {
            LOGGER.info("User with email " + customer.getUser().getEmail() + "not found",e);
        }
        customer.setUser(user);
        customerDao.saveCustomer(customer);
    }

    @Override
    public Customer getCustomerWithDetailsByUserEmail(String email) {
        return customerDao.getCustomerWithDetailsByUserEmail(email);
    }
}
