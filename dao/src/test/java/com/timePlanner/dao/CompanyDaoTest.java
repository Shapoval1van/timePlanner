package com.timePlanner.dao;

import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class , loader = AnnotationConfigContextLoader.class)
public class CompanyDaoTest {

    @Autowired
    private CompanyDao companyDao;


    @Test
    @Transactional(readOnly = true)
    public void getCompanyById(){
        Company company = companyDao.getCompanyById(1);
        assertEquals("The Death Star", company.getName());
        assertEquals("top secret", company.getDescription());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllCompanyTest(){
        List<Company> companyList = companyDao.getAllCompany();
        assertEquals(2, companyList.size());
        assertEquals("The Death Star", companyList.get(0).getName());
    }

    @Test
    @Transactional()
    @Rollback()
    public void saveCompanyTest(){
        Company company = new Company();
        company.setName("test");
        company.setDescription("desc");
        company.setDateCreation(new Date(System.currentTimeMillis()-50));
        companyDao.saveCompany(company);
        List<Company> companyList = companyDao.getAllCompany();
        Company actualCompany = companyList.get(companyList.size()-1);
        assertEquals(company.getName(), actualCompany.getName());
    }

    @Test
    @Transactional()
    @Rollback()
    public void updateCompanyTest(){
        Company company = new Company();
        company.setId(1);
        company.setName("test");
        company.setDescription("desc");
        company.setDateCreation(new Date(System.currentTimeMillis()-50));
        companyDao.updateCompany(company);
        Company actualCompany = companyDao.getCompanyById(1);
        assertEquals(company.getName(), actualCompany.getName());
    }
}
