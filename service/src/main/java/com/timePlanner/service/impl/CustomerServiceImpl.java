package com.timePlanner.service.impl;


import com.timePlanner.dao.CustomerDao;
import com.timePlanner.dto.Customer;
import com.timePlanner.service.CustomerService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getCustomersWithDetailsByCompanyId(int companyId) {
        return customerDao.getCustomersWithDetailsByCompanyId(companyId);
    }
}
