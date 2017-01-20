package com.timePlanner.dao.extractors;

import com.timePlanner.dao.mappers.*;
import com.timePlanner.dto.Customer;
import com.timePlanner.dto.Project;
import com.timePlanner.dto.Sprint;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProjectExtractor implements ResultSetExtractor<List<Project>> {
    @Override
    public List<Project> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Project> projectMap = new HashMap<>();
        SprintMapper sprintMapper = new SprintMapper();
        CompanyMapper companyMapper = new CompanyMapper();
        UserMapper userMapper = new UserMapper();
        CustomerMapper customerMapper = new CustomerMapper();
        ProjectMapper projectMapper = new ProjectMapper();
        while (resultSet.next()){
            int projectId = resultSet.getInt("projectId");
            Project project = projectMap.get(projectId);
            if(project == null){
                project = projectMapper.mapRow(resultSet, 0);
                project.setCompany(companyMapper.mapRow(resultSet,0));
                project.setProjectManager(userMapper.mapRow(resultSet,0));
                projectMap.put(projectId,project);
            }
            if(resultSet.getInt("sprintId")!=0){
                List<Sprint> sprints;
                Sprint sprint = sprintMapper.mapRow(resultSet, 0);
                sprints = project.getSprints();
                if(sprints!=null){
                    sprints.add(sprint);
                }else {
                    sprints = new ArrayList<>();
                    sprints.add(sprint);
                }
                project.setSprints(sprints);
            }if(resultSet.getInt("customerId")!=0){
                Set<Customer> customers;
                Customer customer = customerMapper.mapRow(resultSet, 0);
                customers = project.getCustomers();
                if(customers!=null){
                    customers.add(customer);
                }else {
                    customers = new HashSet<>();
                    customers.add(customer);
                }

                project.setCustomers(customers);
            }
        }
        return new ArrayList<>(projectMap.values());
    }
}
