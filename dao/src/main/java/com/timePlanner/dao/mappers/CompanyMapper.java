package com.timePlanner.dao.mappers;

import com.timePlanner.dto.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMapper implements RowMapper<Company>{
    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getInt("companyId"));
        company.setName(resultSet.getString("companyName"));
        company.setDateCreation(resultSet.getDate("date_creation"));
        company.setDescription(resultSet.getString("companyDescription"));
        return company;
    }
}
