package com.timePlanner.dao;

import com.timePlanner.dto.Company;

import java.util.List;

public interface CompanyDao {
    public Company getCompanyById(int id);
    public Company getCompanyByName(String name);
    public Company getCompanyByUserEmail (String email);
    public int saveCompany(Company company);
    public void updateCompany(Company company);
    public List<Company> getAllCompany();
    public Company getCompanyWithDetails(int id);
}
