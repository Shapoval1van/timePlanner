package com.timePlanner.dao.extractors;


import com.timePlanner.dao.mappers.CompanyMapper;
import com.timePlanner.dao.mappers.TaskMapper;
import com.timePlanner.dto.Company;
import com.timePlanner.dto.Role;
import com.timePlanner.dto.Task;
import com.timePlanner.dto.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, User> userMap = new HashMap<>();
        CompanyMapper companyMapper = new CompanyMapper();
        TaskMapper taskMapper = new TaskMapper();
        User user = null;
        Set<Task> tasks = new HashSet<>();
        while(resultSet.next()){
            int userId = resultSet.getInt("userId");
            user = userMap.get(userId);
            if(user == null){
                tasks = new HashSet<>();
                user = new User();
                user.setId(userId);
                user.setFirstName(resultSet.getString("f_name"));
                user.setLastName(resultSet.getString("l_name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(Role.values()[resultSet.getInt("roleid")-1]);
                user.setBirthDate(resultSet.getDate("birth_date"));
                user.setSex(resultSet.getInt("sex"));
                Company company = companyMapper.mapRow(resultSet,0);
                user.setCompany(company);
                userMap.put(userId, user);
            }
            if(resultSet.getInt("taskId")!=0){
                Task task = taskMapper.mapRow(resultSet, 0);
                tasks.add(task);
                user.setTasks(tasks);
            }
        }
        return new ArrayList<User>(userMap.values());
    }
}
