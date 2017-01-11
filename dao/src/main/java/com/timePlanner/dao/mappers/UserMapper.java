package com.timePlanner.dao.mappers;


import com.timePlanner.dto.Role;
import com.timePlanner.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper  implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("userId"));
        user.setFirstName(resultSet.getString("f_name"));
        user.setLastName(resultSet.getString("l_name"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setRole(Role.values()[resultSet.getInt("roleid")-1]);
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setSex(resultSet.getInt("sex"));
        return user;
    }
}
