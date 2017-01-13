package com.timePlanner.dao.mappers;


import com.timePlanner.dto.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer>{
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("customerId"));
        customer.setCompanyName(resultSet.getString("customerCompanyName"));
        customer.setDescription(resultSet.getString("customerDescription"));
        return customer;
    }
}
