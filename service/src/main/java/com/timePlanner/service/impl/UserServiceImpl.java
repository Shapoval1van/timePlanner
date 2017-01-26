package com.timePlanner.service.impl;


import com.timePlanner.dao.UserDao;
import com.timePlanner.dto.*;
import com.timePlanner.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProjectService projectService;

    @Transactional(readOnly = true)
    public User getUserById(int id) throws EmptyResultException {
        try {
            return userDao.getUserById(id);
        } catch (EmptyResultDataAccessException up) {
            LOGGER.info("User with id " + id + "not found");
            throw new EmptyResultException(up);
        }
    }

    public User getUserByEmail(String email) throws EmptyResultException {
        try {
            return userDao.getUserByEmail(email);
        } catch (EmptyResultDataAccessException up) {
            LOGGER.info("User with email " + email + "not found");
            throw new EmptyResultException(up);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    @Transactional(readOnly = true)
    public User getUserWithDetailsByEmail(String email) {
        return userDao.getUserWithDetailsByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUserAdmin(User user) {
        Company company = user.getCompany();
        company.setDateCreation(new Date(System.currentTimeMillis()));
        int companyId = companyService.saveCompany(company);
        company.setId(companyId);
        user.setCompany(company);
        saveUser(user);
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

    @Transactional(readOnly = true)
    public List<User> getEmployeesForProject(int projectId) {
        return userDao.getEmployeesForProject(projectId);
    }


    /**
     *
     * @param email user email
     * @param projectId project id
     * @return id user with email if user have access to project, zero if does not have access
     */
    @Override
    public int checkAccessUserToProject(String email, int projectId) {
        int userId = 0;
        try {
            User user = getUserByEmail(email);
            List<Project> projectList = projectService.getProjectsForProjectManager(user.getId());
            for (Project i : projectList) {
                if (i.getId() == projectId) {
                    userId = user.getId();
                }
            }
        } catch (EmptyResultException e) {
            LOGGER.info("User with email: " + email + " not found", e);
        }
        return userId;
    }
}
