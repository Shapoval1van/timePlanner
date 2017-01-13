package com.timePlanner.service.impl;


import com.timePlanner.dao.CustomerDao;
import com.timePlanner.dto.Customer;
import com.timePlanner.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getCustomersWithDetailsByCompanyId(int companyId) {
        return customerDao.getCustomersWithDetailsByCompanyId(companyId);
    }
}
