package com.timePlanner.service;

import com.timePlanner.dto.User;

import java.util.List;

public interface UserService {
    public User getUserById(int id);
    public void saveUser(User user);
    public void updateUser(User user);
    public User getUserWithDetailsById(int id);
    public List<User> getAllUsers();
    public List<User> getALlUsersWithDetails();
    public List<User> getAllUsersForCompany(int companyId);
}