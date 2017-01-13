package com.timePlanner.service;

import com.timePlanner.dto.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getCustomersWithDetailsByCompanyId(int id);
}
