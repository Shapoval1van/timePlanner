package com.timePlanner.service;

import com.timePlanner.dto.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getCustomersWithDetailsByCompanyId(int id);
    public void saveCustomer(Customer customer);
    public Customer getCustomerWithDetailsByUserEmail(String email);
}
