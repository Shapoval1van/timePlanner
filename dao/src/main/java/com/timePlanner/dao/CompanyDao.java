package com.timePlanner.dao;

import com.timePlanner.dto.Company;

import java.util.List;

public interface CompanyDao {
    public Company getCompanyById(int id);
    public void saveCompany(Company company);
    public void updateCompany(Company company);
    public List<Company> getAllCompany();
    public Company getCompanyWithDetails(int id);
}
