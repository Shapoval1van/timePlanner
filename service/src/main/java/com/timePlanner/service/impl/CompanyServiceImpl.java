package com.timePlanner.service.impl;


import com.timePlanner.dao.CompanyDao;
import com.timePlanner.dto.Company;
import com.timePlanner.service.CompanyService;
import com.timePlanner.service.EmptyResultException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LogManager.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDao companyDao;

    @Transactional(readOnly = true)
    public Company getCompanyById(int id) throws EmptyResultException {
        try{
            return companyDao.getCompanyById(id);
        }catch (EmptyResultDataAccessException up){
            LOGGER.info("Company with id " + id + "not found");
            throw new EmptyResultException(up);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveCompany(Company company) {
        return  companyDao.saveCompany(company);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCompany(Company company) {
        companyDao.updateCompany(company);
    }

    @Transactional(readOnly = true)
    public List<Company> getAllCompany() {
        return companyDao.getAllCompany();
    }

    @Transactional(readOnly = true)
    public Company getCompanyWithDetails(int id) {
        return companyDao.getCompanyWithDetails(id);
    }

    @Override
    public Company getCompanyByUserEmail(String email) {
        return  companyDao.getCompanyByUserEmail(email);
    }

    @Override
    public Company getCompanyByName(String name) throws EmptyResultException {
        try{
            return companyDao.getCompanyByName(name);
        }catch (EmptyResultDataAccessException up){
            LOGGER.info("Company with id " + name + "not found");
            throw new EmptyResultException(up);
        }
    }
}
