package com.timePlanner.dao;

import com.timePlanner.dto.Customer;

import java.util.List;

public interface CustomerDao {
    public List<Customer> getCustomersWithDetailsByCompanyId(int companyId);
    public void saveCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public List<Customer> getAllCustomerWithDetails();
    public Customer getCustomerWithDetailsByUserEmail(String email);
}   
