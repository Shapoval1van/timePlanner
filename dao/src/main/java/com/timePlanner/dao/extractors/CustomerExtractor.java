package com.timePlanner.dao.extractors;


import com.timePlanner.dao.mappers.CustomerMapper;
import com.timePlanner.dao.mappers.ProjectMapper;
import com.timePlanner.dao.mappers.UserMapper;
import com.timePlanner.dto.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerExtractor implements ResultSetExtractor<List<Customer>>{


    @Override
    public List<Customer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Customer> customerMap = new HashMap<>();
        UserMapper userMapper = new UserMapper();
        ProjectMapper projectMapper = new ProjectMapper();
        CustomerMapper customerMapper = new CustomerMapper();
        while (resultSet.next()){
            int customerId = resultSet.getInt("customerId");
            Customer customer = customerMap.get(customerId);
            if(customer == null){
                customer = customerMapper.mapRow(resultSet,0);
                if(resultSet.getInt("projectId")!=0){
                    customer.setProject(projectMapper.mapRow(resultSet,0));
                }
                if(resultSet.getInt("userId")!=0){
                    customer.setUser(userMapper.mapRow(resultSet,0));
                }
                customerMap.put(customerId, customer);
            }
        }
        return new ArrayList<>(customerMap.values());
    }
}
