package com.timePlanner.dao.impl;

import com.timePlanner.dao.CustomerDao;
import com.timePlanner.dao.extractors.CustomerExtractor;
import com.timePlanner.dto.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao, InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(CustomerDaoImpl.class);

    private final String FIND_BY_COMPANY_ID = "SELECT c.id \"customerId\", c.company_name \"customerCompanyName\", c.description \"customerDescription\",\n" +
            "  u.id \"userId\", f_name, l_name, password, roleId, email, phone, birth_date, sex,\n" +
            "  p.id \"projectId\", p.name \"projectName\", p.description \"projectDescription\", p.start_date \"projectSDate\"," +
            "   p.finish_date \"projectFDate\", p.is_finished \"projectIsFinished\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\"\n" +
            "from customer as c\n" +
            "  JOIN users AS u ON c.user_id = u.id\n" +
            "  JOIN project AS p ON p.id = c.project_id\n" +
            "WHERE p.company_id = ?;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Customer> getCustomersWithDetailsByCompanyId(int companyId) {
        return jdbcTemplate.query(FIND_BY_COMPANY_ID, new Object[]{companyId}, new CustomerExtractor());
    }

    @Override
    public void saveCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public List<Customer> getAllCustomerWithDetails() {
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null) {
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }
}
