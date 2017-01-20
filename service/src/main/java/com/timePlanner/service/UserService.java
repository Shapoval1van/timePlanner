package com.timePlanner.service;

import com.timePlanner.dto.User;

import java.util.List;

public interface UserService {
    public User getUserById(int id) throws EmptyResultException;
    public User getUserByEmail(String email) throws EmptyResultException;
    public void saveUser(User user);
    public void updateUser(User user);
    public User getUserWithDetailsById(int id);
    public User getUserWithDetailsByEmail(String email);
    public void createUserAdmin(User user);
    public List<User> getAllUsers();
    public List<User> getALlUsersWithDetails();
    public List<User> getAllUsersForCompany(int companyId);
    public List<User> getEmployeesForProject(int projectId);
}
