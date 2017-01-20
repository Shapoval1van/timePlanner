package com.timePlanner.dao;

import com.timePlanner.dto.User;

import java.util.List;


public interface UserDao {
    public User getUserById(int id);
    public User getUserByEmail(String email);
    public void saveUser(User user);
    public void updateUser(User user);
    public User getUserWithDetailsById(int id);
    public User getUserWithDetailsByEmail(String email);
    public List<User> getAllUsers();
    public List<User> getALlUsersWithDetails();
    public List<User> getAllUsersForCompany(int companyId);
    public List<User> getEmployeesForProject(int projectId);
}
