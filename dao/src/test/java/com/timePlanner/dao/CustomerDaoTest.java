package com.timePlanner.dao;

import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class , loader = AnnotationConfigContextLoader.class)
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    @Transactional(readOnly = true)
    public void getCustomersWithDetailsByCompanyIdTest(){
        List<Customer> customers = customerDao.getCustomersWithDetailsByCompanyId(1);
        assertEquals(1, customers.size());
        assertEquals("Petro Company", customers.get(0).getCompanyName());
        assertEquals("destroy", customers.get(0).getProject().getName());
        assertEquals("Petro", customers.get(0).getUser().getFirstName());
    }

}
