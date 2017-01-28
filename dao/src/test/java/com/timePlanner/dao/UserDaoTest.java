package com.timePlanner.dao;

import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Role;
import com.timePlanner.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class , loader = AnnotationConfigContextLoader.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional(readOnly = true)
    public void getUserByIdTest(){
        User user = userDao.getUserById(1);
        String password = user.getPassword();
        boolean result = (new BCryptPasswordEncoder()).matches("user1",password);
        assertTrue(result);
        assertEquals(1, user.getId());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllUsersTest(){
        List<User> users =  userDao.getAllUsers();
        assertEquals("Dart", users.get(0).getFirstName());
        assertEquals("Nerzul", users.get(1).getFirstName());
        assertEquals(7, users.size());
    }

    @Test
    @Transactional(readOnly = true)
    public void getUserByIdWithDetailsTest(){
        User user = userDao.getUserWithDetailsById(1);
        assertEquals(2, user.getTasks().size());
        assertEquals("The Death Star", user.getCompany().getName());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllUsersWithDetailsTest(){
        List<User> users = userDao.getALlUsersWithDetails();
        assertEquals(7, users.size());
        assertEquals("The Death Star", users.get(0).getCompany().getName());
        assertEquals("The Death Star", users.get(1).getCompany().getName());
        assertEquals("first Task", users.get(1).getTasks().iterator().next().getName());
    }

    @Test
    @Transactional(readOnly = true)
    public void getEmployeesForProject(){
        List<User> users = userDao.getEmployeesForProject(1);
        assertEquals(2, users.size());
        assertEquals("Nerzul", users.get(1).getFirstName());
        assertEquals(2, users.get(0).getTasks().size());
    }

    @Test
    @Transactional
    @Rollback()
    public void insertUserTest(){
        User user = new User();
        user.setFirstName("F Name");
        user.setLastName("L name");
        user.setPhone("5673452");
        user.setPassword("test");
        user.setEmail("test@gmail.com");
        user.setBirthDate(new Date(System.currentTimeMillis()-100));
        user.setRole(Role.EMPLOYEE);
        userDao.saveUser(user);
        List<User> users = userDao.getAllUsers();
        User userActual = users.get(users.size()-1);
        assertEquals(user.getFirstName(), userActual.getFirstName());
        assertEquals(user.getLastName(), userActual.getLastName());
        assertEquals(user.getPassword(), userActual.getPassword());
    }

    @Test
    @Transactional()
    @Rollback()
    public void updateUserTest(){
        User user = userDao.getUserById(1);
        user.setFirstName("F Name");
        userDao.updateUser(user);
        User userActual = userDao.getUserById(1);
        assertEquals(user.getFirstName(), userActual.getFirstName());
    }

    @Test
    @Transactional(readOnly = true)
    public void findUsersForCompanyTest(){
        assertEquals(4,userDao.getAllUsersForCompany(1).size());
    }

}
