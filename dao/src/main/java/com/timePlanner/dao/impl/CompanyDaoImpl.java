package com.timePlanner.dao.impl;

import com.timePlanner.dao.CompanyDao;
import com.timePlanner.dao.extractors.CompanyExtractor;
import com.timePlanner.dao.mappers.CompanyMapper;
import com.timePlanner.dto.Company;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoImpl  implements CompanyDao, InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(CompanyDaoImpl.class);

    private final String FIND_BY_ID = "SELECT id \"companyId\", name \"companyName\", date_creation, description \"companyDescription\" FROM company WHERE id  = ?";
    private final String SAVE_COMPANY = "INSERT INTO company VALUES (DEFAULT,?,?,?);";
    private final String FIND_All_COMPANY = "SELECT id \"companyId\", name \"companyName\", date_creation, description \"companyDescription\" FROM company";
    private final String UPDATE_COMPANY = "UPDATE company SET name=?, date_creation=?, description=? WHERE  id=?";
    private final String FIND_WITH_DETAILS ="SELECT " +
            "       c.id \"companyId\", c.name \"companyName\", c.description \"companyDescription\", c.date_creation,\n" +
            "       p.id \"projectId\", p.description \"projectDescription\", p.name \"projectName\", " +
            "           p.start_date \"projectSDate\",p.finish_date \"projectFDate\", p.is_finished \"projectIsFinished\"\n" +
            "   FROM company AS c LEFT JOIN project AS p ON c.id = p.company_id WHERE c.id = ?;";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(BasicDataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Company getCompanyById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id} ,new CompanyMapper());
    }

    @Override
    public void saveCompany(Company company) {
        jdbcTemplate.update(SAVE_COMPANY, company.getName(), company.getDateCreation(), company.getDescription());
    }

    @Override
    public void updateCompany(Company company) {
        jdbcTemplate.update(UPDATE_COMPANY, company.getName(),company.getDateCreation(), company.getDescription(), company.getId());
    }

    @Override
    public List<Company> getAllCompany() {
        return jdbcTemplate.query(FIND_All_COMPANY, new CompanyMapper());
    }

    @Override
    public Company getCompanyWithDetails(int id) {
        return jdbcTemplate.query(FIND_WITH_DETAILS, new Object[]{id}, new CompanyExtractor()).get(0);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null){
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }
}
