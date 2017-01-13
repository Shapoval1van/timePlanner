package com.timePlanner.service.impl;


import com.timePlanner.dao.CompanyDao;
import com.timePlanner.dto.Company;
import com.timePlanner.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Transactional(readOnly = true)
    public Company getCompanyById(int id) {
        return companyDao.getCompanyById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCompany(Company company) {
        companyDao.saveCompany(company);
        //todo add create user cos company cant be without user;
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
}
