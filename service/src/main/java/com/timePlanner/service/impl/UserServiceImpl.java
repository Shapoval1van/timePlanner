package com.timePlanner.service.impl;


import com.timePlanner.dao.UserDao;
import com.timePlanner.dto.User;
import com.timePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional(readOnly = true)
    public User getUserWithDetailsById(int id) {
        return userDao.getUserWithDetailsById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    public List<User> getALlUsersWithDetails() {
        return userDao.getALlUsersWithDetails();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsersForCompany(int companyId) {
        return userDao.getAllUsersForCompany(companyId);
    }
}
